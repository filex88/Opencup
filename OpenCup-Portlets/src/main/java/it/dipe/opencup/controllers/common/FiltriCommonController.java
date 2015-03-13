package it.dipe.opencup.controllers.common;

import it.dipe.opencup.dto.FiltroRicercaDTO;
import it.dipe.opencup.facade.AggregataFacade;
import it.dipe.opencup.facade.ProgettiFacade;
import it.dipe.opencup.model.CategoriaIntervento;
import it.dipe.opencup.model.Comune;
import it.dipe.opencup.model.Provincia;
import it.dipe.opencup.model.Regione;
import it.dipe.opencup.model.SottocategoriaSoggetto;
import it.dipe.opencup.model.SottosettoreIntervento;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

public class FiltriCommonController {
	
	@Autowired
	private ProgettiFacade progettiFacade;
	
	@Autowired
	private AggregataFacade aggregataFacade;
	
	@ResourceMapping(value =  "loadRegioneByAreaGeografica")	
	protected View loadRegioneByAreaGeografica(@RequestParam("pattern") Integer pattern){
		
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		
		List<FiltroRicercaDTO> lista = new ArrayList<FiltroRicercaDTO>();
		FiltroRicercaDTO ele = null;
		for(Regione regione : aggregataFacade.findRegioniByIdAreaGeografica(pattern)){
			ele = new FiltroRicercaDTO();
			ele.setId( regione.getId() );
			ele.setLabel( regione.getDescRegione() );
			lista.add(ele);
		}
		
		view.addStaticAttribute("lista", lista);
		return view;
	}
	
	@ResourceMapping(value =  "loadProvinciaByRegione")	
	protected View loadProvinciaByRegione(@RequestParam("pattern") Integer pattern){
		
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		
		List<FiltroRicercaDTO> lista = new ArrayList<FiltroRicercaDTO>();
		FiltroRicercaDTO ele = null;
		for(Provincia provincia : aggregataFacade.findProvinciaByIdRegione(pattern)){
			ele = new FiltroRicercaDTO();
			ele.setId( provincia.getId() );
			ele.setLabel( provincia.getDescProvincia() );
			lista.add(ele);
		}
		
		view.addStaticAttribute("lista", lista);
		return view;
	}
	
	@ResourceMapping(value =  "loadComuniByProvincia")	
	protected View loadComuniByProvincia(@RequestParam("pattern") Integer pattern){
		
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		
		List<FiltroRicercaDTO> lista = new ArrayList<FiltroRicercaDTO>();
		FiltroRicercaDTO ele = null;
		for(Comune comune : aggregataFacade.findComuneByIdProvincia(pattern)){
			ele = new FiltroRicercaDTO();
			ele.setId( comune.getId() );
			ele.setLabel( comune.getDescComune() );
			lista.add(ele);
		}
		
		view.addStaticAttribute("lista", lista);
		return view;
	}
	
	@ResourceMapping(value =  "loadSottosettoreInterventoByArea")	
	protected View loadSottosettoreInterventoBySettore(@RequestParam("pattern") Integer pattern){

		MappingJacksonJsonView view = new MappingJacksonJsonView();
		
		List<FiltroRicercaDTO> lista = new ArrayList<FiltroRicercaDTO>();
		FiltroRicercaDTO ele = null;
		for(SottosettoreIntervento settoreIntervento : aggregataFacade.findSottosettoreByArea(pattern)){
			ele = new FiltroRicercaDTO();
			ele.setId( settoreIntervento.getId() );
			ele.setLabel( settoreIntervento.getDescSottosettoreInt() );
			lista.add(ele);
		}
		view.addStaticAttribute("lista", lista);
		return view;
	}
	
	@ResourceMapping(value =  "loadCategoriaInterventoByAreaSottosettore")	
	protected View loadCategoriaInterventoByAreaSottosettore(@RequestParam("pattern") Integer pattern, @RequestParam("pattern2") Integer pattern2){
		
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		
		List<FiltroRicercaDTO> lista = new ArrayList<FiltroRicercaDTO>();
		FiltroRicercaDTO ele = null;
		for(CategoriaIntervento categoriaIntervento : aggregataFacade.findCategoriaInterventoByAreaSottosettore(pattern, pattern2)){
			ele = new FiltroRicercaDTO();
			ele.setId( categoriaIntervento.getId() );
			ele.setLabel( categoriaIntervento.getDescCategoriaIntervento() );
			lista.add(ele);
		}
		
		view.addStaticAttribute("lista", lista);
		return view;
	}
	
	@ResourceMapping(value =  "loadSottoCategoriaSoggettoByCategoriaSoggetto")	
	protected View loadSottoCategoriaSoggettoByCategoriaSoggetto(@RequestParam("pattern") Integer pattern){
		
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		
		List<FiltroRicercaDTO> lista = new ArrayList<FiltroRicercaDTO>();
		FiltroRicercaDTO ele = null;
		for(SottocategoriaSoggetto sottocategoriaSoggetto : aggregataFacade.findSottocategoriaSoggetto(pattern)){
			ele = new FiltroRicercaDTO();
			ele.setId( sottocategoriaSoggetto.getId() );
			ele.setLabel( sottocategoriaSoggetto.getDescSottocategSoggetto() );
			lista.add(ele);
		}
		
		view.addStaticAttribute("lista", lista);
		return view;
	}
	
}
