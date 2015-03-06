package it.dipe.opencup.controllers;

import it.dipe.opencup.controllers.common.LocalizzazionePortletCommonController;
import it.dipe.opencup.dto.LocalizationValueConverter;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.facade.AggregataFacade;
import it.dipe.opencup.model.Aggregata;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

@Controller
@RequestMapping("VIEW")
public class LocalizzazionePortlet1Controller extends LocalizzazionePortletCommonController{
	
	
	@Autowired
	private AggregataFacade aggregataFacade;
	
	@RenderMapping
	public String handleRenderRequest(	RenderRequest request, 
										RenderResponse response){	
		return "localizzazione1-view";
	}
	
	@ResourceMapping("allTerritoryResource")
	public View processAllTerritoryData(ResourceRequest request){
		String nestedDetailUrl=super.calcolaUrlLocalizzazioneByLivello(request, "localizzazioneregioni");
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		NavigaAggregata filtro = new NavigaAggregata();
		filtro.setIdAreaGeografica("0");
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
			areaGeo.setDetailUrl(nestedDetailUrl+"&idTerr="+codAreaGeo+"&nomeTerr="+nomeAreaGeo);
			valori.add(areaGeo);
		}
	    view.addStaticAttribute("allTerritoryValues", valori);
	    return view;
	}

}
