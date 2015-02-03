package it.dipe.opencup.controllers;

import it.dipe.opencup.dto.AggregataDTO;
import it.dipe.opencup.dto.NavigaClassificazioneEvent;
import it.dipe.opencup.facade.AggregataFacade;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;
import javax.xml.namespace.QName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
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
@SessionAttributes("sessionAttrNav")
public class NaturaPortlet2Controller {

	@Value("#{config['paginazione.risultatiPerPagina']}")
	protected int maxResult;
	
	@Autowired
	private AggregataFacade aggregataFacade;
	
	@ModelAttribute("sessionAttrNav")
	public NavigaClassificazioneEvent sessionAttrNav() {
		NavigaClassificazioneEvent sessionAttrNav = new NavigaClassificazioneEvent();
		sessionAttrNav.setRowIdLiv1("0");
		sessionAttrNav.setRowIdLiv2("-1");
		sessionAttrNav.setRowIdLiv3("-1");
		sessionAttrNav.setRowIdLiv4("-1");
		return sessionAttrNav;
	}
	
	@RenderMapping
	public String handleRenderRequest(RenderRequest request, RenderResponse response, Model model, @ModelAttribute("sessionAttrNav") NavigaClassificazioneEvent sessionAttrNav){
		
		String pageLiv = "";
		String navigaPer = "";
		
		/*
		 * Tramite gli elementi RowIdLiv si determina l apagina da caricare, questi elementi possono assumere 3 tipi di valore:
		 * -1 : cerco il dato aggregato per il livello
		 * 0 : cerco tutti i valori per quel livello
		 * > 0 : cerco il dato per l'id indicato
		 * */
		
		if("0".equals(sessionAttrNav.getRowIdLiv2())){
			pageLiv = "/natliv2";
			navigaPer = "Settore";
		}else if("0".equals(sessionAttrNav.getRowIdLiv3())){
			pageLiv = "/natliv3";
			navigaPer = "Sottosettore";
		}else if("0".equals(sessionAttrNav.getRowIdLiv4())){
			pageLiv = "/natliv4";
			navigaPer = "Categoria Intervento";
		}else{
			pageLiv = "/natliv1";
			navigaPer = "Natura";
		}
		
		//orderByCol is the column name passed in the request while sorting
		String orderByCol = ParamUtil.getString(request, "orderByCol"); 
		if(Validator.isNull(orderByCol)  || Validator.equals("", orderByCol)){
			orderByCol = "numeProgetti";
		}
		
		//orderByType is passed in the request while sorting. It can be either asc or desc
		String orderByType = ParamUtil.getString(request, "orderByType");
		if(Validator.isNull(orderByType)  || Validator.equals("", orderByType)){
		    orderByType = "asc";
		}

		SearchContainer<AggregataDTO> searchContainer = new SearchContainer<AggregataDTO>(request, response.createRenderURL(), null, "There are no nature yet to display.");
		searchContainer.setDelta(maxResult);
		searchContainer.setTotal(aggregataFacade.countAggregataByNatura(Integer.valueOf(sessionAttrNav.getRowIdLiv1()), 
																		Integer.valueOf(sessionAttrNav.getRowIdLiv2()),
																		Integer.valueOf(sessionAttrNav.getRowIdLiv3()),
																		Integer.valueOf(sessionAttrNav.getRowIdLiv4())) );
		
		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByType(orderByType);

		List<AggregataDTO> listaAggregataDTO = aggregataFacade.findAggregataByNatura(Integer.valueOf(sessionAttrNav.getRowIdLiv1()), 
																					 Integer.valueOf(sessionAttrNav.getRowIdLiv2()),
																					 Integer.valueOf(sessionAttrNav.getRowIdLiv3()),
																					 Integer.valueOf(sessionAttrNav.getRowIdLiv4()), 
																					 searchContainer.getCur(), 
																					 searchContainer.getOrderByCol(), 
																					 searchContainer.getOrderByType() );
		
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
				if(nodeNameRemoved.indexOf(pageLiv)>0){
					
					renderURL = PortletURLFactoryUtil.create(request, portletId, layout.getPlid(), PortletRequest.ACTION_PHASE);
					renderURL.setWindowState(WindowState.NORMAL);
					renderURL.setPortletMode(PortletMode.VIEW);

					for(AggregataDTO tmp : listaAggregataDTO){		
						
						//Per ogni elemento oltre a caricare la descrizione e i valori
						//viene generato un linkURL che punta alla pagina successiva
						
						rowIdLiv1URL = sessionAttrNav.getRowIdLiv1();
						rowIdLiv2URL = sessionAttrNav.getRowIdLiv2();
						rowIdLiv3URL = sessionAttrNav.getRowIdLiv3();
						rowIdLiv4URL = sessionAttrNav.getRowIdLiv4();
						
						if( "0".equals(sessionAttrNav.getRowIdLiv4()) ){
							rowIdLiv4URL = tmp.getIdCategoriaIntervento().toString(); 
							tmp.setDescURL( tmp.getDesCategoriaIntervento() );
						}else if( "0".equals(sessionAttrNav.getRowIdLiv3()) ){
							rowIdLiv3URL = tmp.getIdSottoSettore().toString(); 
							rowIdLiv4URL = "0";
							tmp.setDescURL( tmp.getDesSottoSettore() );
						}else if( "0".equals(sessionAttrNav.getRowIdLiv2()) ){
							rowIdLiv2URL = tmp.getIdSettore().toString(); 
							rowIdLiv3URL = "0";
							tmp.setDescURL( tmp.getDesSettore() );
						}else if( "0".equals(sessionAttrNav.getRowIdLiv1()) ){
							rowIdLiv1URL = tmp.getIdNatura().toString(); 
							rowIdLiv2URL = "0";
							tmp.setDescURL( tmp.getDesNatura() );
						}
						
						renderURL.setParameter("rowIdLiv1", rowIdLiv1URL); 
						renderURL.setParameter("rowIdLiv2", rowIdLiv2URL); 
						renderURL.setParameter("rowIdLiv3", rowIdLiv3URL); 
						renderURL.setParameter("rowIdLiv4", rowIdLiv4URL); 
						
						renderURL.setParameter("action", "PublishEvent");
						
						tmp.setLinkURL(renderURL.toString()+"#natura-portlet2");
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
		searchContainer.setResults(listaAggregataDTO);
		model.addAttribute("searchContainer", searchContainer);
		model.addAttribute("navigaPer", navigaPer);

		return "natura2-view";
	}
	
	@ActionMapping(params="action=PublishEvent")
	public void publishEvent(ActionRequest aRequest, ActionResponse aResponse, @ModelAttribute("sessionAttrNav") NavigaClassificazioneEvent sessionAttrNav){
		
		QName eventName = new QName( "http:eventNavigaNatura/events", "event.navigaNatura");
		
		//Leggo la query String per determinare il link di navigazione
		sessionAttrNav = new NavigaClassificazioneEvent();
		sessionAttrNav.setRowIdLiv1(ParamUtil.getString(aRequest, "rowIdLiv1"));
		sessionAttrNav.setRowIdLiv2(ParamUtil.getString(aRequest, "rowIdLiv2"));
		sessionAttrNav.setRowIdLiv3(ParamUtil.getString(aRequest, "rowIdLiv3"));
		sessionAttrNav.setRowIdLiv4(ParamUtil.getString(aRequest, "rowIdLiv4"));
		
		//Setto l'evento con i parametri letti dalla Query string 
		aResponse.setEvent( eventName, sessionAttrNav );
		
	}

}
