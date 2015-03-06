package it.dipe.opencup.controllers;

import it.dipe.opencup.controllers.common.ClassificazionePortletCommonController;
import it.dipe.opencup.dto.DescrizioneValore;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.dto.NavigaProgetti;
import it.dipe.opencup.model.AnnoDecisione;
import it.dipe.opencup.model.AreaGeografica;
import it.dipe.opencup.model.AreaIntervento;
import it.dipe.opencup.model.CategoriaIntervento;
import it.dipe.opencup.model.CategoriaSoggetto;
import it.dipe.opencup.model.Comune;
import it.dipe.opencup.model.Progetti;
import it.dipe.opencup.model.Provincia;
import it.dipe.opencup.model.Regione;
import it.dipe.opencup.model.SottocategoriaSoggetto;
import it.dipe.opencup.model.SottosettoreIntervento;
import it.dipe.opencup.model.StatoProgetto;
import it.dipe.opencup.model.TipologiaIntervento;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
public class ElencoProgettiController extends ClassificazionePortletCommonController {
	
	@ModelAttribute("navigaProgetti")
	public NavigaProgetti modelAttrNavigaProgetti() {
		return new NavigaProgetti();
	}
	
	@RenderMapping
	public String handleRenderRequest(	RenderRequest renderRequest, 
										RenderResponse renderResponse,
										ModelMap modelMap, 
										@ModelAttribute("navigaProgetti") NavigaProgetti navigaProgetti){	
		
		HttpSession session = PortalUtil.getHttpServletRequest(renderRequest).getSession(false);
		NavigaAggregata navigaAggregata = (NavigaAggregata) session.getAttribute("navigaAggregata"); 
		
		if( ! navigaAggregata.getNaviga().equals(navigaProgetti.getNaviga()) ){
			navigaProgetti.importa( navigaAggregata );
		}
		
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

		SearchContainer<Progetti> searchContainerElenco = new SearchContainer<Progetti>(renderRequest, renderResponse.createRenderURL(), null, "Nessun dato trovato per la selezione fatta");
		searchContainerElenco.setDelta(delta);
		
		searchContainerElenco.setOrderByCol(orderByCol);
		searchContainerElenco.setOrderByType(orderByType);

		List<Progetti> elencoProgetti = progettiFacade.findElencoProgetti(	navigaProgetti, 
																			searchContainerElenco.getOrderByCol(), 
																			searchContainerElenco.getOrderByType());	
		
		searchContainerElenco.setTotal(elencoProgetti.size());
		
		elencoProgetti = ListUtil.subList(elencoProgetti, searchContainerElenco.getStart(), searchContainerElenco.getEnd());       
		searchContainerElenco.setResults(elencoProgetti);
		
		modelMap.addAttribute("searchContainerElenco", searchContainerElenco);
		
		// FINE LISTA PROGETTI //
		
		// MASCHERA RICERCA PROGETTI //
		initInModelMascheraRicerca(modelMap, navigaProgetti);
		
		modelMap.addAttribute("filtriProgetti", navigaProgetti);
		modelMap.addAttribute("navigaAggregata", navigaAggregata);
		// FINE RICERCA PROGETTI //
		
		
		// RIEPILOGO //
		SearchContainer<DescrizioneValore> searchContainerRiepilogo = new SearchContainer<DescrizioneValore>(renderRequest, renderResponse.createRenderURL(), null, "Nessun dato trovato per la selezione fatta");
		searchContainerRiepilogo.setDelta(maxResult);
		searchContainerRiepilogo.setTotal(3);
		
		Integer numeProgetti = 0;
		Double impoCostoProgetti = 0.0;
		Double impoImportoFinanziato = 0.0;
		
		for(Progetti aggregataDTO : elencoProgetti){
			numeProgetti++;
			impoCostoProgetti = impoCostoProgetti + aggregataDTO.getImpoCostoProgetto();
			impoImportoFinanziato = impoImportoFinanziato + aggregataDTO.getImpoImportoFinanziato();
		}
		
		List<DescrizioneValore> retval = new ArrayList<DescrizioneValore>();
		retval.add(new DescrizioneValore("VOLUME DEI PROGETTI", numeProgetti));
		retval.add(new DescrizioneValore("COSTO DEI PROGETTI", impoCostoProgetti));
		retval.add(new DescrizioneValore("IMPORTO FINANZIAMENTI", impoImportoFinanziato));
		
		searchContainerRiepilogo.setResults(retval);
		modelMap.addAttribute("searchContainerRiepilogo", searchContainerRiepilogo);
		// FINE RIEPILOGO //
		
		return "elenco-progetti-view";
	}
	
	@ActionMapping(params="action=ricerca")
	public void publishEvent(	ActionRequest aRequest, 
								ActionResponse aResponse, 
								ModelMap modelMap, 
								@ModelAttribute("navigaProgetti") NavigaProgetti navigaProgetti){
		
		modelMap.addAttribute("filtriProgetti", navigaProgetti);
	}
	
	private void initInModelMascheraRicerca(ModelMap modelMap, NavigaProgetti filtro) {
		
		//Carico la lista delle regioni
		List<AreaGeografica> listAreaGeografica = aggregataFacade.findAreaGeografica();
		modelMap.addAttribute("listAreaGeografica", listAreaGeografica);
		
		if( (! "-1".equals( filtro.getIdAreaGeografica() )) && (! "0".equals( filtro.getIdAreaGeografica() )) ){
			//Regione selezionata carico le Province
			List<Regione> listRegione = aggregataFacade.findRegioniByIdAreaGeografica(Integer.valueOf( filtro.getIdAreaGeografica() ));
			modelMap.addAttribute("listRegione", listRegione);
		}
		
		if( (! "-1".equals( filtro.getIdRegione() )) && (! "0".equals( filtro.getIdRegione() )) ){
			//Regione selezionata carico le Province
			List<Provincia> listProvincia = aggregataFacade.findProvinciaByIdRegione(Integer.valueOf( filtro.getIdRegione() ));
			modelMap.addAttribute("listProvincia", listProvincia);
		}
		
		if( (! "-1".equals( filtro.getIdProvincia() )) && (! "0".equals( filtro.getIdProvincia() )) ){
			//Provincia selezionata carico i Comuni
			List<Comune> listComune = aggregataFacade.findComuneByIdProvincia(Integer.valueOf( filtro.getIdProvincia() ));
			modelMap.addAttribute("listComune", listComune);
		}
		
		//Carico la lista degli Anni Decisione
		List<AnnoDecisione> listaAnnoDecisione = aggregataFacade.findAnniDecisione();
		modelMap.addAttribute("listaAnnoDecisione", listaAnnoDecisione);
		
		//Carico la lista delle Tipologia Intervento
		List<TipologiaIntervento> listaTipologiaIntervento = aggregataFacade.findTipologiaIntervento();
		modelMap.addAttribute("listaTipologiaIntervento", listaTipologiaIntervento);
		
		//Carico la lista degli Stato Progetto
		List<StatoProgetto> listaStatoProgetto = aggregataFacade.findStatoProgetto();
		modelMap.addAttribute("listaStatoProgetto", listaStatoProgetto);
		
		//Carico la lista della Categoria Soggetto
		List<CategoriaSoggetto> listCategoriaSoggetto = aggregataFacade.findCategoriaSoggetto();
		modelMap.addAttribute("listCategoriaSoggetto", listCategoriaSoggetto);
	
		if( (! "-1".equals( filtro.getIdCategoriaSoggetto() )) && (! "0".equals( filtro.getIdCategoriaSoggetto() )) ){
			//Carico la lista della Sottocategoria Soggetto
			List<SottocategoriaSoggetto> listSottoCategoriaSoggetto = aggregataFacade.findSottocategoriaSoggetto(Integer.valueOf( filtro.getIdCategoriaSoggetto() ));
			modelMap.addAttribute("listSottoCategoriaSoggetto", listSottoCategoriaSoggetto);
		}
		
		//Carico le Aree d'intervednto
		List<AreaIntervento> listAreaIntervento = aggregataFacade.findAreaIntervento();
		modelMap.addAttribute("listAreaIntervento", listAreaIntervento);
		
		if( (! "-1".equals( filtro.getIdAreaIntervento() )) && (! "0".equals( filtro.getIdAreaIntervento() )) ){
			//Settore intervento selezionata carico i sottosettori
			List<SottosettoreIntervento> listSottosettoreIntervento = aggregataFacade.findSottosettoreByArea(Integer.valueOf( filtro.getIdAreaIntervento() ));
			modelMap.addAttribute("listSottosettoreIntervento", listSottosettoreIntervento);
		}
		
		if( 
				((! "-1".equals( filtro.getIdAreaIntervento() )) && (! "-1".equals( filtro.getIdSottosettoreIntervento() )))
				&&
				((! "0".equals( filtro.getIdAreaIntervento() )) && (! "0".equals( filtro.getIdSottosettoreIntervento() )))
				){
			//Settore intervento e sottosettore intervento selezionati carico le categorie
			List<CategoriaIntervento> listaCategoriaIntervento = aggregataFacade.findCategoriaInterventoByAreaSottosettore(Integer.valueOf( filtro.getIdAreaIntervento() ), Integer.valueOf( filtro.getIdSottosettoreIntervento() ));
			modelMap.addAttribute("listaCategoriaIntervento", listaCategoriaIntervento);
		}
		
	}
}
