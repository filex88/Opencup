package it.dipe.opencup.controllers;

import it.dipe.opencup.dto.AggregataDTO;
import it.dipe.opencup.dto.D3PieConverter;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.facade.AggregataFacade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
public class PieChartPortletController {
	
	@Autowired
	private AggregataFacade aggregataFacade;
	
	@Value("#{config['codice.natura.open.cup']}")
	private String codiNaturaOpenCUP;
	
	@ModelAttribute("navigaAggregata")
	public NavigaAggregata navigaAggregata() {
		
		String idNatura =  (aggregataFacade.findNaturaByCod( codiNaturaOpenCUP )==null)?"0":aggregataFacade.findNaturaByCod( codiNaturaOpenCUP ).getId().toString();
		NavigaAggregata navigaAggregata = new NavigaAggregata(NavigaAggregata.NAVIGA_CLASSIFICAZIONE, idNatura);
		
		return navigaAggregata;
	}
	
	@RenderMapping
	public String renderRequest(RenderRequest renderRequest, 
								RenderResponse renderResponse,
								Model model,
								@ModelAttribute("navigaAggregata") NavigaAggregata navigaAggregata,
								@RequestParam(required=false, defaultValue="VOLUME", value="pattern") String pattern){
		
		model.addAttribute("pattern", pattern);
		
		List<AggregataDTO> listaAggregataDTO = aggregataFacade.findAggregataByNatura(navigaAggregata);
		
		String anchorPortlet = "#pie-chart-portlet";
		impostaLinkURL(renderRequest, navigaAggregata, listaAggregataDTO, anchorPortlet, navigaAggregata.getPagAggregata());
		
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

		//System.out.println( "JSON: " + createJsonStringFromQueryResult(converter) );
		
		model.addAttribute("aggregati4Pie", createJsonStringFromQueryResult(converter));
		
		model.addAttribute("navigaAggregata", navigaAggregata);
		
		return "pie-chart-view";
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
	
	private void impostaLinkURL(	PortletRequest request, 
			NavigaAggregata sessionAttrNav, 
			List<AggregataDTO> listaAggregataDTO, 
			String anchorPortlet,
			String pageTo) {

		LiferayPortletURL renderURL = createLiferayPortletURL(request, pageTo);
		String rowIdLiv1URL = "", rowIdLiv2URL = "", rowIdLiv3URL = "", rowIdLiv4URL = "";

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

			tmp.setLinkURL(renderURL.toString() + anchorPortlet);
		}
	}
	
	private LiferayPortletURL createLiferayPortletURL(PortletRequest request, String toPage) {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		String portletId = (String) request.getAttribute(WebKeys.PORTLET_ID);
		
		LiferayPortletURL renderURL = null;
		String localHost = themeDisplay.getPortalURL();		
		List<Layout> layouts = null;
		try{
			layouts = LayoutLocalServiceUtil.getLayouts(themeDisplay.getLayout().getGroupId(), false);
			for(Layout layout : layouts){

				String nodeNameRemoved = PortalUtil.getLayoutFriendlyURL(layout, themeDisplay).replace(localHost, "");

				//Viene ricercato l'URL esatto per la pagina successiva
				if(nodeNameRemoved.indexOf(toPage)>0){

					renderURL = PortletURLFactoryUtil.create(request, portletId, layout.getPlid(), PortletRequest.ACTION_PHASE);
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
}
