package it.dipe.opencup.controllers;

import it.dipe.opencup.controllers.common.LocalizzazionePortletCommonController;
import it.dipe.opencup.dto.LocalizationValueConverter;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

@Controller
@RequestMapping("VIEW")
public class LocalizzazionePortlet1Controller extends LocalizzazionePortletCommonController{
	
	@RenderMapping
	public String handleRenderRequest(	RenderRequest request, 
										RenderResponse response){	
		return "localizzazione1-view";
	}
	
	@ResourceMapping("allTerritoryResource")
	public View processAllTerritoryData(ResourceRequest request){
		String nestedDetailUrl=super.calcolaUrlLocalizzazioneByLivello(request, "localizzazioneregioni");
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		List<LocalizationValueConverter> valori= new ArrayList<LocalizationValueConverter>();
		LocalizationValueConverter nordEst= new LocalizationValueConverter();
		LocalizationValueConverter nordOvest= new LocalizationValueConverter();
		LocalizationValueConverter sud= new LocalizationValueConverter();
		LocalizationValueConverter isole= new LocalizationValueConverter();
		LocalizationValueConverter centro= new LocalizationValueConverter();
		nordEst.setLocalizationLabel("ALL_NE");
		nordEst.setLocalizationValue(1.0);
		nordEst.setDetailUrl(nestedDetailUrl+"&idTerr=NE");
		nordOvest.setLocalizationLabel("ALL_NO");
		nordOvest.setLocalizationValue(2.0);
		nordOvest.setDetailUrl(nestedDetailUrl+"&idTerr=NO");
		sud.setLocalizationLabel("ALL_SU");
		sud.setLocalizationValue(3.0);
		sud.setDetailUrl(nestedDetailUrl+"&idTerr=SU");
		centro.setLocalizationLabel("ALL_CE");
		centro.setLocalizationValue(4.0);
		centro.setDetailUrl(nestedDetailUrl+"&idTerr=CE");
		isole.setLocalizationLabel("ALL_IS");
		isole.setLocalizationValue(5.0);
		isole.setDetailUrl(nestedDetailUrl+"&idTerr=IS");
		
		valori.add(nordEst);
		valori.add(nordOvest);
		valori.add(isole);
		valori.add(centro);
		valori.add(sud);
	    view.addStaticAttribute("allTerritoryValues", valori);
	    return view;
	}

}
