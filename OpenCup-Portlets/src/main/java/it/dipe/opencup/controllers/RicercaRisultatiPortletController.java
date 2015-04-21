package it.dipe.opencup.controllers;

import it.dipe.opencup.controllers.common.FiltriCommonController;
import it.dipe.opencup.dto.DocumentoDTO;
import it.dipe.opencup.dto.RicercaLiberaDTO;
import it.dipe.opencup.facade.AggregataFacade;
import it.dipe.opencup.facade.ProgettoFacade;
import it.dipe.opencup.model.AnagraficaCup;
import it.dipe.opencup.model.CategoriaIntervento;
import it.dipe.opencup.model.Progetto;
import it.dipe.opencup.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.portlet.bind.annotation.EventMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.util.UriComponentsBuilder;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.StringQueryFactoryUtil;
import com.liferay.portal.kernel.util.HttpUtil;
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
@SessionAttributes({"navigaAggregata"})
public class RicercaRisultatiPortletController extends FiltriCommonController {
	
	private static Log logger = LogFactory.getLog(RicercaRisultatiPortletController.class);
	
	@Value("#{config['paginazione.risultatiPerPagina']}")
	private int maxResult;
	
	
	@Value("#{config['ricerca.assetpublisher.instanceId']}")
	private String assetPublisherPortletId;
	
	@Value("#{config['ricerca.dettaglioProgetto.instanceId']}")
	private String dettaglioProgettoPortletId;
	
	@Value("#{config['ricerca.ricercaLibera.instanceId']}")
	private String ricercaLiberaPortletId;

	@Value("#{config['pagina.elenco.progetti']}")
	private String paginaElencoProgetti;
	
	@Autowired
	private ProgettoFacade progettoFacade;
	
	@Autowired
	private AggregataFacade aggregataFacade;


	@EventMapping(value = "event.risultatiRicerca")
	public void effettuaRicerca(EventRequest request, EventResponse response) {
		RicercaLiberaDTO ricercaDTO = (RicercaLiberaDTO) request.getEvent().getValue();

		System.out.println( "!!! cercaPerKeywords: " + ricercaDTO.getCercaPerKeyword() + " !!!" );

		response.setRenderParameter("mostra", "risultati");
		response.setRenderParameter("cercaPerKeyword", ricercaDTO.getCercaPerKeyword());
	}

	
	@RenderMapping
	public String nessunRisultato() {
		return "ricercarisultati-view";
	}
	
	
	@RenderMapping(params="mostra=risultati") 
	public String mostraRisultati(RenderRequest request, RenderResponse response, 
			Model model, @RequestParam("cercaPerKeyword") String cercaPerKeyword ) {
		
		
		
		try {
			Query query = StringQueryFactoryUtil.create(Field.TITLE + ":" + cercaPerKeyword + " or " + Field.CONTENT + ":" + cercaPerKeyword + " or " +
					Constants.RICERCALIBERA_FIELD_SEARCH + ":" + cercaPerKeyword + " or " +
					Constants.RICERCALIBERA_FIELD_LOCALIZZAZIONE + ":" + cercaPerKeyword + " or " +
					Constants.RICERCALIBERA_FIELD_CODICE_CUP + ":" + cercaPerKeyword);
			
			logger.debug("query = " + query.toString());
			
			Hits hits = SearchEngineUtil.search(SearchEngineUtil.SYSTEM_ENGINE_ID, PortalUtil.getDefaultCompanyId(), query, -1, -1);
			
			logger.info("hits = " + hits.getLength());
			Document[] documents = hits.getDocs();
			List<DocumentoDTO> risultatiGenerici = new ArrayList<DocumentoDTO>();
			List<Progetto> risultatiProgetti = new ArrayList<Progetto>();
			for (Document document : documents) {
				
							
				
				logger.debug("Document: " + document.getUID() );
				for (Map.Entry<String, Field> entry : document.getFields().entrySet() ) {
					logger.debug("-- " +  entry.getKey() + ": " + entry.getValue().getValue() );
				}
				
				
				if (document.get(Field.ENTRY_CLASS_NAME).equals(Progetto.class.getName())) {
					Progetto progetto = getProgettoFromDocument(document);
					progetto.setDettaglioUrl(getProgettoViewURL(request, response, document.get(Field.ENTRY_CLASS_PK), cercaPerKeyword));
					
					risultatiProgetti.add(progetto);
						
				} else {
					
					DocumentoDTO risultato = new DocumentoDTO(); 
					risultato.setTitolo(document.getField(Field.TITLE).getValue());
					AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry( document.get(Field.ENTRY_CLASS_NAME) , Long.parseLong(document.get(Field.ENTRY_CLASS_PK)));
					
					risultato.setUrl(getAssetViewURL(request, response, assetEntry, cercaPerKeyword) );
					risultatiGenerici.add(risultato);
				
				}
				

				
			}
			
			model.addAttribute("risultatiGenerici", risultatiGenerici);
			model.addAttribute("risultatiProgetti", risultatiProgetti);
			
		} catch (SearchException e) {
			logger.error("SearchException: ", e);
		} catch (NumberFormatException e) {
			logger.error("NumberFormatException: ", e);
		} catch (PortalException e) {
			logger.error("PortalException: ", e);
		} catch (SystemException e) {
			logger.error("SystemException: ", e);
		}
		
		return "ricercarisultati-view";
	}
	
	private String getProgettoViewURL(
			RenderRequest request, RenderResponse response, String key, String keywords) {
		
        PortletURL viewURL = null;
        
        ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		
		String localHost = themeDisplay.getPortalURL();		
		List<Layout> layouts = null;
		try{
			layouts = LayoutLocalServiceUtil.getLayouts(themeDisplay.getLayout().getGroupId(), false);
			for (Layout layout : layouts){

				String nodeNameRemoved = PortalUtil.getLayoutFriendlyURL(layout, themeDisplay).replace(localHost, "");

				//Viene ricercato l'URL esatto per la pagina successiva
				if(nodeNameRemoved.indexOf(paginaElencoProgetti) > 0) {
					
					viewURL = PortletURLFactoryUtil.create(request, dettaglioProgettoPortletId, layout.getPlid(), PortletRequest.RENDER_PHASE);
					viewURL.setWindowState(WindowState.NORMAL);
					viewURL.setPortletMode(PortletMode.VIEW);
					
					String currentURL = HttpUtil.addParameter(PortalUtil.getCurrentURL(request), "_" + ricercaLiberaPortletId + "_cercaPerKeyword", keywords);
					viewURL.setParameter("redirect", currentURL);
					
			        String returnURL =  UriComponentsBuilder.fromHttpUrl(viewURL.toString()).queryParam("idPj", key).build().toUriString();
					return returnURL;
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        
        
        return viewURL.toString();

	}
	
	private String getAssetViewURL(
			RenderRequest request, RenderResponse response, AssetEntry assetEntry, String keywords) {

	        PortletURL viewURL = null;
	        
	        ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
			
			String localHost = themeDisplay.getPortalURL();		
			List<Layout> layouts = null;
			try{
				layouts = LayoutLocalServiceUtil.getLayouts(themeDisplay.getLayout().getGroupId(), false);
				for (Layout layout : layouts){

					String nodeNameRemoved = PortalUtil.getLayoutFriendlyURL(layout, themeDisplay).replace(localHost, "");

					//Viene ricercato l'URL esatto per la pagina successiva
					if(nodeNameRemoved.indexOf("ricercarisultato") > 0) {
						
						viewURL = PortletURLFactoryUtil.create(request, assetPublisherPortletId, layout.getPlid(), PortletRequest.RENDER_PHASE);
						viewURL.setWindowState(WindowState.NORMAL);
						viewURL.setPortletMode(PortletMode.VIEW);
						viewURL.setParameter("action", "ricerca");
						
						viewURL.setParameter("struts_action", "/asset_publisher/view_content");

						String currentURL = HttpUtil.addParameter(PortalUtil.getCurrentURL(request), "_" + ricercaLiberaPortletId + "_cercaPerKeyword", keywords);
						viewURL.setParameter("redirect", currentURL);

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
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
	        
	        
	        return viewURL.toString();
	}	
	
	
	private Progetto getProgettoFromDocument(Document doc) {
		Progetto progetto = new Progetto();
		
		progetto.setId(Integer.parseInt(doc.getField(Field.ENTRY_CLASS_PK).getValue()));
		progetto.setAnnoAnnoDecisione(doc.get(Constants.RICERCALIBERA_FIELD_ANNO_DECISIONE));
		
		
		CategoriaIntervento categoriaIntervento = new CategoriaIntervento();
		categoriaIntervento.setDescCategoriaIntervento(doc.get(Constants.RICERCALIBERA_FIELD_CATEGORIA));
		progetto.setCategoriaIntervento(categoriaIntervento);
		progetto.setComuniProgetto(doc.get(Constants.RICERCALIBERA_FIELD_COMUNE));
		progetto.setAnnoAnnoDecisione(doc.get(Constants.RICERCALIBERA_FIELD_ANNO_DECISIONE));
		AnagraficaCup ana = new AnagraficaCup();
		ana.setDescCup(doc.get(Field.TITLE));
		progetto.setAnagraficaCup(ana);
		progetto.setImpoCostoProgetto(Double.parseDouble(doc.get(Constants.RICERCALIBERA_FIELD_COSTO)));
		progetto.setImpoImportoFinanziato(Double.parseDouble(doc.get(Constants.RICERCALIBERA_FIELD_IMPORTO)));
		
		return progetto;
	}
	
}
