package it.dipe.opencup.controllers;

import it.dipe.opencup.controllers.common.LocalizzazionePortletCommonController;
import it.dipe.opencup.dto.LocalizationValueConverter;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.facade.AggregataFacade;
import it.dipe.opencup.model.Aggregata;
import it.dipe.opencup.model.AreaGeografica;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

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
		String nomeTerr=httpServletRequest.getParameter("nomeTerr")!=null?httpServletRequest.getParameter("nomeTerr").toString():"";
		model.addAttribute("selectedTerritory", idTerr);
		model.addAttribute("selectedTerritoryName", nomeTerr);
		return "localizzazione2-view";
	}
	
	
	@ResourceMapping("selectedTerritoryResource")
	public View processSelectedTerritoryData(ResourceRequest request, @RequestParam("selectedTerritory") String  selectedTerritory){
		String nestedDetailUrl=super.calcolaUrlLocalizzazioneByLivello(request, "localizzazioneprovince");
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		NavigaAggregata filtro = new NavigaAggregata();
		filtro.setIdRegione("0");
		AreaGeografica areaSelezionata =aggregataFacade.findAreaGeograficaByCodiceArea(selectedTerritory);
		String strIdAreaSel=areaSelezionata!=null?areaSelezionata.getId().toString():"0";
		filtro.setIdAreaGeografica(strIdAreaSel);
		List <Aggregata> regioniByAreaGeo=aggregataFacade.findAggregataByLocalizzazione(filtro);
		List<LocalizationValueConverter> valori= new ArrayList<LocalizationValueConverter>();
		for (Aggregata aggregata:regioniByAreaGeo){
			LocalizationValueConverter valoreByRegione=new LocalizationValueConverter();
			String codiceRegione=aggregata.getLocalizzazione().getRegione().getCodiRegione();
			String nomeRegione=aggregata.getLocalizzazione().getRegione().getDescRegione();
			valoreByRegione.setLocalizationLabel(codiceRegione);
			valoreByRegione.setVolumeValue(aggregata.getNumeProgetti());
			valoreByRegione.setCostoValue(aggregata.getImpoCostoProgetti());
			valoreByRegione.setImportoValue(aggregata.getImpoImportoFinanziato());
			valoreByRegione.setDetailUrl(nestedDetailUrl+"&codReg="+codiceRegione+"&nomeReg="+nomeRegione);
			valori.add(valoreByRegione);
		}
	    view.addStaticAttribute("selectedTerritoryValues",valori);
	    return view;
	}
}
