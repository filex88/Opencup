package it.dipe.opencup.controllers;

import it.dipe.opencup.dto.AggregataDTO;
import it.dipe.opencup.dto.D3PieConverter;
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
public class PieChartPortletController {
	
	@Autowired
	private AggregataFacade aggregataFacade;
	
	@Value("#{config['codice.natura.open.cup']}")
	private String codiNaturaOpenCUP;
	
	@Value("#{config['pagina.classificazione']}")
	private String paginaClassificazione;
	
	@Value("#{config['pagina.elenco.progetti']}")
	private String paginaElencoProgetti;
	
	@Value("#{config['elenco.progetti.instanceId']}")
	private String elencoProgettiPortletId;
	
	@ModelAttribute("navigaAggregata")
	public NavigaAggregata navigaAggregata() {
		
		String idNatura =  (aggregataFacade.findNaturaByCod( codiNaturaOpenCUP )==null)?"0":aggregataFacade.findNaturaByCod( codiNaturaOpenCUP ).getId().toString();
		NavigaAggregata navigaAggregata = new NavigaAggregata();
		navigaAggregata.setIdNatura(idNatura);
		navigaAggregata.setPagAggregata(paginaClassificazione);
		navigaAggregata.setIdAreaIntervento("0");
		
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
		model.addAttribute("config", config);
		
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		model.addAttribute("jsFolder",themeDisplay.getPathThemeJavaScript());
		
		model.addAttribute("pattern", pattern);
		
		String orderProperty = "";
		if( navigaAggregata.getIdCategoriaIntervento().equals("0") ){
			orderProperty = "classificazione.descCategoria";
		}else if( navigaAggregata.getIdSottosettoreIntervento().equals("0") ){
			orderProperty = "classificazione.descSottosettore";
		}else if( navigaAggregata.getIdAreaIntervento().equals("0") ){
			orderProperty = "classificazione.descAreaIntervento";
		}else if( navigaAggregata.getIdNatura().equals("0") ){
			orderProperty = "classificazione.descNatura";
		}
		navigaAggregata.setOrderProperty(orderProperty);
		navigaAggregata.setOrderType("asc");
		
		List<AggregataDTO> listaAggregataDTO = aggregataFacade.findAggregataByNatura(navigaAggregata);
		
		impostaLinkURL(renderRequest, navigaAggregata, listaAggregataDTO, navigaAggregata.getPagAggregata());
		
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
			
			int percentage = 0;
			if( value > 0 ){
				percentage = (int) round( ((value * 100) / totale), 0);
			}
			
			conv.setPercentage( String.valueOf( percentage ) );
			conv.setValue( value );
			conv.setLinkURL(aggregataDTO.getLinkURL());
			converter.add(conv);

		}

		model.addAttribute("recordCount", converter.size());

		model.addAttribute("aggregati4Pie", createJsonStringFromQueryResult(converter));
		model.addAttribute("navigaAggregata", navigaAggregata);
		
		return "pie-chart-view";
	}

	@ActionMapping(params="action=cambiaAggregazione")
	public void actionCambiaAggregazione(	ActionRequest aRequest, 
									ActionResponse aResponse, 
									Model model, 
									@ModelAttribute("navigaAggregata") NavigaAggregata navigaAggregata,
									@RequestParam(required=false, defaultValue="VOLUME", value="pattern") String pattern){
		
		navigaAggregata.setDistribuzione(pattern);
		
		QName eventName = new QName( "http:eventAccediClassificazione/events", "event.accediClassificazione");
		
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
		navigaAggregata.setIdAreaIntervento(ParamUtil.getString(aRequest, "rowIdLiv2"));
		navigaAggregata.setIdSottosettoreIntervento(ParamUtil.getString(aRequest, "rowIdLiv3"));
		navigaAggregata.setIdCategoriaIntervento(ParamUtil.getString(aRequest, "rowIdLiv4"));

		navigaAggregata.setIdCategoriaIntervento(ParamUtil.getString(aRequest, "rowIdLiv4"));
		if(StringUtils.isEmpty(pattern)){
			pattern = ParamUtil.getString(aRequest, "pattern");
			aResponse.setRenderParameter("pattern", pattern);
		}

		navigaAggregata.setDistribuzione(pattern);
		model.addAttribute("navigaAggregata", navigaAggregata);
		
		if( Integer.valueOf( navigaAggregata.getIdCategoriaIntervento() ) > 0 ){
			LiferayPortletURL renderURL = createLiferayPortletURL(aRequest, paginaElencoProgetti, elencoProgettiPortletId, PortletRequest.RENDER_PHASE);
			
			String jsonnavigaaggregata = createJsonStringFromModelAttribute( navigaAggregata );
			renderURL.setParameter("jsonnavigaaggregata", jsonnavigaaggregata); 
			
			try {
				aResponse.sendRedirect( HttpUtil.encodeParameters( renderURL.toString() ) );// + "&jsonnavigaaggregata="+jsonnavigaaggregata ) );
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		QName eventName = new QName( "http:eventAccediClassificazione/events", "event.accediClassificazione");
		aResponse.setEvent(eventName, navigaAggregata);
		
	}
	
	@ActionMapping(params="action=accedi")
	public void actionAccedi(	ActionRequest aRequest, 
									ActionResponse aResponse, 
									Model model, 
									@ModelAttribute("navigaAggregata") NavigaAggregata navigaAggregata,
									@RequestParam(required=false, value="pattern") String pattern){
		
		if( StringUtils.isEmpty(pattern) ){
			pattern = navigaAggregata.getDistribuzione();
		}else{
			navigaAggregata.setDistribuzione(pattern);
		}
		
		navigaAggregata.setIdNatura(ParamUtil.getString(aRequest, "rowIdLiv1"));
		navigaAggregata.setIdAreaIntervento(ParamUtil.getString(aRequest, "rowIdLiv2"));
		navigaAggregata.setIdSottosettoreIntervento(ParamUtil.getString(aRequest, "rowIdLiv3"));
		navigaAggregata.setIdCategoriaIntervento(ParamUtil.getString(aRequest, "rowIdLiv4"));
		
		navigaAggregata.setDistribuzione(pattern);
		model.addAttribute("navigaAggregata", navigaAggregata);
		
		QName eventName = new QName( "http:eventAccediClassificazione/events", "event.accediClassificazione");
		aResponse.setEvent(eventName, navigaAggregata);
		
	}
	
	@EventMapping(value = "event.accediLocalizzazione")
    public void processAccediLocalizzazione(EventRequest eventRequest,
               				EventResponse eventResponse,
               				Model model) throws CloneNotSupportedException {
		
		NavigaAggregata navigaAggregata = ((NavigaAggregata) eventRequest.getEvent().getValue()).clone();

		navigaAggregata.setIdAreaIntervento("0");
		
		if( "0".equals(navigaAggregata.getIdAreaGeografica()) ){
			navigaAggregata.setIdAreaGeografica("-1");
		}
		if( "0".equals(navigaAggregata.getIdRegione()) ){
			navigaAggregata.setIdRegione("-1");
		}
		if( "0".equals(navigaAggregata.getIdProvincia()) ){
			navigaAggregata.setIdProvincia("-1");
		}

		model.addAttribute("navigaAggregata", navigaAggregata);
    }
	
	@EventMapping(value = "event.accediSoggetto")
    public void processAccediSoggetto(EventRequest eventRequest,
               				EventResponse eventResponse,
               				Model model) throws CloneNotSupportedException {
		
		NavigaAggregata navigaAggregata = ((NavigaAggregata) eventRequest.getEvent().getValue()).clone();

		navigaAggregata.setIdAreaIntervento("0");		
		
		if( "0".equals(navigaAggregata.getIdAreaSoggetto()) ){
			navigaAggregata.setIdAreaSoggetto("-1");
		}
		if( "0".equals(navigaAggregata.getIdCategoriaSoggetto()) ){
			navigaAggregata.setIdCategoriaSoggetto("-1");
		}
		if( "0".equals(navigaAggregata.getIdSottoCategoriaSoggetto()) ){
			navigaAggregata.setIdSottoCategoriaSoggetto("-1");
		}

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
	
	protected String createJsonStringFromQueryResult(List<D3PieConverter> formattedResult){
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

			tmp.setLinkURL(renderURL.toString());
		}
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
	
	private double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
}
