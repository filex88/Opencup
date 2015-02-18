package it.dipe.opencup.controllers.common;

import java.util.List;

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

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

}
