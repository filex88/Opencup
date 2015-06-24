package it.dipe.opencup.controllers;

import it.dipe.opencup.dto.AggregataDTO;
import it.dipe.opencup.dto.D3BarConverter;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.dto.PortletConfigDTO;
import it.dipe.opencup.facade.AggregataFacade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;
import javax.xml.namespace.QName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.EventMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletURLFactoryUtil;

@Controller
@RequestMapping("VIEW")
public class SoggettoPortletController {
	
	@Autowired
	private AggregataFacade aggregataFacade;
	
	@Value("#{config['codice.natura.open.cup']}")
	private String codiNaturaOpenCUP;
	
	@Value("#{config['pagina.soggetto']}")
	private String paginaSoggetto;
	
	@Value("#{config['pagina.elenco.progetti']}")
	private String paginaElencoProgetti;
	
	@Value("#{config['elenco.progetti.instanceId']}")
	private String elencoProgettiPortletId;
	
	@ModelAttribute("navigaAggregata")
	public NavigaAggregata navigaAggregata() {
		
		String idNatura =  (aggregataFacade.findNaturaByCod( codiNaturaOpenCUP )==null)?"0":aggregataFacade.findNaturaByCod( codiNaturaOpenCUP ).getId().toString();
		NavigaAggregata navigaAggregata = new NavigaAggregata();
		navigaAggregata.setIdNatura(idNatura);
		navigaAggregata.setPagAggregata(paginaSoggetto);
		navigaAggregata.setIdAreaSoggetto("0");
		navigaAggregata.setIdCategoriaSoggetto("-1");
		navigaAggregata.setIdSottoCategoriaSoggetto("-1");
		
		return navigaAggregata;
	}
	
	@RenderMapping
	public String renderRequest(RenderRequest renderRequest, 
								RenderResponse renderResponse,
								Model model,
								@ModelAttribute("navigaAggregata") NavigaAggregata navigaAggregata,
								@RequestParam(required=false, value="pattern") String pattern,
								PortletPreferences prefs) throws PortalException, SystemException{
		
		if( StringUtils.isEmpty(pattern) ){
			pattern = navigaAggregata.getDistribuzione();
		}else{
			navigaAggregata.setDistribuzione(pattern);
		}
		
		String portletResource = ParamUtil.getString(renderRequest, "portletResource");
		
		if (Validator.isNotNull(portletResource)) {
		    prefs = PortletPreferencesFactoryUtil.getPortletSetup(renderRequest, portletResource);
		}
		
		PortletConfigDTO config = new PortletConfigDTO();
		config.setSelezionabile( "S".equals( prefs.getValue(PortletConfigDTO.PROP_SELEZIONABILE, "N") ) );
		config.setMostraPulsanti( "S".equals( prefs.getValue(PortletConfigDTO.PROP_MOSTRAPULTANTI, "N") ) );
		config.setPortletPrincipale( "S".equals( prefs.getValue(PortletConfigDTO.PROP_PORTLET_PRINCIPALE, "N") ) );
		config.setPortletSecondariaDX( "S".equals( prefs.getValue(PortletConfigDTO.PROP_PORTLET_SECONDARIA_DX, "N") ) );
		model.addAttribute("configSoggetto", config);
		
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		model.addAttribute("jsFolder",themeDisplay.getPathThemeJavaScript());
		
		model.addAttribute("pattern", pattern);
		
		String orderProperty = "";
		if( navigaAggregata.getIdSottoCategoriaSoggetto().equals("0") ){
			orderProperty = "gerarchiaSoggetto.descSottocategSoggetto";
		}else if( navigaAggregata.getIdCategoriaSoggetto().equals("0") ){
			orderProperty = "gerarchiaSoggetto.descCategSoggetto";
		}else if( navigaAggregata.getIdAreaSoggetto().equals("0") ){
			orderProperty = "gerarchiaSoggetto.descAreaSoggetto";
		}
		navigaAggregata.setOrderProperty(orderProperty);
		navigaAggregata.setOrderType("asc");
		
		navigaAggregata.setDistribuzione("");
		List<AggregataDTO> listaAggregataDTO = aggregataFacade.findAggregataByNatura(navigaAggregata);
		navigaAggregata.setDistribuzione(pattern);
		
		impostaLinkURL(renderRequest, navigaAggregata, listaAggregataDTO, navigaAggregata.getPagAggregata());
		
		List <D3BarConverter> converter = new ArrayList<D3BarConverter>();
		
		D3BarConverter conv = null;
		Double value = 0.0;
		for (AggregataDTO aggregataDTO: listaAggregataDTO){
			
			conv = new D3BarConverter();
			
			if( navigaAggregata.getIdSottoCategoriaSoggetto().equals("0") ){
				conv.setLabel( aggregataDTO.getDescSottocategSoggetto() );
			}else if( navigaAggregata.getIdCategoriaSoggetto().equals("0") ){
				conv.setLabel( aggregataDTO.getDescCategSoggetto() );
			}else if( navigaAggregata.getIdAreaSoggetto().equals("0") ){
				conv.setLabel( aggregataDTO.getDescAreaSoggetto() );
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
			
			conv.setVolume( value.longValue() );
			conv.setLinkURL(aggregataDTO.getLinkURL());
			converter.add(conv);
		}

		model.addAttribute("recordCountSoggetto", converter.size());

		model.addAttribute("aggregati4Soggetto", createJsonStringFromQueryResult(converter));
		model.addAttribute("navigaAggregata", navigaAggregata);
		
		return "soggetto-view";
	}

	@ActionMapping(params="action=cambiaAggregazione")
	public void actionCambiaAggregazione(	ActionRequest aRequest, 
									ActionResponse aResponse, 
									Model model, 
									@ModelAttribute("navigaAggregata") NavigaAggregata navigaAggregata,
									@RequestParam(required=false, defaultValue="VOLUME", value="pattern") String pattern){
		
		navigaAggregata.setDistribuzione(pattern);
		
		QName eventName = new QName( "http:eventAccediSoggetto/events", "event.accediSoggetto");
		aResponse.setEvent(eventName, navigaAggregata);
		
		model.addAttribute("navigaAggregata", navigaAggregata);
		aResponse.setRenderParameter("pattern", pattern);

	}
	
	@ActionMapping(params="action=navigazione")
	public void actionNavigazione(	ActionRequest aRequest, 
									ActionResponse aResponse, 
									Model model, 
									@ModelAttribute("navigaAggregata") NavigaAggregata navigaAggregata,
									@RequestParam(required=false, value="pattern") String pattern){
		
		navigaAggregata.setIdNatura(ParamUtil.getString(aRequest, "rowIdLiv1"));
		navigaAggregata.setIdAreaSoggetto(ParamUtil.getString(aRequest, "rowIdLiv2"));
		navigaAggregata.setIdCategoriaSoggetto(ParamUtil.getString(aRequest, "rowIdLiv3"));
		navigaAggregata.setIdSottoCategoriaSoggetto(ParamUtil.getString(aRequest, "rowIdLiv4"));
		if(StringUtils.isEmpty(pattern)){
			pattern = ParamUtil.getString(aRequest, "pattern");
			aResponse.setRenderParameter("pattern", pattern);
		}
		
		navigaAggregata.setDistribuzione(pattern);
		model.addAttribute("navigaAggregata", navigaAggregata);
		
		if( Integer.valueOf( navigaAggregata.getIdSottoCategoriaSoggetto() ) > 0 ){
			LiferayPortletURL renderURL = createLiferayPortletURL(aRequest, paginaElencoProgetti, elencoProgettiPortletId, PortletRequest.RENDER_PHASE);
			
			String jsonnavigaaggregata = createJsonStringFromModelAttribute( navigaAggregata );
			renderURL.setParameter("jsonnavigaaggregata", jsonnavigaaggregata); 
			
			try {
				aResponse.sendRedirect( HttpUtil.encodeParameters( renderURL.toString() ) );
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		QName eventName = new QName( "http:eventAccediSoggetto/events", "event.accediSoggetto");
		aResponse.setEvent(eventName, navigaAggregata);
		
	}
	
	@EventMapping(value = "event.accediClassificazione")
    public void processAccediClassificazione(EventRequest eventRequest,
               				EventResponse eventResponse,
               				Model model) throws CloneNotSupportedException {
		
		NavigaAggregata p = (NavigaAggregata) eventRequest.getEvent().getValue();
		NavigaAggregata navigaAggregata = p.clone();
		
		navigaAggregata.rimuoviZero();
		navigaAggregata.setIdAreaSoggetto("0");
		navigaAggregata.setIdCategoriaSoggetto("-1");
		navigaAggregata.setIdSottoCategoriaSoggetto("-1");
		
		model.addAttribute("navigaAggregata", navigaAggregata);
    }
	
	@EventMapping(value = "event.accediLocalizzazione")
    public void processAccediLocalizzazione(EventRequest eventRequest,
               				EventResponse eventResponse,
               				Model model) throws CloneNotSupportedException {
		
		NavigaAggregata navigaAggregata = ((NavigaAggregata) eventRequest.getEvent().getValue()).clone();

		navigaAggregata.rimuoviZero();
		navigaAggregata.setIdAreaSoggetto("0");
		navigaAggregata.setIdCategoriaSoggetto("-1");
		navigaAggregata.setIdSottoCategoriaSoggetto("-1");

		model.addAttribute("navigaAggregata", navigaAggregata);
    }
	
	protected String createJsonStringFromModelAttribute(NavigaAggregata filtro){
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
	
	protected String createJsonStringFromQueryResultAggregataDTO(List<AggregataDTO> formattedResult){
		ObjectMapper mapper= new ObjectMapper();
		String jsonString=null;
		try {
			jsonString = mapper.writeValueAsString(formattedResult);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return jsonString;
	}
	
	private void impostaLinkURL(PortletRequest request, 
			NavigaAggregata sessionAttrNav, 
			List<AggregataDTO> listaAggregataDTO, 
			String pageTo) {

		LiferayPortletURL renderURL = createLiferayPortletURL(request, pageTo, (String) request.getAttribute(WebKeys.PORTLET_ID), PortletRequest.ACTION_PHASE);
		String rowIdLiv1URL = "", rowIdLiv2URL = "", rowIdLiv3URL = "", rowIdLiv4URL = "";

		for(AggregataDTO tmp : listaAggregataDTO){		

			//Per ogni elemento oltre a caricare la descrizione e i valori
			//viene generato un linkURL che punta alla pagina successiva

			rowIdLiv1URL = String.valueOf(sessionAttrNav.getIdNatura());
			
			rowIdLiv2URL = String.valueOf(sessionAttrNav.getIdAreaSoggetto());
			rowIdLiv3URL = String.valueOf(sessionAttrNav.getIdCategoriaSoggetto());
			rowIdLiv4URL = String.valueOf(sessionAttrNav.getIdSottoCategoriaSoggetto());

			if( sessionAttrNav.getIdSottoCategoriaSoggetto().equals("0") ){
				rowIdLiv4URL = tmp.getIdSottocategSoggetto().toString(); 
				tmp.setDescURL( tmp.getDescSottocategSoggetto() );
			}else if( sessionAttrNav.getIdCategoriaSoggetto().equals("0") ){
				rowIdLiv3URL = tmp.getIdCategSoggetto().toString(); 
				rowIdLiv4URL = "0";
				tmp.setDescURL( tmp.getDescCategSoggetto() );
			}else if( sessionAttrNav.getIdAreaSoggetto().equals("0") ){
				rowIdLiv2URL = tmp.getIdAreaSoggetto().toString(); 
				rowIdLiv3URL = "0";
				rowIdLiv4URL = "-1";
				tmp.setDescURL( tmp.getDescAreaSoggetto() );
			}

			renderURL.setParameter("rowIdLiv1", rowIdLiv1URL); 
			renderURL.setParameter("rowIdLiv2", rowIdLiv2URL); 
			renderURL.setParameter("rowIdLiv3", rowIdLiv3URL); 
			renderURL.setParameter("rowIdLiv4", rowIdLiv4URL); 

			renderURL.setParameter("action", "navigazione");

			tmp.setLinkURL(renderURL.toString());
		}
	}
	
	protected String createJsonStringFromQueryResult(List<D3BarConverter> formattedResult){
		ObjectMapper mapper= new ObjectMapper();
		String jsonString=null;
		try {
			jsonString = mapper.writeValueAsString(formattedResult);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return jsonString;
	}
	
	private LiferayPortletURL createLiferayPortletURL(PortletRequest request, String toPage, String portletId, String portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		
		LiferayPortletURL renderURL = null;
		String localHost = themeDisplay.getPortalURL();		
		List<Layout> layouts = null;
		try{
			layouts = LayoutLocalServiceUtil.getLayouts(themeDisplay.getLayout().getGroupId(), false);
			for(Layout layout : layouts){

				String nodeNameRemoved = PortalUtil.getLayoutFriendlyURL(layout, themeDisplay).replace(localHost, "");

				//Viene ricercato l'URL esatto per la pagina successiva
				if(nodeNameRemoved.indexOf(toPage)>0){

					renderURL = PortletURLFactoryUtil.create(request, portletId, layout.getPlid(), portletRequest);
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
