package it.dipe.opencup.controllers;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import it.dipe.opencup.controllers.common.LocalizzazionePortletCommonController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.liferay.portal.util.PortalUtil;


@Controller
@RequestMapping("VIEW")
public class LocalizzazionePortlet3Controller extends LocalizzazionePortletCommonController{
	
	@RenderMapping
	public String handleRenderRequest(RenderRequest request, RenderResponse response,Model model){
		HttpServletRequest httpServletRequest = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(request));
		String regione=httpServletRequest.getParameter("regione")!=null?httpServletRequest.getParameter("regione").toString():"";
		model.addAttribute("selectedRegion", regione);
		return "localizzazione3-view";
	}

}
