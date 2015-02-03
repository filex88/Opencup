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
@SessionAttributes("navigaclassificazione2Controller")
public class NaturaPortlet2Controller {

	@Value("#{config['paginazione.risultatiPerPagina']}")
	protected int maxResult;
	
	@Autowired
	private AggregataFacade aggregataFacade;
	
	@ModelAttribute("navigaclassificazione2Controller")
	public NavigaClassificazioneEvent navigaClassificazioneEvent() {
		NavigaClassificazioneEvent retval = new NavigaClassificazioneEvent();
		retval.setRowIdLiv1("0");
		retval.setRowIdLiv2("-1");
		retval.setRowIdLiv3("-1");
		retval.setRowIdLiv4("-1");
		return retval;
	}
	
	@RenderMapping
	public String handleRenderRequest(RenderRequest request, RenderResponse response, Model model, @ModelAttribute("navigaclassificazione2Controller") NavigaClassificazioneEvent navigaclassificazione2Controller){
		
//		HttpServletRequest convertReq = PortalUtil.getHttpServletRequest(request);
//		HttpServletRequest originalReq = PortalUtil.getOriginalServletRequest(convertReq);       
//		String rowId = originalReq.getParameter("rowIdLiv1");
		
//		String rowIdLiv1 = ParamUtil.getString(request, "rowIdLiv1"); 
//		String rowIdLiv2 = ParamUtil.getString(request, "rowIdLiv2"); 
//		String rowIdLiv3 = ParamUtil.getString(request, "rowIdLiv3"); 
//		String rowIdLiv4 = ParamUtil.getString(request, "rowIdLiv4"); 
//		if(Validator.isNull(navigaNaturaEvent.getRowIdLiv1()) || Validator.equals("", navigaNaturaEvent.getRowIdLiv1()) || ("0".equals(navigaNaturaEvent.getRowIdLiv1())) ){
//			pageLiv = "/natliv1";
//			navigaNaturaEvent.setRowIdLiv1("0");
//			navigaNaturaEvent.setRowIdLiv2("-1");
//			navigaNaturaEvent.setRowIdLiv3("-1");
//			navigaNaturaEvent.setRowIdLiv4("-1");
//			navigaPer = "Natura";
//		}else 
		
		String pageLiv = "";
		String navigaPer = "";
		
		if("0".equals(navigaclassificazione2Controller.getRowIdLiv2())){
			pageLiv = "/natliv2";
			navigaPer = "Settore";
		}else if("0".equals(navigaclassificazione2Controller.getRowIdLiv3())){
			pageLiv = "/natliv3";
			navigaPer = "Sottosettore";
		}else if("0".equals(navigaclassificazione2Controller.getRowIdLiv4())){
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
		searchContainer.setTotal(aggregataFacade.countAggregataByNatura(Integer.valueOf(navigaclassificazione2Controller.getRowIdLiv1()), 
																		Integer.valueOf(navigaclassificazione2Controller.getRowIdLiv2()),
																		Integer.valueOf(navigaclassificazione2Controller.getRowIdLiv3()),
																		Integer.valueOf(navigaclassificazione2Controller.getRowIdLiv4())) );
		
		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByType(orderByType);

		List<AggregataDTO> listaAggregataDTO = aggregataFacade.findAggregataByNatura(Integer.valueOf(navigaclassificazione2Controller.getRowIdLiv1()), 
																					 Integer.valueOf(navigaclassificazione2Controller.getRowIdLiv2()),
																					 Integer.valueOf(navigaclassificazione2Controller.getRowIdLiv3()),
																					 Integer.valueOf(navigaclassificazione2Controller.getRowIdLiv4()), 
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
				
				if(nodeNameRemoved.indexOf(pageLiv)>0){
					
					renderURL = PortletURLFactoryUtil.create(request, portletId, layout.getPlid(), PortletRequest.ACTION_PHASE);
					renderURL.setWindowState(WindowState.NORMAL);
					renderURL.setPortletMode(PortletMode.VIEW);

					for(AggregataDTO tmp : listaAggregataDTO){		
						
						rowIdLiv1URL = navigaclassificazione2Controller.getRowIdLiv1();
						rowIdLiv2URL = navigaclassificazione2Controller.getRowIdLiv2();
						rowIdLiv3URL = navigaclassificazione2Controller.getRowIdLiv3();
						rowIdLiv4URL = navigaclassificazione2Controller.getRowIdLiv4();
						
						if( "0".equals(navigaclassificazione2Controller.getRowIdLiv4()) ){
							rowIdLiv4URL = tmp.getIdCategoriaIntervento().toString(); 
							tmp.setDescURL( tmp.getDesCategoriaIntervento() );
						}else if( "0".equals(navigaclassificazione2Controller.getRowIdLiv3()) ){
							rowIdLiv3URL = tmp.getIdSottoSettore().toString(); 
							rowIdLiv4URL = "0";
							tmp.setDescURL( tmp.getDesSottoSettore() );
						}else if( "0".equals(navigaclassificazione2Controller.getRowIdLiv2()) ){
							rowIdLiv2URL = tmp.getIdSettore().toString(); 
							rowIdLiv3URL = "0";
							tmp.setDescURL( tmp.getDesSettore() );
						}else if( "0".equals(navigaclassificazione2Controller.getRowIdLiv1()) ){
							rowIdLiv1URL = tmp.getIdNatura().toString(); 
							rowIdLiv2URL = "0";
							tmp.setDescURL( tmp.getDesNatura() );
						}
						
						renderURL.setParameter("rowIdLiv1", rowIdLiv1URL); 
						renderURL.setParameter("rowIdLiv2", rowIdLiv2URL); 
						renderURL.setParameter("rowIdLiv3", rowIdLiv3URL); 
						renderURL.setParameter("rowIdLiv4", rowIdLiv4URL); 
						
						renderURL.setParameter("action", "PublishEvent");
						
						tmp.setLinkURL(renderURL.toString());
					}
				}
			}
		
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WindowStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //to set windowsState
		catch (PortletModeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//to set portletmode
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		searchContainer.setResults(listaAggregataDTO);
		model.addAttribute("searchContainer", searchContainer);
		model.addAttribute("navigaPer", navigaPer);

//		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
//		System.out.println(themeDisplay.getLayout().getFriendlyURL());
//		
//		try {
//			
//			String localHost = themeDisplay.getPortalURL();			
//			List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(themeDisplay.getLayout().getGroupId(), false);
//
//			for(Layout layout : layouts){
//		
//					String nodeNameRemoved = PortalUtil.getLayoutFriendlyURL(layout, themeDisplay).replace(localHost, "");
//					int lastSlashIndex = nodeNameRemoved.lastIndexOf("/");
//					String siteContextBase = nodeNameRemoved.substring(0,lastSlashIndex);
//					
//					System.out.println("////////////////////////////////////////");
//					System.out.println(nodeNameRemoved);
//					System.out.println(siteContextBase);
//					
//					layout.getPlid()
//					
//					
//			}
//		
//		} catch (SystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (PortalException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		return "natura2-view";
	}
	
	@ActionMapping(params="action=PublishEvent")
	public void publishName(ActionRequest aRequest, ActionResponse aResponse, @ModelAttribute("navigaclassificazione2Controller") NavigaClassificazioneEvent navigaclassificazione2Controller){
		
		QName eventName = new QName( "http:eventNavigaNatura/events", "event.navigaNatura");
		
//		aResponse.setEvent(eventName, name);
		navigaclassificazione2Controller = new NavigaClassificazioneEvent();
		navigaclassificazione2Controller.setRowIdLiv1(ParamUtil.getString(aRequest, "rowIdLiv1"));
		navigaclassificazione2Controller.setRowIdLiv2(ParamUtil.getString(aRequest, "rowIdLiv2"));
		navigaclassificazione2Controller.setRowIdLiv3(ParamUtil.getString(aRequest, "rowIdLiv3"));
		navigaclassificazione2Controller.setRowIdLiv4(ParamUtil.getString(aRequest, "rowIdLiv4"));
		
		aResponse.setEvent( eventName, navigaclassificazione2Controller );
		
	}

}
