package it.dipe.opencup.controllers;

import it.dipe.opencup.dto.AggregataDTO;
import it.dipe.opencup.dto.LocalizationValueConverter;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.facade.AggregataFacade;
import it.dipe.opencup.model.Aggregata;
import it.dipe.opencup.model.Natura;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

@Controller
@RequestMapping("VIEW")
public class HomePagePortletController {
	
	@Autowired
	private AggregataFacade aggregataFacade;
	@Value("#{config['codice.natura.open.cup']}")
	protected String codNaturaOpenCUP;
	
	@RenderMapping
	public String renderRequest(RenderRequest renderRequest, RenderResponse renderResponse,Model model){
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		model.addAttribute("jsFolder",themeDisplay.getPathThemeJavaScript());
		model.addAttribute("imgFolder",themeDisplay.getPathThemeImages());
	
		// Localizzazione
		NavigaAggregata filtro=new NavigaAggregata(NavigaAggregata.NAVIGA_LOCALIZZAZIONE,"0");
		filtro.setIdAreaGeografica("0");
		Natura naturaOpenCup=aggregataFacade.findNaturaByCod(codNaturaOpenCUP);
		filtro.setIdNatura(naturaOpenCup.getId().toString());
		filtro.setDescNatura(naturaOpenCup.getDescNatura());
		// costruzione risultato
		// totali per localizzazione
	

		List<Aggregata> risultati=aggregataFacade.findAggregataByLocalizzazione(filtro);
		List<LocalizationValueConverter> valori= new ArrayList<LocalizationValueConverter>();
		for (Aggregata aggregata:risultati){
			LocalizationValueConverter areaGeo= new LocalizationValueConverter();
			String codAreaGeo=aggregata.getLocalizzazione().getAreaGeografica().getCodiAreaGeografica();
			String nomeAreaGeo=aggregata.getLocalizzazione().getAreaGeografica().getDescAreaGeografica();
			areaGeo.setLocalizationLabel(codAreaGeo);
			areaGeo.setImportoValue(aggregata.getImpoImportoFinanziato());
			areaGeo.setCostoValue(aggregata.getImpoCostoProgetti());
			areaGeo.setVolumeValue(aggregata.getNumeProgetti());
			areaGeo.setFullLabel(nomeAreaGeo);
			valori.add(areaGeo);
		}
	
		model.addAttribute("valoriLoc", valori);
		model.addAttribute("jsonResultLocalizzazione",createJsonStringLocalizzazioneFromQueryResult(valori));
		
		
		Long numeProgettiClass = new Long(0);
		double impoCostoProgettiClass = 0.0;
		double impoImportoFinanziatoClass = 0.0;
		
		NavigaAggregata filtroClass=new NavigaAggregata(NavigaAggregata.NAVIGA_CLASSIFICAZIONE,"0");
		filtroClass.setIdNatura(naturaOpenCup.getId().toString());
		filtroClass.setDescNatura(naturaOpenCup.getDescNatura());
		filtroClass.setIdAreaIntervento("0");
		List<AggregataDTO> risultatiClassificazione=aggregataFacade.findAggregataByNatura(filtroClass);
		for(AggregataDTO aggregataDTO : risultatiClassificazione){
			numeProgettiClass = numeProgettiClass + aggregataDTO.getNumeProgetti();
			impoCostoProgettiClass = impoCostoProgettiClass + aggregataDTO.getImpoCostoProgetti();
			impoImportoFinanziatoClass = impoImportoFinanziatoClass + aggregataDTO.getImpoImportoFinanziato();
		}
		
		model.addAttribute("jsonResultClassificazione",createJsonStringClassificazioneFromQueryResult(risultatiClassificazione));
		
		return "homepage-view";
	}
	
	
	private String createJsonStringLocalizzazioneFromQueryResult(List<LocalizationValueConverter> formattedResult){
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
	
	private String createJsonStringClassificazioneFromQueryResult(List<AggregataDTO> formattedResult){
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
