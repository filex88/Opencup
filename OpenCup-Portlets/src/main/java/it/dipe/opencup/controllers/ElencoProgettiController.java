package it.dipe.opencup.controllers;

import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.facade.ProgettiFacade;
import it.dipe.opencup.model.Progetti;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.liferay.portal.util.PortalUtil;

@Controller
@RequestMapping("VIEW")
public class ElencoProgettiController {
	
	@Autowired
	private ProgettiFacade progettiFacade;
	
	protected static final String SESSION_FILTRI_CLASSIFICAZIONE = "SESSION_FILTRI_CLASSIFICAZIONE";
	
	@RenderMapping
	public String handleRenderRequest(	RenderRequest renderRequest, 
										RenderResponse renderResponse,
										Model model){	
		
		System.out.println("ElencoProgettiController.handleRenderRequest");
		
		HttpSession session = PortalUtil.getHttpServletRequest(renderRequest).getSession(false);
		NavigaAggregata filtri = (session.getAttribute(SESSION_FILTRI_CLASSIFICAZIONE)==null)?null:(NavigaAggregata) session.getAttribute(SESSION_FILTRI_CLASSIFICAZIONE);
		
		System.out.println( filtri.toString() );
		List<Progetti> elencoProgetti = progettiFacade.findElencoProgetti(filtri);	
		
		model.addAttribute("elencoProgetti", elencoProgetti);
		
		return "elenco-progetti-view";
	}
}
