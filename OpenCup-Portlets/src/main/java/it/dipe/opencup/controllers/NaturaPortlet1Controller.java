package it.dipe.opencup.controllers;

import it.dipe.opencup.dto.AggregataDTO;
import it.dipe.opencup.dto.D3PieConverter;
import it.dipe.opencup.facade.AggregataFacade;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

@Controller
@RequestMapping("VIEW")
public class NaturaPortlet1Controller {
	
	@Autowired
	private AggregataFacade aggregataFacade;

	@RenderMapping
	public String handleRenderRequest(RenderRequest request, RenderResponse response, Model model){
		return "natura1-view";
	}
	
	public static String getRandomColor() {
		String[] letters = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
		String color = "#";
		for (int i = 0; i < 6; i++ ) {
			color += letters[(int) Math.round(Math.random() * 15)];
		}
		return color;
	}


	@ResourceMapping(value =  "naturaPortlet1")	
	public View naturaPortlet1(@RequestParam("pattern") String pattern){

		List<AggregataDTO> listaAggregataDTO = aggregataFacade.findAggregataByNatura();
		List <D3PieConverter> converter = new ArrayList<D3PieConverter>();

		MappingJacksonJsonView view = new MappingJacksonJsonView();
		D3PieConverter conv = null;


		for (AggregataDTO aggregataDTO: listaAggregataDTO){
			conv = new D3PieConverter();
			conv.setId(aggregataDTO.getId().toString());
			conv.setLabel(aggregataDTO.getDesNatura());
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

	//	@ActionMapping(params="action=PublishEvent")
	//	public void publishName(ActionRequest aRequest, ActionResponse aResponse){
	//		
	//		System.out.println("publishName");
	//		
	//		Name name = new Name();
	//		name.setFirstName(aRequest.getParameter("firstName"));
	//		name.setLastName(aRequest.getParameter("lastName"));
	//
	//		QName eventName = new QName( "http:eventSample/events", "event.publishName");
	//
	//		System.out.println("publishName: " + name.getFirstName() + " " + name.getLastName());
	//		
	//		aResponse.setEvent(eventName, name);
	//	}
}
