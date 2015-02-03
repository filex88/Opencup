package it.dipe.opencup.controllers;

import it.dipe.opencup.dto.AggregataDTO;
import it.dipe.opencup.dto.DescrizioneValore;
import it.dipe.opencup.dto.NavigaClassificazioneEvent;
import it.dipe.opencup.facade.AggregataFacade;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@SessionAttributes("sessionAttrRiepilogo")
public class NaturaPortlet3Controller {

	@Value("#{config['paginazione.risultatiPerPagina']}")
	protected int maxResult;
	
	@Autowired
	private AggregataFacade aggregataFacade;
	
	@ModelAttribute("sessionAttrRiepilogo")
	public NavigaClassificazioneEvent sessionAttrRiepilogo() {
		NavigaClassificazioneEvent sessionAttrRiepilogo = new NavigaClassificazioneEvent();
		sessionAttrRiepilogo.setRowIdLiv1("0");
		sessionAttrRiepilogo.setRowIdLiv2("-1");
		sessionAttrRiepilogo.setRowIdLiv3("-1");
		sessionAttrRiepilogo.setRowIdLiv4("-1");
		return sessionAttrRiepilogo;
	}
	
	@RenderMapping
	public String handleRenderRequest(	RenderRequest request, 
										RenderResponse response, 
										@RequestParam(required = false) String[] navigaNatura,
										Model model, @ModelAttribute("sessionAttrRiepilogo") NavigaClassificazioneEvent sessionAttrRiepilogo){
		
		if( navigaNatura != null && navigaNatura.length == 4 ){
			sessionAttrRiepilogo.setRowIdLiv1(navigaNatura[0]);
			sessionAttrRiepilogo.setRowIdLiv2(navigaNatura[1]);
			sessionAttrRiepilogo.setRowIdLiv3(navigaNatura[2]);
			sessionAttrRiepilogo.setRowIdLiv4(navigaNatura[3]);
		}
		
		SearchContainer<DescrizioneValore> searchContainer = new SearchContainer<DescrizioneValore>(request, response.createRenderURL(), null, "There are no nature yet to display.");
		searchContainer.setDelta(maxResult);
		searchContainer.setTotal(3);
		
		List<AggregataDTO> listaAggregataDTO = aggregataFacade.findAggregataByNatura(Integer.valueOf(sessionAttrRiepilogo.getRowIdLiv1()), 
																					 Integer.valueOf(sessionAttrRiepilogo.getRowIdLiv2()),
																					 Integer.valueOf(sessionAttrRiepilogo.getRowIdLiv3()),
																					 Integer.valueOf(sessionAttrRiepilogo.getRowIdLiv4()) );
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
	
	@EventMapping(value = "event.navigaNatura")
	public void processEvent(EventRequest eventRequest, EventResponse eventResponse) {
	
		NavigaClassificazioneEvent naviga = (NavigaClassificazioneEvent) eventRequest.getEvent().getValue();
		
		String[] navigaNatura = { naviga.getRowIdLiv1(), naviga.getRowIdLiv2(), naviga.getRowIdLiv3(), naviga.getRowIdLiv4() };
		eventResponse.setRenderParameter("navigaNatura", navigaNatura);

	
	}

}
