package it.dipe.opencup.controllers;

import it.dipe.opencup.controllers.common.LocalizzazionePortletCommonController;
import it.dipe.opencup.dto.LocalizationValueConverter;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.facade.AggregataFacade;
import it.dipe.opencup.model.Aggregata;
import it.dipe.opencup.model.Regione;

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
public class LocalizzazionePortlet3Controller extends LocalizzazionePortletCommonController{
	
	@Autowired
	private AggregataFacade aggregataFacade;
	
	
	@RenderMapping
	public String handleRenderRequest(RenderRequest request, RenderResponse response,Model model){
		HttpServletRequest httpServletRequest = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(request));
		String codReg=httpServletRequest.getParameter("codReg")!=null?httpServletRequest.getParameter("codReg").toString():"";
		String nomeReg=httpServletRequest.getParameter("nomeReg")!=null?httpServletRequest.getParameter("nomeReg").toString():"";
		model.addAttribute("selectedRegion", codReg);
		model.addAttribute("regionName", nomeReg);
		return "localizzazione3-view";
	}

	@ResourceMapping("selectedRegionResource")
	public View processSelectedTerritoryData(ResourceRequest request, @RequestParam("selectedRegion") String  selectedRegion){
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		NavigaAggregata filtro = new NavigaAggregata();
		filtro.setIdProvincia("0");
		Regione regione=aggregataFacade.findRegionebyCodice(selectedRegion);
		String strIdRegione=regione!=null?regione.getId().toString():"0";
		filtro.setIdRegione(strIdRegione);
		String strIdAreaGeografica=regione!=null?regione.getAreaGeografica().getId().toString():"0";
		filtro.setIdAreaGeografica(strIdAreaGeografica);
		List <Aggregata> provinceByRegione=aggregataFacade.findAggregataByLocalizzazione(filtro);
		List<LocalizationValueConverter> valori= new ArrayList<LocalizationValueConverter>();
		for (Aggregata aggregata:provinceByRegione){
			LocalizationValueConverter valoreByProvincia=new LocalizationValueConverter();
			valoreByProvincia.setLocalizationLabel(aggregata.getLocalizzazione().getProvincia().getCodiProvincia());
			valoreByProvincia.setVolumeValue(aggregata.getNumeProgetti());
			valoreByProvincia.setCostoValue(aggregata.getImpoCostoProgetti());
			valoreByProvincia.setImportoValue(aggregata.getImpoImportoFinanziato());
			
			valori.add(valoreByProvincia);
		}
	    view.addStaticAttribute("selectedRegionValues",valori);
	    return view;
	}	

	
}
