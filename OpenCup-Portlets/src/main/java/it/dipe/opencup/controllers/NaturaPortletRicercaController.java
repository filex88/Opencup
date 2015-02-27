package it.dipe.opencup.controllers;

import it.dipe.opencup.controllers.common.NaturaPortletCommonController;
import it.dipe.opencup.dto.NavigaAggregata;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.liferay.portal.util.PortalUtil;

@Controller
@RequestMapping("VIEW")
public class NaturaPortletRicercaController extends NaturaPortletCommonController {
	
	@RenderMapping
	public String handleRenderRequest(	RenderRequest request, 
										RenderResponse response, 
										Model model,
										@ModelAttribute("modelAttrNaturaRicerca") NavigaAggregata modelAttrNaturaRicerca){

		HttpSession session = PortalUtil.getHttpServletRequest(request).getSession(false);
		modelAttrNaturaRicerca = (session.getAttribute(SESSION_FILTRI_RICERCA)==null)?new NavigaAggregata(NavigaAggregata.NAVIGA_CLASSIFICAZIONE):(NavigaAggregata) session.getAttribute(SESSION_FILTRI_RICERCA);
		
		impostaDesFiltriImpostati(modelAttrNaturaRicerca);

		model.addAttribute("modelAttrNaturaRicerca", modelAttrNaturaRicerca);

		return "natura-ricerca-view";
	}

	private void impostaDesFiltriImpostati( NavigaAggregata filtro ) {
		
		if( Integer.valueOf( filtro.getIdAreaGeografica() ) > 0 ){
			filtro.setDescAreaGeografica( aggregataFacade.findAreaGeografica(Integer.valueOf( filtro.getIdAreaGeografica() )).getDescAreaGeografica() );
		}else{
			filtro.setDescAreaGeografica(null);
		}
		
		if( Integer.valueOf( filtro.getIdRegione() ) > 0 ){
			filtro.setDescRegione( aggregataFacade.findRegione(Integer.valueOf( filtro.getIdRegione() )).getDescRegione() );
		}else{
			filtro.setDescRegione(null);
		}
		
		if( Integer.valueOf( filtro.getIdProvincia() ) > 0 ){
			filtro.setDescProvincia( aggregataFacade.findProvincia( Integer.valueOf( filtro.getIdProvincia() )).getDescProvincia() );
		}else{
			filtro.setDescProvincia(null);
		}
		
		if( Integer.valueOf( filtro.getIdComune() ) > 0 ){
			filtro.setDescComune( aggregataFacade.findComune(Integer.valueOf( filtro.getIdComune() )).getDescComune() );
		}else{
			filtro.setDescComune(null);
		}
		
		String lDescAnno = "";
		if( 	(		(filtro.getIdAnnoDecisiones().size() == 1) &&
						(Integer.valueOf( filtro.getIdAnnoDecisiones().get(0) ) > 0)
				) || (
						(filtro.getIdAnnoDecisiones().size() > 1) &&
						(Integer.valueOf( filtro.getIdAnnoDecisiones().get(1) ) > 0)	)	
		){
			for(String tmp: filtro.getIdAnnoDecisiones()){
				if( "".equals(lDescAnno) ){
					lDescAnno = (aggregataFacade.findAnniDecisione(Integer.valueOf( tmp )) ).getAnnoDadeAnnoDecisione();
				}else{
					lDescAnno = lDescAnno + ", " + (aggregataFacade.findAnniDecisione(Integer.valueOf( tmp )) ).getAnnoDadeAnnoDecisione();
				}
			}
		}
		filtro.setDescAnnoDecisiones( "".equals(lDescAnno)?null:lDescAnno );
		
		if( Integer.valueOf( filtro.getIdTipologiaIntervento() ) > 0 ){
			filtro.setDescTipologiaIntervento( aggregataFacade.findTipologiaIntervento(Integer.valueOf( filtro.getIdTipologiaIntervento() )).getDescTipologiaIntervento() );
		}else{
			filtro.setDescTipologiaIntervento(null);
		}
		
		if( Integer.valueOf( filtro.getIdStatoProgetto() ) > 0 ){
			filtro.setDescStatoProgetto( aggregataFacade.findStatoProgetto( Integer.valueOf( filtro.getIdStatoProgetto() )).getDescStatoProgetto() );
		}else{
			filtro.setDescStatoProgetto(null);
		}
		
		if( Integer.valueOf( filtro.getIdCategoriaSoggetto() ) > 0 ){
			filtro.setDescCategoriaSoggetto( aggregataFacade.findCategoriaSoggetto(Integer.valueOf( filtro.getIdCategoriaSoggetto() )).getDescCategoriaSoggetto() );
		}else{
			filtro.setDescCategoriaSoggetto(null);
		}
		
		if( Integer.valueOf( filtro.getIdSottoCategoriaSoggetto() ) > 0 ){
			filtro.setDescSottoCategoriaSoggetto( aggregataFacade.findSottoCategoriaSoggetto(Integer.valueOf( filtro.getIdSottoCategoriaSoggetto() )).getDescSottocategSoggetto() );
		}else{
			filtro.setDescSottoCategoriaSoggetto(null);
		}
		
		if( Integer.valueOf( filtro.getIdNatura() ) > 0 ){
			filtro.setDescNatura( aggregataFacade.findNatura(Integer.valueOf( filtro.getIdNatura() )).getDescNatura() );
		}else{
			filtro.setDescNatura(null);
		}
		
		if( Integer.valueOf( filtro.getIdSettoreIntervento() ) > 0 ){
			filtro.setDescSettoreIntervento( aggregataFacade.findSettoreIntervento(Integer.valueOf( filtro.getIdSettoreIntervento() )).getDescSettoreIntervento() );
		}else{
			filtro.setDescSettoreIntervento(null);
		}
		
		if( Integer.valueOf( filtro.getIdSottosettoreIntervento() ) > 0 ){
			filtro.setDescSottosettoreIntervento( aggregataFacade.findSottosettoreIntervento(Integer.valueOf( filtro.getIdSottosettoreIntervento() )).getDescSottosettoreInt() );
		}else{
			filtro.setDescSottosettoreIntervento(null);
		}
		
		if( Integer.valueOf( filtro.getIdCategoriaIntervento() ) > 0 ){
			filtro.setDescCategoriaIntervento( aggregataFacade.findCategoriaIntervento(Integer.valueOf( filtro.getIdCategoriaIntervento() )).getDescCategoriaIntervento() );
		}else{
			filtro.setDescCategoriaIntervento(null);
		}
		
	}


	@RenderMapping(params="action=affinaricerca")
	public String handleRenderRicercaRequest(	RenderRequest request, 
												RenderResponse response, 
												Model model, 
												@ModelAttribute("modelAttrNaturaRicerca") NavigaAggregata modelAttrNaturaRicerca){
		
		HttpSession session = PortalUtil.getHttpServletRequest(request).getSession(false);
		modelAttrNaturaRicerca = (session.getAttribute(SESSION_FILTRI_RICERCA)==null)?new NavigaAggregata(NavigaAggregata.NAVIGA_CLASSIFICAZIONE):(NavigaAggregata) session.getAttribute(SESSION_FILTRI_RICERCA);

		initInModelMascheraRicerca(model, modelAttrNaturaRicerca);

		model.addAttribute("modelAttrNaturaRicerca", modelAttrNaturaRicerca);

		return "natura-ricerca-content";
	}
	
	@ActionMapping(params="action=ricerca")
	public void actionPublishEvent(	ActionRequest aRequest, 
									ActionResponse aResponse, 
									Model model, 
									@ModelAttribute("modelAttrNaturaRicerca") NavigaAggregata modelAttrNaturaRicerca){
		//Metto in sessione il filtro
		HttpSession session = PortalUtil.getHttpServletRequest(aRequest).getSession(false);
		session.setAttribute(SESSION_FILTRI_RICERCA, modelAttrNaturaRicerca);
	}

}
