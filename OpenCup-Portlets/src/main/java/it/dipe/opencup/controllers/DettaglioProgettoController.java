package it.dipe.opencup.controllers;

import it.dipe.opencup.dto.NavigaProgetti;
import it.dipe.opencup.facade.ProgettiFacade;
import it.dipe.opencup.model.Progetti;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.liferay.portal.util.PortalUtil;

@Controller
@RequestMapping("VIEW")
public class DettaglioProgettoController {
	
	@Autowired
	ProgettiFacade progettiFacade;
	
	@RenderMapping
	public String handleRenderRequest(	RenderRequest renderRequest, 
										RenderResponse renderResponse,
										ModelMap modelMap){
		
		HttpSession session = PortalUtil.getHttpServletRequest(renderRequest).getSession(false);
		NavigaProgetti sessionNavigaProgetti = (NavigaProgetti) session.getAttribute("navigaProgetti"); 
		
		if( sessionNavigaProgetti != null && (! sessionNavigaProgetti.getIdProgetto().isEmpty()) ){
			Progetti progetto = progettiFacade.findProgettoById( Integer.valueOf( sessionNavigaProgetti.getIdProgetto() ) );
			modelMap.addAttribute("dettProgetto", progetto);
		}

		return "dettaglio-progetto-view";
	}
	
//	@EventMapping(value = "event.dettaglioProgetto")
//    public void processName(EventRequest eventRequest, EventResponse eventResponse) {
//		NavigaProgetti navigaProgetti = (NavigaProgetti) eventRequest.getEvent().getValue();
//		
//		System.out.println( "!!! navigaProgetti.getIdProgetto(): " + navigaProgetti.getIdProgetto() + " !!!" );
//		
//		eventResponse.setRenderParameter("idProgettoDettaglio", navigaProgetti.getIdProgetto());
//		eventResponse.setRenderParameter("pagElencoProgetti", navigaProgetti.getPagElencoProgetti());
//    }

}
