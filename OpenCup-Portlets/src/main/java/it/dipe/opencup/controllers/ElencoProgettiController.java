package it.dipe.opencup.controllers;

import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.facade.AggregataFacade;
import it.dipe.opencup.facade.ProgettiFacade;
import it.dipe.opencup.model.AnnoDecisione;
import it.dipe.opencup.model.AreaGeografica;
import it.dipe.opencup.model.CategoriaSoggetto;
import it.dipe.opencup.model.Comune;
import it.dipe.opencup.model.Progetti;
import it.dipe.opencup.model.Provincia;
import it.dipe.opencup.model.Regione;
import it.dipe.opencup.model.SottocategoriaSoggetto;
import it.dipe.opencup.model.StatoProgetto;
import it.dipe.opencup.model.TipologiaIntervento;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;

@Controller
@RequestMapping("VIEW")
public class ElencoProgettiController {
	
	@Value("#{config['paginazione.risultatiPerPagina']}")
	protected int maxResult;
	
	@Autowired
	private ProgettiFacade progettiFacade;
	
	@Autowired
	private AggregataFacade aggregataFacade;

	protected static final String SESSION_FILTRI_RICERCA = "SESSION_FILTRI_RICERCA";
	
	@ModelAttribute("modelAttrFiltriRicercaElePj")
	public NavigaAggregata modelAttrNaturaRicerca(PortletRequest portletRequest) {
		HttpSession session = PortalUtil.getHttpServletRequest(portletRequest).getSession(false);
		NavigaAggregata filtri = (session.getAttribute(SESSION_FILTRI_RICERCA)==null)?null:(NavigaAggregata) session.getAttribute(SESSION_FILTRI_RICERCA);
		if( filtri == null )
			filtri = new NavigaAggregata();
		return filtri;
	}

	@RenderMapping
	public String handleRenderRequest(	RenderRequest renderRequest, 
			RenderResponse renderResponse,
			Model model,
			@ModelAttribute("modelAttrFiltriRicercaElePj") NavigaAggregata filtri){	

		// LISTA PROGETTI //
		
		//orderByCol is the column name passed in the request while sorting
		String orderByCol = ParamUtil.getString(renderRequest, "orderByCol"); 
		if(Validator.isNull(orderByCol)  || Validator.equals("", orderByCol)){
			orderByCol = "impoCostoProgetto";
		}
		
		//orderByType is passed in the request while sorting. It can be either asc or desc
		String orderByType = ParamUtil.getString(renderRequest, "orderByType");
		if(Validator.isNull(orderByType)  || Validator.equals("", orderByType)){
		    orderByType = "asc";
		}
		
		//delta
		String sDelta = ParamUtil.getString(renderRequest, "delta");
		int delta = maxResult;
		if( ! ( Validator.isNull(sDelta) || Validator.equals("", sDelta) ) ){
		    delta = Integer.parseInt(sDelta);
		}

		SearchContainer<Progetti> searchContainer = new SearchContainer<Progetti>(renderRequest, renderResponse.createRenderURL(), null, "Nessun dato trovato per la selezione fatta");
		searchContainer.setDelta(delta);
		
		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByType(orderByType);

		List<Progetti> elencoProgetti = progettiFacade.findElencoProgetti(	filtri, 
																			searchContainer.getOrderByCol(), 
																			searchContainer.getOrderByType());	
		
		searchContainer.setTotal(elencoProgetti.size());
		
		elencoProgetti = ListUtil.subList(elencoProgetti, searchContainer.getStart(), searchContainer.getEnd());       
		searchContainer.setResults(elencoProgetti);
		
		model.addAttribute("searchContainer", searchContainer);
		
		// FINE LISTA PROGETTI //
		
		/////////////////////////////////////
		
		// RICERCA PROGETTI //
		
		//Carico la lista delle regioni
		
		List<AreaGeografica> listAreaGeografica = aggregataFacade.findAreaGeografica();
		model.addAttribute("listAreaGeografica", listAreaGeografica);
		
		if( ! "-1".equals( filtri.getIdAreaGeografica() ) ){
			//Regione selezionata carico le Province
			List<Regione> listRegione = aggregataFacade.findRegioniByIdAreaGeografica(Integer.valueOf( filtri.getIdAreaGeografica() ));
			model.addAttribute("listRegione", listRegione);
		}
		
		if( ! "-1".equals( filtri.getIdRegione() ) ){
			//Regione selezionata carico le Province
			List<Provincia> listProvincia = aggregataFacade.findProvinciaByIdRegione(Integer.valueOf( filtri.getIdRegione() ));
			model.addAttribute("listProvincia", listProvincia);
		}
		
		if( ! "-1".equals( filtri.getIdProvincia() ) ){
			//Provincia selezionata carico i Comuni
			List<Comune> listComune = aggregataFacade.findComuneByIdProvincia(Integer.valueOf( filtri.getIdProvincia() ));
			model.addAttribute("listComune", listComune);
		}
		
		//Carico la lista degli Anni Decisione
		List<AnnoDecisione> listaAnnoDecisione = aggregataFacade.findAnniDecisione();
		model.addAttribute("listaAnnoDecisione", listaAnnoDecisione);
		
		//Carico la lista delle Tipologia Intervento
		List<TipologiaIntervento> listaTipologiaIntervento = aggregataFacade.findTipologiaIntervento();
		model.addAttribute("listaTipologiaIntervento", listaTipologiaIntervento);
		
		//Carico la lista degli Stato Progetto
		List<StatoProgetto> listaStatoProgetto = aggregataFacade.findStatoProgetto();
		model.addAttribute("listaStatoProgetto", listaStatoProgetto);
		
		//Carico la lista della Categoria Soggetto
		List<CategoriaSoggetto> listCategoriaSoggetto = aggregataFacade.findCategoriaSoggetto();
		model.addAttribute("listCategoriaSoggetto", listCategoriaSoggetto);
		
		//Carico la lista della Sottocategoria Soggetto
		List<SottocategoriaSoggetto> listSottoCategoriaSoggetto = aggregataFacade.findSottocategoriaSoggetto();
		model.addAttribute("listSottoCategoriaSoggetto", listSottoCategoriaSoggetto);
		
		model.addAttribute("modelAttrFiltriRicercaElePj", filtri);
		
		// FINE RICERCA PROGETTI //
		
		return "elenco-progetti-view";
	}
	
	@ActionMapping(params="action=ricerca")
	public void publishEvent(ActionRequest aRequest, ActionResponse aResponse, Model model, @ModelAttribute("modelAttrFiltriRicercaElePj") NavigaAggregata filtri){
		
		HttpSession session = PortalUtil.getHttpServletRequest(aRequest).getSession(false);
		session.setAttribute(SESSION_FILTRI_RICERCA,filtri);
		
		model.addAttribute("modelAttrFiltriRicercaElePj", filtri);
	}
	
	
}
