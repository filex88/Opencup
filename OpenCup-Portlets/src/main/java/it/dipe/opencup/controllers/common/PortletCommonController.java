package it.dipe.opencup.controllers.common;

import it.dipe.opencup.dto.FiltroRicercaDTO;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.facade.AggregataFacade;
import it.dipe.opencup.facade.ProgettoFacade;
import it.dipe.opencup.model.AnnoDecisione;
import it.dipe.opencup.model.AreaGeografica;
import it.dipe.opencup.model.CategoriaIntervento;
import it.dipe.opencup.model.CategoriaSoggetto;
import it.dipe.opencup.model.Comune;
import it.dipe.opencup.model.Natura;
import it.dipe.opencup.model.Provincia;
import it.dipe.opencup.model.Regione;
import it.dipe.opencup.model.SettoreIntervento;
import it.dipe.opencup.model.SottocategoriaSoggetto;
import it.dipe.opencup.model.SottosettoreIntervento;
import it.dipe.opencup.model.StatoProgetto;
import it.dipe.opencup.model.TipologiaIntervento;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

public class PortletCommonController {
	
	protected static final String SESSION_FILTRI_RICERCA = "SESSION_FILTRI_RICERCA";
	
	@Value("#{config['paginazione.risultatiPerPagina']}")
	protected int maxResult;
	
	@Autowired
	protected ProgettoFacade progettoFacade;
	
	@Autowired
	protected AggregataFacade aggregataFacade;
	
	protected void initInModelMascheraRicerca(Model model, NavigaAggregata filtro) {
		
		//Carico la lista delle regioni
		List<AreaGeografica> listAreaGeografica = aggregataFacade.findAreaGeografica();
		model.addAttribute("listAreaGeografica", listAreaGeografica);
		
		if( (! "-1".equals( filtro.getIdAreaGeografica() )) && (! "0".equals( filtro.getIdAreaGeografica() )) ){
			//Regione selezionata carico le Province
			List<Regione> listRegione = aggregataFacade.findRegioniByIdAreaGeografica(Integer.valueOf( filtro.getIdAreaGeografica() ));
			model.addAttribute("listRegione", listRegione);
		}
		
		if( (! "-1".equals( filtro.getIdRegione() )) && (! "0".equals( filtro.getIdRegione() )) ){
			//Regione selezionata carico le Province
			List<Provincia> listProvincia = aggregataFacade.findProvinciaByIdRegione(Integer.valueOf( filtro.getIdRegione() ));
			model.addAttribute("listProvincia", listProvincia);
		}
		
		if( (! "-1".equals( filtro.getIdProvincia() )) && (! "0".equals( filtro.getIdProvincia() )) ){
			//Provincia selezionata carico i Comuni
			List<Comune> listComune = aggregataFacade.findComuneByIdProvincia(Integer.valueOf( filtro.getIdProvincia() ));
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
		
		//Carico la lista delle NAture
		List<Natura> listaNatura = aggregataFacade.findNatura();
		model.addAttribute("listaNatura", listaNatura);
		
		if( (! "-1".equals( filtro.getIdNatura() )) && (! "0".equals( filtro.getIdNatura() )) ){
			//Natura selezionata carico i settori
			List<SettoreIntervento> listSettoreIntervento = aggregataFacade.findSettoreByNatura(Integer.valueOf( filtro.getIdNatura() ));
			model.addAttribute("listSettoreIntervento", listSettoreIntervento);
		}
		
		if( (! "-1".equals( filtro.getIdAreaIntervento() )) && (! "0".equals( filtro.getIdAreaIntervento() )) ){
			//Settore intervento selezionata carico i sottosettori
			List<SottosettoreIntervento> listSottosettoreIntervento = aggregataFacade.findSottosettoreByArea(Integer.valueOf( filtro.getIdAreaIntervento() ));
			model.addAttribute("listSottosettoreIntervento", listSottosettoreIntervento);
		}
		
		if( 
				((! "-1".equals( filtro.getIdAreaIntervento() )) && (! "-1".equals( filtro.getIdSottosettoreIntervento() )))
				&&
				((! "0".equals( filtro.getIdAreaIntervento() )) && (! "0".equals( filtro.getIdSottosettoreIntervento() )))
				){
			//Settore intervento e sottosettore intervento selezionati carico le categorie
			List<CategoriaIntervento> listaCategoriaIntervento = aggregataFacade.findCategoriaInterventoByAreaSottosettore(Integer.valueOf( filtro.getIdAreaIntervento() ), Integer.valueOf( filtro.getIdSottosettoreIntervento() ));
			model.addAttribute("listaCategoriaIntervento", listaCategoriaIntervento);
		}
		
	}
	
	@ResourceMapping(value =  "loadRegioneByAreaGeografica")	
	public View loadRegioneByAreaGeografica(@RequestParam("pattern") Integer pattern){
		
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		
		List<FiltroRicercaDTO> regioni = new ArrayList<FiltroRicercaDTO>();
		FiltroRicercaDTO ele = null;
		for(Regione regione : aggregataFacade.findRegioniByIdAreaGeografica(pattern)){
			ele = new FiltroRicercaDTO();
			ele.setId( regione.getId() );
			ele.setLabel( regione.getDescRegione() );
			regioni.add(ele);
		}
		
		view.addStaticAttribute("lista", regioni);
		return view;
	}
	
	@ResourceMapping(value =  "loadProvinciaByRegione")	
	public View loadProvinciaByRegione(@RequestParam("pattern") Integer pattern){
		
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		
		List<FiltroRicercaDTO> provincie = new ArrayList<FiltroRicercaDTO>();
		FiltroRicercaDTO ele = null;
		for(Provincia provincia : aggregataFacade.findProvinciaByIdRegione(pattern)){
			ele = new FiltroRicercaDTO();
			ele.setId( provincia.getId() );
			ele.setLabel( provincia.getDescProvincia() );
			provincie.add(ele);
		}
		
		view.addStaticAttribute("lista", provincie);
		return view;
	}
	
	@ResourceMapping(value =  "loadComuniByProvincia")	
	public View loadComuniByProvincia(@RequestParam("pattern") Integer pattern){
		
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		
		List<FiltroRicercaDTO> comuni = new ArrayList<FiltroRicercaDTO>();
		FiltroRicercaDTO ele = null;
		for(Comune comune : aggregataFacade.findComuneByIdProvincia(pattern)){
			ele = new FiltroRicercaDTO();
			ele.setId( comune.getId() );
			ele.setLabel( comune.getDescComune() );
			comuni.add(ele);
		}
		
		view.addStaticAttribute("lista", comuni);
		return view;
	}
	
	@ResourceMapping(value =  "loadSettoreInterventoByNatura")	
	public View loadSettoreInterventoByNatura(@RequestParam("pattern") Integer pattern){
		
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		
		List<FiltroRicercaDTO> settoriIntervento = new ArrayList<FiltroRicercaDTO>();
		FiltroRicercaDTO ele = null;
		for(SettoreIntervento settoreIntervento : aggregataFacade.findSettoreByNatura(pattern)){
			ele = new FiltroRicercaDTO();
			ele.setId( settoreIntervento.getId() );
			ele.setLabel( settoreIntervento.getDescSettoreIntervento() );
			settoriIntervento.add(ele);
		}
		
		view.addStaticAttribute("lista", settoriIntervento);
		return view;
	}
	
	@ResourceMapping(value =  "loadSottosettoreInterventoBySettore")	
	public View loadSottosettoreInterventoBySettore(@RequestParam("pattern") Integer pattern){
		
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		
		List<FiltroRicercaDTO> SottosettoriIntervento = new ArrayList<FiltroRicercaDTO>();
		FiltroRicercaDTO ele = null;
		for(SottosettoreIntervento settoreIntervento : aggregataFacade.findSottosettoreByArea(pattern)){
			ele = new FiltroRicercaDTO();
			ele.setId( settoreIntervento.getId() );
			ele.setLabel( settoreIntervento.getDescSottosettoreInt() );
			SottosettoriIntervento.add(ele);
		}
		
		view.addStaticAttribute("lista", SottosettoriIntervento);
		return view;
	}
	
	@ResourceMapping(value =  "loadCategoriaInterventoBySettoreSottosettore")	
	public View loadCategoriaInterventoBySettoreSottosettore(@RequestParam("pattern") Integer pattern, @RequestParam("pattern") Integer pattern2){
		
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		
		List<FiltroRicercaDTO> categorieIntervento = new ArrayList<FiltroRicercaDTO>();
		FiltroRicercaDTO ele = null;
		for(CategoriaIntervento categoriaIntervento : aggregataFacade.findCategoriaInterventoByAreaSottosettore(pattern, pattern2)){
			ele = new FiltroRicercaDTO();
			ele.setId( categoriaIntervento.getId() );
			ele.setLabel( categoriaIntervento.getDescCategoriaIntervento() );
			categorieIntervento.add(ele);
		}
		
		view.addStaticAttribute("lista", categorieIntervento);
		return view;
	}
	
}
