package it.dipe.opencup.controllers;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping("VIEW")
public class NaturaPortlet2Controller {

	@RenderMapping
	public String handleRenderRequest(RenderRequest request, RenderResponse response, Model model){
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
