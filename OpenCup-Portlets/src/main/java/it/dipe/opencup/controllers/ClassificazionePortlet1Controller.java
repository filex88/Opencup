package it.dipe.opencup.controllers;

import it.dipe.opencup.dto.AggregataDTO;
import it.dipe.opencup.dto.D3PieConverter;
import it.dipe.opencup.dto.DescrizioneValore;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.facade.AggregataFacade;
import it.dipe.opencup.facade.ProgettiFacade;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLFactoryUtil;

@Controller
@RequestMapping("VIEW")
@SessionAttributes("navigaAggregata")
public class ClassificazionePortlet1Controller {
	
	@Value("#{config['paginazione.risultatiPerPagina']}")
	private int maxResult;
	
	@Value("#{config['codice.natura.open.cup']}")
	private String codiNaturaOpenCUP;
	
	@Autowired
	private ProgettiFacade progettiFacade;
	
	@Autowired
	private AggregataFacade aggregataFacade;
	
	//Array che contiene i nomi delle pagine per la navigazione nella Classificazione
	private final String[] pageLiv = {"/natura", "/natura", "/natelencoprogetti"};
	
	//Array per la personalizzazione della voce di navigazione
	private final String[] navigaPer = {"Area Intervento", "Sottosettore", "Categoria Intervento"};
	
	@ModelAttribute("navigaAggregata")
	public NavigaAggregata navigaAggregata() {
		
		String idNatura =  (aggregataFacade.findNaturaByCod( codiNaturaOpenCUP )==null)?"0":aggregataFacade.findNaturaByCod( codiNaturaOpenCUP ).getId().toString();
		NavigaAggregata navigaAggregata = new NavigaAggregata(NavigaAggregata.NAVIGA_CLASSIFICAZIONE, idNatura);
		
		return navigaAggregata;
	}
	
	@ActionMapping(params="action=ricerca")
	public void actionRicerca(	ActionRequest aRequest, 
								ActionResponse aResponse, 
								Model model, 
								@ModelAttribute("navigaAggregata") NavigaAggregata navigaAggregata){
		
		System.out.println("AR1: " + navigaAggregata.toString());
		model.addAttribute("navigaAggregata", navigaAggregata);
	}
	
	@ActionMapping(params="action=navigazione")
	public void actionNavigazione(	ActionRequest aRequest, 
									ActionResponse aResponse, 
									Model model, 
									@ModelAttribute("navigaAggregata") NavigaAggregata navigaAggregata){
		
		navigaAggregata.setIdNatura(ParamUtil.getString(aRequest, "rowIdLiv1"));
		navigaAggregata.setIdAreaIntervento(ParamUtil.getString(aRequest, "rowIdLiv2"));
		navigaAggregata.setIdSottosettoreIntervento(ParamUtil.getString(aRequest, "rowIdLiv3"));
		navigaAggregata.setIdCategoriaIntervento(ParamUtil.getString(aRequest, "rowIdLiv4"));
		
		System.out.println("AN1: " + navigaAggregata.toString());
		model.addAttribute("navigaAggregata", navigaAggregata);
	}

	@RenderMapping
	public String handleRenderRequest(	RenderRequest renderRequest, 
										RenderResponse renderResponse,
										Model model,
										@ModelAttribute("navigaAggregata") NavigaAggregata navigaAggregata){
		
		System.out.println("RM1: " + navigaAggregata.toString());
		
		int index = calcolaIndicePagina(navigaAggregata);
		
		//orderByCol is the column name passed in the request while sorting
		String orderByCol = ParamUtil.getString(renderRequest, "orderByCol"); 
		if(Validator.isNull(orderByCol)  || Validator.equals("", orderByCol)){
			orderByCol = "numeProgetti";
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
		
		SearchContainer<AggregataDTO> searchContainerDettaglio = new SearchContainer<AggregataDTO>(renderRequest, renderResponse.createRenderURL(), null, "Nessun dato trovato per la selezione fatta");
		searchContainerDettaglio.setDelta(delta);
		
		searchContainerDettaglio.setOrderByCol(orderByCol);
		searchContainerDettaglio.setOrderByType(orderByType);

		List<AggregataDTO> listaAggregataDTO = aggregataFacade.findAggregataByNatura(	navigaAggregata, 
																						searchContainerDettaglio.getOrderByCol(), 
																						searchContainerDettaglio.getOrderByType() );
		
		searchContainerDettaglio.setTotal( listaAggregataDTO.size() );
		
		
		
		List<AggregataDTO> subListaAggregataDTO = ListUtil.subList(listaAggregataDTO, searchContainerDettaglio.getStart(), searchContainerDettaglio.getEnd());       
		
		String anchorPortlet = "#natura-portlet2";
		impostaLinkURL(renderRequest, navigaAggregata, index, subListaAggregataDTO, anchorPortlet);
		
		searchContainerDettaglio.setResults(subListaAggregataDTO);
		
		model.addAttribute("searchContainerDettaglio", searchContainerDettaglio);
		model.addAttribute("navigaPer", navigaPer[index]);
		
		///////////////////////////////////////////////////////////////////////////////////////////
		
		SearchContainer<DescrizioneValore> searchContainerRiepilogo = new SearchContainer<DescrizioneValore>(renderRequest, renderResponse.createRenderURL(), null, "Nessun dato trovato per la selezione fatta");
		searchContainerRiepilogo.setDelta(maxResult);
		searchContainerRiepilogo.setTotal(3);
		
		Integer numeProgetti = 0;
		Double impoCostoProgetti = 0.0;
		Double impoImportoFinanziato = 0.0;
		
		for(AggregataDTO aggregataDTO : listaAggregataDTO){
			numeProgetti = numeProgetti + aggregataDTO.getNumeProgetti();
			impoCostoProgetti = impoCostoProgetti + aggregataDTO.getImpoCostoProgetti();
			impoImportoFinanziato = impoImportoFinanziato + aggregataDTO.getImpoImportoFinanziato();
		}
		
		List<DescrizioneValore> retval = new ArrayList<DescrizioneValore>();
		retval.add(new DescrizioneValore("VOLUME DEI PROGETTI", numeProgetti));
		retval.add(new DescrizioneValore("COSTO DEI PROGETTI", impoCostoProgetti));
		retval.add(new DescrizioneValore("IMPORTO FINANZIAMENTI", impoImportoFinanziato));
		
		searchContainerRiepilogo.setResults(retval);
		model.addAttribute("searchContainerRiepilogo", searchContainerRiepilogo);
		
		//Calcolo l'url per elenco progetti
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String portletId = (String) renderRequest.getAttribute(WebKeys.PORTLET_ID);
		
		//PortletDisplay portletDisplay= themeDisplay.getPortletDisplay();
		LiferayPortletURL renderURL = null;
		String localHost = themeDisplay.getPortalURL();		
		List<Layout> layouts = null;
		try {
			layouts = LayoutLocalServiceUtil.getLayouts(themeDisplay.getLayout().getGroupId(), false);
			for(Layout layout : layouts){
				
				String nodeNameRemoved = PortalUtil.getLayoutFriendlyURL(layout, themeDisplay).replace(localHost, "");
				
				//Viene ricercato l'URL esatto per la pagina successiva
				if(nodeNameRemoved.indexOf("natelencoprogetti")>0){
					renderURL = PortletURLFactoryUtil.create(renderRequest, portletId, layout.getPlid(), PortletRequest.ACTION_PHASE);
					renderURL.setWindowState(WindowState.NORMAL);
					renderURL.setPortletMode(PortletMode.VIEW);
					model.addAttribute("linkURLElencoProgetti", renderURL.toString());
					break;
				}
			}
		} catch (SystemException e) {
			e.printStackTrace();
		} catch (WindowStateException e) {
			e.printStackTrace();
		} catch (PortletModeException e) {
			e.printStackTrace();
		} catch (PortalException e) {
			e.printStackTrace();
		}
		
		impostaDesFiltriImpostati(navigaAggregata);
		
		return "classificazione-view";
	}
	
	@ResourceMapping(value =  "aggregati4Pie")	
	public View caricaDati4Pie(	ResourceRequest request, 
								@RequestParam("pattern") String pattern, 
								@ModelAttribute("navigaAggregata") NavigaAggregata navigaAggregata ){
		
		System.out.println("CD1: " + navigaAggregata.toString());
		
		List<AggregataDTO> listaAggregataDTO = aggregataFacade.findAggregataByNatura(navigaAggregata);
		
		int index = calcolaIndicePagina(navigaAggregata);
		
		String anchorPortlet = "#natura-portlet1";
		impostaLinkURL(request, navigaAggregata, index, listaAggregataDTO, anchorPortlet);
		
		List <D3PieConverter> converter = new ArrayList<D3PieConverter>();

		MappingJacksonJsonView view = new MappingJacksonJsonView();
		D3PieConverter conv = null;

		for (AggregataDTO aggregataDTO: listaAggregataDTO){
			conv = new D3PieConverter();
			conv.setId(aggregataDTO.getId().toString());
			
			if( navigaAggregata.getIdCategoriaIntervento().equals("0") ){
				conv.setLabel(aggregataDTO.getDesCategoriaIntervento() );
			}else if( navigaAggregata.getIdSottosettoreIntervento().equals("0") ){
				conv.setLabel(aggregataDTO.getDesSottoSettore() );
			}else if( navigaAggregata.getIdAreaIntervento().equals("0") ){
				conv.setLabel(aggregataDTO.getDesArea() );
			}else if( navigaAggregata.getIdNatura().equals("0") ){
				conv.setLabel(aggregataDTO.getDesNatura());
			}

			if("VOLUME".equals(pattern)){
				conv.setValue(Double.valueOf( aggregataDTO.getNumeProgetti() ));
			}else if("COSTO".equals(pattern)){
				conv.setValue(aggregataDTO.getImpoCostoProgetti());
			}else if("IMPORTO".equals(pattern)){
				conv.setValue(aggregataDTO.getImpoImportoFinanziato());
			}else {
				conv.setValue(0.0);
			}
			
			conv.setLinkURL(aggregataDTO.getLinkURL());
			conv.setColor(getRandomColor());
			converter.add(conv);
		}

		view.addStaticAttribute("aggregati4Pie", converter);
		return view;
	}
	
	private void impostaLinkURL(	PortletRequest request, 
									NavigaAggregata sessionAttrNav, 
									int index, 
									List<AggregataDTO> listaAggregataDTO, 
									String anchorPortlet) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		String portletId = (String) request.getAttribute(WebKeys.PORTLET_ID);

		//PortletDisplay portletDisplay= themeDisplay.getPortletDisplay();
		LiferayPortletURL renderURL = null;

		String localHost = themeDisplay.getPortalURL();		
		List<Layout> layouts = null;
		try {
			layouts = LayoutLocalServiceUtil.getLayouts(themeDisplay.getLayout().getGroupId(), false);
			String rowIdLiv1URL = "", rowIdLiv2URL = "", rowIdLiv3URL = "", rowIdLiv4URL = "";
			for(Layout layout : layouts){

				String nodeNameRemoved = PortalUtil.getLayoutFriendlyURL(layout, themeDisplay).replace(localHost, "");

				//Viene ricercato l'URL esatto per la pagina successiva
				if(nodeNameRemoved.indexOf(pageLiv[index])>0){

					renderURL = PortletURLFactoryUtil.create(request, portletId, layout.getPlid(), PortletRequest.ACTION_PHASE);
					renderURL.setWindowState(WindowState.NORMAL);
					renderURL.setPortletMode(PortletMode.VIEW);

					for(AggregataDTO tmp : listaAggregataDTO){		

						//Per ogni elemento oltre a caricare la descrizione e i valori
						//viene generato un linkURL che punta alla pagina successiva

						rowIdLiv1URL = String.valueOf(sessionAttrNav.getIdNatura());
						rowIdLiv2URL = String.valueOf(sessionAttrNav.getIdAreaIntervento());
						rowIdLiv3URL = String.valueOf(sessionAttrNav.getIdSottosettoreIntervento());
						rowIdLiv4URL = String.valueOf(sessionAttrNav.getIdCategoriaIntervento());

						if( sessionAttrNav.getIdCategoriaIntervento().equals("0") ){
							rowIdLiv4URL = tmp.getIdCategoriaIntervento().toString(); 
							tmp.setDescURL( tmp.getDesCategoriaIntervento() );
						}else if( sessionAttrNav.getIdSottosettoreIntervento().equals("0") ){
							rowIdLiv3URL = tmp.getIdSottoSettore().toString(); 
							rowIdLiv4URL = "0";
							tmp.setDescURL( tmp.getDesSottoSettore() );
						}else if( sessionAttrNav.getIdAreaIntervento().equals("0") ){
							rowIdLiv2URL = tmp.getIdArea().toString(); 
							rowIdLiv3URL = "0";
							rowIdLiv4URL = "-1";
							tmp.setDescURL( tmp.getDesArea() );
						}

						renderURL.setParameter("rowIdLiv1", rowIdLiv1URL); 
						renderURL.setParameter("rowIdLiv2", rowIdLiv2URL); 
						renderURL.setParameter("rowIdLiv3", rowIdLiv3URL); 
						renderURL.setParameter("rowIdLiv4", rowIdLiv4URL); 

						renderURL.setParameter("action", "navigazione");

						tmp.setLinkURL(renderURL.toString()+anchorPortlet);
					}
				}
			}
		} catch (SystemException e) {
			e.printStackTrace();
		} catch (WindowStateException e) {
			e.printStackTrace();
		} catch (PortletModeException e) {
			e.printStackTrace();
		} catch (PortalException e) {
			e.printStackTrace();
		}
	}
	
	private void impostaDesFiltriImpostati( NavigaAggregata filtro ) {
		
		if( Integer.valueOf( filtro.getIdNatura() ) > 0 ){
			filtro.setDescNatura( aggregataFacade.findNatura(Integer.valueOf( filtro.getIdNatura() )).getDescNatura() );
		}else{
			filtro.setDescNatura(null);
		}

		if( Integer.valueOf( filtro.getIdAreaIntervento() ) > 0 ){
			filtro.setDescAreaIntervento( aggregataFacade.findAreaIntervento(Integer.valueOf( filtro.getIdAreaIntervento() )).getDescAreaIntervento() );
		}else{
			filtro.setDescAreaIntervento(null);
		}
		
		if( Integer.valueOf( filtro.getIdSottosettoreIntervento() ) > 0 ){
			filtro.setDescSottosettoreIntervento( aggregataFacade.findSottosettoreIntervento(Integer.valueOf( filtro.getIdSottosettoreIntervento() )).getDescSottosettoreInt() );
		}else{
			filtro.setDescSottosettoreIntervento(null);
		}
		
		if( Integer.valueOf( filtro.getIdCategoriaIntervento() ) > 0 ){
			filtro.setDescCategoriaIntervento( aggregataFacade.findCategoriaIntervento(Integer.valueOf( filtro.getIdCategoriaIntervento() )).getDescCategoriaIntervento() );
		}else{
			filtro.setDescCategoriaIntervento(null);
		}
		
	}
	
	private int calcolaIndicePagina(NavigaAggregata sessionAttrNav) {
		int index;
		if( sessionAttrNav.getIdAreaIntervento().equals("0") ){
			index = 0;
		}else if( sessionAttrNav.getIdSottosettoreIntervento().equals("0") ){
			index = 1;
		}else if( sessionAttrNav.getIdCategoriaIntervento().equals("0")){
			index = 2;
		}else{
			index = 0;
		}
		return index;
	}
	
	public static String getRandomColor() {
		String[] letters = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
		String color = "#";
		for (int i = 0; i < 6; i++ ) {
			color += letters[(int) Math.round(Math.random() * 15)];
		}
		return color;
	}
	
}
