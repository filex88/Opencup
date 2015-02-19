package it.dipe.opencup.controllers;

import it.dipe.opencup.controllers.common.NaturaPortletCommonController;
import it.dipe.opencup.dto.AggregataDTO;
import it.dipe.opencup.dto.NavigaAggregata;

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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

@Controller
@RequestMapping("VIEW")
@SessionAttributes("sessionAttrNaturaNav")
public class NaturaPortlet2Controller extends NaturaPortletCommonController {
	
	@ModelAttribute("sessionAttrNaturaNav")
	public NavigaAggregata sessionAttrNaturaNav() {
		return super.sessionAttr();
	}
	
	@RenderMapping
	public String handleRenderRequest(RenderRequest renderRequest, 
									  RenderResponse responseRequest, 
									  Model model, 
									  @RequestParam(required = false) String[] pNavigaClassificazione,
									  @RequestParam(required = false) String[] pFiltriRicerca,
									  @RequestParam(required = false) String[] pFiltriRicercAnni,
									  @ModelAttribute("sessionAttrNaturaNav") NavigaAggregata sessionAttrNaturaNav){
				
		initRender(renderRequest, pNavigaClassificazione, pFiltriRicerca, pFiltriRicercAnni, sessionAttrNaturaNav, NaturaPortletCommonController.SESSION_FILTRI_CLASSIFICAZIONE);
		
		/*
		 * Tramite gli elementi RowIdLiv si determina l apagina da caricare, questi elementi possono assumere 3 tipi di valore:
		 * -1 : cerco il dato aggregato per il livello
		 * 0 : cerco tutti i valori per quel livello
		 * > 0 : cerco il dato per l'id indicato
		 * */
		
		int index = calcolaIndicePagina(sessionAttrNaturaNav);
		
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

		SearchContainer<AggregataDTO> searchContainer = new SearchContainer<AggregataDTO>(renderRequest, responseRequest.createRenderURL(), null, "Nessun dato trovato per la selezione fatta");
		searchContainer.setDelta(maxResult);
		
		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByType(orderByType);

		List<AggregataDTO> listaAggregataDTO = aggregataFacade.findAggregataByNatura(sessionAttrNaturaNav, 
																					 searchContainer.getOrderByCol(), 
																					 searchContainer.getOrderByType() );
		
		searchContainer.setTotal(listaAggregataDTO.size());
		
		String anchorPortlet = "#natura-portlet2";
		
		impostaLinkURL(renderRequest, sessionAttrNaturaNav, index, listaAggregataDTO, anchorPortlet);
		
		listaAggregataDTO = ListUtil.subList(listaAggregataDTO, searchContainer.getStart(), searchContainer.getEnd());       
		searchContainer.setResults(listaAggregataDTO);
		
		model.addAttribute("searchContainer", searchContainer);
		model.addAttribute("navigaPer", navigaPer[index]);

		return "natura2-view";
	}

	@ActionMapping(params="action=PublishEvent")
	public void publishEvent(ActionRequest aRequest, ActionResponse aResponse, Model model){
		
		//Gestione del click sul link della tabella
		String nomeAttr = "sessionAttrNaturaNav";
		QName eventName = new QName( "http:eventNavigaClassificazione/events", "event.navigaClassificazione");
		
		publishEvent(aRequest, aResponse, model, nomeAttr, eventName);
	}
	
	@EventMapping(value = "event.navigaClassificazionePie")
	public void processEvent(EventRequest eventRequest, EventResponse eventResponse) {
		processEventNavigaClassificazione(eventRequest, eventResponse);
	}
	
	@EventMapping(value = "event.filtraClassificazione")
	public void processEventFiltro(EventRequest eventRequest, EventResponse eventResponse) {
		processEventFiltroClassificazione(eventRequest, eventResponse);
	}

}
