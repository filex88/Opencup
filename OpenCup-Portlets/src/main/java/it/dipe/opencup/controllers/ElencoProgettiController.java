package it.dipe.opencup.controllers;

import it.dipe.opencup.controllers.common.FiltriCommonController;
import it.dipe.opencup.dto.DescrizioneValore;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.dto.NavigaProgetti;
import it.dipe.opencup.facade.AggregataFacade;
import it.dipe.opencup.facade.ProgettoFacade;
import it.dipe.opencup.model.AnnoDecisione;
import it.dipe.opencup.model.AreaGeografica;
import it.dipe.opencup.model.AreaIntervento;
import it.dipe.opencup.model.AreaSoggetto;
import it.dipe.opencup.model.CategoriaIntervento;
import it.dipe.opencup.model.CategoriaSoggetto;
import it.dipe.opencup.model.Comune;
import it.dipe.opencup.model.Progetto;
import it.dipe.opencup.model.Provincia;
import it.dipe.opencup.model.Regione;
import it.dipe.opencup.model.SottocategoriaSoggetto;
import it.dipe.opencup.model.SottosettoreIntervento;
import it.dipe.opencup.model.StatoProgetto;
import it.dipe.opencup.model.TipologiaIntervento;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLFactoryUtil;

@Controller
@RequestMapping("VIEW")
@SessionAttributes({"navigaProgetti"})
public class ElencoProgettiController extends FiltriCommonController {
	
	@Value("#{config['paginazione.risultatiPerPagina']}")
	private int maxResult;
	
	@Autowired
	private ProgettoFacade progettoFacade;
	
	@Autowired
	private AggregataFacade aggregataFacade;
	
	@ModelAttribute("navigaProgetti")
	public NavigaProgetti modelAttrNavigaProgetti() {
		return new NavigaProgetti();
	}
	
	@RenderMapping
	public String handleRenderRequest(	RenderRequest renderRequest, 
										RenderResponse renderResponse,
										Model model, 
										@ModelAttribute("navigaProgetti") NavigaProgetti navigaProgetti){	
		
//		HttpSession session = PortalUtil.getHttpServletRequest(renderRequest).getSession(false);
//		NavigaAggregata navigaAggregata = (NavigaAggregata) session.getAttribute("navigaAggregata"); 
//		
//		if( navigaAggregata.isImportaInNavigaProgetti() ){
//			navigaProgetti.importa( navigaAggregata );
//			navigaAggregata.setImportaInNavigaProgetti(false);
//			session.setAttribute("navigaAggregata", navigaAggregata); 
//		}
		
		HttpServletRequest httpServletRequest = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest));
		String jsonnavigaaggregata=httpServletRequest.getParameter("jsonnavigaaggregata")!=null?httpServletRequest.getParameter("jsonnavigaaggregata").toString():"";
		if(!StringUtils.isEmpty(jsonnavigaaggregata)){
			NavigaAggregata navigaAggregata = createModelFromJsonString(jsonnavigaaggregata);
			navigaProgetti.importa( navigaAggregata );
			navigaAggregata.setImportaInNavigaProgetti(false);
		}
		
		// LISTA PROGETTI //
		
		//orderByCol is the column name passed in the request while sorting
		String orderByCol = ParamUtil.getString(renderRequest, "orderByCol"); 
		if(Validator.isNull(orderByCol)  || Validator.equals("", orderByCol)){
			orderByCol = "impoCostoProgetto";
		}
		
		//orderByType is passed in the request while sorting. It can be either asc or desc
		String orderByType = ParamUtil.getString(renderRequest, "orderByType");
		if(Validator.isNull(orderByType)  || Validator.equals("", orderByType)){
		    orderByType = "asc";
		}
		
		//delta
		String sDelta = ParamUtil.getString(renderRequest, "delta");
		int delta = maxResult;
		if( ! ( Validator.isNull(sDelta) || Validator.equals("", sDelta) ) ){
		    delta = Integer.parseInt(sDelta);
		}

		SearchContainer<Progetto> searchContainerElenco = new SearchContainer<Progetto>(renderRequest, renderResponse.createRenderURL(), null, "Nessun dato trovato per la selezione fatta");
		searchContainerElenco.setDelta(delta);
		
		searchContainerElenco.setOrderByCol(orderByCol);
		searchContainerElenco.setOrderByType(orderByType);
		int size =  progettoFacade.sizeElencoProgetti( navigaProgetti ).getSize();

		searchContainerElenco.setTotal(size);
		
		List<Progetto> elencoProgetti = progettoFacade.findElencoProgetti(	navigaProgetti, 
																			searchContainerElenco.getOrderByCol(), 
																			searchContainerElenco.getOrderByType(),
																			searchContainerElenco.getStart(), 
																			searchContainerElenco.getEnd());	

		//elencoProgetti = ListUtil.subList(elencoProgetti, searchContainerElenco.getStart(), searchContainerElenco.getEnd() );       
		
		searchContainerElenco.setResults(elencoProgetti);
		
		model.addAttribute("searchContainerElenco", searchContainerElenco);
		
		model.addAttribute("pagDettaglioProgetto", navigaProgetti.getPagDettaglioProgetto());
		
		// FINE LISTA PROGETTI //
		
		// MASCHERA RICERCA PROGETTI //
		initInModelMascheraRicerca(model, navigaProgetti);
		
		model.addAttribute("navigaProgetti", navigaProgetti);
		// FINE RICERCA PROGETTI //
		
		
		// RIEPILOGO //
		SearchContainer<DescrizioneValore> searchContainerRiepilogo = new SearchContainer<DescrizioneValore>(renderRequest, renderResponse.createRenderURL(), null, "Nessun dato trovato per la selezione fatta");
		searchContainerRiepilogo.setDelta(maxResult);
		searchContainerRiepilogo.setTotal(3);
		
		Double impoCostoProgetti = 0.0;
		Double impoImportoFinanziato = 0.0;
		
		for(Progetto aggregataDTO : elencoProgetti){
			impoCostoProgetti = impoCostoProgetti + aggregataDTO.getImpoCostoProgetto();
			impoImportoFinanziato = impoImportoFinanziato + aggregataDTO.getImpoImportoFinanziato();
		}
		
		List<DescrizioneValore> retval = new ArrayList<DescrizioneValore>();
		retval.add(new DescrizioneValore("VOLUME DEI PROGETTI", size));
		retval.add(new DescrizioneValore("COSTO DEI PROGETTI", impoCostoProgetti));
		retval.add(new DescrizioneValore("IMPORTO FINANZIAMENTI", impoImportoFinanziato));
		
		searchContainerRiepilogo.setResults(retval);
		model.addAttribute("searchContainerRiepilogo", searchContainerRiepilogo);
		// FINE RIEPILOGO //
		
		return "elenco-progetti-view";
	}
	
	protected NavigaAggregata createModelFromJsonString(String jsonString){
		ObjectMapper mapper= new ObjectMapper();
		NavigaAggregata model=null;
		try {
			model = mapper.readValue(jsonString, NavigaAggregata.class);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return model;
	}
	
	@ActionMapping(params="action=dettaglio")
	public void actionDettaglio(	ActionRequest aRequest, 
									ActionResponse aResponse, 
									Model model, 
									@ModelAttribute("navigaProgetti") NavigaProgetti navigaProgetti, 
									@RequestParam(value = "idProgettoDettaglio") String idProgettoDettaglio){
		/*
		NavigaProgetti navigaProgetti = new NavigaProgetti();
		
		navigaProgetti.setIdProgetto(idProgettoDettaglio);
		navigaProgetti.setPagElencoProgetti(pagDettaglioProgetto);
        
        QName eventName = new QName( "http:eventDettaglioProgetto/events", "event.dettaglioProgetto");
        aResponse.setEvent(eventName, navigaProgetti);
		*/

		
		navigaProgetti.setIdProgetto( idProgettoDettaglio );
		
//		HttpSession session = PortalUtil.getHttpServletRequest(aRequest).getSession(false);
//		session.setAttribute("navigaProgetti", navigaProgetti);
		
		LiferayPortletURL renderURL = createLiferayPortletURL(aRequest, navigaProgetti.getPagDettaglioProgetto());
		
		String jsonnavigaprogetti = createJsonStringFromModelAttribute( navigaProgetti );
		
		try {
			aResponse.sendRedirect( renderURL.toString()  + "&jsonnavigaprogetti="+jsonnavigaprogetti  );
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	protected String createJsonStringFromModelAttribute(NavigaProgetti filtro){
		ObjectMapper mapper= new ObjectMapper();

		String jsonString=null;
		try {
			jsonString = mapper.writeValueAsString(filtro);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return jsonString;
	}
	
	@ActionMapping(params="action=ricerca")
	public void actionRicerca(	ActionRequest aRequest, 
								ActionResponse aResponse, 
								Model model, 
								@ModelAttribute("navigaProgetti") NavigaProgetti navigaProgetti){
		
		model.addAttribute("navigaProgetti", navigaProgetti);
	}
	
	private void initInModelMascheraRicerca(Model model, NavigaProgetti filtro) {
		
		//Carico la lista delle regioni
		List<AreaGeografica> listAreaGeografica = aggregataFacade.findAreaGeografica();
		model.addAttribute("listAreaGeografica", listAreaGeografica);
		
		if( (! "-1".equals( filtro.getIdAreaGeografica() )) && (! "0".equals( filtro.getIdAreaGeografica() )) ){
			//Regione selezionata carico le Province
			List<Regione> listRegione = aggregataFacade.findRegioniByIdAreaGeografica(Integer.valueOf( filtro.getIdAreaGeografica() ));
			model.addAttribute("listRegione", listRegione);
		}
		
		if( (! "-1".equals( filtro.getIdRegione() )) && (! "0".equals( filtro.getIdRegione() )) ){
			//Regione selezionata carico le Province
			List<Provincia> listProvincia = aggregataFacade.findProvinciaByIdRegione(Integer.valueOf( filtro.getIdRegione() ));
			model.addAttribute("listProvincia", listProvincia);
		}
		
		if( (! "-1".equals( filtro.getIdProvincia() )) && (! "0".equals( filtro.getIdProvincia() )) ){
			//Provincia selezionata carico i Comuni
			List<Comune> listComune = aggregataFacade.findComuneByIdProvincia(Integer.valueOf( filtro.getIdProvincia() ));
			model.addAttribute("listComune", listComune);
		}
		
		//Carico la lista degli Anni Decisione
		List<AnnoDecisione> listaAnnoDecisione = aggregataFacade.findAnniDecisione();
		model.addAttribute("listaAnnoDecisione", listaAnnoDecisione);
		
		//Carico la lista delle Tipologia Intervento
		List<TipologiaIntervento> listaTipologiaIntervento = aggregataFacade.findTipologiaIntervento();
		model.addAttribute("listaTipologiaIntervento", listaTipologiaIntervento);
		
		//Carico la lista degli Stato Progetto
		List<StatoProgetto> listaStatoProgetto = aggregataFacade.findStatoProgetto();
		model.addAttribute("listaStatoProgetto", listaStatoProgetto);
		
		
		//Carico la lista della Aree Soggetto
		List<AreaSoggetto> listAreaSoggetto = aggregataFacade.findAreaSoggetto();
		model.addAttribute("listAreaSoggetto", listAreaSoggetto);
		
		if( (! "-1".equals( filtro.getIdAreaSoggetto() )) && (! "0".equals( filtro.getIdAreaSoggetto() )) ){
			//Carico la lista della Categoria Soggetto
			List<CategoriaSoggetto> listCategoriaSoggetto = aggregataFacade.findCategoriaSoggettoByIdAreaSoggetto(Integer.valueOf( filtro.getIdAreaSoggetto() ));
			model.addAttribute("listCategoriaSoggetto", listCategoriaSoggetto);
		}
	
		if( (! "-1".equals( filtro.getIdCategoriaSoggetto() )) && (! "0".equals( filtro.getIdCategoriaSoggetto() )) ){
			//Carico la lista della Sottocategoria Soggetto
			List<SottocategoriaSoggetto> listSottoCategoriaSoggetto = aggregataFacade.findSottocategoriaSoggetto(Integer.valueOf( filtro.getIdCategoriaSoggetto() ));
			model.addAttribute("listSottoCategoriaSoggetto", listSottoCategoriaSoggetto);
		}
		
		//Carico le Aree d'intervednto
		List<AreaIntervento> listAreaIntervento = aggregataFacade.findAreaIntervento();
		model.addAttribute("listAreaIntervento", listAreaIntervento);
		
		if( (! "-1".equals( filtro.getIdAreaIntervento() )) && (! "0".equals( filtro.getIdAreaIntervento() )) ){
			//Settore intervento selezionata carico i sottosettori
			List<SottosettoreIntervento> listSottosettoreIntervento = aggregataFacade.findSottosettoreByArea(Integer.valueOf( filtro.getIdAreaIntervento() ));
			model.addAttribute("listSottosettoreIntervento", listSottosettoreIntervento);
		}
		
		if( 
				((! "-1".equals( filtro.getIdAreaIntervento() )) && (! "-1".equals( filtro.getIdSottosettoreIntervento() )))
				&&
				((! "0".equals( filtro.getIdAreaIntervento() )) && (! "0".equals( filtro.getIdSottosettoreIntervento() )))
				){
			//Settore intervento e sottosettore intervento selezionati carico le categorie
			List<CategoriaIntervento> listaCategoriaIntervento = aggregataFacade.findCategoriaInterventoByAreaSottosettore(Integer.valueOf( filtro.getIdAreaIntervento() ), Integer.valueOf( filtro.getIdSottosettoreIntervento() ));
			model.addAttribute("listaCategoriaIntervento", listaCategoriaIntervento);
		}
		
	}
	
	private LiferayPortletURL createLiferayPortletURL(PortletRequest request, String toPage) {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		String portletId = (String) request.getAttribute(WebKeys.PORTLET_ID);
		LiferayPortletURL renderURL = null;
		String localHost = themeDisplay.getPortalURL();		
		List<Layout> layouts = null;
		try{
			layouts = LayoutLocalServiceUtil.getLayouts(themeDisplay.getLayout().getGroupId(), false);
			
			for(Layout layout : layouts){

				String nodeNameRemoved = PortalUtil.getLayoutFriendlyURL(layout, themeDisplay).replace(localHost, "");
				
				//Viene ricercato l'URL esatto per la pagina successiva
				if(nodeNameRemoved.indexOf(toPage)>0){
					
					renderURL = PortletURLFactoryUtil.create(request, portletId, layout.getPlid(), PortletRequest.ACTION_PHASE);
					renderURL.setWindowState(WindowState.NORMAL);
					renderURL.setPortletMode(PortletMode.VIEW);
					
					break;
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return renderURL;
	}
	
}
