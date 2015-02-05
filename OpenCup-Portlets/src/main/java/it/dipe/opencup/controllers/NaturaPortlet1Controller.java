package it.dipe.opencup.controllers;

import it.dipe.opencup.controllers.common.NaturaPortletCommonController;
import it.dipe.opencup.dto.AggregataDTO;
import it.dipe.opencup.dto.D3PieConverter;
import it.dipe.opencup.dto.NavigaClassificazioneEvent;

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

import com.liferay.portal.kernel.util.ParamUtil;

@Controller
@RequestMapping("VIEW")
@SessionAttributes("sessionAttrPie")
public class NaturaPortlet1Controller extends NaturaPortletCommonController {

	@ModelAttribute("sessionAttrPie")
	public NavigaClassificazioneEvent sessionAttrPie() {
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
	public String handleRenderRequest(	RenderRequest request, 
										RenderResponse response, 
										@RequestParam(required = false) String[] pFiltriRicerca,
										Model model, @ModelAttribute("sessionAttrPie") NavigaClassificazioneEvent sessionAttrPie){
		
		//Setto in sessione i filtri di ricerca processati in processEvent
		if( pFiltriRicerca != null && pFiltriRicerca.length == 4 ){
			sessionAttrPie.setRowIdLiv1(pFiltriRicerca[0]);
			sessionAttrPie.setRowIdLiv2(pFiltriRicerca[1]);
			sessionAttrPie.setRowIdLiv3(pFiltriRicerca[2]);
			sessionAttrPie.setRowIdLiv4(pFiltriRicerca[3]);
		}

		return "natura1-view";
	}

	@ResourceMapping(value =  "aggregati4Pie")	
	public View caricaDati4Pie(ResourceRequest request, @RequestParam("pattern") String pattern, @ModelAttribute("sessionAttrPie") NavigaClassificazioneEvent sessionAttrPie){
		
		List<AggregataDTO> listaAggregataDTO = aggregataFacade.findAggregataByNatura(Integer.valueOf(sessionAttrPie.getRowIdLiv1()), 
																					 Integer.valueOf(sessionAttrPie.getRowIdLiv2()),
																					 Integer.valueOf(sessionAttrPie.getRowIdLiv3()),
																					 Integer.valueOf(sessionAttrPie.getRowIdLiv4()) );
		
		int index = calcolaIndicePagina(sessionAttrPie);
		
		String anchorPortlet = "#natura-portlet1";
		impostaLinkURL(request, sessionAttrPie, index, listaAggregataDTO, anchorPortlet);
		
		List <D3PieConverter> converter = new ArrayList<D3PieConverter>();

		MappingJacksonJsonView view = new MappingJacksonJsonView();
		D3PieConverter conv = null;

		for (AggregataDTO aggregataDTO: listaAggregataDTO){
			conv = new D3PieConverter();
			conv.setId(aggregataDTO.getId().toString());
			
			if( "0".equals(sessionAttrPie.getRowIdLiv4()) ){
				conv.setLabel(aggregataDTO.getDesCategoriaIntervento() );
			}else if( "0".equals(sessionAttrPie.getRowIdLiv3()) ){
				conv.setLabel(aggregataDTO.getDesSottoSettore() );
			}else if( "0".equals(sessionAttrPie.getRowIdLiv2()) ){
				conv.setLabel(aggregataDTO.getDesSettore() );
			}else if( "0".equals(sessionAttrPie.getRowIdLiv1()) ){
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

		
	@EventMapping(value = "event.navigaNatura")
	public void processEvent(EventRequest eventRequest, EventResponse eventResponse) {
	
		NavigaClassificazioneEvent naviga = (NavigaClassificazioneEvent) eventRequest.getEvent().getValue();
		
		String[] pFiltriRicerca = { naviga.getRowIdLiv1(), naviga.getRowIdLiv2(), naviga.getRowIdLiv3(), naviga.getRowIdLiv4() };
		eventResponse.setRenderParameter("pFiltriRicerca", pFiltriRicerca);
	
	}
	
	@ActionMapping(params="action=PublishEvent")
	public void publishEvent(ActionRequest aRequest, ActionResponse aResponse, @ModelAttribute("sessionAttrPie") NavigaClassificazioneEvent sessionAttrPie){

		QName eventName = new QName( "http:eventNavigaNaturaPie/events", "event.navigaNaturaPie");
		
		//Leggo la query String per determinare il link di navigazione
		sessionAttrPie = new NavigaClassificazioneEvent();
		sessionAttrPie.setRowIdLiv1(ParamUtil.getString(aRequest, "rowIdLiv1"));
		sessionAttrPie.setRowIdLiv2(ParamUtil.getString(aRequest, "rowIdLiv2"));
		sessionAttrPie.setRowIdLiv3(ParamUtil.getString(aRequest, "rowIdLiv3"));
		sessionAttrPie.setRowIdLiv4(ParamUtil.getString(aRequest, "rowIdLiv4"));

		//Setto l'evento con i parametri letti dalla Query string 
		aResponse.setEvent( eventName, sessionAttrPie );
	}
	
}
