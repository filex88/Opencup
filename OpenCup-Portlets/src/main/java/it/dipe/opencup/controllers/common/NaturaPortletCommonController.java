package it.dipe.opencup.controllers.common;

import it.dipe.opencup.dto.AggregataDTO;
import it.dipe.opencup.dto.NavigaClassificazioneEvent;
import it.dipe.opencup.facade.AggregataFacade;

import java.util.List;

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import org.springframework.beans.factory.annotation.Autowired;
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

public class NaturaPortletCommonController {

	@Value("#{config['paginazione.risultatiPerPagina']}")
	protected int maxResult;
	
	@Autowired
	protected AggregataFacade aggregataFacade;
	
	//Array che contiene i nomi delle pagine per la navigazione nella Classificazione
	protected final String[] pageLiv = {"/natliv2", "/natliv3", "/natliv4", "/natliv1"};
	
	//Array per la personalizzazione della voce di navigazione
	protected final String[] navigaPer = {"Settore", "Sottosettore", "Categoria Intervento", "Natura"};
	
	//Metodo per la creazione del session attribute
	protected NavigaClassificazioneEvent sessionAttr() {
		NavigaClassificazioneEvent sessionAttr = new NavigaClassificazioneEvent();
		sessionAttr.setRowIdLiv1("0");
		sessionAttr.setRowIdLiv2("-1");
		sessionAttr.setRowIdLiv3("-1");
		sessionAttr.setRowIdLiv4("-1");
		return sessionAttr;
	}
	
	protected int calcolaIndicePagina(NavigaClassificazioneEvent sessionAttrNav) {
		int index;
		if("0".equals(sessionAttrNav.getRowIdLiv2())){
			index = 0;
		}else if("0".equals(sessionAttrNav.getRowIdLiv3())){
			index = 1;
		}else if("0".equals(sessionAttrNav.getRowIdLiv4())){
			index = 2;
		}else{
			index = 3;
		}
		return index;
	}
	
	protected void impostaLinkURL(PortletRequest request, NavigaClassificazioneEvent sessionAttrNav, int index, List<AggregataDTO> listaAggregataDTO, String anchorPortlet) {
		
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
	
	
}
