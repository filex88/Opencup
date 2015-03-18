package it.dipe.opencup.controllers;

import it.dipe.opencup.dto.NavigaProgetti;
import it.dipe.opencup.facade.ProgettiFacade;
import it.dipe.opencup.model.Progetti;

import java.util.Date;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
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
										Model model){
		
		HttpSession session = PortalUtil.getHttpServletRequest(renderRequest).getSession(false);
		NavigaProgetti sessionNavigaProgetti = (NavigaProgetti) session.getAttribute("navigaProgetti"); 
		
		if( sessionNavigaProgetti != null && (! sessionNavigaProgetti.getIdProgetto().isEmpty()) ){
			Progetti progetto = progettiFacade.findProgettoById( Integer.valueOf( sessionNavigaProgetti.getIdProgetto() ) );
			model.addAttribute("dettProgetto", progetto);
			
			double impFinanziato = progetto.getImpoImportoFinanziato().doubleValue();
			double costoProgetto = progetto.getImpoCostoProgetto().doubleValue();
			
			double percentage = 0.0;
			if( impFinanziato > 0.0 && costoProgetto > 0.0 ){
				percentage = (impFinanziato * 100.0) / costoProgetto;
			}
			model.addAttribute("coperturaPercentuale", percentage );
			
			Double impoCostoProgetto = (progetto.getImpoCostoProgetto() < 0.0)?0.0:progetto.getImpoCostoProgetto();
			Double impoImportoFinanziato = (progetto.getImpoImportoFinanziato() < 0.0)?0.0:progetto.getImpoImportoFinanziato();
			
			model.addAttribute("impoCostoProgetto", impoCostoProgetto );
			model.addAttribute("impoImportoFinanziato", impoImportoFinanziato );

			String addressMap = "Italia";
			String zoomMap = "5";
			String multiLocalizzazione = "N";
			
			String sTmp = progetto.getRegioneProgetto();
			if(sTmp.indexOf( "," ) == -1){
				if( ! StringUtils.isEmpty( sTmp ) ){
					addressMap = addressMap + ", " + sTmp;
					zoomMap = "7";
				}
			}else{
				multiLocalizzazione = "S";
			}
			
			sTmp = progetto.getProvinceProgetto();
			if(sTmp.indexOf( "," ) == -1){
				if( ! StringUtils.isEmpty( sTmp ) ){
					addressMap = addressMap + ", " + sTmp;
					zoomMap = "9";
				}
			}else{
				multiLocalizzazione = "S";
			}
			sTmp = progetto.getComuniProgetto();
			if(sTmp.indexOf( "," ) == -1){
				if( ! StringUtils.isEmpty( sTmp ) ){
					addressMap = addressMap + ", " + sTmp;
					zoomMap = "9";
				}
			}else{
				multiLocalizzazione = "S";
			}
			
			model.addAttribute("addressMap", addressMap);
			model.addAttribute("zoomMap", zoomMap);
			model.addAttribute("multiLocalizzazione", multiLocalizzazione);
			
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
			model.addAttribute("maxDataModifica", maxDataModifica);
			
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
