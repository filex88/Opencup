package it.dipe.opencup.controllers;

import it.dipe.opencup.controllers.common.PortletCommonController;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.model.Progetti;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;

@Controller
@RequestMapping("VIEW")
public class ElencoProgettiController extends PortletCommonController {

	@ModelAttribute("filtriProgetti")
	public NavigaAggregata modelAttrNaturaRicerca(PortletRequest portletRequest) {
		HttpSession session = PortalUtil.getHttpServletRequest(portletRequest).getSession(false);
		NavigaAggregata filtri = (session.getAttribute(SESSION_FILTRI_RICERCA)==null)?new NavigaAggregata():(NavigaAggregata) session.getAttribute(SESSION_FILTRI_RICERCA);
		return filtri;
	}

	@RenderMapping
	public String handleRenderRequest(	RenderRequest renderRequest, 
										RenderResponse renderResponse,
										Model model,
										@ModelAttribute("filtriProgetti") NavigaAggregata filtriProgetti){	

		// LISTA PROGETTI //
		
		//orderByCol is the column name passed in the request while sorting
		String orderByCol = ParamUtil.getString(renderRequest, "orderByCol"); 
		if(Validator.isNull(orderByCol)  || Validator.equals("", orderByCol)){
			orderByCol = "impoCostoProgetto";
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

		SearchContainer<Progetti> searchContainer = new SearchContainer<Progetti>(renderRequest, renderResponse.createRenderURL(), null, "Nessun dato trovato per la selezione fatta");
		searchContainer.setDelta(delta);
		
		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByType(orderByType);

		List<Progetti> elencoProgetti = progettiFacade.findElencoProgetti(	filtriProgetti, 
																			searchContainer.getOrderByCol(), 
																			searchContainer.getOrderByType());	
		
		searchContainer.setTotal(elencoProgetti.size());
		
		elencoProgetti = ListUtil.subList(elencoProgetti, searchContainer.getStart(), searchContainer.getEnd());       
		searchContainer.setResults(elencoProgetti);
		
		model.addAttribute("searchContainer", searchContainer);
		
		// FINE LISTA PROGETTI //
		
		// MASCHERA RICERCA PROGETTI //
		initInModelMascheraRicerca(model, filtriProgetti);
		
		model.addAttribute("filtriProgetti", filtriProgetti);
		// FINE RICERCA PROGETTI //
		
		return "elenco-progetti-view";
	}
	
	@ActionMapping(params="action=ricerca")
	public void publishEvent(ActionRequest aRequest, ActionResponse aResponse, Model model, @ModelAttribute("filtriProgetti") NavigaAggregata filtri){
		
		HttpSession session = PortalUtil.getHttpServletRequest(aRequest).getSession(false);
		session.setAttribute(SESSION_FILTRI_RICERCA, filtri);
		
		model.addAttribute("filtriProgetti", filtri);
	}
	
	
}
