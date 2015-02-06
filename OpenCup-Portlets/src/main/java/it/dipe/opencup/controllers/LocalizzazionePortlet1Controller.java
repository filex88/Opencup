package it.dipe.opencup.controllers;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping("VIEW")
public class LocalizzazionePortlet1Controller {
	
	@RenderMapping
	public String handleRenderRequest(	RenderRequest request, 
										RenderResponse response){
		
		return "localizzazione1-view";
	}

}
