package it.dipe.opencup.controllers;

import it.dipe.opencup.controllers.common.FiltriCommonController;
import it.dipe.opencup.dto.AggregataDTO;
import it.dipe.opencup.dto.D3PieConverter;
import it.dipe.opencup.dto.DescrizioneValore;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.facade.AggregataFacade;
import it.dipe.opencup.facade.ProgettoFacade;
import it.dipe.opencup.model.AnnoAggregato;
import it.dipe.opencup.model.AreaGeografica;
import it.dipe.opencup.model.AreaIntervento;
import it.dipe.opencup.model.AreaSoggetto;
import it.dipe.opencup.model.CategoriaIntervento;
import it.dipe.opencup.model.CategoriaSoggetto;
import it.dipe.opencup.model.Comune;
import it.dipe.opencup.model.Provincia;
import it.dipe.opencup.model.Regione;
import it.dipe.opencup.model.SottocategoriaSoggetto;
import it.dipe.opencup.model.SottosettoreIntervento;
import it.dipe.opencup.model.StatoProgetto;
import it.dipe.opencup.model.TipologiaIntervento;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.WindowState;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.ListUtil;
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
@SessionAttributes({"navigaAggregata"})
public class ClassificazionePortlet1Controller extends FiltriCommonController {
	
	@Value("#{config['paginazione.risultatiPerPagina']}")
	private int maxResult;
	
	@Autowired
	private ProgettoFacade progettoFacade;
	
	@Autowired
	private AggregataFacade aggregataFacade;
	
	@Value("#{config['codice.natura.open.cup']}")
	private String codiNaturaOpenCUP;

	//Array per la personalizzazione della voce di navigazione
	private final String[] navigaPer = {"Area Intervento", "Sottosettore", "Categoria Intervento"};
	
	@ModelAttribute("navigaAggregata")
	public NavigaAggregata navigaAggregata() {
		
		String idNatura =  (aggregataFacade.findNaturaByCod( codiNaturaOpenCUP )==null)?"0":aggregataFacade.findNaturaByCod( codiNaturaOpenCUP ).getId().toString();
		NavigaAggregata navigaAggregata = new NavigaAggregata(NavigaAggregata.NAVIGA_CLASSIFICAZIONE, idNatura);
		
		return navigaAggregata;
	}
	
	@ActionMapping(params="action=ricerca")
	public void actionRicerca(	ActionRequest aRequest, 
								ActionResponse aResponse, 
								Model model, 
								@ModelAttribute("navigaAggregata") NavigaAggregata navigaAggregata){
		
		model.addAttribute("navigaAggregata", navigaAggregata);
		
		setNavigaAggregataInSession(aRequest, navigaAggregata);
	}
	
	@ActionMapping(params="action=affinaricerca")
	public void actionAffinaRicerca(	ActionRequest aRequest, 
								ActionResponse aResponse, 
								Model model, 
								@ModelAttribute("navigaAggregata") NavigaAggregata navigaAggregata){
		
		model.addAttribute("navigaAggregata", navigaAggregata);
		
		setNavigaAggregataInSession(aRequest, navigaAggregata);
	}
	
	@ActionMapping(params="action=navigazione")
	public void actionNavigazione(	ActionRequest aRequest, 
									ActionResponse aResponse, 
									Model model, 
									@ModelAttribute("navigaAggregata") NavigaAggregata navigaAggregata){
		
		navigaAggregata.setIdNatura(ParamUtil.getString(aRequest, "rowIdLiv1"));
		navigaAggregata.setIdAreaIntervento(ParamUtil.getString(aRequest, "rowIdLiv2"));
		navigaAggregata.setIdSottosettoreIntervento(ParamUtil.getString(aRequest, "rowIdLiv3"));
		navigaAggregata.setIdCategoriaIntervento(ParamUtil.getString(aRequest, "rowIdLiv4"));
		
		model.addAttribute("navigaAggregata", navigaAggregata);
		
		setNavigaAggregataInSession(aRequest, navigaAggregata);
		
		if( Integer.valueOf( navigaAggregata.getIdCategoriaIntervento() ) > 0 ){
			LiferayPortletURL renderURL = createLiferayPortletURL(aRequest, navigaAggregata.getPagElencoProgetti());
			try {
				aResponse.sendRedirect( renderURL.toString() );
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@RenderMapping
	public String handleRenderRequest(	RenderRequest renderRequest, 
										RenderResponse renderResponse,
										Model model, 
										@ModelAttribute("navigaAggregata") NavigaAggregata navigaAggregata){

		if( Integer.valueOf( navigaAggregata.getIdCategoriaIntervento() ) > 0 ) {
			navigaAggregata.setIdCategoriaIntervento("0");
		}
		
		int index = calcolaIndicePagina(navigaAggregata);
		
		//orderByCol is the column name passed in the request while sorting
		String orderByCol = ParamUtil.getString(renderRequest, "orderByCol"); 
		if(Validator.isNull(orderByCol)  || Validator.equals("", orderByCol)){
			orderByCol = "numeProgetti";
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
		
		SearchContainer<AggregataDTO> searchContainerDettaglio = new SearchContainer<AggregataDTO>(renderRequest, renderResponse.createRenderURL(), null, "Nessun dato trovato per la selezione fatta");
		searchContainerDettaglio.setDelta(delta);
		
		searchContainerDettaglio.setOrderByCol(orderByCol);
		searchContainerDettaglio.setOrderByType(orderByType);

		List<AggregataDTO> listaAggregataDTO = aggregataFacade.findAggregataByNatura(	navigaAggregata, 
																						searchContainerDettaglio.getOrderByCol(), 
																						searchContainerDettaglio.getOrderByType() );
		
		searchContainerDettaglio.setTotal( listaAggregataDTO.size() );

		List<AggregataDTO> subListaAggregataDTO = ListUtil.subList(listaAggregataDTO, searchContainerDettaglio.getStart(), searchContainerDettaglio.getEnd());       
		
		String anchorPortlet = "#classificazione-portlet2";
		impostaLinkURL(renderRequest, navigaAggregata, subListaAggregataDTO, anchorPortlet, navigaAggregata.getPagAggregata());
		
		searchContainerDettaglio.setResults(subListaAggregataDTO);
		
		model.addAttribute("searchContainerDettaglio", searchContainerDettaglio);
		model.addAttribute("navigaPer", navigaPer[index]);
		
		///////////////////////////////////////////////////////////////////////////////////////////
		
		SearchContainer<DescrizioneValore> searchContainerRiepilogo = new SearchContainer<DescrizioneValore>(renderRequest, renderResponse.createRenderURL(), null, "Nessun dato trovato per la selezione fatta");
		searchContainerRiepilogo.setDelta(maxResult);
		searchContainerRiepilogo.setTotal(3);
		
		Integer numeProgetti = 0;
		Double impoCostoProgetti = 0.0;
		Double impoImportoFinanziato = 0.0;
		
		for(AggregataDTO aggregataDTO : listaAggregataDTO){
			numeProgetti = numeProgetti + aggregataDTO.getNumeProgetti();
			impoCostoProgetti = impoCostoProgetti + aggregataDTO.getImpoCostoProgetti();
			impoImportoFinanziato = impoImportoFinanziato + aggregataDTO.getImpoImportoFinanziato();
		}
		
		List<DescrizioneValore> retval = new ArrayList<DescrizioneValore>();
		retval.add(new DescrizioneValore("VOLUME DEI PROGETTI", numeProgetti));
		retval.add(new DescrizioneValore("COSTO DEI PROGETTI", impoCostoProgetti));
		retval.add(new DescrizioneValore("IMPORTO FINANZIAMENTI", impoImportoFinanziato));
		
		searchContainerRiepilogo.setResults(retval);
		model.addAttribute("searchContainerRiepilogo", searchContainerRiepilogo);
		
		///////////////////////////////////////////////////////////////////////////////////////////
		
		//Calcolo l'url per elenco progetti
		LiferayPortletURL renderURL = createLiferayPortletURL(renderRequest, navigaAggregata.getPagElencoProgetti());
		model.addAttribute("linkURLElencoProgetti", renderURL.toString());
		
		impostaDesFiltriImpostati(navigaAggregata);
		
		model.addAttribute("navigaAggregata", navigaAggregata);
		
		setNavigaAggregataInSession(renderRequest, navigaAggregata);
		
		// MASCHERA RICERCA //
		initInModelMascheraRicerca(model, navigaAggregata);
		
		return "classificazione-view";
	}
	
	@ResourceMapping(value = "aggregati4Pie")	
	public View caricaDati4Pie(	ResourceRequest request, 
								@RequestParam("pattern") String pattern, 
								Model model,
								@ModelAttribute("navigaAggregata") NavigaAggregata navigaAggregata ){
		
		List<AggregataDTO> listaAggregataDTO = aggregataFacade.findAggregataByNatura(navigaAggregata);
		
		String anchorPortlet = "#classificazione-portlet1";
		impostaLinkURL(request, navigaAggregata, listaAggregataDTO, anchorPortlet, navigaAggregata.getPagAggregata());
		
		List <D3PieConverter> converter = new ArrayList<D3PieConverter>();
		
		double totale = 0.0;
		for (AggregataDTO aggregataDTO: listaAggregataDTO){
			if("VOLUME".equals(pattern)){
				totale = totale + Double.valueOf( aggregataDTO.getNumeProgetti()).doubleValue();
			}else if("COSTO".equals(pattern)){
				totale = totale + aggregataDTO.getImpoCostoProgetti().doubleValue();
			}else if("IMPORTO".equals(pattern)){
				totale = totale + aggregataDTO.getImpoImportoFinanziato().doubleValue();
			}else {
				totale = totale + 0.0;
			}
		}
		
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		D3PieConverter conv = null;
		double value = 0.0;
		for (AggregataDTO aggregataDTO: listaAggregataDTO){
			
			conv = new D3PieConverter();
			conv.setId(aggregataDTO.getId().toString());
			
			if( navigaAggregata.getIdCategoriaIntervento().equals("0") ){
				conv.setLabel(aggregataDTO.getDesCategoriaIntervento() );
			}else if( navigaAggregata.getIdSottosettoreIntervento().equals("0") ){
				conv.setLabel(aggregataDTO.getDesSottoSettore() );
			}else if( navigaAggregata.getIdAreaIntervento().equals("0") ){
				conv.setLabel(aggregataDTO.getDesArea() );
			}else if( navigaAggregata.getIdNatura().equals("0") ){
				conv.setLabel(aggregataDTO.getDesNatura());
			}

			if("VOLUME".equals(pattern)){
				value = Double.valueOf( aggregataDTO.getNumeProgetti() );
			}else if("COSTO".equals(pattern)){
				value = aggregataDTO.getImpoCostoProgetti();
			}else if("IMPORTO".equals(pattern)){
				value = aggregataDTO.getImpoImportoFinanziato();
			}else {
				value = 0.0;
			}
			
			double percentage = 0.0;
			if( value > 0 ){
				percentage = round( ((value * 100.0) / totale), 2);
			}
			
			conv.setPercentage( String.valueOf( percentage ) + "%" );
			conv.setValue( value );
			conv.setLinkURL(aggregataDTO.getLinkURL());
//			conv.setColor(getColor(indiceColore++));
			converter.add(conv);
		}

		view.addStaticAttribute("aggregati4Pie", converter);
		
		model.addAttribute("navigaAggregata", navigaAggregata);
		
		setNavigaAggregataInSession(request, navigaAggregata);
		
		return view;
	}
	
	private double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}

	private void setNavigaAggregataInSession(PortletRequest request, NavigaAggregata navigaAggregata) {
		HttpSession session = PortalUtil.getHttpServletRequest(request).getSession(false);
		navigaAggregata.setImportaInNavigaProgetti(true);
		session.setAttribute("navigaAggregata", navigaAggregata);
	}
	
	private void impostaLinkURL(	PortletRequest request, 
									NavigaAggregata sessionAttrNav, 
									List<AggregataDTO> listaAggregataDTO, 
									String anchorPortlet,
									String pageTo) {

		LiferayPortletURL renderURL = createLiferayPortletURL(request, pageTo);
		String rowIdLiv1URL = "", rowIdLiv2URL = "", rowIdLiv3URL = "", rowIdLiv4URL = "";
		
		for(AggregataDTO tmp : listaAggregataDTO){		

			//Per ogni elemento oltre a caricare la descrizione e i valori
			//viene generato un linkURL che punta alla pagina successiva

			rowIdLiv1URL = String.valueOf(sessionAttrNav.getIdNatura());
			rowIdLiv2URL = String.valueOf(sessionAttrNav.getIdAreaIntervento());
			rowIdLiv3URL = String.valueOf(sessionAttrNav.getIdSottosettoreIntervento());
			rowIdLiv4URL = String.valueOf(sessionAttrNav.getIdCategoriaIntervento());

			if( sessionAttrNav.getIdCategoriaIntervento().equals("0") ){
				rowIdLiv4URL = tmp.getIdCategoriaIntervento().toString(); 
				tmp.setDescURL( tmp.getDesCategoriaIntervento() );
			}else if( sessionAttrNav.getIdSottosettoreIntervento().equals("0") ){
				rowIdLiv3URL = tmp.getIdSottoSettore().toString(); 
				rowIdLiv4URL = "0";
				tmp.setDescURL( tmp.getDesSottoSettore() );
			}else if( sessionAttrNav.getIdAreaIntervento().equals("0") ){
				rowIdLiv2URL = tmp.getIdArea().toString(); 
				rowIdLiv3URL = "0";
				rowIdLiv4URL = "-1";
				tmp.setDescURL( tmp.getDesArea() );
			}

			renderURL.setParameter("rowIdLiv1", rowIdLiv1URL); 
			renderURL.setParameter("rowIdLiv2", rowIdLiv2URL); 
			renderURL.setParameter("rowIdLiv3", rowIdLiv3URL); 
			renderURL.setParameter("rowIdLiv4", rowIdLiv4URL); 

			renderURL.setParameter("action", "navigazione");

			tmp.setLinkURL(renderURL.toString() + anchorPortlet);
		}
	}
	
	private void impostaDesFiltriImpostati( NavigaAggregata filtro ) {
		
		if( Integer.valueOf( filtro.getIdNatura() ) > 0 ){
			filtro.setDescNatura( aggregataFacade.findNatura(Integer.valueOf( filtro.getIdNatura() )).getDescNatura() );
		}else{
			filtro.setDescNatura(null);
		}

		if( Integer.valueOf( filtro.getIdAreaIntervento() ) > 0 ){
			filtro.setDescAreaIntervento( aggregataFacade.findAreaIntervento(Integer.valueOf( filtro.getIdAreaIntervento() )).getDescAreaIntervento() );
		}else{
			filtro.setDescAreaIntervento(null);
		}
		
		if( Integer.valueOf( filtro.getIdSottosettoreIntervento() ) > 0 ){
			filtro.setDescSottosettoreIntervento( aggregataFacade.findSottosettoreIntervento(Integer.valueOf( filtro.getIdSottosettoreIntervento() )).getDescSottosettoreInt() );
		}else{
			filtro.setDescSottosettoreIntervento(null);
		}
		
		if( Integer.valueOf( filtro.getIdCategoriaIntervento() ) > 0 ){
			filtro.setDescCategoriaIntervento( aggregataFacade.findCategoriaIntervento(Integer.valueOf( filtro.getIdCategoriaIntervento() )).getDescCategoriaIntervento() );
		}else{
			filtro.setDescCategoriaIntervento(null);
		}
		
	}
	
	private int calcolaIndicePagina(NavigaAggregata sessionAttrNav) {
		int index;
		if( sessionAttrNav.getIdAreaIntervento().equals("0") ){
			index = 0;
		}else if( sessionAttrNav.getIdSottosettoreIntervento().equals("0") ){
			index = 1;
		}else if( sessionAttrNav.getIdCategoriaIntervento().equals("0")){
			index = 2;
		}else{
			index = 0;
		}
		return index;
	}

//	public static String getRandomColor() {
//		String[] letters = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
//		String color = "#";
//		for (int i = 0; i < 6; i++ ) {
//			color += letters[(int) Math.round(Math.random() * 15)];
//		}
//		return color;
//	}
	
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
	
	private void initInModelMascheraRicerca(Model model, NavigaAggregata filtro ) {
		
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
		
		//Carico la lista degli Anni Aggregata
		List<AnnoAggregato> listaAnnoAggregato = aggregataFacade.findAnnoAggregato();
		model.addAttribute("listaAnnoAggregato", listaAnnoAggregato);
		
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
	
}
