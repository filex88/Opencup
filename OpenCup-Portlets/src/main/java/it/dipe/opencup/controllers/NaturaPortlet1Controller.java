package it.dipe.opencup.controllers;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping("VIEW")
public class NaturaPortlet1Controller {

	//@Autowired
	//private AggregataFacade aggregataFacade;

	@RenderMapping
	public String handleRenderRequest(RenderRequest request, RenderResponse response, Model model){

		System.out.println("handleRenderRequest");
		
		//List<AggregataDTO> aggregataDTO = aggregataFacade.findAggregataByNatura();

		//model.addAttribute("aggregata", aggregataDTO);

		return "natura1-view";
	}

//	@ActionMapping(params="action=PublishEvent")
//	public void publishName(ActionRequest aRequest, ActionResponse aResponse){
//		
//		System.out.println("publishName");
//		
//		Name name = new Name();
//		name.setFirstName(aRequest.getParameter("firstName"));
//		name.setLastName(aRequest.getParameter("lastName"));
//
//		QName eventName = new QName( "http:eventSample/events", "event.publishName");
//
//		System.out.println("publishName: " + name.getFirstName() + " " + name.getLastName());
//		
//		aResponse.setEvent(eventName, name);
//	}
}
