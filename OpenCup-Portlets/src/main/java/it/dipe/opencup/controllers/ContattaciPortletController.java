package it.dipe.opencup.controllers;

import it.dipe.opencup.dto.NavigaAggregata;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.liferay.portal.kernel.util.ParamUtil;

@Controller
@RequestMapping("VIEW")
public class ContattaciPortletController {

	@RenderMapping
	public String renderRequest(RenderRequest renderRequest, 
								RenderResponse renderResponse,
								Model model, 
								@ModelAttribute("navigaAggregata") NavigaAggregata navigaAggregata){
		
		String orderByCol = ParamUtil.getString(renderRequest, "orderByCol"); 
		String orderByType = ParamUtil.getString(renderRequest, "orderByType");
		String sDelta = ParamUtil.getString(renderRequest, "delta");

		return handleRenderRequest(renderRequest, renderResponse, model, navigaAggregata);
	}
	
	@RenderMapping(params="render=action")
	public String handleRenderRequest(	RenderRequest renderRequest, 
										RenderResponse renderResponse,
										Model model, 
										@ModelAttribute("navigaAggregata") NavigaAggregata navigaAggregata){
		
		if( Integer.valueOf( navigaAggregata.getIdCategoriaIntervento() ) > 0 ) {
			navigaAggregata.setIdCategoriaIntervento("0");
		}
		

		
		
		return "contattaci-view";
	}
}

