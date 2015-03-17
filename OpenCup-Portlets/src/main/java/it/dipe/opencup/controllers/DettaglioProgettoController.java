package it.dipe.opencup.controllers;

import java.util.Date;

import it.dipe.opencup.dto.NavigaProgetti;
import it.dipe.opencup.facade.ProgettiFacade;
import it.dipe.opencup.model.CupLocalizzazione;
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
			
			double impFinanziato = progetto.getImpoImportoFinanziato().doubleValue();
			double costoProgetto = progetto.getImpoCostoProgetto().doubleValue();
			double percentage = (impFinanziato * 100.0) / costoProgetto;
			//double temp = Math.pow(10, 3);
			//percentage = Math.round(percentage * temp) / temp; 
			modelMap.addAttribute("coperturaPercentuale", percentage );
			
			String comuniProgetto = "";
			for( CupLocalizzazione c : progetto.getAnagraficaCup().getCupLocalizzazioneList() ){
				comuniProgetto = ( "".equals(comuniProgetto) )? c.getComune().getDescComune() : comuniProgetto + ", " + c.getComune().getDescComune();
			}
			modelMap.addAttribute("comuniProgetto", comuniProgetto);
			
			String provinceProgetto = "";
			for( CupLocalizzazione c : progetto.getAnagraficaCup().getCupLocalizzazioneList() ){
				if( provinceProgetto.indexOf( c.getProvincia().getDescProvincia() ) == 0 ){
					provinceProgetto = ( "".equals(provinceProgetto) )? c.getProvincia().getDescProvincia() : provinceProgetto + ", " + c.getProvincia().getDescProvincia();
				}
			}
			modelMap.addAttribute("provinceProgetto", provinceProgetto);
			
			String regioneProgetto = "";
			for( CupLocalizzazione c : progetto.getAnagraficaCup().getCupLocalizzazioneList() ){
				if( regioneProgetto.indexOf( c.getRegione().getDescRegione() ) == 0 ){
					regioneProgetto = ( "".equals(regioneProgetto) )? c.getRegione().getDescRegione() : regioneProgetto + ", " + c.getRegione().getDescRegione();
				}
			}
			modelMap.addAttribute("regioneProgetto", regioneProgetto);
			
			Date maxDataModifica = null;
			if( progetto.getAnagraficaCup().getDataUltimaModSSC() != null && progetto.getAnagraficaCup().getDataUltimaModUtente() == null ){
				maxDataModifica = progetto.getAnagraficaCup().getDataUltimaModSSC();
			}else if( progetto.getAnagraficaCup().getDataUltimaModSSC() == null && progetto.getAnagraficaCup().getDataUltimaModUtente() != null ){
				maxDataModifica = progetto.getAnagraficaCup().getDataUltimaModUtente();
			}else if( progetto.getAnagraficaCup().getDataUltimaModSSC() != null && progetto.getAnagraficaCup().getDataUltimaModUtente() != null ){
				if( progetto.getAnagraficaCup().getDataUltimaModSSC().compareTo( progetto.getAnagraficaCup().getDataUltimaModUtente() ) >0 ){
					maxDataModifica = progetto.getAnagraficaCup().getDataUltimaModSSC();
				}else{
					maxDataModifica = progetto.getAnagraficaCup().getDataUltimaModUtente();
				}
			}
			modelMap.addAttribute("maxDataModifica", maxDataModifica);
			
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
