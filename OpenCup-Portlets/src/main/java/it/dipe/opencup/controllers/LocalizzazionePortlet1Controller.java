package it.dipe.opencup.controllers;

import it.dipe.opencup.controllers.common.LocalizzazionePortletCommonController;
import it.dipe.opencup.dto.DescrizioneValore;
import it.dipe.opencup.dto.LocalizationValueConverter;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.facade.AggregataFacade;
import it.dipe.opencup.model.Aggregata;
import it.dipe.opencup.model.Natura;
import it.dipe.opencup.util.CommonLocalizationValueComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

@Controller
@RequestMapping("VIEW")
public class LocalizzazionePortlet1Controller extends LocalizzazionePortletCommonController{
	
	
	@Autowired
	private AggregataFacade aggregataFacade;
	
	@RenderMapping
	public String handleRenderRequest(	RenderRequest request, RenderResponse response,Model model){
		Integer numeProgetti = 0;
		double impoCostoProgetti = 0.0;
		double impoImportoFinanziato = 0.0;
		String nestedDetailUrl=calcolaUrlLocalizzazioneByLivello(request, "localizzazioneregioni");
		String allRegioniUrl=calcolaUrlLocalizzazioneByLivello(request, "localizzazioneregioniall");
		NavigaAggregata filtro = new NavigaAggregata();
		filtro.setIdAreaGeografica("0");
		Natura naturaOpenCup=aggregataFacade.findNaturaByCod(codNaturaOpenCUP);
		filtro.setIdNatura(naturaOpenCup.getId().toString());
		List<Aggregata> risultati=aggregataFacade.findAggregataByLocalizzazione(filtro);
		List<LocalizationValueConverter> valori= new ArrayList<LocalizationValueConverter>();
		for (Aggregata aggregata:risultati){
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
			areaGeo.setDetailUrl(nestedDetailUrl+"&idTerr="+codAreaGeo);
			areaGeo.setFullLabel(nomeAreaGeo);
			valori.add(areaGeo);
		}
		model.addAttribute("statoSelected",filtro.getDescStato());
		model.addAttribute("jsonResultLocalizzazione",createJsonStringFromQueryResult(valori));
		model.addAttribute("linkallregioni",allRegioniUrl);
		//orderByCol is the column name passed in the request while sorting
		String orderByCol = ParamUtil.getString(request, "orderByCol"); 
		if(Validator.isNull(orderByCol)  || Validator.equals("", orderByCol)){
			orderByCol = "volumeValue";
		}
				
		//orderByType is passed in the request while sorting. It can be either asc or desc
		String orderByType = ParamUtil.getString(request, "orderByType");
		if(Validator.isNull(orderByType)  || Validator.equals("", orderByType)){
				  orderByType = "desc";
		}
				
		//delta
		String sDelta = ParamUtil.getString(request, "delta");
		int delta = maxResult;
		if( ! ( Validator.isNull(sDelta) || Validator.equals("", sDelta) ) ){
				  delta = Integer.parseInt(sDelta);
		}
				
		SearchContainer<LocalizationValueConverter> searchContainerDistinct = new SearchContainer<LocalizationValueConverter>(request, response.createRenderURL(), null, "Nessun dato trovato per la selezione fatta");
		searchContainerDistinct.setDelta(delta);
				
		searchContainerDistinct.setOrderByCol(orderByCol);
		searchContainerDistinct.setOrderByType(orderByType);
		searchContainerDistinct.setTotal(valori.size());
				
		valori = ListUtil.subList(valori, searchContainerDistinct.getStart(), searchContainerDistinct.getEnd());       
		
		searchContainerDistinct.setResults(valori);
		// ordinamento in base a property selezionata
		Collections.sort(valori,new CommonLocalizationValueComparator(orderByCol, orderByType));
		model.addAttribute("searchContainerDistinct", searchContainerDistinct);
		
		// search container per totali
		SearchContainer<DescrizioneValore> searchContainerSummary = new SearchContainer<DescrizioneValore>(request, response.createRenderURL(), null, "Nessun dato trovato per la selezione fatta");
		searchContainerSummary.setDelta(maxResult);
		searchContainerSummary.setTotal(3);
		List<DescrizioneValore> retval = new ArrayList<DescrizioneValore>();
		retval.add(new DescrizioneValore("VOLUME DEI PROGETTI", numeProgetti));
		retval.add(new DescrizioneValore("COSTO DEI PROGETTI", impoCostoProgetti));
		retval.add(new DescrizioneValore("IMPORTO FINANZIAMENTI", impoImportoFinanziato));
		searchContainerSummary.setResults(retval);
		model.addAttribute("searchContainerSummary", searchContainerSummary);
		

		return "localizzazione1-view";
	}
}
