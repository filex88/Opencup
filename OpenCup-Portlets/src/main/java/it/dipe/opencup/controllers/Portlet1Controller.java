package it.dipe.opencup.controllers;

import it.dipe.opencup.dto.AggregataDTO;
import it.dipe.opencup.dto.Name;
import it.dipe.opencup.facade.AgregataFacade;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.xml.namespace.QName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping("VIEW")
public class Portlet1Controller {

	@Autowired
	private AgregataFacade agregataFacade;

	@RenderMapping
	public String handleRenderRequest(RenderRequest request, RenderResponse response, Model model){

		System.out.println("handleRenderRequest");
		List<AggregataDTO> aggregataDTO = agregataFacade.findAggregataByNatura();

		model.addAttribute("aggregata", aggregataDTO);

		return "portlet1-view";
	}

	@ActionMapping(params="action=PublishEvent")
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
