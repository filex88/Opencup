package it.dipe.opencup.controllers;

import it.dipe.opencup.dto.AggregataDTO;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.facade.AggregataFacade;

import java.io.IOException;
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
		
		List<AggregataDTO> risultati = aggregataFacade.findAggregata4Testata(navigaAggregata);
		
		System.out.println( "JSON 3 (" + risultati.size() + "): " + createJsonStringFromQueryResult(risultati) );
		
		/*
		AggregataDTO risultato = ( risultati.size() > 0 )?risultati.get(0) : new AggregataDTO();
		
		System.out.println("I*********************************************************************");
		System.out.println( risultato.getDesArea() );
		System.out.println( risultato.getDesCategoriaIntervento() );
		System.out.println( risultato.getDesNatura() );
		System.out.println( risultato.getDesSottoSettore() );
		System.out.println("F*********************************************************************");
		*/
		return "testata-view";
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
	
	@EventMapping(value = "event.accediClassificazione")
    public void processAccediClassificazione(EventRequest eventRequest,
               				EventResponse eventResponse,
               				Model model) {
		
		NavigaAggregata navigaAggregata = (NavigaAggregata) eventRequest.getEvent().getValue();
		model.addAttribute("navigaAggregata", navigaAggregata);
		
    }
	 
}
