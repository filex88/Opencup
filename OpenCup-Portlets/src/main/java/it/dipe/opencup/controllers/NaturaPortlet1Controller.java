package it.dipe.opencup.controllers;

import it.dipe.opencup.dto.AggregataDTO;
import it.dipe.opencup.dto.D3PieConverter;
import it.dipe.opencup.dto.NavigaClassificazioneEvent;
import it.dipe.opencup.facade.AggregataFacade;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.portlet.bind.annotation.EventMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

@Controller
@RequestMapping("VIEW")
@SessionAttributes("navigaclassificazione1Controller")
public class NaturaPortlet1Controller {
	
	@Autowired
	private AggregataFacade aggregataFacade;

	@ModelAttribute("navigaclassificazione1Controller")
	public NavigaClassificazioneEvent navigaClassificazioneEvent() {
		NavigaClassificazioneEvent retval = new NavigaClassificazioneEvent();
		retval.setRowIdLiv1("0");
		retval.setRowIdLiv2("-1");
		retval.setRowIdLiv3("-1");
		retval.setRowIdLiv4("-1");
		return retval;
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
										@RequestParam(required = false) String[] navigaNatura,
										Model model, @ModelAttribute("navigaclassificazione1Controller") NavigaClassificazioneEvent navigaclassificazione1Controller){
		
		if( navigaNatura != null && navigaNatura.length == 4 ){
			navigaclassificazione1Controller.setRowIdLiv1(navigaNatura[0]);
			navigaclassificazione1Controller.setRowIdLiv2(navigaNatura[1]);
			navigaclassificazione1Controller.setRowIdLiv3(navigaNatura[2]);
			navigaclassificazione1Controller.setRowIdLiv4(navigaNatura[3]);
		}

		return "natura1-view";
	}

	@ResourceMapping(value =  "naturaPortlet1")	
	public View naturaPortlet1(@RequestParam("pattern") String pattern, @ModelAttribute("navigaclassificazione1Controller") NavigaClassificazioneEvent navigaclassificazione1Controller){
		
		List<AggregataDTO> listaAggregataDTO = aggregataFacade.findAggregataByNatura(Integer.valueOf(navigaclassificazione1Controller.getRowIdLiv1()), 
																					 Integer.valueOf(navigaclassificazione1Controller.getRowIdLiv2()),
																					 Integer.valueOf(navigaclassificazione1Controller.getRowIdLiv3()),
																					 Integer.valueOf(navigaclassificazione1Controller.getRowIdLiv4()) );
		List <D3PieConverter> converter = new ArrayList<D3PieConverter>();

		MappingJacksonJsonView view = new MappingJacksonJsonView();
		D3PieConverter conv = null;


		for (AggregataDTO aggregataDTO: listaAggregataDTO){
			conv = new D3PieConverter();
			conv.setId(aggregataDTO.getId().toString());
			
			if( "0".equals(navigaclassificazione1Controller.getRowIdLiv4()) ){
				conv.setLabel(aggregataDTO.getDesCategoriaIntervento() );
			}else if( "0".equals(navigaclassificazione1Controller.getRowIdLiv3()) ){
				conv.setLabel(aggregataDTO.getDesSottoSettore() );
			}else if( "0".equals(navigaclassificazione1Controller.getRowIdLiv2()) ){
				conv.setLabel(aggregataDTO.getDesSettore() );
			}else if( "0".equals(navigaclassificazione1Controller.getRowIdLiv1()) ){
				conv.setLabel(aggregataDTO.getDesNatura());
			}

			if("VOLUME".equals(pattern)){
				conv.setValue(Double.valueOf( aggregataDTO.getNumeProgetti() ));
			}
			else if("COSTO".equals(pattern)){
				conv.setValue(aggregataDTO.getImpoCostoProgetti());
			}
			else if("IMPORTO".equals(pattern)){
				conv.setValue(aggregataDTO.getImpoImportoFinanziato());
			}
			else {
				conv.setValue(0.0);
			}

			conv.setColor(getRandomColor());

			converter.add(conv);
		}

		view.addStaticAttribute("naturaPortlet1", converter);
		return view;
	}

		
	@EventMapping(value = "event.navigaNatura")
	public void processName(EventRequest eventRequest, EventResponse eventResponse) {
	
		NavigaClassificazioneEvent navigaclassificazione1Controller = (NavigaClassificazioneEvent) eventRequest.getEvent().getValue();
		
		String[] navigaNatura = { navigaclassificazione1Controller.getRowIdLiv1(), navigaclassificazione1Controller.getRowIdLiv2(), navigaclassificazione1Controller.getRowIdLiv3(), navigaclassificazione1Controller.getRowIdLiv4() };
		eventResponse.setRenderParameter("navigaNatura", navigaNatura);
		
//		Name name = (Name) eventRequest.getEvent().getValue();
//		eventResponse.setRenderParameter("firstName", name.getFirstName());
//		eventResponse.setRenderParameter("lastName", name.getLastName());

	
	}
}
