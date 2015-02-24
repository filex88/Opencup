package it.dipe.opencup.controllers;

import it.dipe.opencup.controllers.common.NaturaPortletCommonController;
import it.dipe.opencup.dto.AggregataDTO;
import it.dipe.opencup.dto.DescrizioneValore;
import it.dipe.opencup.dto.NavigaAggregata;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.portlet.bind.annotation.EventMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.liferay.portal.kernel.dao.search.SearchContainer;

@Controller
@RequestMapping("VIEW")
@SessionAttributes("sessionAttrNaturaRiepilogo")
public class NaturaPortlet3Controller extends NaturaPortletCommonController {

	@ModelAttribute("sessionAttrNaturaRiepilogo")
	public NavigaAggregata sessionAttrNaturaRiepilogo() {
		return super.sessionAttr();
	}
	
	@RenderMapping
	public String handleRenderRequest(	RenderRequest request, 
										RenderResponse response, 
										@RequestParam(required = false) String[] pNavigaClassificazione,
										@RequestParam(required = false) String[] pFiltriRicerca,
										@RequestParam(required = false) String[] pFiltriRicercAnni,
										Model model, 
										@ModelAttribute("sessionAttrNaturaRiepilogo") NavigaAggregata sessionAttrNaturaRiepilogo){
		
		initRender(request, pNavigaClassificazione, pFiltriRicerca, pFiltriRicercAnni, sessionAttrNaturaRiepilogo, NaturaPortletCommonController.SESSION_FILTRI_CLASSIFICAZIONE);
		
		SearchContainer<DescrizioneValore> searchContainer = new SearchContainer<DescrizioneValore>(request, response.createRenderURL(), null, "There are no nature yet to display.");
		searchContainer.setDelta(maxResult);
		searchContainer.setTotal(3);
		
		List<AggregataDTO> listaAggregataDTO = aggregataFacade.findAggregataByNatura(sessionAttrNaturaRiepilogo);
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
		
		searchContainer.setResults(retval);
		model.addAttribute("searchContainer", searchContainer);
		
//		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		
//		String urlElencoProgettiPage = "#";
//		
//		try{
//			
//			String portletId = (String) request.getAttribute(WebKeys.PORTLET_ID);
//			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
//			List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(themeDisplay.getLayout().getGroupId(), false);
//			String localHost = themeDisplay.getPortalURL();		
//			
//			for( Layout layout : layouts ){
//				
//				String nodeNameRemoved = PortalUtil.getLayoutFriendlyURL(layout, themeDisplay).replace(localHost, "");
//				
//				//Viene ricercato l'URL esatto per la pagina successiva
//				if(nodeNameRemoved.indexOf("elencoprogettipage")>0){
//					
//					LiferayPortletURL renderURL = null;
//					renderURL = PortletURLFactoryUtil.create(request, portletId, layout.getPlid(), PortletRequest.ACTION_PHASE);
//					renderURL.setWindowState(WindowState.NORMAL);
//					renderURL.setPortletMode(PortletMode.VIEW);
//					
//					urlElencoProgettiPage = renderURL.toString();
//					
//				}
//			}
//	
//		} catch (SystemException e) {
//			e.printStackTrace();
//		} catch (WindowStateException e) {
//			e.printStackTrace();
//		} catch (PortletModeException e) {
//			e.printStackTrace();
//		} catch (PortalException e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println( "urlElencoProgettiPage: " + urlElencoProgettiPage );
//		
//		model.addAttribute("urlElencoProgettiPage", urlElencoProgettiPage);
//		
//		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		return "natura3-view";
	}

	@EventMapping(value = "event.navigaClassificazionePie")
	public void processEventPie(EventRequest eventRequest, EventResponse eventResponse) {
		processEventNavigaClassificazione(eventRequest, eventResponse);
	}
	
	@EventMapping(value = "event.navigaClassificazione")
	public void processEvent(EventRequest eventRequest, EventResponse eventResponse) {
		processEventNavigaClassificazione(eventRequest, eventResponse);
	}
	
	@EventMapping(value = "event.filtraClassificazione")
	public void processEventFiltro(EventRequest eventRequest, EventResponse eventResponse) {
		processEventFiltroClassificazione(eventRequest, eventResponse);	
	}

}
