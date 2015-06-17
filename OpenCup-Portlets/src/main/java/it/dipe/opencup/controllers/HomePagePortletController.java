package it.dipe.opencup.controllers;

import it.dipe.opencup.dto.AggregataDTO;
import it.dipe.opencup.dto.D3PieConverter;
import it.dipe.opencup.dto.LocalizationValueConverter;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.facade.AggregataFacade;
import it.dipe.opencup.model.Aggregata;
import it.dipe.opencup.model.Natura;
import it.dipe.opencup.model.ProgettiTotali;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLFactoryUtil;

@Controller
@RequestMapping("VIEW")
public class HomePagePortletController {
	
	@Autowired
	private AggregataFacade aggregataFacade;
	@Value("#{config['codice.natura.open.cup']}")
	protected String codNaturaOpenCUP;
	
	@Value("#{config['pagina.classificazione']}")
	private String paginaClassificazione;
	
	@Value("#{config['pagina.localizzazione']}")
	private String paginaLocalizzazione;
	
	@Value("#{config['pagina.soggetto']}")
	private String paginaSoggetto;

	@Value("#{config['portlet.classificazione.instanceId']}")
	private String portletClassificazione;
	
	@Value("#{config['portlet.localizzazione.instanceId']}")
	private String portletLocalizzazione;
	
	@Value("#{config['portlet.soggetto.instanceId']}")
	private String portletSoggetto;
	
	
	@RenderMapping
	public String renderRequest(RenderRequest renderRequest, RenderResponse renderResponse,Model model, @RequestParam(required=false, defaultValue="VOLUME", value="pattern") String pattern){
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		model.addAttribute("jsFolder",themeDisplay.getPathThemeJavaScript());
		model.addAttribute("imgFolder",themeDisplay.getPathThemeImages());
	
		// Localizzazione
		NavigaAggregata filtro=new NavigaAggregata(NavigaAggregata.NAVIGA_LOCALIZZAZIONE,"0");
		filtro.setIdAreaGeografica("0");
		Natura naturaOpenCup=aggregataFacade.findNaturaByCod(codNaturaOpenCUP);
		filtro.setIdNatura(naturaOpenCup.getId().toString());
		filtro.setDescNatura(naturaOpenCup.getDescNatura());
		// costruzione risultato
		// totali per localizzazione
	

		List<Aggregata> risultati=aggregataFacade.findAggregataByLocalizzazione(filtro);
		List<LocalizationValueConverter> valori= new ArrayList<LocalizationValueConverter>();
		for (Aggregata aggregata:risultati){
			LocalizationValueConverter areaGeo= new LocalizationValueConverter();
			String codAreaGeo=aggregata.getLocalizzazione().getAreaGeografica().getCodiAreaGeografica();
			String nomeAreaGeo=aggregata.getLocalizzazione().getAreaGeografica().getDescAreaGeografica();
			areaGeo.setLocalizationLabel(codAreaGeo);
			areaGeo.setImportoValue(aggregata.getImpoImportoFinanziato());
			areaGeo.setCostoValue(aggregata.getImpoCostoProgetti());
			areaGeo.setVolumeValue(aggregata.getNumeProgetti());
			areaGeo.setFullLabel(nomeAreaGeo);
			valori.add(areaGeo);
		}
	
		model.addAttribute("valoriLoc", valori);
		model.addAttribute("jsonResultLocalizzazione",createJsonStringLocalizzazioneFromQueryResult(valori));
		
		//** CAROUSEL 1 Pagina **//
		
		Long numeProgettiClass = new Long(0);
		double impoCostoProgettiClass = 0.0;
		double impoImportoFinanziatoClass = 0.0;
		
		NavigaAggregata filtroClass=new NavigaAggregata(NavigaAggregata.NAVIGA_CLASSIFICAZIONE,"0");
		filtroClass.setIdNatura(naturaOpenCup.getId().toString());
		filtroClass.setDescNatura(naturaOpenCup.getDescNatura());
		filtroClass.setIdAreaIntervento("0");
		List<AggregataDTO> risultatiClassificazione=aggregataFacade.findAggregataByNatura(filtroClass);
		for(AggregataDTO aggregataDTO : risultatiClassificazione){
			numeProgettiClass = numeProgettiClass + aggregataDTO.getNumeProgetti();
			impoCostoProgettiClass = impoCostoProgettiClass + aggregataDTO.getImpoCostoProgetti();
			impoImportoFinanziatoClass = impoImportoFinanziatoClass + aggregataDTO.getImpoImportoFinanziato();
		}
		
		model.addAttribute("jsonResultClassificazione",createJsonStringClassificazioneFromQueryResult(risultatiClassificazione));
		model.addAttribute("numeProgettiClass", numeProgettiClass);
		model.addAttribute("impoCostoProgettiClass", impoCostoProgettiClass);
		model.addAttribute("impoImportoFinanziatoClass", impoImportoFinanziatoClass);
		
		//** CAROUSEL 2 - Progetti Totali **//
		
		Double numeProgettiTotaliClass = new Double(0);
		double impoCostoProgetti = 0.0;
		double importoFinanziato = 0.0;
		
		NavigaAggregata filtroTotaliClass=new NavigaAggregata(NavigaAggregata.NAVIGA_CLASSIFICAZIONE,"0");
		filtroTotaliClass.setIdNatura(naturaOpenCup.getId().toString());
		filtroTotaliClass.setDescNatura(naturaOpenCup.getDescNatura());
		filtroTotaliClass.setIdAreaIntervento("0");
		List<ProgettiTotali> risultatiTotaliClassificazione=aggregataFacade.findDatiCUPByNatura(Integer.parseInt(filtroTotaliClass.getIdNatura()));
		for(ProgettiTotali progettiTotali : risultatiTotaliClassificazione){
			numeProgettiTotaliClass = numeProgettiTotaliClass + progettiTotali.getNumeProgetti();
			impoCostoProgetti = impoCostoProgetti + progettiTotali.getImpoCostoProgetti();
			importoFinanziato = importoFinanziato + progettiTotali.getImpoImportoFinanziato();
		}
		
		model.addAttribute("jsonResultClassificazione",createJsonStringClassificazioneFromQueryResult(risultatiClassificazione));
		model.addAttribute("numeProgettiTotaliClass", numeProgettiTotaliClass);
		model.addAttribute("impoCostoProgetti", impoCostoProgetti);
		model.addAttribute("importoFinanziato", importoFinanziato);
	
	
		
		creaGraficoClassificazione(renderRequest, renderResponse, model, pattern);
		return "homepage-view";
		
		
	}
	
	@ActionMapping(params="action=cambiaAggregazione")
	public void actionCambiaAggregazione(	ActionRequest aRequest, 
									ActionResponse aResponse, 
									Model model,
									@RequestParam(required=false, defaultValue="VOLUME", value="pattern") String pattern){
		
		aResponse.setRenderParameter("pattern", pattern);

	}
	
	
	private List<AggregataDTO> creaGraficoClassificazione(RenderRequest renderRequest, RenderResponse renderResponse, Model model,String pattern){
		// Localizzazione
		String idNatura =  (aggregataFacade.findNaturaByCod( codNaturaOpenCUP )==null)?"0":aggregataFacade.findNaturaByCod( codNaturaOpenCUP ).getId().toString();
		NavigaAggregata navigaAggregataClass = new NavigaAggregata();
		navigaAggregataClass.setIdNatura(idNatura);
		navigaAggregataClass.setPagAggregata(paginaClassificazione);
		navigaAggregataClass.setIdAreaIntervento("0");
		List<AggregataDTO> listaAggregataClassDTO = aggregataFacade.findAggregataByNatura(navigaAggregataClass);
		impostaLinkURL(renderRequest, navigaAggregataClass, listaAggregataClassDTO, navigaAggregataClass.getPagAggregata(),pattern);
		List<D3PieConverter> converterClassificazione=  getListaAggregata(pattern, listaAggregataClassDTO, navigaAggregataClass);
		
		
		NavigaAggregata navigaAggregataLoc = new NavigaAggregata();
		navigaAggregataLoc.setIdNatura(idNatura);
		navigaAggregataLoc.setPagAggregata(paginaLocalizzazione);
		navigaAggregataLoc.setIdAreaGeografica("0");
		List<AggregataDTO> listaAggregataLocDTO = aggregataFacade.findAggregataByNatura(navigaAggregataLoc);
		impostaLinkURL(renderRequest, navigaAggregataLoc, listaAggregataLocDTO, navigaAggregataLoc.getPagAggregata(),pattern);
		List<D3PieConverter> converterLocalizzazione=  getListaAggregata(pattern, listaAggregataLocDTO, navigaAggregataLoc);
		
		
		NavigaAggregata navigaAggregataSog = new NavigaAggregata();
		navigaAggregataSog.setIdNatura(idNatura);
		navigaAggregataSog.setPagAggregata(paginaSoggetto);
		navigaAggregataSog.setIdAreaSoggetto("0");
		navigaAggregataSog.setIdCategoriaSoggetto("-1");
		navigaAggregataSog.setIdSottoCategoriaSoggetto("-1");
		List<AggregataDTO> listaAggregataSogDTO = aggregataFacade.findAggregataByNatura(navigaAggregataSog);
		impostaLinkURL(renderRequest, navigaAggregataSog, listaAggregataSogDTO, navigaAggregataSog.getPagAggregata(),pattern);
		List<D3PieConverter> converterSoggetto=  getListaAggregata(pattern, listaAggregataSogDTO, navigaAggregataSog);
		
		
		model.addAttribute("pattern", pattern);
		model.addAttribute("aggregatiClassificazione", createJsonStringFromQueryResult(converterClassificazione));
		model.addAttribute("aggregatiLocalizzazione", createJsonStringFromQueryResult(converterLocalizzazione));
		model.addAttribute("aggregatiSoggetto", createJsonStringFromQueryResult(converterSoggetto));
		String tipoAggregazione ="";
		if("VOLUME".equals(pattern))
			tipoAggregazione ="progetti";
		if("COSTO".equals(pattern))
			tipoAggregazione ="costo";
		if("IMPORTO".equals(pattern))
			tipoAggregazione ="finanziamenti";
		model.addAttribute("tipoAggregazione", tipoAggregazione);
		return null;
	}
	
	private List <D3PieConverter> getListaAggregata(String pattern, List<AggregataDTO> listaAggregataDTO, NavigaAggregata navigaAggregata){
		List <D3PieConverter> converter = new ArrayList<D3PieConverter>();
		double totale = 0.0;
		for (AggregataDTO aggregataDTO: listaAggregataDTO){
			if("VOLUME".equals(pattern)){
				totale = totale + Double.valueOf( aggregataDTO.getNumeProgetti()).doubleValue();
			}else if("COSTO".equals(pattern)){
				totale = totale + aggregataDTO.getImpoCostoProgetti().doubleValue();
			}else if("IMPORTO".equals(pattern)){
				totale = totale + aggregataDTO.getImpoImportoFinanziato().doubleValue();
			}else {
				totale = totale + 0.0;
			}
		}
		
		D3PieConverter conv = null;
		double value = 0.0;
		for (AggregataDTO aggregataDTO: listaAggregataDTO){
			
			conv = new D3PieConverter();
			conv.setId(aggregataDTO.getId().toString());
			
			if( navigaAggregata.getIdAreaGeografica().equals("0") ){
				conv.setLabel(aggregataDTO.getDescAreaGeografica() );
			}else if( navigaAggregata.getIdAreaSoggetto().equals("0") ){
				if(aggregataDTO.getDescAreaSoggetto()!=null && aggregataDTO.getDescAreaSoggetto().toLowerCase().contains("centrale"))
					conv.setLabel("PAC");
				else if(aggregataDTO.getDescAreaSoggetto()!=null && aggregataDTO.getDescAreaSoggetto().toLowerCase().contains("locale"))
					conv.setLabel("PAL");
				else
					conv.setLabel(aggregataDTO.getDescAreaSoggetto() );
			}else if( navigaAggregata.getIdAreaIntervento().equals("0") ){
				conv.setLabel(aggregataDTO.getDesArea() );
			}

			if("VOLUME".equals(pattern)){
				value = Double.valueOf( aggregataDTO.getNumeProgetti() );
			}else if("COSTO".equals(pattern)){
				value = aggregataDTO.getImpoCostoProgetti();
			}else if("IMPORTO".equals(pattern)){
				value = aggregataDTO.getImpoImportoFinanziato();
			}else {
				value = 0.0;
			}
			
			int percentage = 0;
			if( value > 0 ){
				percentage = (int) round( ((value * 100) / totale), 0);
			}
			
			conv.setPercentage( String.valueOf( percentage ) );
			conv.setValue( value );
			conv.setLinkURL(aggregataDTO.getLinkURL());
			converter.add(conv);
		}
		
		return converter;
	}
	
	private String createJsonStringLocalizzazioneFromQueryResult(List<LocalizationValueConverter> formattedResult){
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
	
	private String createJsonStringClassificazioneFromQueryResult(List<AggregataDTO> formattedResult){
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
	
	private void impostaLinkURL(PortletRequest request, 
			NavigaAggregata sessionAttrNav, 
			List<AggregataDTO> listaAggregataDTO, 
			String pageTo,String pattern) {

		String portletId = (String) request.getAttribute(WebKeys.PORTLET_ID);
		if(pageTo.equals(paginaSoggetto)){
			portletId = portletSoggetto;
		}
		else if(pageTo.equals(paginaClassificazione)){
			portletId = portletClassificazione;
		}
		else if(pageTo.equals(paginaLocalizzazione)){
			portletId = portletLocalizzazione;
		}
		LiferayPortletURL renderURL = createLiferayPortletURL(request, pageTo, portletId, PortletRequest.ACTION_PHASE);
		
		String rowIdLiv1URL = "", rowIdLiv2URL = "", rowIdLiv3URL = "", rowIdLiv4URL = "";

		for(AggregataDTO tmp : listaAggregataDTO){		

			//Per ogni elemento oltre a caricare la descrizione e i valori
			//viene generato un linkURL che punta alla pagina successiva

			if(pageTo.equals(paginaSoggetto)){
				rowIdLiv1URL = String.valueOf(sessionAttrNav.getIdNatura());
				rowIdLiv2URL = tmp.getIdAreaSoggetto().toString(); 
				rowIdLiv3URL = "0";
				rowIdLiv4URL = "-1";
				tmp.setDescURL( tmp.getDescAreaSoggetto() );
			} 
			else if(pageTo.equals(paginaClassificazione)){
				rowIdLiv1URL = String.valueOf(sessionAttrNav.getIdNatura());
				rowIdLiv2URL = tmp.getIdArea().toString(); 
				rowIdLiv3URL = "0";
				rowIdLiv4URL = "-1";
				tmp.setDescURL( tmp.getDesArea() );
			} else if(pageTo.equals(paginaLocalizzazione)){
				rowIdLiv1URL = String.valueOf(sessionAttrNav.getIdNatura());
				rowIdLiv2URL = tmp.getIdAreaGeografica().toString();
				rowIdLiv3URL = "0";
				rowIdLiv4URL = "-1";
			}

			renderURL.setParameter("rowIdLiv1", rowIdLiv1URL); 
			renderURL.setParameter("rowIdLiv2", rowIdLiv2URL); 
			renderURL.setParameter("rowIdLiv3", rowIdLiv3URL); 
			renderURL.setParameter("rowIdLiv4", rowIdLiv4URL); 

			renderURL.setParameter("action", "navigazione");
			renderURL.setParameter("pattern", pattern);

			tmp.setLinkURL(renderURL.toString());
		}
	}
	
	private LiferayPortletURL createLiferayPortletURL(PortletRequest request, String toPage, String portletId, String portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		
		LiferayPortletURL renderURL = null;
		String localHost = themeDisplay.getPortalURL();		
		List<Layout> layouts = null;
		try{
			layouts = LayoutLocalServiceUtil.getLayouts(themeDisplay.getLayout().getGroupId(), false);
			for(Layout layout : layouts){

				String nodeNameRemoved = PortalUtil.getLayoutFriendlyURL(layout, themeDisplay).replace(localHost, "");

				//Viene ricercato l'URL esatto per la pagina successiva
				if(nodeNameRemoved.indexOf(toPage)>0){

					renderURL = PortletURLFactoryUtil.create(request, portletId, layout.getPlid(), portletRequest);
					renderURL.setWindowState(WindowState.NORMAL);
					renderURL.setPortletMode(PortletMode.VIEW);
					
					break;
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return renderURL;
	}
	
	private double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	protected String createJsonStringFromQueryResult(List<D3PieConverter> formattedResult){
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
}
