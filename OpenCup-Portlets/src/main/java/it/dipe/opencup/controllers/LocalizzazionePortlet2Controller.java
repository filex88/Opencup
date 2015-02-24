package it.dipe.opencup.controllers;

import it.dipe.opencup.controllers.common.LocalizzazionePortletCommonController;
import it.dipe.opencup.dto.LocalizationValueConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.servlet.http.HttpServletRequest;

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
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		List<LocalizationValueConverter> regioniByTerritorio=loadRegionValuesByTerritory(selectedTerritory, request);
	    view.addStaticAttribute("selectedTerritoryValues",regioniByTerritorio);
	    return view;
	}

	
	private List<LocalizationValueConverter> loadRegionValuesByTerritory(String selectedTerritory,ResourceRequest request){
		
		String nestedDetailUrl=super.calcolaUrlLocalizzazioneByLivello(request, "localizzazioneprovince");
		
		// stub da sostituire con query 
		HashMap<String, List<LocalizationValueConverter>> mappaTerritorioRegioni= new HashMap<String, List<LocalizationValueConverter>>();
		
		// nordOvest 
		List<LocalizationValueConverter> regioniNO= new ArrayList<LocalizationValueConverter>();
		LocalizationValueConverter localPiemonte= new LocalizationValueConverter("PIEMONTE",1.0,nestedDetailUrl+"&regione=PIEMONTE");
		LocalizationValueConverter localAosta= new LocalizationValueConverter("VALLE-D_AOSTA",2.0,nestedDetailUrl+"&regione=VALLE-D_AOSTA");
		LocalizationValueConverter localLiguria= new LocalizationValueConverter("LIGURIA",3.0,nestedDetailUrl+"&regione=LIGURIA");
		LocalizationValueConverter localLombardia= new LocalizationValueConverter("LOMBARDIA",4.0,nestedDetailUrl+"&regione=LOMBARDIA");
		regioniNO.add(localPiemonte);
		regioniNO.add(localAosta);
		regioniNO.add(localLombardia);
		regioniNO.add(localLiguria);
		mappaTerritorioRegioni.put("NO",regioniNO);
		
		// nordEst
		List<LocalizationValueConverter> regioniNE= new ArrayList<LocalizationValueConverter>();
		LocalizationValueConverter localFriuli= new LocalizationValueConverter("FRIULI-VENEZIA-GIULIA",1.0,nestedDetailUrl+"&regione=FRIULI-VENEZIA-GIULIA");
		LocalizationValueConverter localTrentino= new LocalizationValueConverter("TRENTINO-ALTO-ADIGE",2.0,nestedDetailUrl+"&regione=TRENTINO-ALTO-ADIGE");
		LocalizationValueConverter localVeneto= new LocalizationValueConverter("VENETO",3.0,nestedDetailUrl+"&regione=VENETO");
		LocalizationValueConverter localEmilia= new LocalizationValueConverter("EMILIA-ROMAGNA",4.0,nestedDetailUrl+"&regione=EMILIA-ROMAGNA");
		regioniNE.add(localFriuli);
		regioniNE.add(localTrentino);
		regioniNE.add(localVeneto);
		regioniNE.add(localEmilia);
		mappaTerritorioRegioni.put("NE",regioniNE);
		
		// centro
		List<LocalizationValueConverter> regioniCE= new ArrayList<LocalizationValueConverter>();
		LocalizationValueConverter localToscana= new LocalizationValueConverter("TOSCANA",1.0,nestedDetailUrl+"&regione=TOSCANA");
		LocalizationValueConverter localUmbria= new LocalizationValueConverter("UMBRIA",2.0,nestedDetailUrl+"&regione=UMBRIA");
		LocalizationValueConverter localLazio= new LocalizationValueConverter("LAZIO",3.0,nestedDetailUrl+"&regione=LAZIO");
		LocalizationValueConverter localMarche= new LocalizationValueConverter("MARCHE",4.0,nestedDetailUrl+"&regione=MARCHE");
		regioniCE.add(localToscana);
		regioniCE.add(localUmbria);
		regioniCE.add(localLazio);
		regioniCE.add(localMarche);
		mappaTerritorioRegioni.put("CE",regioniCE);
		
		// sud
		List<LocalizationValueConverter> regioniSU= new ArrayList<LocalizationValueConverter>();
		LocalizationValueConverter localCampania= new LocalizationValueConverter("CAMPANIA",1.0,nestedDetailUrl+"&regione=CAMPANIA");
		LocalizationValueConverter localAbruzzo= new LocalizationValueConverter("ABRUZZO",2.0,nestedDetailUrl+"&regione=ABRUZZO");
		LocalizationValueConverter localPuglia= new LocalizationValueConverter("PUGLIA",3.0,nestedDetailUrl+"&regione=PUGLIA");
		LocalizationValueConverter localBasilicata= new LocalizationValueConverter("BASILICATA",4.0,nestedDetailUrl+"&regione=BASILICATA");
		LocalizationValueConverter localCalabria= new LocalizationValueConverter("CALABRIA",5.0,nestedDetailUrl+"&regione=CALABRIA");
		LocalizationValueConverter localMolise= new LocalizationValueConverter("MOLISE",6.0,nestedDetailUrl+"&regione=MOLISE");
		regioniSU.add(localCampania);
		regioniSU.add(localAbruzzo);
		regioniSU.add(localPuglia);
		regioniSU.add(localBasilicata);
		regioniSU.add(localCalabria);
		regioniSU.add(localMolise);
		mappaTerritorioRegioni.put("SU",regioniSU);
		
		// centro
		List<LocalizationValueConverter> regioniIS= new ArrayList<LocalizationValueConverter>();
		LocalizationValueConverter localSicilia= new LocalizationValueConverter("SICILIA",1.0,nestedDetailUrl+"&regione=SICILIA");
		LocalizationValueConverter localSardegna= new LocalizationValueConverter("SARDEGNA",2.0,nestedDetailUrl+"&regione=SARDEGNA");
		regioniIS.add(localSicilia);
		regioniIS.add(localSardegna);
		mappaTerritorioRegioni.put("IS",regioniIS);
		
		return mappaTerritorioRegioni.get(selectedTerritory);
		
	}
	
}
