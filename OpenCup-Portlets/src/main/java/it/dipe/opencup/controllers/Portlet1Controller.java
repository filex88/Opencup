package it.dipe.opencup.controllers;

import it.dipe.opencup.dto.Name;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.xml.namespace.QName;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping("VIEW")
public class Portlet1Controller {

//	@Autowired
//	private Portlet1Facade portlet1Facade;

	@RenderMapping
	public String handleRenderRequest(RenderRequest request, RenderResponse response, Model model){

		System.out.println("handleRenderRequest");
//		List<AggregataDTO> aggregataDTO = portlet1Facade.findAggregataByNatura();
//
//		model.addAttribute("aggregata", aggregataDTO);

		return "portlet1-view";
	}

	@ActionMapping(params="myaction=PublishEvent")
	public void publishName(ActionRequest aRequest, ActionResponse aResponse){
		
		System.out.println("publishName");
		
		Name name = new Name();
		name.setFirstName(aRequest.getParameter("firstName"));
		name.setLastName(aRequest.getParameter("lastName"));

		QName eventName = new QName( "http:eventSample/events", "event.publishName");

		System.out.println("publishName: " + name.getFirstName() + " " + name.getLastName());
		
		aResponse.setEvent(eventName, name);
	}
}
