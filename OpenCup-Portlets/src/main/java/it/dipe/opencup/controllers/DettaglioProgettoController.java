package it.dipe.opencup.controllers;

import it.dipe.opencup.facade.ProgettoFacade;
import it.dipe.opencup.model.Progetto;

import java.util.Date;
import java.util.List;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLFactoryUtil;

@Controller
@RequestMapping("VIEW")
public class DettaglioProgettoController {
	
	@Autowired
	ProgettoFacade progettoFacade;
	
	@RenderMapping
	public String handleRenderRequest(	RenderRequest renderRequest, 
										RenderResponse renderResponse,
										Model model, 
										@RequestParam(required=false, value="returnUrl") String returnUrl,
										@RequestParam(required=false, value="idPj") String idPj
										){

		HttpServletRequest httpServletRequest = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest));
		String codiceCup = "";
		if( StringUtils.isEmpty( idPj ) ){
			idPj = (httpServletRequest.getParameter("idPj") != null) ? httpServletRequest.getParameter("idPj").toString() : "";
		}

		if( idPj != null && (! idPj.isEmpty()) ){
			Progetto progetto = progettoFacade.findProgettoById( Integer.valueOf( idPj ) );
			model.addAttribute("dettProgetto", progetto);
			codiceCup = progetto.getAnagraficaCup().getCodiCup();
			double impFinanziato = progetto.getImpoImportoFinanziato().doubleValue();
			double costoProgetto = progetto.getImpoCostoProgetto().doubleValue();
			
			double percentage = 0.0;
			if( impFinanziato > 0.0 && costoProgetto > 0.0 ){
				percentage = (impFinanziato * 100.0) / costoProgetto;
			}
			model.addAttribute("coperturaPercentuale", percentage );
			
			Double impoCostoProgetto = (progetto.getImpoCostoProgetto() < 0.0)?0.0:progetto.getImpoCostoProgetto();
			Double impoImportoFinanziato = (progetto.getImpoImportoFinanziato() < 0.0)?0.0:progetto.getImpoImportoFinanziato();
			
			model.addAttribute("impoCostoProgetto", impoCostoProgetto );
			model.addAttribute("impoImportoFinanziato", impoImportoFinanziato );

			String addressMap = "Italia";
			String zoomMap = "5";
			String multiLocalizzazione = "N";
			
			String sTmp = progetto.getRegioneProgetto();
			if(sTmp.indexOf( "," ) == -1 && sTmp.toLowerCase().indexOf("tutt") == -1){
				if( ! StringUtils.isEmpty( sTmp ) ){
					addressMap = addressMap + ", " + sTmp;
					zoomMap = "7";
				}
			}else{
				multiLocalizzazione = "S";
			}
			
			sTmp = progetto.getProvinceProgetto();
			if(sTmp.indexOf( "," ) == -1 && sTmp.toLowerCase().indexOf("tutt") == -1){
				if( ! StringUtils.isEmpty( sTmp ) ){
					addressMap = addressMap + ", " + sTmp;
					zoomMap = "9";
				}
			}else{
				multiLocalizzazione = "S";
			}
			sTmp = progetto.getComuniProgetto();
			if(sTmp.indexOf( "," ) == -1 && sTmp.toLowerCase().indexOf("tutt") == -1){
				if( ! StringUtils.isEmpty( sTmp ) ){
					addressMap = addressMap + ", " + sTmp;
					zoomMap = "9";
				}
			}else{
				multiLocalizzazione = "S";
			}
			
			model.addAttribute("addressMap", addressMap);
			model.addAttribute("zoomMap", zoomMap);
			model.addAttribute("multiLocalizzazione", multiLocalizzazione);
			
			Date maxDataModifica = null;
			if( progetto.getAnagraficaCup().getDataUltimaModSSC() != null && progetto.getAnagraficaCup().getDataUltimaModUtente() == null ){
				maxDataModifica = progetto.getAnagraficaCup().getDataUltimaModSSC();
			}else if( progetto.getAnagraficaCup().getDataUltimaModSSC() == null && progetto.getAnagraficaCup().getDataUltimaModUtente() != null ){
				maxDataModifica = progetto.getAnagraficaCup().getDataUltimaModUtente();
			}else if( progetto.getAnagraficaCup().getDataUltimaModSSC() != null && progetto.getAnagraficaCup().getDataUltimaModUtente() != null ){
				if( progetto.getAnagraficaCup().getDataUltimaModSSC().compareTo( progetto.getAnagraficaCup().getDataUltimaModUtente() ) >0 ){
					maxDataModifica = progetto.getAnagraficaCup().getDataUltimaModSSC();
				}else{
					maxDataModifica = progetto.getAnagraficaCup().getDataUltimaModUtente();
				}
			}
			model.addAttribute("maxDataModifica", maxDataModifica);
			
		}
		if (!Validator.isBlank(returnUrl)) {
			model.addAttribute("returnUrl", returnUrl);
		}else{
			model.addAttribute("returnUrl", "");
		}
		
		LiferayPortletURL renderURL = createLiferayPortletURL(renderRequest, "contattaci", "contattaciportlet_WAR_OpenCupPortletsportlet", PortletRequest.RENDER_PHASE);
		renderURL.setParameter("codiceCup", codiceCup);
		model.addAttribute("contattaciUrl", renderURL.toString());

		return "dettaglio-progetto-view";
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

//	private NavigaProgetti createModelFromJsonString(String jsonString) {
//		ObjectMapper mapper= new ObjectMapper();
//		NavigaProgetti model=null;
//		try {
//			model = mapper.readValue(jsonString, NavigaProgetti.class);
//		} catch (JsonGenerationException e) {
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return model;
//	}
	
//	@EventMapping(value = "event.dettaglioProgetto")
//    public void processName(EventRequest eventRequest, EventResponse eventResponse) {
//		NavigaProgetti navigaProgetti = (NavigaProgetti) eventRequest.getEvent().getValue();
//		
//		System.out.println( "!!! navigaProgetti.getIdProgetto(): " + navigaProgetti.getIdProgetto() + " !!!" );
//		
//		eventResponse.setRenderParameter("idProgettoDettaglio", navigaProgetti.getIdProgetto());
//		eventResponse.setRenderParameter("pagElencoProgetti", navigaProgetti.getPagElencoProgetti());
//    }

}
