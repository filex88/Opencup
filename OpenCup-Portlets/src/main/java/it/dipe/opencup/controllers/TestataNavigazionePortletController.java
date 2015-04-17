package it.dipe.opencup.controllers;

import it.dipe.opencup.dto.AggregataDTO;
import it.dipe.opencup.dto.D3BarConverter;
import it.dipe.opencup.dto.D3PieConverter;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.facade.AggregataFacade;

import java.io.IOException;
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
	
	@ModelAttribute("navigaAggregata")
	public NavigaAggregata navigaAggregata() {
		
		String idNatura =  (aggregataFacade.findNaturaByCod( codiNaturaOpenCUP )==null)?"0":aggregataFacade.findNaturaByCod( codiNaturaOpenCUP ).getId().toString();
		NavigaAggregata navigaAggregata = new NavigaAggregata(NavigaAggregata.NAVIGA_CLASSIFICAZIONE, idNatura);
		
		return navigaAggregata;
	}
	
	@RenderMapping
	public String renderRequest(RenderRequest renderRequest, 
								RenderResponse renderResponse,
								Model model,
								@ModelAttribute("navigaAggregata") NavigaAggregata navigaAggregata){
		
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		model.addAttribute("jsFolder",themeDisplay.getPathThemeJavaScript());
		model.addAttribute("imgFolder",themeDisplay.getPathThemeImages());
		
		rimuoviZero(navigaAggregata);
		
		List<AggregataDTO> risultati = aggregataFacade.findAggregataByNatura(navigaAggregata);
		model.addAttribute("jsonResultRiepilogo",createJsonStringFromQueryResult(risultati));
		
		navigaAggregata.setIdStatoProgetto("0");
		List<AggregataDTO> risultati4Stato = aggregataFacade.findAggregataByNatura(navigaAggregata, "statoProgetto.descStatoProgetto", "asc");
		
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
		navigaAggregata.setIdStatoProgetto("-1");
		
		List<String> idAnnoAggregatos = new ArrayList<String>();
		idAnnoAggregatos.add("0");
		navigaAggregata.setIdAnnoAggregatos(idAnnoAggregatos );
		List<AggregataDTO> tmpRisultati4Anno = aggregataFacade.findAggregataByNatura(navigaAggregata, "annoAggregato.annoAggregato", "asc");
		
		AggregataDTO appo = tmpRisultati4Anno.get( tmpRisultati4Anno.size() - 1 );
		tmpRisultati4Anno.remove( tmpRisultati4Anno.size() - 1 );
		
		List<D3BarConverter> risultati4Anno = new ArrayList<D3BarConverter>();
		
		D3BarConverter ele = new D3BarConverter();
		ele.setLabel( "&lt; 2003" );
		ele.setVolume( appo.getNumeProgetti() );
		risultati4Anno.add(ele);
		
		for(AggregataDTO tmp : tmpRisultati4Anno){
			ele = new D3BarConverter();
			ele.setLabel( tmp.getAnnoAnnoAggregato() );
			ele.setVolume( tmp.getNumeProgetti() );
			risultati4Anno.add(ele);
		}
		
		model.addAttribute("jsonResultDistribuzione4TestataAnni", createJsonStringFromQueryResultD3BarConverter(risultati4Anno));
		model.addAttribute("recordCountAnni", risultati4Anno.size() );
		
//		System.out.println( "X ANNO" );
//		for( AggregataDTO tmp : risultati4Anno ){
//			System.out.println( tmp.getAnnoAnnoAggregato() + "  --> " + tmp.getDesStatoProgetto() + " --> " + tmp.getNumeProgetti() );
//		}
		
		return "testata-view";
	}

	@EventMapping(value = "event.accediClassificazione")
    public void processAccediClassificazione(EventRequest eventRequest,
               				EventResponse eventResponse,
               				Model model) throws CloneNotSupportedException {
		
		NavigaAggregata navigaAggregata = (NavigaAggregata) eventRequest.getEvent().getValue();
		model.addAttribute("navigaAggregata", navigaAggregata.clone());
		
    }
	
	private void rimuoviZero(NavigaAggregata navigaAggregata) {
		if( navigaAggregata.getIdNatura().equals("0") ){
			navigaAggregata.setIdNatura("-1");
		}
		if( navigaAggregata.getIdAreaIntervento().equals("0") ){
			navigaAggregata.setIdAreaIntervento("-1");
		}
		if( navigaAggregata.getIdSottosettoreIntervento().equals("0") ){
			navigaAggregata.setIdSottosettoreIntervento("-1");
		}
		if( navigaAggregata.getIdCategoriaIntervento().equals("0") ){
			navigaAggregata.setIdCategoriaIntervento("-1");
		}
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
