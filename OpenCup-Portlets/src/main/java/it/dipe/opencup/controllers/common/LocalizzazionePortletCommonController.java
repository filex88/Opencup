package it.dipe.opencup.controllers.common;

import it.dipe.opencup.dto.LocalizationValueConverter;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.facade.AggregataFacade;
import it.dipe.opencup.model.AnnoAggregato;
import it.dipe.opencup.model.AreaGeografica;
import it.dipe.opencup.model.AreaIntervento;
import it.dipe.opencup.model.AreaSoggetto;
import it.dipe.opencup.model.CategoriaIntervento;
import it.dipe.opencup.model.CategoriaSoggetto;
import it.dipe.opencup.model.Comune;
import it.dipe.opencup.model.Provincia;
import it.dipe.opencup.model.Regione;
import it.dipe.opencup.model.SottocategoriaSoggetto;
import it.dipe.opencup.model.SottosettoreIntervento;
import it.dipe.opencup.model.StatoProgetto;
import it.dipe.opencup.model.TipologiaIntervento;

import java.io.IOException;
import java.util.List;

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLFactoryUtil;

public class LocalizzazionePortletCommonController extends FiltriCommonController{
	
	@Value("#{config['paginazione.risultatiPerPagina.localizzazione']}")
	protected int maxResult;
	
	@Value("#{config['codice.natura.open.cup']}")
	protected String codNaturaOpenCUP;
	
	@Autowired
	private AggregataFacade aggregataFacade;
	
	
	protected String calcolaUrlLocalizzazioneByLivello(PortletRequest request,String friendlyUrlEnd){
		LiferayPortletURL renderURL = null;
		String returnUrl="";
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);		
		Long groupID=themeDisplay.getLayout().getGroupId();
		String portletId = (String) request.getAttribute(WebKeys.PORTLET_ID);
		
		try {
			List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(groupID, false);
			for (Layout layout: layouts){
				String nodeName = PortalUtil.getLayoutFriendlyURL(layout, themeDisplay);
				if (nodeName.endsWith(friendlyUrlEnd)){
					renderURL = PortletURLFactoryUtil.create(request, portletId, layout.getPlid(), PortletRequest.ACTION_PHASE);
					renderURL.setWindowState(WindowState.NORMAL);
					renderURL.setPortletMode(PortletMode.VIEW);
					returnUrl=renderURL.toString();
					break;
				}
			}			
		} catch (SystemException e) {
			e.printStackTrace();
		} catch (PortalException e) {
			e.printStackTrace();
		}catch (WindowStateException e) {
			e.printStackTrace();
		} catch (PortletModeException e) {
			e.printStackTrace();
		}
		
		return returnUrl;
	}

	protected String createJsonStringFromQueryResult(List<LocalizationValueConverter> formattedResult){
		ObjectMapper mapper= new ObjectMapper();
		String jsonString=null;
		try {
			jsonString = mapper.writeValueAsString(formattedResult);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return jsonString;
	}
	
	
	protected String createJsonStringFromModelAttribute(NavigaAggregata filtro){
		ObjectMapper mapper= new ObjectMapper();
		
		String jsonString=null;
		try {
			jsonString = mapper.writeValueAsString(filtro);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return jsonString;
	}
	
	protected NavigaAggregata createModelFromJsonString(String jsonString){
		ObjectMapper mapper= new ObjectMapper();
		NavigaAggregata model=null;
		try {
			model = mapper.readValue(jsonString, NavigaAggregata.class);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return model;
	}
	
	
	protected void inizializzaFiltriRicercaLocalizzazione(NavigaAggregata filtro,Model model){
		
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
		
		//Carico la lista degli Anni Aggregata
		List<AnnoAggregato> listaAnnoAggregato = aggregataFacade.findAnnoAggregato();
		model.addAttribute("listaAnnoAggregato", listaAnnoAggregato);
		
		//Carico la lista delle Tipologia Intervento
		List<TipologiaIntervento> listaTipologiaIntervento = aggregataFacade.findTipologiaIntervento();
		model.addAttribute("listaTipologiaIntervento", listaTipologiaIntervento);
		
		//Carico la lista degli Stato Progetto
		List<StatoProgetto> listaStatoProgetto = aggregataFacade.findStatoProgetto();
		model.addAttribute("listaStatoProgetto", listaStatoProgetto);
		
		//Carico la lista della Aree Soggetto
		List<AreaSoggetto> listAreaSoggetto = aggregataFacade.findAreaSoggetto();
		model.addAttribute("listAreaSoggetto", listAreaSoggetto);
		
		if( (! "-1".equals( filtro.getIdAreaSoggetto() )) && (! "0".equals( filtro.getIdAreaSoggetto() )) ){
			//Carico la lista della Categoria Soggetto
			List<CategoriaSoggetto> listCategoriaSoggetto = aggregataFacade.findCategoriaSoggettoByIdAreaSoggetto(Integer.valueOf( filtro.getIdAreaSoggetto() ));
			model.addAttribute("listCategoriaSoggetto", listCategoriaSoggetto);
		}
		
		if( (! "-1".equals( filtro.getIdCategoriaSoggetto() )) && (! "0".equals( filtro.getIdCategoriaSoggetto() )) ){
			//Carico la lista della Sottocategoria Soggetto
			List<SottocategoriaSoggetto> listSottoCategoriaSoggetto = aggregataFacade.findSottocategoriaSoggetto(Integer.valueOf( filtro.getIdCategoriaSoggetto() ));
			model.addAttribute("listSottoCategoriaSoggetto", listSottoCategoriaSoggetto);
		}
		
		//Carico le Aree d'intervednto
		List<AreaIntervento> listAreaIntervento = aggregataFacade.findAreaIntervento();
		model.addAttribute("listAreaIntervento", listAreaIntervento);
		
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
	
}
