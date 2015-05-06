package it.dipe.opencup.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.dipe.opencup.dto.LocalizationValueConverter;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.facade.AggregataFacade;
import it.dipe.opencup.model.Aggregata;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.xml.namespace.QName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

@Controller
@RequestMapping("VIEW")
public class LocalizzazionePortlet0Controller{
	
	@Value("#{config['pagina.elenco.progetti']}")
	private String paginaElencoProgetti;
	
	@Value("#{config['codice.natura.open.cup']}")
	private String codiNaturaOpenCUP;
	
	@Value("#{config['pagina.localizzazione']}")
	private String paginaLocalizzazione;
	
	@Autowired
	private AggregataFacade aggregataFacade;
	
	@ModelAttribute("navigaAggregata")
	public NavigaAggregata navigaAggregata() {
		
		String idNatura =  (aggregataFacade.findNaturaByCod( codiNaturaOpenCUP )==null)?"0":aggregataFacade.findNaturaByCod( codiNaturaOpenCUP ).getId().toString();
		NavigaAggregata navigaAggregata = new NavigaAggregata();
		navigaAggregata.setIdNatura(idNatura);
		navigaAggregata.setPagAggregata(paginaLocalizzazione);
		navigaAggregata.setIdAreaGeografica("0");
		
		return navigaAggregata;
	}
	
	@RenderMapping
	public String handleRenderRequest(	RenderRequest renderRequest, 
										RenderResponse renderResponse,
										Model model,
										@ModelAttribute("navigaAggregata") NavigaAggregata navigaAggregata,
										@RequestParam(required=false, value="pattern") String pattern ){
		
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		model.addAttribute("jsFolder",themeDisplay.getPathThemeJavaScript());
		
		Long numeProgetti = new Long(0);
		double impoCostoProgetti = 0.0;
		double impoImportoFinanziato = 0.0;
		List<Aggregata> risultati = aggregataFacade.findAggregataByLocalizzazione(navigaAggregata);
		List<LocalizationValueConverter> valori = new ArrayList<LocalizationValueConverter>();
		for (Aggregata aggregata : risultati){
			LocalizationValueConverter areaGeo= new LocalizationValueConverter();
			String codAreaGeo=aggregata.getLocalizzazione().getAreaGeografica().getCodiAreaGeografica();
			String nomeAreaGeo=aggregata.getLocalizzazione().getAreaGeografica().getDescAreaGeografica();
			areaGeo.setLocalizationLabel(codAreaGeo);
			areaGeo.setImportoValue(aggregata.getImpoImportoFinanziato());
			impoImportoFinanziato+=areaGeo.getImportoValue();
			areaGeo.setCostoValue(aggregata.getImpoCostoProgetti());
			impoCostoProgetti+=areaGeo.getCostoValue();
			areaGeo.setVolumeValue(aggregata.getNumeProgetti());
			numeProgetti+=areaGeo.getVolumeValue();
			areaGeo.setDetailUrl("#");
			areaGeo.setFullLabel(nomeAreaGeo);
			valori.add(areaGeo);
		}
		model.addAttribute("statoSelected",navigaAggregata.getDescStato());
		model.addAttribute("jsonResultLocalizzazione",createJsonStringFromQueryResult(valori));
		// valori totali
		model.addAttribute("volumeDeiProgetti", numeProgetti);
		model.addAttribute("costoDeiProgetti", impoCostoProgetti);
		model.addAttribute("importoFinanziamenti", impoImportoFinanziato);
		
		model.addAttribute("pattern", navigaAggregata.getDistribuzione());
		
		
		model.addAttribute("linkallregioni", "#");
		return "localizzazione0-view";
	}
	
	@ActionMapping(params="action=cambiaAggregazione")
	public void actionCambiaAggregazione(	ActionRequest aRequest, 
									ActionResponse aResponse, 
									Model model, 
									@ModelAttribute("navigaAggregata") NavigaAggregata navigaAggregata,
									@RequestParam(required=false, defaultValue="VOLUME", value="pattern") String pattern){
		
		navigaAggregata.setDistribuzione(pattern);
		model.addAttribute("navigaAggregata", navigaAggregata);
		
		aResponse.setRenderParameter("pattern", pattern);
		
		QName eventName = new QName( "http:eventAccediClassificazione/events", "event.accediClassificazione");
		aResponse.setEvent(eventName, navigaAggregata);
		
	}
	
	private String createJsonStringFromQueryResult(List<LocalizationValueConverter> formattedResult){
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
