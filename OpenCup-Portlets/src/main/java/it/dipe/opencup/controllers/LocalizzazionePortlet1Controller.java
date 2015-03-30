package it.dipe.opencup.controllers;

import it.dipe.opencup.controllers.common.LocalizzazionePortletCommonController;
import it.dipe.opencup.dto.LocalizationValueConverter;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.facade.AggregataFacade;
import it.dipe.opencup.model.Aggregata;
import it.dipe.opencup.model.Natura;
import it.dipe.opencup.util.CommonLocalizationValueComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;

@Controller
@RequestMapping("VIEW")
public class LocalizzazionePortlet1Controller extends LocalizzazionePortletCommonController{
	
	
	@Autowired
	private AggregataFacade aggregataFacade;
	
	@RenderMapping
	public String handleRenderRequest(	RenderRequest request, RenderResponse response,Model model){
		HttpServletRequest httpServletRequest = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(request));
		String filtriPrec=httpServletRequest.getParameter("filtri")!=null?httpServletRequest.getParameter("filtri").toString():"";
		Integer numeProgetti = 0;
		double impoCostoProgetti = 0.0;
		double impoImportoFinanziato = 0.0;
		String nestedDetailUrl=calcolaUrlLocalizzazioneByLivello(request, "localizzazioneregioni");
		String allRegioniUrl=calcolaUrlLocalizzazioneByLivello(request, "localizzazioneregioniall");
		PortletURL portletURL = response.createRenderURL();
		NavigaAggregata filtro = null;
		// se ho parametri impostati (solo al primo caricamento)
		if (!Validator.isNull(filtriPrec)  && !Validator.equals("", filtriPrec)){
			filtro=super.createModelFromJsonString(filtriPrec);
			filtro.setIdAreaGeografica("0");
			filtro.setIdRegione("-1");
			filtro.setIdProvincia("-1");
			String jsonString=createJsonStringFromModelAttribute(filtro);
			portletURL.setParameter("filtroAsString", jsonString);
		}else{

			if (model.asMap().get("navigaAggregata")==null && (Validator.isNull(ParamUtil.getString(request,"filtroAsString")) || "".equals(ParamUtil.getString(request,"filtroAsString")))){
				filtro=new NavigaAggregata(NavigaAggregata.NAVIGA_LOCALIZZAZIONE,"0");
				filtro.setIdAreaGeografica("0");
				Natura naturaOpenCup=aggregataFacade.findNaturaByCod(codNaturaOpenCUP);
				filtro.setIdNatura(naturaOpenCup.getId().toString());
				filtro.setDescNatura(naturaOpenCup.getDescNatura());
			}else{
				if (model.asMap().get("navigaAggregata")!=null)
				{
					filtro=(NavigaAggregata)model.asMap().get("navigaAggregata");
				}else{ // lo prendo dal json
					filtro=super.createModelFromJsonString(ParamUtil.getString(request,"filtroAsString"));
				}
				String jsonString=createJsonStringFromModelAttribute(filtro);
				portletURL.setParameter("filtroAsString", jsonString);
			}
		}
	
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
			String detailUrl=nestedDetailUrl+"&idTerr="+codAreaGeo;
			// se ci sono filtri, li riporto nella pag successiva
			if (filtro.getCountAffRicercaLocalizzazione()!=null){
				detailUrl+="&filtri="+createJsonStringFromModelAttribute(filtro);
			}
			areaGeo.setDetailUrl(HttpUtil.encodeParameters(detailUrl));
			areaGeo.setFullLabel(nomeAreaGeo);
			valori.add(areaGeo);
		}
		model.addAttribute("statoSelected",filtro.getDescStato());
		model.addAttribute("jsonResultLocalizzazione",createJsonStringFromQueryResult(valori));
		if (filtro.getCountAffRicercaLocalizzazione()!=null){
			allRegioniUrl+="&filtri="+createJsonStringFromModelAttribute(filtro);
		}
		model.addAttribute("linkallregioni",HttpUtil.encodeParameters(allRegioniUrl));
		super.inizializzaFiltriRicercaLocalizzazione(filtro,model);
		model.addAttribute("navigaAggregata", filtro);
		
		// link elenco progetti
		String urlElencoProgetti=super.calcolaUrlLocalizzazioneByLivello(request, filtro.getPagElencoProgetti());
		urlElencoProgetti+="&jsonnavigaaggregata="+createJsonStringFromModelAttribute(filtro);
		model.addAttribute("linkElencoProgetti", HttpUtil.encodeParameters(urlElencoProgetti));
	
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
				
		SearchContainer<LocalizationValueConverter> searchContainerDistinct = new SearchContainer<LocalizationValueConverter>(request, portletURL, null, "Nessun dato trovato per la selezione fatta");
		searchContainerDistinct.setDelta(delta);
				
		searchContainerDistinct.setOrderByCol(orderByCol);
		searchContainerDistinct.setOrderByType(orderByType);
		searchContainerDistinct.setTotal(valori.size());
		
		
		valori = ListUtil.subList(valori, searchContainerDistinct.getStart(), searchContainerDistinct.getEnd());       
		
		searchContainerDistinct.setResults(valori);
		
		// ordinamento in base a property selezionata
		Collections.sort(valori,new CommonLocalizationValueComparator(orderByCol, orderByType));
		model.addAttribute("searchContainerDistinct", searchContainerDistinct);
		
		// valori totali
		model.addAttribute("volumeDeiProgetti", numeProgetti);
		model.addAttribute("costoDeiProgetti", impoCostoProgetti);
		model.addAttribute("importoFinanziamenti", impoImportoFinanziato);
		

		return "localizzazione1-view";
	}
	
	@ActionMapping(params="action=affinaricerca")
	public void actionAffinaRicerca(ActionRequest aRequest, ActionResponse aResponse, Model model, @ModelAttribute("navigaAggregata") NavigaAggregata navigaAggregata){
		model.addAttribute("navigaAggregata", navigaAggregata);

	}
	
	
}
