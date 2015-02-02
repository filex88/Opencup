package it.dipe.opencup.controllers;

import it.dipe.opencup.dto.AggregataDTO;
import it.dipe.opencup.facade.AggregataFacade;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

@Controller
@RequestMapping("VIEW")
public class NaturaPortlet2Controller {

	@Value("#{config['paginazione.risultatiPerPagina']}")
	protected int maxResult;
	
	@Autowired
	private AggregataFacade aggregataFacade;
	
	@RenderMapping
	public String handleRenderRequest(RenderRequest request, RenderResponse response, Model model){
		
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
		
		SearchContainer<AggregataDTO> searchContainer = new SearchContainer<AggregataDTO>(request, response.createRenderURL(), null, "There are no nature yet to display.");
		searchContainer.setDelta(maxResult);
		searchContainer.setTotal(aggregataFacade.countAggregataByNatura(0, -1, -1, -1));
		
		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByType(orderByType);

		
		List<AggregataDTO> listaAggregataDTO = aggregataFacade.findAggregataByNatura(0, -1, -1, -1, searchContainer.getCur(), searchContainer.getOrderByCol(), searchContainer.getOrderByType() );
		searchContainer.setResults(listaAggregataDTO);
		
		model.addAttribute("searchContainer", searchContainer);
		
		return "natura2-view";
	}
	

//	@EventMapping(value = "event.publishName")
//	public void processName(EventRequest eventRequest, EventResponse eventResponse) {
//		
//		System.out.println("processName");	
//		
//		Name name = (Name) eventRequest.getEvent().getValue();
//		eventResponse.setRenderParameter("firstName", name.getFirstName());
//		eventResponse.setRenderParameter("lastName", name.getLastName());
//		
//		System.out.println("processName: " + name.getFirstName() + " " + name.getLastName());
//	
//	}

}
