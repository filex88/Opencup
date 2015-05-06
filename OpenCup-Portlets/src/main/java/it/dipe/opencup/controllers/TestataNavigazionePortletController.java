package it.dipe.opencup.controllers;

import it.dipe.opencup.dto.AggregataDTO;
import it.dipe.opencup.dto.D3BarConverter;
import it.dipe.opencup.dto.D3PieConverter;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.facade.AggregataFacade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.portlet.bind.annotation.EventMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

@Controller
@RequestMapping("VIEW")
public class TestataNavigazionePortletController {
	
	@Autowired
	private AggregataFacade aggregataFacade;
	
	@Value("#{config['codice.natura.open.cup']}")
	private String codiNaturaOpenCUP;
	
	private NavigaAggregata newNavigaAggregata() {
		String idNatura =  (aggregataFacade.findNaturaByCod( codiNaturaOpenCUP )==null)?"0":aggregataFacade.findNaturaByCod( codiNaturaOpenCUP ).getId().toString();
		NavigaAggregata navigaAggregata = new NavigaAggregata();
		navigaAggregata.setIdNatura(idNatura);
		return navigaAggregata;
	}
	
	@ModelAttribute("navigaAggregata")
	public NavigaAggregata navigaAggregata() {
		return newNavigaAggregata();
	}
	
	@ModelAttribute("navigaAggregataStato")
	public NavigaAggregata navigaAggregataStato() {
		return newNavigaAggregata();
	}
	
	@ModelAttribute("navigaAggregataAnno")
	public NavigaAggregata navigaAggregataAnno() {
		return newNavigaAggregata();
	}
	
	@RenderMapping
	public String renderRequest(RenderRequest renderRequest, 
								RenderResponse renderResponse,
								Model model,
								@ModelAttribute("navigaAggregata") NavigaAggregata navigaAggregata,
								@ModelAttribute("navigaAggregataStato") NavigaAggregata navigaAggregataStato,
								@ModelAttribute("navigaAggregataAnno") NavigaAggregata navigaAggregataAnno){
		
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		model.addAttribute("jsFolder",themeDisplay.getPathThemeJavaScript());
		model.addAttribute("imgFolder",themeDisplay.getPathThemeImages());
		
		
		
		navigaAggregata.rimuoviZero();
		
		List<AggregataDTO> risultati = aggregataFacade.findAggregataByNatura(navigaAggregata);
		model.addAttribute("jsonResultRiepilogo",createJsonStringFromQueryResult(risultati));
		
		// PIE BY STATO
		navigaAggregataStato.rimuoviZero();
		navigaAggregataStato.setIdStatoProgetto("0");
		navigaAggregataStato.setOrderProperty("statoProgetto.descStatoProgetto");
		navigaAggregataStato.setOrderType("asc");

		List<AggregataDTO> risultati4Stato = aggregataFacade.findAggregataByNatura(navigaAggregataStato);
		
//		System.out.println( "X STATO" );
//		for( AggregataDTO tmp : risultati4Stato ){
//			System.out.println( tmp.getAnnoAnnoAggregato() + "  --> " + tmp.getDesStatoProgetto() + " --> " + tmp.getNumeProgetti() );
//		}
		
		List <D3PieConverter> converter = new ArrayList<D3PieConverter>();
		D3PieConverter conv = null;
		for( AggregataDTO aggregataDTO: risultati4Stato ){
			conv = new D3PieConverter();
			conv.setId(aggregataDTO.getId().toString());
			conv.setLabel(aggregataDTO.getDesStatoProgetto());
			conv.setValue( Double.valueOf( aggregataDTO.getNumeProgetti() ) );
			converter.add(conv);
		}
		model.addAttribute("jsonResultDistribuzione4PieTestataStato", createJsonStringFromQueryResultD3PieConverter(converter));
		model.addAttribute("recordCountStato", converter.size() );
		
		// HISTOGRAM BY ANNO
		navigaAggregataAnno.rimuoviZero();
		int endYear = Calendar.getInstance().get(Calendar.YEAR);
		int startYear = endYear - 9;
		List<String> idAnnoAggregatos = new ArrayList<String>();
		for(int year=startYear;year<=endYear;year++){
			idAnnoAggregatos.add(String.valueOf(year));
		}
		navigaAggregataAnno.setIdAnnoAggregatos( idAnnoAggregatos );
		navigaAggregataAnno.setFlagAggrefaAnni(false);
		navigaAggregataAnno.setOrderProperty("annoAggregato.annoAggregato");
		navigaAggregataAnno.setOrderType("asc");

		List<AggregataDTO> tmpRisultati4Anno = aggregataFacade.findAggregataByNatura(navigaAggregataAnno);
		
		List<D3BarConverter> risultati4Anno = new ArrayList<D3BarConverter>();
		
		D3BarConverter ele = null;
		int year=startYear;
		int currentYear=0;
		
		for(AggregataDTO tmp : tmpRisultati4Anno){
			currentYear = Integer.valueOf( tmp.getAnnoAnnoAggregato() );
			if( currentYear > year ){
				//Riempio i buchi
				for( int y=year; y<currentYear; y++ ){
					ele = new D3BarConverter();
					ele.setLabel( String.valueOf( y ) );
					ele.setVolume( Long.valueOf(0) );
					risultati4Anno.add(ele);
				}
			}
			ele = new D3BarConverter();
			ele.setLabel( tmp.getAnnoAnnoAggregato() );
			ele.setVolume( tmp.getNumeProgetti() );
			risultati4Anno.add(ele);
			year = currentYear + 1;
		}
		if( currentYear<endYear ){
			for( int y=currentYear+1 ; y<=endYear ; y++ ){
				ele = new D3BarConverter();
				ele.setLabel( String.valueOf( y ) );
				ele.setVolume( Long.valueOf(0) );
				risultati4Anno.add(ele);
			}
		}
		
		model.addAttribute("jsonResultDistribuzione4TestataAnni", createJsonStringFromQueryResultD3BarConverter(risultati4Anno));
		model.addAttribute("recordCountAnni", risultati4Anno.size() );
		model.addAttribute("startYear", startYear );
		model.addAttribute("endYear", endYear );
		
		return "testata-view";
	}

	@EventMapping(value = "event.accediClassificazione")
    public void processAccediClassificazione(EventRequest eventRequest,
               				EventResponse eventResponse,
               				Model model) throws CloneNotSupportedException {
		
		NavigaAggregata navigaAggregata = (NavigaAggregata) eventRequest.getEvent().getValue();
		model.addAttribute("navigaAggregata", navigaAggregata.clone());
		model.addAttribute("navigaAggregataStato", navigaAggregata.clone());
		model.addAttribute("navigaAggregataAnno", navigaAggregata.clone());
		
    }
	
	protected String createJsonStringFromQueryResult(List<AggregataDTO> formattedResult){
		ObjectMapper mapper= new ObjectMapper();
		String jsonString=null;
		try {
			jsonString = mapper.writeValueAsString(formattedResult);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return jsonString;
	}
	
	protected String createJsonStringFromQueryResultD3PieConverter(List<D3PieConverter> formattedResult){
		ObjectMapper mapper= new ObjectMapper();
		String jsonString=null;
		try {
			jsonString = mapper.writeValueAsString(formattedResult);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return jsonString;
	}
	
	protected String createJsonStringFromQueryResultD3BarConverter(List<D3BarConverter> formattedResult){
		ObjectMapper mapper= new ObjectMapper();
		String jsonString=null;
		try {
			jsonString = mapper.writeValueAsString(formattedResult);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return jsonString;
	}
	 
}
