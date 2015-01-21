package it.dipe.opencup.controllers;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller(value = "ProvaController")
@RequestMapping("VIEW")
public class Portlet1Controller {

	
	@RenderMapping
	public String handleRenderRequest(RenderRequest request, RenderResponse response, Model model){
		return "portlet1-view";
	}
}
