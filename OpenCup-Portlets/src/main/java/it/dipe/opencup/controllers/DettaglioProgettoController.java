package it.dipe.opencup.controllers;

import it.dipe.opencup.dto.NavigaProgetti;
import it.dipe.opencup.facade.ProgettoFacade;
import it.dipe.opencup.model.Progetto;

import java.io.IOException;
import java.util.Date;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.portal.util.PortalUtil;

@Controller
@RequestMapping("VIEW")
public class DettaglioProgettoController {
	
	@Autowired
	ProgettoFacade progettoFacade;
	
	@RenderMapping
	public String handleRenderRequest(	RenderRequest renderRequest, 
										RenderResponse renderResponse,
										Model model){
		
//		HttpSession session = PortalUtil.getHttpServletRequest(renderRequest).getSession(false);
//		NavigaProgetti sessionNavigaProgetti = (NavigaProgetti) session.getAttribute("navigaProgetti"); 
		
		HttpServletRequest httpServletRequest = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest));
		
		//String jsonnavigaaggregata=httpServletRequest.getParameter("jsonnavigaprogetti")!=null?httpServletRequest.getParameter("jsonnavigaprogetti").toString():"";
		//NavigaProgetti sessionNavigaProgetti = createModelFromJsonString(jsonnavigaaggregata);
		//if( sessionNavigaProgetti != null && (! sessionNavigaProgetti.getIdProgetto().isEmpty()) ){
		
		String idPj=httpServletRequest.getParameter("idPj")!=null?httpServletRequest.getParameter("idPj").toString():"";

		if( idPj != null && (! idPj.isEmpty()) ){
			Progetto progetto = progettoFacade.findProgettoById( Integer.valueOf( idPj ) );
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
			if(sTmp.indexOf( "," ) == -1 && sTmp.toLowerCase().indexOf("tutt") == -1){
				if( ! StringUtils.isEmpty( sTmp ) ){
					addressMap = addressMap + ", " + sTmp;
					zoomMap = "7";
				}
			}else{
				multiLocalizzazione = "S";
			}
			
			sTmp = progetto.getProvinceProgetto();
			if(sTmp.indexOf( "," ) == -1 && sTmp.toLowerCase().indexOf("tutt") == -1){
				if( ! StringUtils.isEmpty( sTmp ) ){
					addressMap = addressMap + ", " + sTmp;
					zoomMap = "9";
				}
			}else{
				multiLocalizzazione = "S";
			}
			sTmp = progetto.getComuniProgetto();
			if(sTmp.indexOf( "," ) == -1 && sTmp.toLowerCase().indexOf("tutt") == -1){
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

//	private NavigaProgetti createModelFromJsonString(String jsonString) {
//		ObjectMapper mapper= new ObjectMapper();
//		NavigaProgetti model=null;
//		try {
//			model = mapper.readValue(jsonString, NavigaProgetti.class);
//		} catch (JsonGenerationException e) {
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return model;
//	}
	
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
