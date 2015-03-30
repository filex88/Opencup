package it.dipe.opencup.controllers;

import it.dipe.opencup.controllers.common.LocalizzazionePortletCommonController;
import it.dipe.opencup.dto.DescrizioneValore;
import it.dipe.opencup.dto.LocalizationValueConverter;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.facade.AggregataFacade;
import it.dipe.opencup.model.Aggregata;
import it.dipe.opencup.model.Natura;
import it.dipe.opencup.model.Regione;
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
public class LocalizzazionePortlet3Controller extends LocalizzazionePortletCommonController{
	
	@Autowired
	private AggregataFacade aggregataFacade;
	
	
	@RenderMapping
	public String handleRenderRequest(RenderRequest request, RenderResponse response,Model model){
		HttpServletRequest httpServletRequest = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(request));
		String codReg=httpServletRequest.getParameter("codReg")!=null?httpServletRequest.getParameter("codReg").toString():"";
		String noAree=httpServletRequest.getParameter("noAree")!=null?httpServletRequest.getParameter("noAree").toString():"";
		String filtriPrec=httpServletRequest.getParameter("filtri")!=null?httpServletRequest.getParameter("filtri").toString():"";
		NavigaAggregata filtro= null;
		PortletURL portletURL = response.createRenderURL();
		
		// se non vengo dalla prima request
		if (Validator.isNull(codReg)  || Validator.equals("", codReg)){
			codReg=ParamUtil.getString(request, "codReg");
		}
		
		// se non vengo dalla prima request e provengo dal link diretto per regione
		if (Validator.isNull(noAree)  || Validator.equals("", noAree)){
			noAree=ParamUtil.getString(request, "noAree");
		}
		Regione regione=aggregataFacade.findRegionebyCodice(codReg);
		String strIdRegione=regione!=null?regione.getId().toString():"0";
		String strNomeRegione=regione!=null?regione.getDescRegione().toUpperCase():"";
		String strIdAreaGeografica=regione!=null?regione.getAreaGeografica().getId().toString():"0";
		
		// se ho parametri impostati (solo al primo caricamento)
		if (!Validator.isNull(filtriPrec)  && !Validator.equals("", filtriPrec)){
			filtro=super.createModelFromJsonString(filtriPrec);
			filtro.setIdProvincia("0");
			filtro.setIdRegione(strIdRegione);
			filtro.setIdAreaGeografica(strIdAreaGeografica);
			String jsonString=createJsonStringFromModelAttribute(filtro);
			portletURL.setParameter("filtroAsString", jsonString);
		}else{
			if (model.asMap().get("navigaAggregata")==null && (Validator.isNull(ParamUtil.getString(request,"filtroAsString")) || "".equals(ParamUtil.getString(request,"filtroAsString")))){
			// caricamento full senza filtri
				filtro=new NavigaAggregata(NavigaAggregata.NAVIGA_LOCALIZZAZIONE,"0");
				filtro.setIdProvincia("0");
				filtro.setIdRegione(strIdRegione);
				filtro.setIdAreaGeografica(strIdAreaGeografica);
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
		
		
		portletURL.setParameter("codReg",codReg);
		model.addAttribute("selectedRegion", codReg);
		portletURL.setParameter("noAree",noAree);
		
		Integer numeProgetti = 0;
		double impoCostoProgetti = 0.0;
		double impoImportoFinanziato = 0.0;
		
		List <Aggregata> provinceByRegione=aggregataFacade.findAggregataByLocalizzazione(filtro);
		List<LocalizationValueConverter> valori= new ArrayList<LocalizationValueConverter>();
		for (Aggregata aggregata:provinceByRegione){
			LocalizationValueConverter valoreByProvincia=new LocalizationValueConverter();
			String strNomeProvincia=aggregata.getLocalizzazione().getProvincia().getDescProvincia();
			valoreByProvincia.setLocalizationLabel(aggregata.getLocalizzazione().getProvincia().getCodiProvincia());
			valoreByProvincia.setVolumeValue(aggregata.getNumeProgetti());
			numeProgetti+=valoreByProvincia.getVolumeValue();
			valoreByProvincia.setCostoValue(aggregata.getImpoCostoProgetti());
			impoCostoProgetti+=valoreByProvincia.getCostoValue();
			valoreByProvincia.setImportoValue(aggregata.getImpoImportoFinanziato());
			impoImportoFinanziato+=valoreByProvincia.getImportoValue();
			valoreByProvincia.setFullLabel(strNomeProvincia.replace("'", "$"));
			valori.add(valoreByProvincia);
		}
		model.addAttribute("statoSelected",filtro.getDescStato());
		model.addAttribute("isDirect", (noAree!=null && noAree!="")?true:false);
		model.addAttribute("regionName", strNomeRegione);
		model.addAttribute("jsonResultLocalizzazione",createJsonStringFromQueryResult(valori));
		
		String strCodAreaGeo=regione.getAreaGeografica().getCodiAreaGeografica();
		model.addAttribute("areaGEO",strCodAreaGeo);
		model.addAttribute("selectedTerritoryName",regione.getAreaGeografica().getDescAreaGeografica());
		
		String strLinkBackAreaGeo=calcolaUrlLocalizzazioneByLivello(request, "localizzazioneregioni")+"&idTerr="+strCodAreaGeo;
		String strLinkBackDaAreaGeo=calcolaUrlLocalizzazioneByLivello(request, "localizzazione");
		String strLinkBackAllRegioni=calcolaUrlLocalizzazioneByLivello(request, "localizzazioneregioniall");
		String regioniLinkBack=(noAree!=null && noAree!="")?strLinkBackAllRegioni:strLinkBackAreaGeo;
		
		if (filtro.getCountAffRicercaLocalizzazione()!=null){
			regioniLinkBack+="&filtri="+createJsonStringFromModelAttribute(filtro);
			strLinkBackDaAreaGeo+="&filtri="+createJsonStringFromModelAttribute(filtro);
		}
		model.addAttribute("regioniBackLink",HttpUtil.encodeParameters(regioniLinkBack));
		model.addAttribute("areeGeoBackLink",HttpUtil.encodeParameters(strLinkBackDaAreaGeo));
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
		searchContainerDistinct.setIteratorURL(portletURL);
		
		valori = ListUtil.subList(valori, searchContainerDistinct.getStart(), searchContainerDistinct.getEnd());       
		
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
		
		return "localizzazione3-view";
	}
	
	@ActionMapping(params="action=affinaricerca")
	public void actionAffinaRicerca(ActionRequest aRequest, ActionResponse aResponse, Model model, @ModelAttribute("navigaAggregata") NavigaAggregata navigaAggregata){
		model.addAttribute("navigaAggregata", navigaAggregata);
		Regione regioneSel=aggregataFacade.findRegione(Integer.valueOf(navigaAggregata.getIdRegione()));
		aResponse.setRenderParameter("codReg",regioneSel.getCodiRegione());
		String noAree="";
		if (!Validator.isNull(aRequest.getParameter("isDirect")) && Validator.equals("true", (String)aRequest.getParameter("isDirect"))){
			noAree="si";
		}
		aResponse.setRenderParameter("noAree",noAree);
	}
}
