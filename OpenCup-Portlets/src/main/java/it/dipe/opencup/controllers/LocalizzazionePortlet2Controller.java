package it.dipe.opencup.controllers;

import it.dipe.opencup.controllers.common.LocalizzazionePortletCommonController;
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

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;

@Controller
@RequestMapping("VIEW")
public class LocalizzazionePortlet2Controller extends LocalizzazionePortletCommonController{
	
	@Value("#{config['pagina.elenco.progetti']}")
	private String paginaElencoProgetti;
	
	@Autowired
	private AggregataFacade aggregataFacade;
	
	@RenderMapping
	public String handleRenderRequest(RenderRequest request, RenderResponse response,Model model){
		HttpServletRequest httpServletRequest = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(request));
		String idTerr=httpServletRequest.getParameter("idTerr")!=null?httpServletRequest.getParameter("idTerr").toString():"";
		String filtriPrec=httpServletRequest.getParameter("filtri")!=null?httpServletRequest.getParameter("filtri").toString():"";
		NavigaAggregata filtro= null;
		PortletURL portletURL = response.createRenderURL();
		// se non vengo dalla prima request
		if (Validator.isNull(idTerr)  || Validator.equals("", idTerr)){
			idTerr=ParamUtil.getString(request, "idTerr");
		}

		AreaGeografica areaSelezionata =aggregataFacade.findAreaGeograficaByCodiceArea(idTerr);
		String strIdAreaSel=areaSelezionata!=null?areaSelezionata.getId().toString():"0";
		String descAreaGeo=areaSelezionata!=null?areaSelezionata.getDescAreaGeografica().toUpperCase():"";
		
		// se ho parametri impostati (solo al primo caricamento)
		if (!Validator.isNull(filtriPrec)  && !Validator.equals("", filtriPrec)){
			filtro=super.createModelFromJsonString(filtriPrec);
			filtro.setIdRegione("0");
			filtro.setIdProvincia("-1");
			filtro.setIdAreaGeografica(strIdAreaSel);
			String jsonString=createJsonStringFromModelAttribute(filtro);
			portletURL.setParameter("filtroAsString", jsonString);
		}else{
			if (model.asMap().get("navigaAggregata")==null && (Validator.isNull(ParamUtil.getString(request,"filtroAsString")) || "".equals(ParamUtil.getString(request,"filtroAsString")))){
				// caricamento full senza filtri
				filtro=new NavigaAggregata(NavigaAggregata.NAVIGA_LOCALIZZAZIONE,"0");
				filtro.setIdRegione("0");
				filtro.setIdAreaGeografica(strIdAreaSel);
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
		
		
		portletURL.setParameter("idTerr",idTerr);
		model.addAttribute("selectedTerritory", idTerr);
		
		Long numeProgetti = new Long(0);
		double impoCostoProgetti = 0.0;
		double impoImportoFinanziato = 0.0;
		String nestedDetailUrl=calcolaUrlLocalizzazioneByLivello(request, "localizzazioneprovince");
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
			String detailUrl=nestedDetailUrl+"&codReg="+codiceRegione;
			if (filtro.getCountAffRicercaLocalizzazione()!=null){
				detailUrl+="&filtri="+createJsonStringFromModelAttribute(filtro);
			}
			valoreByRegione.setDetailUrl(HttpUtil.encodeParameters(detailUrl));
			valoreByRegione.setFullLabel(nomeRegione.replace("'", "$"));
			valoreByRegione.setLinkMatch(idTerr+"_"+codiceRegione);
			valori.add(valoreByRegione);
		}
		model.addAttribute("statoSelected",filtro.getDescStato());
		String strAreeBackLink=calcolaUrlLocalizzazioneByLivello(request, "localizzazione");
		if (filtro.getCountAffRicercaLocalizzazione()!=null){
			strAreeBackLink+="&filtri="+createJsonStringFromModelAttribute(filtro);
		}
		model.addAttribute("areeGeoBackLink",HttpUtil.encodeParameters(strAreeBackLink));
		model.addAttribute("selectedTerritoryName", descAreaGeo);
		model.addAttribute("jsonResultLocalizzazione",createJsonStringFromQueryResult(valori));
		super.inizializzaFiltriRicercaLocalizzazione(filtro,model);
		model.addAttribute("navigaAggregata", filtro);
		

		// link elenco progetti
		String urlElencoProgetti=super.calcolaUrlLocalizzazioneByLivello(request, paginaElencoProgetti);
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
	
		searchContainerDistinct.setResults(valori);
		// ordinamento in base a property selezionata
		Collections.sort(valori,new CommonLocalizationValueComparator(orderByCol, orderByType));
		model.addAttribute("searchContainerDistinct", searchContainerDistinct);
		

		// valori totali
		model.addAttribute("volumeDeiProgetti", numeProgetti);
		model.addAttribute("costoDeiProgetti", impoCostoProgetti);
		model.addAttribute("importoFinanziamenti", impoImportoFinanziato);
		
		
		return "localizzazione2-view";
	}
	
	@ActionMapping(params="action=affinaricerca")
	public void actionAffinaRicerca(ActionRequest aRequest, ActionResponse aResponse, Model model, @ModelAttribute("navigaAggregata") NavigaAggregata navigaAggregata){
		model.addAttribute("navigaAggregata", navigaAggregata);
		AreaGeografica areaSel=aggregataFacade.findAreaGeografica(Integer.valueOf(navigaAggregata.getIdAreaGeografica()));
		aResponse.setRenderParameter("idTerr",areaSel.getCodiAreaGeografica());
	}
}
