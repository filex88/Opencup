package it.dipe.opencup.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.dipe.opencup.dto.LocalizationValueConverter;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.facade.AggregataFacade;
import it.dipe.opencup.model.Aggregata;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;
import javax.xml.namespace.QName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLFactoryUtil;

@Controller
@RequestMapping("VIEW")
public class LocalizzazionePortlet0Controller{
	
	@Value("#{config['pagina.elenco.progetti']}")
	private String paginaElencoProgetti;
	
	@Value("#{config['codice.natura.open.cup']}")
	private String codiNaturaOpenCUP;
	
	@Value("#{config['pagina.localizzazione']}")
	private String paginaLocalizzazione;
	
	@Value("#{config['elenco.progetti.instanceId']}")
	private String elencoProgettiPortletId;
	
	@Autowired
	private AggregataFacade aggregataFacade;
	
	@ModelAttribute("navigaAggregata")
	public NavigaAggregata navigaAggregata() {
		
		String idNatura =  (aggregataFacade.findNaturaByCod( codiNaturaOpenCUP )==null)?"0":aggregataFacade.findNaturaByCod( codiNaturaOpenCUP ).getId().toString();
		NavigaAggregata navigaAggregata = new NavigaAggregata();
		navigaAggregata.setIdNatura(idNatura);
		navigaAggregata.setPagAggregata(paginaLocalizzazione);
		navigaAggregata.setIdAreaGeografica("0");
		
		return navigaAggregata;
	}
	
	@RenderMapping
	public String handleRenderRequest(	RenderRequest renderRequest, 
										RenderResponse renderResponse,
										Model model,
										@ModelAttribute("navigaAggregata") NavigaAggregata navigaAggregata,
										@RequestParam(required=false, value="pattern") String pattern ){

		String retval = "localizzazione0-view";
		
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		model.addAttribute("jsFolder",themeDisplay.getPathThemeJavaScript());
		
		boolean flagAreaGeografica = ( Integer.valueOf( navigaAggregata.getIdAreaGeografica() ) > 0 );
		boolean flagRegione = ( Integer.valueOf( navigaAggregata.getIdRegione() ) > 0 );
		
		String idAreaGeografica = navigaAggregata.getIdAreaGeografica();
		String idRegione = navigaAggregata.getIdRegione();
		
		if( "R".equals( navigaAggregata.getIndicatoreNavigaLocalizzazione() ) 
				&&
			"-1".equals( navigaAggregata.getIdRegione() )
		){
			navigaAggregata.setIdAreaGeografica( "0" );
			navigaAggregata.setIdRegione( "0" );
		}
		List<Aggregata> risultati = aggregataFacade.findAggregataByLocalizzazione(navigaAggregata);
		
		navigaAggregata.setIdAreaGeografica( idAreaGeografica );
		navigaAggregata.setIdRegione( idRegione );

		Long numeProgetti = new Long(0);
		double impoCostoProgetti = 0.0;
		double impoImportoFinanziato = 0.0;
		List<LocalizationValueConverter> valori = new ArrayList<LocalizationValueConverter>();
		String strCodAreaGeo = "";
		String strCodRegione= "";
		LocalizationValueConverter areaGeo = null;
		String codice = "";
		String nome = "";
		for (Aggregata aggregata : risultati){
			
			areaGeo = new LocalizationValueConverter();
			codice = "";
			nome = "";

			if(flagAreaGeografica && !flagRegione){
				//Visualizzo un'area geografica precisa
				strCodAreaGeo = aggregata.getLocalizzazione().getAreaGeografica().getCodiAreaGeografica();
				codice = aggregata.getLocalizzazione().getRegione().getCodiRegione();
				nome = aggregata.getLocalizzazione().getRegione().getDescRegione();
			}else if(flagAreaGeografica && flagRegione){
				//Visualizzo una regione
				strCodAreaGeo = aggregata.getLocalizzazione().getAreaGeografica().getCodiAreaGeografica();
				strCodRegione = aggregata.getLocalizzazione().getRegione().getCodiRegione();
				codice = aggregata.getLocalizzazione().getProvincia().getCodiProvincia();
				nome = aggregata.getLocalizzazione().getProvincia().getDescProvincia();
			}else if( "R".equals( navigaAggregata.getIndicatoreNavigaLocalizzazione() ) ){
				//Visualizzo tutta l'italia
				codice = aggregata.getLocalizzazione().getRegione().getCodiRegione();
				nome = aggregata.getLocalizzazione().getRegione().getDescRegione();
			}else{
				//Visualizzo tutta l'italia
				codice = aggregata.getLocalizzazione().getAreaGeografica().getCodiAreaGeografica();
				nome = aggregata.getLocalizzazione().getAreaGeografica().getDescAreaGeografica();
			}
			areaGeo.setLocalizationLabel(codice);
			areaGeo.setFullLabel(nome);
			
			String url = impostaLinkURL(renderRequest, navigaAggregata, aggregata);
			areaGeo.setDetailUrl(url);
			
			areaGeo.setImportoValue(aggregata.getImpoImportoFinanziato());
			impoImportoFinanziato+=areaGeo.getImportoValue();
			
			areaGeo.setCostoValue(aggregata.getImpoCostoProgetti());
			impoCostoProgetti+=areaGeo.getCostoValue();
			
			areaGeo.setVolumeValue(aggregata.getNumeProgetti());
			numeProgetti+=areaGeo.getVolumeValue();
			
			valori.add(areaGeo);
			
		}
		
		if(flagAreaGeografica && !flagRegione){
			model.addAttribute("areaGEO", strCodAreaGeo);
			retval = "localizzazione0area-view";
		}else if(flagAreaGeografica && flagRegione){
			model.addAttribute("codRegione", strCodRegione);
			model.addAttribute("areaGEO", strCodAreaGeo);
			retval = "localizzazione0regione-view";
		}
		
		model.addAttribute("statoSelected",navigaAggregata.getDescStato());
		model.addAttribute("jsonResultLocalizzazione",createJsonStringFromQueryResult(valori));
		// valori totali
		model.addAttribute("volumeDeiProgetti", numeProgetti);
		model.addAttribute("costoDeiProgetti", impoCostoProgetti);
		model.addAttribute("importoFinanziamenti", impoImportoFinanziato);
		
		model.addAttribute("pattern", navigaAggregata.getDistribuzione());
		model.addAttribute("indicatoreNavigaLocalizzazione", navigaAggregata.getIndicatoreNavigaLocalizzazione());
		model.addAttribute("linkallregioni", "#");
		
		return retval;
	}
	
	@ActionMapping(params="action=navigazione")
	public void actionNavigazione(	ActionRequest aRequest, 
									ActionResponse aResponse, 
									Model model, 
									@ModelAttribute("navigaAggregata") NavigaAggregata navigaAggregata,
									@RequestParam(required=false, value="pattern") String pattern){
		
		navigaAggregata.setIdNatura(ParamUtil.getString(aRequest, "rowIdLiv1"));
		navigaAggregata.setIdAreaGeografica(ParamUtil.getString(aRequest, "rowIdLiv2"));
		navigaAggregata.setIdRegione(ParamUtil.getString(aRequest, "rowIdLiv3"));
		navigaAggregata.setIdProvincia(ParamUtil.getString(aRequest, "rowIdLiv4"));
		
		navigaAggregata.setDistribuzione(pattern);
		model.addAttribute("navigaAggregata", navigaAggregata);
		
		if( Integer.valueOf( navigaAggregata.getIdProvincia() ) > 0 ){
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
		
		QName eventName = new QName( "http:eventAccediLocalizzazione/events", "event.accediLocalizzazione");
		aResponse.setEvent(eventName, navigaAggregata);
		
	}

	@ActionMapping(params="action=cambiaAggregazione")
	public void actionCambiaAggregazione(	ActionRequest aRequest, 
									ActionResponse aResponse, 
									Model model, 
									@ModelAttribute("navigaAggregata") NavigaAggregata navigaAggregata,
									@RequestParam(required=false, defaultValue="VOLUME", value="pattern") String pattern){
		
		navigaAggregata.setDistribuzione(pattern);
		model.addAttribute("navigaAggregata", navigaAggregata);
		
		aResponse.setRenderParameter("pattern", pattern);
		
		QName eventName = new QName( "http:eventAccediLocalizzazione/events", "event.accediLocalizzazione");
		aResponse.setEvent(eventName, navigaAggregata);
		
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
	
	private String createJsonStringFromQueryResult(List<LocalizationValueConverter> formattedResult){
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
	
	private String impostaLinkURL(	PortletRequest request,
			NavigaAggregata navigaAggregata, 
			Aggregata aggregata) {

		LiferayPortletURL renderURL = createLiferayPortletURL(request, navigaAggregata.getPagAggregata(), (String) request.getAttribute(WebKeys.PORTLET_ID), PortletRequest.ACTION_PHASE);
		String rowIdLiv1URL = "", rowIdLiv2URL = "", rowIdLiv3URL = "", rowIdLiv4URL = "";

		rowIdLiv1URL = String.valueOf(navigaAggregata.getIdNatura());
		rowIdLiv2URL = String.valueOf(navigaAggregata.getIdAreaGeografica());
		rowIdLiv3URL = String.valueOf(navigaAggregata.getIdRegione());
		rowIdLiv4URL = String.valueOf(navigaAggregata.getIdProvincia());

		if( "R".equals( navigaAggregata.getIndicatoreNavigaLocalizzazione() ) ){
			if( navigaAggregata.getIdProvincia().equals("0") ){
				rowIdLiv4URL = aggregata.getLocalizzazione().getProvincia().getId().toString();
			}else if( navigaAggregata.getIdRegione().equals("0") ){
				rowIdLiv3URL = aggregata.getLocalizzazione().getRegione().getId().toString();
				rowIdLiv4URL = "0";
			}else if( navigaAggregata.getIdAreaGeografica().equals("0") ){
				rowIdLiv2URL = aggregata.getLocalizzazione().getAreaGeografica().getId().toString();
				rowIdLiv3URL = aggregata.getLocalizzazione().getRegione().getId().toString();
				rowIdLiv4URL = "0";
			}
		}else{
			if( navigaAggregata.getIdProvincia().equals("0") ){
				rowIdLiv4URL = aggregata.getLocalizzazione().getProvincia().getId().toString();
			}else if( navigaAggregata.getIdRegione().equals("0") ){
				rowIdLiv3URL = aggregata.getLocalizzazione().getRegione().getId().toString();
				rowIdLiv4URL = "0";
			}else if( navigaAggregata.getIdAreaGeografica().equals("0") ){
				rowIdLiv2URL = aggregata.getLocalizzazione().getAreaGeografica().getId().toString();
				rowIdLiv3URL = "0";
				rowIdLiv4URL = "-1";
			}
		}

		renderURL.setParameter("rowIdLiv1", rowIdLiv1URL); 
		renderURL.setParameter("rowIdLiv2", rowIdLiv2URL); 
		renderURL.setParameter("rowIdLiv3", rowIdLiv3URL); 
		renderURL.setParameter("rowIdLiv4", rowIdLiv4URL); 

		renderURL.setParameter("action", "navigazione");

		return renderURL.toString();
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
