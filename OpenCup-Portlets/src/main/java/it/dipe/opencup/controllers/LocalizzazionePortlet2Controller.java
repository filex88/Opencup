package it.dipe.opencup.controllers;

import it.dipe.opencup.controllers.common.LocalizzazionePortletCommonController;
import it.dipe.opencup.dto.DescrizioneValore;
import it.dipe.opencup.dto.LocalizationValueConverter;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.facade.AggregataFacade;
import it.dipe.opencup.model.Aggregata;
import it.dipe.opencup.model.AreaGeografica;
import it.dipe.opencup.model.Natura;
import it.dipe.opencup.util.CommonLocalizationValueComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;

@Controller
@RequestMapping("VIEW")
public class LocalizzazionePortlet2Controller extends LocalizzazionePortletCommonController{
	
	@Autowired
	private AggregataFacade aggregataFacade;
	
	@RenderMapping
	public String handleRenderRequest(RenderRequest request, RenderResponse response,Model model){
		HttpServletRequest httpServletRequest = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(request));
		String idTerr=httpServletRequest.getParameter("idTerr")!=null?httpServletRequest.getParameter("idTerr").toString():"";
		
		// se non vengo dalla prima request
		if (Validator.isNull(idTerr)  || Validator.equals("", idTerr)){
			idTerr=ParamUtil.getString(request, "idTerr");
		}

		PortletURL portletURL = response.createRenderURL();
		portletURL.setParameter("idTerr",idTerr);

		model.addAttribute("selectedTerritory", idTerr);
		
		Integer numeProgetti = 0;
		double impoCostoProgetti = 0.0;
		double impoImportoFinanziato = 0.0;
		String nestedDetailUrl=calcolaUrlLocalizzazioneByLivello(request, "localizzazioneprovince");
		NavigaAggregata filtro = new NavigaAggregata();
		filtro.setIdRegione("0");
		AreaGeografica areaSelezionata =aggregataFacade.findAreaGeograficaByCodiceArea(idTerr);
		String strIdAreaSel=areaSelezionata!=null?areaSelezionata.getId().toString():"0";
		filtro.setIdAreaGeografica(strIdAreaSel);
		String descAreaGeo=areaSelezionata!=null?areaSelezionata.getDescAreaGeografica().toUpperCase():"";
		Natura naturaOpenCup=aggregataFacade.findNaturaByCod(codNaturaOpenCUP);
		filtro.setIdNatura(naturaOpenCup.getId().toString());
		List<Aggregata> risultati=aggregataFacade.findAggregataByLocalizzazione(filtro);
		List<LocalizationValueConverter> valori= new ArrayList<LocalizationValueConverter>();
		for (Aggregata aggregata:risultati){
			LocalizationValueConverter valoreByRegione=new LocalizationValueConverter();
			String codiceRegione=aggregata.getLocalizzazione().getRegione().getCodiRegione();
			String nomeRegione=aggregata.getLocalizzazione().getRegione().getDescRegione();
			valoreByRegione.setLocalizationLabel(codiceRegione);
			valoreByRegione.setVolumeValue(aggregata.getNumeProgetti());
			numeProgetti+=valoreByRegione.getVolumeValue();
			valoreByRegione.setCostoValue(aggregata.getImpoCostoProgetti());
			impoCostoProgetti+=valoreByRegione.getCostoValue();
			valoreByRegione.setImportoValue(aggregata.getImpoImportoFinanziato());
			impoImportoFinanziato+=valoreByRegione.getImportoValue();
			valoreByRegione.setDetailUrl(nestedDetailUrl+"&codReg="+codiceRegione);
			valoreByRegione.setFullLabel(nomeRegione.replace("'", "$"));
			valori.add(valoreByRegione);
		}
		model.addAttribute("statoSelected",filtro.getDescStato());
		model.addAttribute("areeGeoBackLink",calcolaUrlLocalizzazioneByLivello(request, "localizzazione"));
		model.addAttribute("selectedTerritoryName", descAreaGeo);
		model.addAttribute("jsonResultLocalizzazione",createJsonStringFromQueryResult(valori));
		
		//orderByCol is the column name passed in the request while sorting
		String orderByCol = ParamUtil.getString(request, "orderByCol"); 
		if(Validator.isNull(orderByCol)  || Validator.equals("", orderByCol)){
			orderByCol = "volumeValue";
		}
				
		//orderByType is passed in the request while sorting. It can be either asc or desc
		String orderByType = ParamUtil.getString(request, "orderByType");
		if(Validator.isNull(orderByType)  || Validator.equals("", orderByType)){
				  orderByType = "asc";
		}
				
		//delta
		String sDelta = ParamUtil.getString(request, "delta");
		int delta = maxResult;
		if( ! ( Validator.isNull(sDelta) || Validator.equals("", sDelta) ) ){
				  delta = Integer.parseInt(sDelta);
		}
				
		SearchContainer<LocalizationValueConverter> searchContainerDistinct = new SearchContainer<LocalizationValueConverter>(request, portletURL, null, "Nessun dato trovato per la selezione fatta");
		searchContainerDistinct.setDelta(delta);
				
		searchContainerDistinct.setOrderByCol(orderByCol);
		searchContainerDistinct.setOrderByType(orderByType);
		searchContainerDistinct.setTotal(valori.size());
	
		searchContainerDistinct.setResults(valori);
		// ordinamento in base a property selezionata
		Collections.sort(valori,new CommonLocalizationValueComparator(orderByCol, orderByType));
		model.addAttribute("searchContainerDistinct", searchContainerDistinct);
		
		// search container per totali
		SearchContainer<DescrizioneValore> searchContainerSummary = new SearchContainer<DescrizioneValore>(request, portletURL, null, "Nessun dato trovato per la selezione fatta");
		searchContainerSummary.setDelta(maxResult);
		searchContainerSummary.setTotal(3);
		List<DescrizioneValore> retval = new ArrayList<DescrizioneValore>();
		retval.add(new DescrizioneValore("VOLUME DEI PROGETTI", numeProgetti));
		retval.add(new DescrizioneValore("COSTO DEI PROGETTI", impoCostoProgetti));
		retval.add(new DescrizioneValore("IMPORTO FINANZIAMENTI", impoImportoFinanziato));
		searchContainerSummary.setResults(retval);
		model.addAttribute("searchContainerSummary", searchContainerSummary);
		
		
		return "localizzazione2-view";
	}
}
