package it.dipe.opencup.controllers.common;

import it.dipe.opencup.dto.LocalizationValueConverter;

import java.io.IOException;
import java.util.List;

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLFactoryUtil;

public class LocalizzazionePortletCommonController {
	
	@Value("#{config['paginazione.risultatiPerPagina.localizzazione']}")
	protected int maxResult;
	
	@Value("#{config['codice.natura.open.cup']}")
	protected String codNaturaOpenCUP;
	
	
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
}
