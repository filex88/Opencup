package it.dipe.opencup.controllers;

import it.dipe.opencup.controllers.common.NaturaPortletCommonController;
import it.dipe.opencup.dto.AggregataDTO;
import it.dipe.opencup.dto.D3PieConverter;
import it.dipe.opencup.dto.NavigaAggregata;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.xml.namespace.QName;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.EventMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

@Controller
@RequestMapping("VIEW")
@SessionAttributes("sessionAttrNaturaPie")
public class NaturaPortlet1Controller extends NaturaPortletCommonController {

	@ModelAttribute("sessionAttrNaturaPie")
	public NavigaAggregata sessionAttrNaturaPie() {
		return super.sessionAttr();
	}
	
	public static String getRandomColor() {
		String[] letters = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
		String color = "#";
		for (int i = 0; i < 6; i++ ) {
			color += letters[(int) Math.round(Math.random() * 15)];
		}
		return color;
	}
	
	@RenderMapping
	public String handleRenderRequest(	RenderRequest renderRequest, 
										RenderResponse renderResponse, 
										@RequestParam(required = false) String[] pNavigaClassificazione,
										@RequestParam(required = false) String[] pFiltriRicerca,
										@RequestParam(required = false) String[] pFiltriRicercAnni,
										Model model, 
										@ModelAttribute("sessionAttrNaturaPie") NavigaAggregata sessionAttrNaturaPie){
		
		
		initRender(renderRequest, pNavigaClassificazione, pFiltriRicerca, pFiltriRicercAnni, sessionAttrNaturaPie, NaturaPortletCommonController.SESSION_FILTRI_CLASSIFICAZIONE);

		return "natura1-view";
	}

	@ResourceMapping(value =  "aggregati4Pie")	
	public View caricaDati4Pie(	ResourceRequest request, 
								@RequestParam("pattern") String pattern, 
								@ModelAttribute("sessionAttrNaturaPie") NavigaAggregata sessionAttrNaturaPie){
		
		List<AggregataDTO> listaAggregataDTO = aggregataFacade.findAggregataByNatura(sessionAttrNaturaPie);
		
		int index = calcolaIndicePagina(sessionAttrNaturaPie);
		
		String anchorPortlet = "#natura-portlet1";
		impostaLinkURL(request, sessionAttrNaturaPie, index, listaAggregataDTO, anchorPortlet);
		
		List <D3PieConverter> converter = new ArrayList<D3PieConverter>();

		MappingJacksonJsonView view = new MappingJacksonJsonView();
		D3PieConverter conv = null;

		for (AggregataDTO aggregataDTO: listaAggregataDTO){
			conv = new D3PieConverter();
			conv.setId(aggregataDTO.getId().toString());
			
			if( sessionAttrNaturaPie.getIdCategoriaIntervento().equals("0") ){
				conv.setLabel(aggregataDTO.getDesCategoriaIntervento() );
			}else if( sessionAttrNaturaPie.getIdSottosettoreIntervento().equals("0") ){
				conv.setLabel(aggregataDTO.getDesSottoSettore() );
			}else if( sessionAttrNaturaPie.getIdSettoreInternvanto().equals("0") ){
				conv.setLabel(aggregataDTO.getDesSettore() );
			}else if( sessionAttrNaturaPie.getIdNatura().equals("0") ){
				conv.setLabel(aggregataDTO.getDesNatura());
			}

			if("VOLUME".equals(pattern)){
				conv.setValue(Double.valueOf( aggregataDTO.getNumeProgetti() ));
			}else if("COSTO".equals(pattern)){
				conv.setValue(aggregataDTO.getImpoCostoProgetti());
			}else if("IMPORTO".equals(pattern)){
				conv.setValue(aggregataDTO.getImpoImportoFinanziato());
			}else {
				conv.setValue(0.0);
			}
			
			conv.setLinkURL(aggregataDTO.getLinkURL());
			conv.setColor(getRandomColor());
			converter.add(conv);
		}

		view.addStaticAttribute("aggregati4Pie", converter);
		return view;
	}
	
	@ActionMapping(params="action=PublishEvent")
	public void publishEvent(ActionRequest aRequest, ActionResponse aResponse, Model model){
		
		//Gestione del click sulla sezione della torta
		String nomeAttr = "sessionAttrNaturaPie";
		QName eventName = new QName( "http:eventNavigaClassificazionePie/events", "event.navigaClassificazionePie");
		
		publishEvent(aRequest, aResponse, model, nomeAttr, eventName);
	}
	
	@EventMapping(value = "event.navigaClassificazione")
	public void processEvent(EventRequest eventRequest, EventResponse eventResponse) {
		processEventNavigaClassificazione(eventRequest, eventResponse);
	}
	
	@EventMapping(value = "event.filtraClassificazione")
	public void processEventFiltro(EventRequest eventRequest, EventResponse eventResponse) {
		processEventFiltroClassificazione(eventRequest, eventResponse);
	}

}
