package it.dipe.opencup.controllers;

import it.dipe.opencup.controllers.common.NaturaPortletCommonController;
import it.dipe.opencup.dto.AggregataDTO;
import it.dipe.opencup.dto.DescrizioneValore;
import it.dipe.opencup.dto.NavigaAggregata;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.portlet.bind.annotation.EventMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.liferay.portal.kernel.dao.search.SearchContainer;

@Controller
@RequestMapping("VIEW")
@SessionAttributes("sessionAttrNaturaRiepilogo")
public class NaturaPortlet3Controller extends NaturaPortletCommonController {

	@ModelAttribute("sessionAttrNaturaRiepilogo")
	public NavigaAggregata sessionAttrNaturaRiepilogo() {
		return super.sessionAttr();
	}
	
	@RenderMapping
	public String handleRenderRequest(	RenderRequest request, 
										RenderResponse response, 
										@RequestParam(required = false) String[] pFiltriRicerca,
										Model model, 
										@ModelAttribute("sessionAttrNaturaRiepilogo") NavigaAggregata sessionAttrNaturaRiepilogo){
		
		if( pFiltriRicerca != null && pFiltriRicerca.length == 4 ){
			sessionAttrNaturaRiepilogo.setIdNatura(pFiltriRicerca[0]);
			sessionAttrNaturaRiepilogo.setIdSettoreInternvanto(pFiltriRicerca[1]);
			sessionAttrNaturaRiepilogo.setIdSottosettoreIntervento(pFiltriRicerca[2]);
			sessionAttrNaturaRiepilogo.setIdCategoriaIntervento(pFiltriRicerca[3]);
		}
		
		SearchContainer<DescrizioneValore> searchContainer = new SearchContainer<DescrizioneValore>(request, response.createRenderURL(), null, "There are no nature yet to display.");
		searchContainer.setDelta(maxResult);
		searchContainer.setTotal(3);
		
		List<AggregataDTO> listaAggregataDTO = aggregataFacade.findAggregataByNatura(sessionAttrNaturaRiepilogo);
		Integer numeProgetti = 0;
		Double impoCostoProgetti = 0.0;
		Double impoImportoFinanziato = 0.0;
		
		for(AggregataDTO aggregataDTO : listaAggregataDTO){
			numeProgetti = numeProgetti + aggregataDTO.getNumeProgetti();
			impoCostoProgetti = impoCostoProgetti + aggregataDTO.getImpoCostoProgetti();
			impoImportoFinanziato = impoImportoFinanziato + aggregataDTO.getImpoImportoFinanziato();
		}
		
		List<DescrizioneValore> retval = new ArrayList<DescrizioneValore>();
		retval.add(new DescrizioneValore("VOLUME DEI PROGETTI", numeProgetti));
		retval.add(new DescrizioneValore("COSTO DEI PROGETTI", impoCostoProgetti));
		retval.add(new DescrizioneValore("IMPORTO FINANZIAMENTI", impoImportoFinanziato));
		
		searchContainer.setResults(retval);
		model.addAttribute("searchContainer", searchContainer);
		
		return "natura3-view";
	}
	
	@EventMapping(value = "event.navigaNaturaPie")
	public void processEventPie(EventRequest eventRequest, EventResponse eventResponse) {
		processaEvento(eventRequest, eventResponse);
	}
	
	@EventMapping(value = "event.navigaNatura")
	public void processEvent(EventRequest eventRequest, EventResponse eventResponse) {
		processaEvento(eventRequest, eventResponse);
	}
	
	private void processaEvento(EventRequest eventRequest, EventResponse eventResponse) {
		
		NavigaAggregata naviga = (NavigaAggregata) eventRequest.getEvent().getValue();
		
		String[] pFiltriRicerca = {String.valueOf(naviga.getIdNatura()), 
								   String.valueOf(naviga.getIdSettoreInternvanto()), 
								   String.valueOf(naviga.getIdSottosettoreIntervento()), 
								   String.valueOf(naviga.getIdCategoriaIntervento()) };
		
		eventResponse.setRenderParameter("pFiltriRicerca", pFiltriRicerca);
	}

}
