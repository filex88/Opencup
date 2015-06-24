package it.dipe.opencup.controllers;

import it.dipe.opencup.controllers.common.FiltriCommonController;
import it.dipe.opencup.dto.AggregataDTO;
import it.dipe.opencup.dto.DocumentoDTO;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.dto.NavigaProgetti;
import it.dipe.opencup.dto.RicercaLiberaDTO;
import it.dipe.opencup.facade.AggregataFacade;
import it.dipe.opencup.facade.ProgettoFacade;
import it.dipe.opencup.model.AnagraficaCup;
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
import it.dipe.opencup.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.springframework.web.portlet.bind.annotation.EventMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.StringQueryFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;

@Controller
@RequestMapping("VIEW")
@SessionAttributes({"navigaProgetti"})
public class ElencoProgettiController extends FiltriCommonController {
	
	private static Log logger = LogFactory.getLog(ElencoProgettiController.class);
	
	@Value("#{config['ricerca.assetpublisher.instanceId']}")
	private String assetPublisherPortletId;
	
	@Value("#{config['paginazione.risultatiPerPagina']}")
	private int maxResult;
	
	@Value("#{config['pagina.dettaglio.progetto']}")
	private String paginaDettaglioProgetto;	
	
	@Value("#{config['pagina.dettaglio.contenuto']}")
	private String paginaDettaglioContenuto;
	
	@Value("#{config['ricerca.dettaglioProgetto.instanceId']}")
	private String dettaglioProgettoInstanceId;
	
	@Value("#{config['codice.natura.open.cup']}")
	private String codiNaturaOpenCUP;
	
	@Autowired
	private ProgettoFacade progettoFacade;
	
	@Autowired
	private AggregataFacade aggregataFacade;
	
	@ModelAttribute("navigaProgetti")
	public NavigaProgetti modelAttrNavigaProgetti() {
		NavigaProgetti navigaProgetti = new NavigaProgetti();
		String idNatura =  (aggregataFacade.findNaturaByCod( codiNaturaOpenCUP )==null)?"0":aggregataFacade.findNaturaByCod( codiNaturaOpenCUP ).getId().toString();
		navigaProgetti.setIdNatura(idNatura);
		return navigaProgetti;
	}
	
	@RenderMapping
	public String handleRenderRequest(	RenderRequest renderRequest, 
										RenderResponse renderResponse,
										Model model, 
										@ModelAttribute("navigaProgetti") NavigaProgetti navigaProgetti,
										@RequestParam(required=false, value="jsonnavigaaggregata") String jsonnavigaaggregata
										){
		
		if(!StringUtils.isEmpty(jsonnavigaaggregata)){
			NavigaAggregata navigaAggregata = createModelAggregataFromJsonString(jsonnavigaaggregata);
			navigaProgetti.importa( navigaAggregata );
		}
		navigaProgetti.setCurrentAction("elencoProgetti");
		
		return elencoProgettiRenderRequest(renderRequest, renderResponse, model, navigaProgetti);

	}
	
	@RenderMapping(params="action=elencoProgetti")
	public String elencoProgettiRenderRequest(	RenderRequest renderRequest, 
			RenderResponse renderResponse,
			Model model, 
			@ModelAttribute("navigaProgetti") NavigaProgetti navigaProgetti){
			//,@RequestParam(required=false, value="jsonnavigaprogetti") String jsonnavigaprogetti){

		//if(!StringUtils.isEmpty(jsonnavigaprogetti)){
		//	navigaProgetti = createModelProgettiFromJsonString(jsonnavigaprogetti);
		//}

		if(StringUtils.isEmpty( navigaProgetti.getCurrentAction() ) ){
			navigaProgetti.setCurrentAction("elencoProgetti");
		}
		
		model.addAttribute("currentAction", navigaProgetti.getCurrentAction() );
		model.addAttribute("paginate", "true");
		
		// LISTA PROGETTI //
		//orderByCol is the column name passed in the request while sorting
		String orderByCol = ParamUtil.getString(renderRequest, "orderByCol"); 
		if(Validator.isNull(orderByCol)  || Validator.equals("", orderByCol)){
			orderByCol = "impoCostoProgetto";
		}
		
		//orderByType is passed in the request while sorting. It can be either asc or desc
		String orderByType = ParamUtil.getString(renderRequest, "orderByType");
		if(Validator.isNull(orderByType)  || Validator.equals("", orderByType)){
		    orderByType = "desc";
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
		
		navigaProgetti.setOrderByCol(searchContainerElenco.getOrderByCol());
		navigaProgetti.setOrderByType(searchContainerElenco.getOrderByType());
		navigaProgetti.setStart(searchContainerElenco.getStart());
		navigaProgetti.setDelta(delta);
		
		List<Progetto> elencoProgetti4Pag = progettoFacade.findElencoProgetti(	navigaProgetti );
		//int size =  elencoProgetti.size();
		/*
		searchContainerElenco.setTotal(size);
		int fromIndex = searchContainerElenco.getStart();
		int toIndex = (((searchContainerElenco.getStart() + delta) > size)?size:(searchContainerElenco.getStart() + delta)) - 1;
		
		System.out.println( fromIndex );
		System.out.println( toIndex );
		
		List<Progetto> elencoProgetti4Pag = new ArrayList<Progetto>();
		for( Progetto tmp : elencoProgetti.subList(fromIndex, toIndex) ){
			if( tmp.getAnagraficaCup().getFkDcupDcupIdMaster() != null ){
				tmp.getAnagraficaCup().setAnagraficaCup(
						progettoFacade.findAnagraficaCupById( tmp.getAnagraficaCup().getFkDcupDcupIdMaster() ) );
			}
			elencoProgetti4Pag.add(tmp);
		}
		*/
		searchContainerElenco.setResults(elencoProgetti4Pag);
		searchContainerElenco.setTotal(size);
		
		model.addAttribute("searchContainerElenco", searchContainerElenco);
		
		model.addAttribute("pagDettaglioProgetto", paginaDettaglioProgetto );
		// FINE LISTA PROGETTI //
		
		// MASCHERA RICERCA PROGETTI //
		initInModelMascheraRicerca(model, navigaProgetti);
		model.addAttribute("navigaProgetti", navigaProgetti);
		// FINE RICERCA PROGETTI //
		
		// RIEPILOGO //
		//DATI TOTALI
		
		NavigaAggregata navigaAggregata = new NavigaAggregata();
		String idNatura =  (aggregataFacade.findNaturaByCod( codiNaturaOpenCUP )==null)?"0":aggregataFacade.findNaturaByCod( codiNaturaOpenCUP ).getId().toString();
		navigaAggregata.setIdNatura(idNatura);
		List<AggregataDTO> listaAggregataDTO = aggregataFacade.findAggregataByNatura(navigaAggregata);
		NavigaProgetti navigaProgettitot = new NavigaProgetti();
		navigaProgettitot.setIdNatura(idNatura);

		Double impoCostoProgetti = 0.0;
		Double impoImportoFinanziato = 0.0;
		
		for(AggregataDTO aggregataDTO : listaAggregataDTO){
			impoCostoProgetti = impoCostoProgetti + aggregataDTO.getImpoCostoProgetti();
			impoImportoFinanziato = impoImportoFinanziato + aggregataDTO.getImpoImportoFinanziato();
		}
		
		int sizetot =  progettoFacade.sizeElencoProgetti( navigaProgettitot ).getSize();
		
		model.addAttribute("volumeDeiProgetti", sizetot);
		model.addAttribute("costoDeiProgetti", impoCostoProgetti);
		model.addAttribute("importoFinanziamenti", impoImportoFinanziato);
		
		navigaAggregata = new NavigaAggregata();
		navigaAggregata.setIdNatura(idNatura);
		navigaAggregata.importa( navigaProgetti );
		//Gestione ANNI
		if( navigaProgetti.getIdAnnoDecisiones() != null && navigaProgetti.getIdAnnoDecisiones().size() > 0){
			if( navigaProgetti.getIdAnnoDecisiones().contains("-1") ){
				List<String> idAnnoAggregatos = new ArrayList<String>();
				idAnnoAggregatos.add("0");
				navigaAggregata.setIdAnnoAggregatos( idAnnoAggregatos );
			}else{
				List<String> idAnnoAggregatos = new ArrayList<String>();
				for( String tmp : navigaProgetti.getIdAnnoDecisiones() ){
					idAnnoAggregatos.add((aggregataFacade.findAnniDecisione(Integer.valueOf(tmp))).getAnnoAggregato().getId().toString());
				}
				navigaAggregata.setIdAnnoAggregatos( idAnnoAggregatos );
			}
		}
		//FINE Gestione ANNI
		
		listaAggregataDTO = aggregataFacade.findAggregataByNatura(navigaAggregata);

		Double impoCostoProgettiProg = 0.0;
		Double impoImportoFinanziatoProg = 0.0;
		
		for(AggregataDTO aggregataDTO : listaAggregataDTO){
			impoCostoProgettiProg = impoCostoProgettiProg + aggregataDTO.getImpoCostoProgetti();
			impoImportoFinanziatoProg = impoImportoFinanziatoProg + aggregataDTO.getImpoImportoFinanziato();
		}
		
		model.addAttribute("volumeDeiProgettiProg", size);
		model.addAttribute("costoDeiProgettiProg", impoCostoProgettiProg);
		model.addAttribute("importoFinanziamentiProg", impoImportoFinanziatoProg);
		
		/*
		System.out.println("STEP 4");
		
		Double impoCostoProgettiProg = 0.0;
		Double impoImportoFinanziatoProg = 0.0;
		
		for( Progetto p : elencoProgetti ){
			impoCostoProgettiProg = impoCostoProgettiProg + p.getImpoCostoProgetto();
			impoImportoFinanziatoProg = impoImportoFinanziatoProg + p.getImpoImportoFinanziato();
		}
		
		model.addAttribute("volumeDeiProgettiProg", size);
		model.addAttribute("costoDeiProgettiProg", impoCostoProgettiProg);
		model.addAttribute("importoFinanziamentiProg", impoImportoFinanziatoProg);
		*/
		// FINE RIEPILOGO //
		
		model.addAttribute("valoreRicercaValido", "SI");
		
		return "elenco-progetti-view";
		
	}
	
	@RenderMapping(params="action=ricercaAvanzata")
	public String effettuaRicercaAvanzata(	RenderRequest renderRequest, 
									RenderResponse renderResponse, 
									Model model, 
									@ModelAttribute("navigaProgetti") NavigaProgetti navigaProgetti,
									@RequestParam("cercaPerKeyword") String cercaPerKeyword) {
			
		model.addAttribute("currentAction", "ricercaAvanzata");
		navigaProgetti.setCurrentAction("ricercaAvanzata");

		model.addAttribute("paginate", "false");
		
		model.addAttribute("navigaProgetti", navigaProgetti);
		model.addAttribute("valoreRicercaValido", "NO");
		
		// MASCHERA RICERCA PROGETTI //
		initInModelMascheraRicerca(model, navigaProgetti);
		
		model.addAttribute("navigaProgetti", navigaProgetti);
		// FINE RICERCA PROGETTI //
		
		return "elenco-progetti-view";
		
	}
	
	@RenderMapping(params="action=ricercaLibera")
	public String effettuaRicerca(	RenderRequest renderRequest, 
									RenderResponse renderResponse, 
									Model model, 
									@ModelAttribute("navigaProgetti") NavigaProgetti navigaProgetti,
									@RequestParam("cercaPerKeyword") String cercaPerKeyword) {
			
		model.addAttribute("currentAction", "ricercaLibera");
		navigaProgetti.setCurrentAction("ricercaLibera");
		
		model.addAttribute("cercaPerKeyword", cercaPerKeyword);
		model.addAttribute("navigaProgetti", navigaProgetti);
		
		logger.info("effettuaRicerca.cercaPerKeyword: " + cercaPerKeyword );
		try {
			
			Document[] documents = null;
			List<DocumentoDTO> risultatiGenerici = new ArrayList<DocumentoDTO>();
			List<Progetto> risultatiProgetti = new ArrayList<Progetto>();
			
			if( cercaPerKeyword != null && cercaPerKeyword.length() > 3 ){
				Query query = StringQueryFactoryUtil.create(Field.TITLE + ":" + cercaPerKeyword + " or " + Field.CONTENT + ":" + cercaPerKeyword + " or " +
						Constants.RICERCALIBERA_FIELD_SEARCH + ":" + cercaPerKeyword + " or " +
						Constants.RICERCALIBERA_FIELD_LOCALIZZAZIONE + ":" + cercaPerKeyword + " or " +
						Constants.RICERCALIBERA_FIELD_CODICE_CUP + ":" + cercaPerKeyword);
				
				logger.debug("query = " + query.toString());
				
				Hits hits = SearchEngineUtil.search(SearchEngineUtil.SYSTEM_ENGINE_ID, PortalUtil.getDefaultCompanyId(), query, -1, -1);
				
				logger.info("hits = " + hits.getLength());
				documents = hits.getDocs();
				model.addAttribute("valoreRicercaValido", "SI");
			}else{
				SessionMessages.add(renderRequest, "valore-ricerca-non-valido");
				model.addAttribute("valoreRicercaValido", "NO");
			}
			
			if( documents != null ){
				DocumentoDTO documento = null;
				Progetto progetto = null;
				int contaDoc = 0;
				for (Document document : documents) {
					logger.debug("Document: " + document.getUID() );
					
//					for (Map.Entry<String, Field> entry : document.getFields().entrySet() ) {
//						logger.debug("-- " +  entry.getKey() + ": " + entry.getValue().getValue() );
//					}
					
					if (document.get(Field.ENTRY_CLASS_NAME).equals(Progetto.class.getName())) {
					
						progetto = getProgettoFromDocument(document);
						risultatiProgetti.add(progetto);
							
					} else {
						
						documento = new DocumentoDTO(); 
						documento.setTitolo(document.getField(Field.TITLE).getValue());
						String testo = "non disponibile";
						if( document.getField(Field.CONTENT) != null){
							testo = trunc(document.getField(Field.CONTENT).getValue(), 37);
						}
						documento.setTesto(testo);
						documento.setId(contaDoc++);
						
						AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry( document.get(Field.ENTRY_CLASS_NAME) , Long.parseLong(document.get(Field.ENTRY_CLASS_PK)));
						documento.setUrl(getAssetViewURL(renderRequest, renderResponse, assetEntry, cercaPerKeyword) );
						
						risultatiGenerici.add(documento);
						
					}	
				}
			}
			
			//model.addAttribute("risultatiGenerici", risultatiGenerici);
			model.addAttribute("risultatiGenericiSize", risultatiGenerici.size());
			
			SearchContainer<DocumentoDTO> searchContainerElencoDoc = new SearchContainer<DocumentoDTO>(renderRequest, renderResponse.createRenderURL(), null, "Nessun dato trovato per la selezione fatta");
			searchContainerElencoDoc.setDelta(risultatiGenerici.size());
			searchContainerElencoDoc.setTotal(risultatiGenerici.size());
			searchContainerElencoDoc.setResults(risultatiGenerici);
			model.addAttribute("searchContainerElencoDoc", searchContainerElencoDoc);
			
			
			//model.addAttribute("risultatiProgetti", risultatiProgetti);
			SearchContainer<Progetto> searchContainerElencoPro = new SearchContainer<Progetto>(renderRequest, renderResponse.createRenderURL(), null, "Nessun dato trovato per la selezione fatta");
			searchContainerElencoPro.setDelta(risultatiProgetti.size());
			searchContainerElencoPro.setTotal(risultatiProgetti.size());
			searchContainerElencoPro.setResults(risultatiProgetti);
			model.addAttribute("searchContainerElenco", searchContainerElencoPro);

		} catch (SearchException e) {
			logger.error("SearchException: ", e);
		} catch (NumberFormatException e) {
			logger.error("NumberFormatException: ", e);
		} catch (PortalException e) {
			logger.error("PortalException: ", e);
		} catch (SystemException e) {
			logger.error("SystemException: ", e);
		}

		return "elenco-progetti-view";
		
	}
	
	private String trunc(String testo, int n){
        boolean toLong = testo.length() > n;
        String retval = (toLong)? testo.substring(0, n) : testo;		
        return (toLong) ? retval + "..." : retval;
     };
	
     protected NavigaAggregata createModelAggregataFromJsonString(String jsonString){
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
     
    protected NavigaProgetti createModelProgettiFromJsonString(String jsonString){
 		ObjectMapper mapper= new ObjectMapper();
 		NavigaProgetti model=null;
 		try {
 			model = mapper.readValue(jsonString, NavigaProgetti.class);
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
									@RequestParam(value = "idProgettoDettaglio") String idProgettoDettaglio,
									@RequestParam(value = "currentAction") String currentAction,
									@RequestParam(required = false , value = "cercaPerKeyword") String cercaPerKeyword){

		navigaProgetti.setIdProgetto( idProgettoDettaglio );
		LiferayPortletURL renderURL = createLiferayPortletURL(aRequest, paginaDettaglioProgetto, dettaglioProgettoInstanceId );

		renderURL.setParameter("idPj", idProgettoDettaglio );
		
		try {
			
			ThemeDisplay themeDisplay = (ThemeDisplay)aRequest.getAttribute(WebKeys.THEME_DISPLAY);
			PortletURL returnURL = PortletURLFactoryUtil.create(aRequest, (String)aRequest.getAttribute(WebKeys.PORTLET_ID), themeDisplay.getLayout().getPlid(), PortletRequest.RENDER_PHASE);
			returnURL.setWindowState(WindowState.NORMAL);
			returnURL.setPortletMode(PortletMode.VIEW);
			//returnURL.setParameter("jsonnavigaprogetti", createJsonStringFromModelAttribute(navigaProgetti));
			returnURL.setParameter("action", currentAction);
			if( !StringUtils.isEmpty(cercaPerKeyword) ){
				returnURL.setParameter("cercaPerKeyword", cercaPerKeyword);
			}
			
			renderURL.setParameter("returnUrl", returnURL.toString() );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			aResponse.sendRedirect( HttpUtil.encodeParameters( renderURL.toString() ) );//  + "&idPj="+navigaProgetti.getIdProgetto() ) );
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@EventMapping(value = "event.risultatiRicerca")
	public void effettuaRicerca(EventRequest request, EventResponse response) {
		RicercaLiberaDTO ricercaDTO = (RicercaLiberaDTO) request.getEvent().getValue();
		response.setRenderParameter("action", ricercaDTO.getTipoRicerca() );
		response.setRenderParameter("cercaPerKeyword", ricercaDTO.getCercaPerKeyword() );
	}
	
	private String getAssetViewURL(
			PortletRequest request, PortletResponse response, AssetEntry assetEntry, String keywords) {
			
			try {
				ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
				PortletURL returnURL = PortletURLFactoryUtil.create(request, (String)request.getAttribute(WebKeys.PORTLET_ID), themeDisplay.getLayout().getPlid(), PortletRequest.RENDER_PHASE);
				returnURL.setWindowState(WindowState.NORMAL);
				returnURL.setPortletMode(PortletMode.VIEW);
				returnURL.setParameter("action", "ricercaLibera");
				returnURL.setParameter("cercaPerKeyword", keywords);
			
			
		        PortletURL viewURL = null;
		        
				String localHost = themeDisplay.getPortalURL();		
				List<Layout> layouts = null;
				
				
				layouts = LayoutLocalServiceUtil.getLayouts(themeDisplay.getLayout().getGroupId(), false);
				for (Layout layout : layouts){

					String nodeNameRemoved = PortalUtil.getLayoutFriendlyURL(layout, themeDisplay).replace(localHost, "");

					//Viene ricercato l'URL esatto per la pagina successiva
					if(nodeNameRemoved.indexOf(paginaDettaglioContenuto) > 0) {
						
						viewURL = PortletURLFactoryUtil.create(request, assetPublisherPortletId, layout.getPlid(), PortletRequest.RENDER_PHASE);
						viewURL.setWindowState(WindowState.NORMAL);
						viewURL.setPortletMode(PortletMode.VIEW);
						viewURL.setParameter("action", "ricerca");
						viewURL.setParameter("struts_action", "/asset_publisher/view_content");

						//String currentURL = HttpUtil.addParameter(PortalUtil.getCurrentURL(request), "_" + request.getAttribute(WebKeys.PORTLET_ID) + "_cercaPerKeyword", keywords);
						viewURL.setParameter("redirect", returnURL.toString());

				        viewURL.setParameter("assetEntryId", String.valueOf(assetEntry.getEntryId()));

				        AssetRendererFactory assetRendererFactory = assetEntry.getAssetRendererFactory();

				        AssetRenderer assetRenderer = assetEntry.getAssetRenderer();

				        viewURL.setParameter("type", assetRendererFactory.getType());

				        if (Validator.isNotNull(assetRenderer.getUrlTitle())) {

			                if (assetRenderer.getGroupId() != themeDisplay.getScopeGroupId()) {
			                	viewURL.setParameter("groupId", String.valueOf(assetRenderer.getGroupId()));
			                }

			                viewURL.setParameter("urlTitle", assetRenderer.getUrlTitle());
				        }
						
			
						break;
						
					}
				}
				
				return viewURL.toString();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
	        return null;
	}	
	
	
	private Progetto getProgettoFromDocument(Document doc) {
		Progetto progetto = new Progetto();
		
		progetto.setId(Integer.parseInt(doc.getField(Field.ENTRY_CLASS_PK).getValue()));
		progetto.setAnnoAnnoDecisione(doc.get(Constants.RICERCALIBERA_FIELD_ANNO_DECISIONE));
		
		CategoriaIntervento categoriaIntervento = new CategoriaIntervento();
		categoriaIntervento.setDescCategoriaIntervento(doc.get(Constants.RICERCALIBERA_FIELD_CATEGORIA));
		progetto.setCategoriaIntervento(categoriaIntervento);
		progetto.setComuniProgetto(doc.get(Constants.RICERCALIBERA_FIELD_COMUNE));
		AnnoDecisione annodec = new AnnoDecisione();
		annodec.setAnnoDadeAnnoDecisione(doc.get(Constants.RICERCALIBERA_FIELD_ANNO_DECISIONE));
		progetto.setAnnoDecisione(annodec);
		AnagraficaCup ana = new AnagraficaCup();
		ana.setDescCup(doc.get(Field.TITLE));
		ana.setCodiCup( doc.get (Constants.RICERCALIBERA_FIELD_CODICE_CUP) );
		progetto.setAnagraficaCup(ana);
		progetto.setImpoCostoProgetto(Double.parseDouble(doc.get(Constants.RICERCALIBERA_FIELD_COSTO)));
		progetto.setImpoImportoFinanziato(Double.parseDouble(doc.get(Constants.RICERCALIBERA_FIELD_IMPORTO)));
		
		return progetto;
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
			List<CategoriaSoggetto> listCategoriaSoggetto = aggregataFacade.findCategoriaSoggettoByIdAreaSoggetto();//Integer.valueOf( filtro.getIdAreaSoggetto() ));
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
	
	private LiferayPortletURL createLiferayPortletURL(PortletRequest request, String toPage, String portletId) {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
//		String portletId = (String) request.getAttribute(WebKeys.PORTLET_ID);
		LiferayPortletURL renderURL = null;
		String localHost = themeDisplay.getPortalURL();		
		List<Layout> layouts = null;
		try{
			layouts = LayoutLocalServiceUtil.getLayouts(themeDisplay.getLayout().getGroupId(), false);
			
			for(Layout layout : layouts){

				String nodeNameRemoved = PortalUtil.getLayoutFriendlyURL(layout, themeDisplay).replace(localHost, "");
				
				//Viene ricercato l'URL esatto per la pagina successiva
				if(nodeNameRemoved.indexOf(toPage)>0){
					
					renderURL = PortletURLFactoryUtil.create(request, portletId, layout.getPlid(), PortletRequest.RENDER_PHASE);
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
