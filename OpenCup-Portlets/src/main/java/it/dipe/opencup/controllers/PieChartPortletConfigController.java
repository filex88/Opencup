package it.dipe.opencup.controllers;

import it.dipe.opencup.dto.PortletConfigDTO;
import it.dipe.opencup.facade.AggregataFacade;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.ReadOnlyException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ValidatorException;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletURLFactoryUtil;

@Controller
@RequestMapping("EDIT")
public class PieChartPortletConfigController {
	
	private static Log logger = LogFactory.getLog(PieChartPortletConfigController.class);
	
	@Autowired
	private AggregataFacade aggregataFacade;
	
	
	@RenderMapping
	public String renderRequest(RenderRequest renderRequest, 
								RenderResponse renderResponse,
								Model model,
								PortletPreferences prefs) throws PortalException, SystemException, WindowStateException, PortletModeException {
		
		String portletResource = ParamUtil.getString(renderRequest, "portletResource");
		
		if (Validator.isNotNull(portletResource)) {
		    prefs = PortletPreferencesFactoryUtil.getPortletSetup(renderRequest, portletResource);
		}
		
		PortletConfigDTO config = new PortletConfigDTO();
		config.setSelezionabile( "S".equals( prefs.getValue(PortletConfigDTO.PROP_SELEZIONABILE, "N") ) );
		config.setMostraPulsanti( "S".equals( prefs.getValue(PortletConfigDTO.PROP_MOSTRAPULTANTI, "N") ) );
		config.setPortletPrincipale( "S".equals( prefs.getValue(PortletConfigDTO.PROP_PORTLET_PRINCIPALE, "N") ) );
		config.setPortletSecondariaDX( "S".equals( prefs.getValue(PortletConfigDTO.PROP_PORTLET_SECONDARIA_DX, "N") ) );
		model.addAttribute("config", config);
		
		
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String portletId = (String) renderRequest.getAttribute(WebKeys.PORTLET_ID);
		LiferayPortletURL actionURL = PortletURLFactoryUtil.create(renderRequest, portletId, themeDisplay.getLayout().getPlid(), PortletRequest.ACTION_PHASE);
		actionURL.setWindowState(WindowState.NORMAL);
		actionURL.setPortletMode(PortletMode.EDIT);
		actionURL.setParameter("action", "save");
				
		logger.info("RENDER6 actionURL: " + actionURL.toString());
		
		model.addAttribute("saveURL", actionURL.toString());
		
		return "pie-chart-config-view";
	}
	
	@ActionMapping(params="action=save")
	public void salva(@ModelAttribute("config") PortletConfigDTO config, PortletPreferences prefs, ActionRequest actionRequest) throws ReadOnlyException, ValidatorException, IOException {
		
		
		prefs.reset(PortletConfigDTO.PROP_MOSTRAPULTANTI);
		prefs.reset(PortletConfigDTO.PROP_SELEZIONABILE);
		prefs.reset(PortletConfigDTO.PROP_PORTLET_PRINCIPALE);
		prefs.reset(PortletConfigDTO.PROP_PORTLET_SECONDARIA_DX);
		
		prefs.setValue(PortletConfigDTO.PROP_MOSTRAPULTANTI, config.isMostraPulsanti() ?  "S" : "N");
		prefs.setValue(PortletConfigDTO.PROP_SELEZIONABILE, config.isSelezionabile() ?  "S" : "N");
		prefs.setValue(PortletConfigDTO.PROP_PORTLET_PRINCIPALE, config.isPortletPrincipale() ?  "S" : "N");
		prefs.setValue(PortletConfigDTO.PROP_PORTLET_SECONDARIA_DX, config.isPortletSecondariaDX() ?  "S" : "N");
		
		prefs.store();
		
		SessionMessages.add(actionRequest, "config-salvata");
		
	}
}
