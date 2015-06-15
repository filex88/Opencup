package it.dipe.opencup.controllers;

import it.dipe.opencup.comparator.AreaGeograficaComparator;
import it.dipe.opencup.dto.LocalizationValueConverter;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.facade.AggregataFacade;
import it.dipe.opencup.model.Aggregata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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

@Controller
@RequestMapping("VIEW")
public class LocalizzazionePortletController{
	
	@Autowired
	private AggregataFacade aggregataFacade;
	
	@Value("#{config['codice.natura.open.cup']}")
	private String codiNaturaOpenCUP;
	
	@Value("#{config['pagina.localizzazione']}")
	private String paginaLocalizzazione;
	
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
	public String handleRenderRequest( RenderRequest request, RenderResponse response, Model model, NavigaAggregata navigaAggregata){
		
		interroga(model, navigaAggregata);
		
		return "localizzazione-view";
	}

	private void interroga(Model model, NavigaAggregata navigaAggregata) {
		Long numeProgetti = new Long(0);
		double impoCostoProgetti = 0.0;
		double impoImportoFinanziato = 0.0;
		

		List<Aggregata> risultati = aggregataFacade.findAggregataByLocalizzazione(navigaAggregata);
		
		Collections.sort(risultati, new AreaGeograficaComparator());
		
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
		
		model.addAttribute("dimension", navigaAggregata.getDistribuzione());
	}
	
	@EventMapping(value = "event.accediClassificazione")
    public void processAccediClassificazione(EventRequest eventRequest,
               				EventResponse eventResponse,
               				Model model) throws CloneNotSupportedException {
		
		NavigaAggregata p = (NavigaAggregata) eventRequest.getEvent().getValue();
		NavigaAggregata navigaAggregata = p.clone();
		
		navigaAggregata.rimuoviZero();
		navigaAggregata.setIdAreaGeografica("0");
		
		model.addAttribute("navigaAggregata", navigaAggregata);
    }
	
	@EventMapping(value = "event.accediSoggetto")
    public void processAccediSoggetto(EventRequest eventRequest,
               				EventResponse eventResponse,
               				Model model) throws CloneNotSupportedException {
		
		NavigaAggregata p = (NavigaAggregata) eventRequest.getEvent().getValue();
		NavigaAggregata navigaAggregata = p.clone();
		
		navigaAggregata.rimuoviZero();
		navigaAggregata.setIdAreaGeografica("0");
		
		model.addAttribute("navigaAggregata", navigaAggregata);
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
