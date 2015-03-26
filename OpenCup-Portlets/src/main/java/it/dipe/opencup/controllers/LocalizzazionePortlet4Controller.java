package it.dipe.opencup.controllers;

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

import it.dipe.opencup.controllers.common.LocalizzazionePortletCommonController;
import it.dipe.opencup.dto.DescrizioneValore;
import it.dipe.opencup.dto.LocalizationValueConverter;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.facade.AggregataFacade;
import it.dipe.opencup.model.Aggregata;
import it.dipe.opencup.model.Natura;
import it.dipe.opencup.util.CommonLocalizationValueComparator;

@Controller
@RequestMapping("VIEW")
public class LocalizzazionePortlet4Controller extends LocalizzazionePortletCommonController{
	
	@Autowired
	private AggregataFacade aggregataFacade;
	
	@RenderMapping
	public String handleRenderRequest(	RenderRequest request, RenderResponse response,Model model){
		HttpServletRequest httpServletRequest = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(request));
		String filtriPrec=httpServletRequest.getParameter("filtri")!=null?httpServletRequest.getParameter("filtri").toString():"";
		Integer numeProgetti = 0;
		double impoCostoProgetti = 0.0;
		double impoImportoFinanziato = 0.0;
		PortletURL portletURL = response.createRenderURL();
		NavigaAggregata filtro = null;
		// se ho parametri impostati (solo al primo caricamento)
		if (!Validator.isNull(filtriPrec)  && !Validator.equals("", filtriPrec)){
			filtro=super.createModelFromJsonString(filtriPrec);
			filtro.setIdAreaGeografica("0");
			filtro.setIdRegione("0");
			filtro.setIdProvincia("-1");
			String jsonString=createJsonStringFromModelAttribute(filtro);
			portletURL.setParameter("filtroAsString", jsonString);
		}else{

			if (model.asMap().get("navigaAggregata")==null && (Validator.isNull(ParamUtil.getString(request,"filtroAsString")) || "".equals(ParamUtil.getString(request,"filtroAsString")))){
				filtro=new NavigaAggregata(NavigaAggregata.NAVIGA_LOCALIZZAZIONE,"0");
				filtro.setIdAreaGeografica("0");
				filtro.setIdRegione("0");
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
		
		String nestedDetailUrl=calcolaUrlLocalizzazioneByLivello(request, "localizzazioneprovince");
		String areeGeoUrl=calcolaUrlLocalizzazioneByLivello(request, "localizzazione");	
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
			String detailUrl=nestedDetailUrl+"&codReg="+codiceRegione+"&noAree=si";
			// se ci sono filtri, li riporto nella pag successiva
			if (filtro.getCountAffRicercaLocalizzazione()!=null){
				detailUrl+="&filtri="+createJsonStringFromModelAttribute(filtro);
			}
			valoreByRegione.setDetailUrl(HttpUtil.encodeParameters(detailUrl));
			valoreByRegione.setFullLabel(nomeRegione.replace("'", "$"));
			valori.add(valoreByRegione);
		}
		model.addAttribute("statoSelected",filtro.getDescStato());
		model.addAttribute("jsonResultLocalizzazione",createJsonStringFromQueryResult(valori));
		if (filtro.getCountAffRicercaLocalizzazione()!=null){
			areeGeoUrl+="&filtri="+createJsonStringFromModelAttribute(filtro);
		}
		model.addAttribute("linkAreeGeo",HttpUtil.encodeParameters(areeGeoUrl));
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
		

		return "localizzazione4-view";
	}

	@ActionMapping(params="action=affinaricerca")
	public void actionAffinaRicerca(ActionRequest aRequest, ActionResponse aResponse, Model model, @ModelAttribute("navigaAggregata") NavigaAggregata navigaAggregata){
		model.addAttribute("navigaAggregata", navigaAggregata);

	}
	
}
