package it.dipe.opencup.controllers;

import it.dipe.opencup.controllers.common.NaturaPortletCommonController;
import it.dipe.opencup.dto.AggregataDTO;
import it.dipe.opencup.dto.NavigaClassificazioneEvent;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.xml.namespace.QName;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.EventMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

@Controller
@RequestMapping("VIEW")
@SessionAttributes("sessionAttrNav")
public class NaturaPortlet2Controller extends NaturaPortletCommonController {
	
	@ModelAttribute("sessionAttrNav")
	public NavigaClassificazioneEvent sessionAttrNav() {
		return super.sessionAttr();
	}
	
	@RenderMapping
	public String handleRenderRequest(RenderRequest request, 
									  RenderResponse response, 
									  Model model, 
									  @RequestParam(required = false) String[] pFiltriRicerca,
									  @ModelAttribute("sessionAttrNav") NavigaClassificazioneEvent sessionAttrNav){
		
		//Setto in sessione i filtri di ricerca processati in processEvent
		if( pFiltriRicerca != null && pFiltriRicerca.length == 4 ){
			sessionAttrNav.setRowIdLiv1(pFiltriRicerca[0]);
			sessionAttrNav.setRowIdLiv2(pFiltriRicerca[1]);
			sessionAttrNav.setRowIdLiv3(pFiltriRicerca[2]);
			sessionAttrNav.setRowIdLiv4(pFiltriRicerca[3]);
		}
				
		/*
		 * Tramite gli elementi RowIdLiv si determina l apagina da caricare, questi elementi possono assumere 3 tipi di valore:
		 * -1 : cerco il dato aggregato per il livello
		 * 0 : cerco tutti i valori per quel livello
		 * > 0 : cerco il dato per l'id indicato
		 * */
		
		int index = calcolaIndicePagina(sessionAttrNav);
		
		//orderByCol is the column name passed in the request while sorting
		String orderByCol = ParamUtil.getString(request, "orderByCol"); 
		if(Validator.isNull(orderByCol)  || Validator.equals("", orderByCol)){
			orderByCol = "numeProgetti";
		}
		
		//orderByType is passed in the request while sorting. It can be either asc or desc
		String orderByType = ParamUtil.getString(request, "orderByType");
		if(Validator.isNull(orderByType)  || Validator.equals("", orderByType)){
		    orderByType = "asc";
		}

		SearchContainer<AggregataDTO> searchContainer = new SearchContainer<AggregataDTO>(request, response.createRenderURL(), null, "Nessun dato trovato per la selezione fatta");
		searchContainer.setDelta(maxResult);
		searchContainer.setTotal(aggregataFacade.countAggregataByNatura(Integer.valueOf(sessionAttrNav.getRowIdLiv1()), 
																		Integer.valueOf(sessionAttrNav.getRowIdLiv2()),
																		Integer.valueOf(sessionAttrNav.getRowIdLiv3()),
																		Integer.valueOf(sessionAttrNav.getRowIdLiv4())) );
		
		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByType(orderByType);

		List<AggregataDTO> listaAggregataDTO = aggregataFacade.findAggregataByNatura(Integer.valueOf(sessionAttrNav.getRowIdLiv1()), 
																					 Integer.valueOf(sessionAttrNav.getRowIdLiv2()),
																					 Integer.valueOf(sessionAttrNav.getRowIdLiv3()),
																					 Integer.valueOf(sessionAttrNav.getRowIdLiv4()), 
																					 searchContainer.getCur(), 
																					 searchContainer.getOrderByCol(), 
																					 searchContainer.getOrderByType() );
		
		String anchorPortlet = "#natura-portlet2";
		
		impostaLinkURL(request, sessionAttrNav, index, listaAggregataDTO, anchorPortlet);
		
		searchContainer.setResults(listaAggregataDTO);
		model.addAttribute("searchContainer", searchContainer);
		model.addAttribute("navigaPer", navigaPer[index]);

		return "natura2-view";
	}
	
	@EventMapping(value = "event.navigaNaturaPie")
	public void processEvent(EventRequest eventRequest, EventResponse eventResponse) {
	
		NavigaClassificazioneEvent naviga = (NavigaClassificazioneEvent) eventRequest.getEvent().getValue();
		
		String[] pFiltriRicerca = { naviga.getRowIdLiv1(), naviga.getRowIdLiv2(), naviga.getRowIdLiv3(), naviga.getRowIdLiv4() };
		eventResponse.setRenderParameter("pFiltriRicerca", pFiltriRicerca);
	
	}

	@ActionMapping(params="action=PublishEvent")
	public void publishEvent(ActionRequest aRequest, ActionResponse aResponse, @ModelAttribute("sessionAttrNav") NavigaClassificazioneEvent sessionAttrNav){
		
		QName eventName = new QName( "http:eventNavigaNatura/events", "event.navigaNatura");
		
		//Leggo la query String per determinare il link di navigazione
		sessionAttrNav = new NavigaClassificazioneEvent();
		sessionAttrNav.setRowIdLiv1(ParamUtil.getString(aRequest, "rowIdLiv1"));
		sessionAttrNav.setRowIdLiv2(ParamUtil.getString(aRequest, "rowIdLiv2"));
		sessionAttrNav.setRowIdLiv3(ParamUtil.getString(aRequest, "rowIdLiv3"));
		sessionAttrNav.setRowIdLiv4(ParamUtil.getString(aRequest, "rowIdLiv4"));

		//Setto l'evento con i parametri letti dalla Query string 
		aResponse.setEvent( eventName, sessionAttrNav );
	}

}
