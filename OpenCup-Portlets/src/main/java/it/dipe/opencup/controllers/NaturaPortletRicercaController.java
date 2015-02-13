package it.dipe.opencup.controllers;

import it.dipe.opencup.controllers.common.NaturaPortletCommonController;
import it.dipe.opencup.dto.FiltroRicercaDTO;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.model.AnnoDecisione;
import it.dipe.opencup.model.Comune;
import it.dipe.opencup.model.Provincia;
import it.dipe.opencup.model.Regione;
import it.dipe.opencup.model.StatoProgetto;
import it.dipe.opencup.model.TipologiaIntervento;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.liferay.portal.util.PortalUtil;

@Controller
@RequestMapping("VIEW")
@SessionAttributes("sessionAttrNaturaRicerca")
public class NaturaPortletRicercaController extends NaturaPortletCommonController {
	
	@ModelAttribute("sessionAttrNaturaRicerca")
	public NavigaAggregata sessionAttrNaturaRicerca() {
		return super.sessionAttr();
	}
	
	
	@RenderMapping(params="action=affinaricerca")
	public String handleRenderRicercaRequest(	RenderRequest request, 
												RenderResponse response, 
												Model model, 
												@ModelAttribute("sessionAttrNaturaRicerca") NavigaAggregata sessionAttrNaturaRicerca){
		
		HttpSession session = PortalUtil.getHttpServletRequest(request).getSession(false);
		sessionAttrNaturaRicerca = (session.getAttribute("sessionAttrNaturaRicercaSession")==null)?new NavigaAggregata():(NavigaAggregata) session.getAttribute("sessionAttrNaturaRicercaSession");
		
		//Carico la lista delle regioni
		List<Regione> listRegione = aggregataFacade.findRegioni();
		model.addAttribute("listRegione", listRegione);
		
		if( ! "-1".equals( sessionAttrNaturaRicerca.getIdRegione() ) ){
			//Regione selezionata carico le Province
			List<Provincia> listProvincia = aggregataFacade.findProvinciaByIdRegione(Integer.valueOf( sessionAttrNaturaRicerca.getIdRegione() ));
			model.addAttribute("listProvincia", listProvincia);
		}
		
		if( ! "-1".equals( sessionAttrNaturaRicerca.getIdProvincia() ) ){
			//Provincia selezionata carico i Comuni
			List<Comune> listComune = aggregataFacade.findComuneByIdProvincia(Integer.valueOf( sessionAttrNaturaRicerca.getIdProvincia() ));
			model.addAttribute("listComune", listComune);
		}
		
		//Carico la lista degli Anni Decisione
		List<AnnoDecisione> listaAnnoDecisione = aggregataFacade.findAnniDecisione();
		model.addAttribute("listaAnnoDecisione", listaAnnoDecisione);
		
		//Carico la lista delle Tipologia Intervento
		List<TipologiaIntervento> listaTipologiaIntervento = aggregataFacade.findTipologiaIntervento();
		model.addAttribute("listaTipologiaIntervento", listaTipologiaIntervento);
		
		//Carico la lista degli Stato Progetto
		List<StatoProgetto> listaStatoProgetto = aggregataFacade.findStatoProgetto();
		model.addAttribute("listaStatoProgetto", listaStatoProgetto);
		
		
		model.addAttribute("sessionAttrNaturaRicerca", sessionAttrNaturaRicerca);
				
		return "natura-ricerca-content";
	}
	
	@RenderMapping
	public String handleRenderRequest(	RenderRequest request, 
										RenderResponse response, 
										Model model, 
										@ModelAttribute("sessionAttrNaturaRicerca") NavigaAggregata sessionAttrNaturaRicerca){
		
		HttpSession session = PortalUtil.getHttpServletRequest(request).getSession(false);
		sessionAttrNaturaRicerca = (session.getAttribute("sessionAttrNaturaRicercaSession")==null)?new NavigaAggregata():(NavigaAggregata) session.getAttribute("sessionAttrNaturaRicercaSession");
		
		model.addAttribute("sessionAttrNaturaRicerca", sessionAttrNaturaRicerca);
		
		return "natura-ricerca-view";
	}
	
	
	@ResourceMapping(value =  "loadProvinciaByRegione")	
	public View loadProvinciaByRegione(ResourceRequest request, @RequestParam("pattern") Integer pattern, @ModelAttribute("sessionAttrNaturaRicerca") NavigaAggregata sessionAttrNaturaRicerca){
		
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		
		List<FiltroRicercaDTO> provincie = new ArrayList<FiltroRicercaDTO>();
		FiltroRicercaDTO ele = null;
		for(Provincia provincia : aggregataFacade.findProvinciaByIdRegione(pattern)){
			ele = new FiltroRicercaDTO();
			ele.setId( provincia.getId() );
			ele.setLabel( provincia.getDescProvincia() );
			provincie.add(ele);
		}
		
		view.addStaticAttribute("lista", provincie);
		return view;
	}
	
	@ResourceMapping(value =  "loadComuniByProvincia")	
	public View loadComuniByProvincia(ResourceRequest request, @RequestParam("pattern") Integer pattern, @ModelAttribute("sessionAttrNaturaRicerca") NavigaAggregata sessionAttrNaturaRicerca){
		
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		
		List<FiltroRicercaDTO> provincie = new ArrayList<FiltroRicercaDTO>();
		FiltroRicercaDTO ele = null;
		for(Comune provincia : aggregataFacade.findComuneByIdProvincia(pattern)){
			ele = new FiltroRicercaDTO();
			ele.setId( provincia.getId() );
			ele.setLabel( provincia.getDescComune() );
			provincie.add(ele);
		}
		
		view.addStaticAttribute("lista", provincie);
		return view;
	}
	
	@ActionMapping(params="action=ricerca")
	public void publishEvent(ActionRequest aRequest, ActionResponse aResponse, Model model, @ModelAttribute("sessionAttrNaturaRicerca") NavigaAggregata sessionAttrNaturaRicerca){
		
		HttpSession session = PortalUtil.getHttpServletRequest(aRequest).getSession(false);
		session.setAttribute("sessionAttrNaturaRicercaSession", sessionAttrNaturaRicerca);
		
		QName eventName = new QName( "http:eventFiltraClassificazione/events", "event.filtraClassificazione");

		model.addAttribute("sessionAttrNaturaRicerca", sessionAttrNaturaRicerca);
		
		//Setto l'evento con i parametri letti dalla Query string 
		aResponse.setEvent( eventName, sessionAttrNaturaRicerca );
	}

}
