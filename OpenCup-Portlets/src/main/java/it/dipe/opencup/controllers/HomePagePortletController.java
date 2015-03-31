package it.dipe.opencup.controllers;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping("VIEW")
public class HomePagePortletController {
	@RenderMapping
	public String renderRequest(RenderRequest renderRequest, 
								RenderResponse renderResponse,
								Model model){
		
		return "homepage-view";
	}
}
