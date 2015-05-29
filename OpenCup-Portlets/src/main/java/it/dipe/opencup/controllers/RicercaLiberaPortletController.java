package it.dipe.opencup.controllers;

import it.dipe.opencup.controllers.common.FiltriCommonController;
import it.dipe.opencup.dto.RicercaLiberaDTO;
import it.dipe.opencup.facade.AggregataFacade;
import it.dipe.opencup.facade.ProgettoFacade;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLFactoryUtil;

@Controller
@RequestMapping("VIEW")
@SessionAttributes({"navigaAggregata"})
public class RicercaLiberaPortletController extends FiltriCommonController {
	
	private static Log logger = LogFactory.getLog(RicercaLiberaPortletController.class);
	
	@Value("#{config['paginazione.risultatiPerPagina']}")
	private int maxResult;
	
	
	@Value("#{config['ricerca.assetpublisher.instanceId']}")
	private String assetPublisherPortletId;
	
	@Value("#{config['pagina.elenco.progetti']}")
	private String paginaElencoProgetti;
	
	@Autowired
	private ProgettoFacade progettoFacade;
	
	@Autowired
	private AggregataFacade aggregataFacade;

	
	@RenderMapping
	public String creaWidgetRicerca(RenderRequest request, 
				RenderResponse response, Model model) throws WindowStateException, PortletModeException, PortalException, SystemException {
		
		model.addAttribute("ricerca", new RicercaLiberaDTO());
		
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY);
		String portletId = (String) request.getAttribute(WebKeys.PORTLET_ID);
		model.addAttribute("jsFolder",themeDisplay.getPathThemeJavaScript());
		
		LiferayPortletURL renderURL = null;
		String localHost = themeDisplay.getPortalURL();		
		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(themeDisplay.getLayout().getGroupId(), false);
		for (Layout layout : layouts) {

			String nodeNameRemoved = PortalUtil.getLayoutFriendlyURL(layout, themeDisplay).replace(localHost, "");

			//Viene ricercato l'URL esatto per la pagina successiva
			if (nodeNameRemoved.indexOf(paginaElencoProgetti) > 0) {

				renderURL = PortletURLFactoryUtil.create(request, portletId, layout.getPlid(), PortletRequest.ACTION_PHASE);
				renderURL.setWindowState(WindowState.NORMAL);
				renderURL.setPortletMode(PortletMode.VIEW);
				renderURL.setParameter("action", "ricerca");
				model.addAttribute("ricercaLiberaURL", renderURL.toString());
				
				renderURL.setParameter("action", "ricercaAvanzata");
				model.addAttribute("ricercaAvanzataURL", renderURL.toString());
				
				break;
			}
		}
	
		// TODO da rimuovere
		/*
		Indexer indexer = IndexerRegistryUtil.getIndexer(Progetto.class);
		try {
						
			logger.info("Indicizzazione di prova - metodo indiretto");
			
			indexer.reindex(progettoFacade.findProgettoById(17085970));
			
		} catch (SearchException e) {
			logger.error("SearchException: ", e);
		}
		*/
		
		return "ricercalibera-view";
	}	
	
	
	@ActionMapping(params="action=ricerca")
	public void effettuaRicerca(ActionRequest request, 
								ActionResponse response, 
								Model model, 
								@ModelAttribute("ricerca") RicercaLiberaDTO ricercaDTO) {
		
		logger.info("cercaPerKeyword: " + ricercaDTO.getCercaPerKeyword());
		
		// invia evento a portlet risultati
		QName eventName = new QName( "http:risultatiRicerca/events", "event.risultatiRicerca");
		ricercaDTO.setTipoRicerca("ricercaLibera");
	    response.setEvent(eventName, ricercaDTO);
	}
	
	@ActionMapping(params="action=ricercaAvanzata")
	public void effettuaRicercaAvanzata(ActionRequest request, 
								ActionResponse response, 
								Model model, 
								@ModelAttribute("ricerca") RicercaLiberaDTO ricercaDTO) {
		
		logger.info("cercaPerKeyword: " + ricercaDTO.getCercaPerKeyword());
		
		// invia evento a portlet risultati
		QName eventName = new QName( "http:risultatiRicerca/events", "event.risultatiRicerca");
		ricercaDTO.setTipoRicerca("ricercaAvanzata");
	    response.setEvent(eventName, ricercaDTO);
	}
	

}
