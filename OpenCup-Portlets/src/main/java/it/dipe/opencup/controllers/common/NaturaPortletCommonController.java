package it.dipe.opencup.controllers.common;

import it.dipe.opencup.dto.AggregataDTO;
import it.dipe.opencup.dto.NavigaAggregata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLFactoryUtil;

public class NaturaPortletCommonController extends PortletCommonController {
	
	//Array che contiene i nomi delle pagine per la navigazione nella Classificazione
	protected final String[] pageLiv = {"/natura", "/natura", "/natelencoprogetti", "/natura"};
	
	//Array per la personalizzazione della voce di navigazione
	protected final String[] navigaPer = {"Settore", "Sottosettore", "Categoria Intervento", "Natura"};
	
	protected int calcolaIndicePagina(NavigaAggregata sessionAttrNav) {
		int index;
		if( sessionAttrNav.getIdSettoreIntervento().equals("0") ){
			index = 0;
		}else if( sessionAttrNav.getIdSottosettoreIntervento().equals("0") ){
			index = 1;
		}else if( sessionAttrNav.getIdCategoriaIntervento().equals("0")){
			index = 2;
		}else{
			index = 3;
		}
		return index;
	}
	
	protected void impostaLinkURL(	PortletRequest request, 
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
						rowIdLiv2URL = String.valueOf(sessionAttrNav.getIdSettoreIntervento());
						rowIdLiv3URL = String.valueOf(sessionAttrNav.getIdSottosettoreIntervento());
						rowIdLiv4URL = String.valueOf(sessionAttrNav.getIdCategoriaIntervento());
						
						if( sessionAttrNav.getIdCategoriaIntervento().equals("0") ){
							rowIdLiv4URL = tmp.getIdCategoriaIntervento().toString(); 
							tmp.setDescURL( tmp.getDesCategoriaIntervento() );
						}else if( sessionAttrNav.getIdSottosettoreIntervento().equals("0") ){
							rowIdLiv3URL = tmp.getIdSottoSettore().toString(); 
							rowIdLiv4URL = "0";
							tmp.setDescURL( tmp.getDesSottoSettore() );
						}else if( sessionAttrNav.getIdSettoreIntervento().equals("0") ){
							rowIdLiv2URL = tmp.getIdSettore().toString(); 
							rowIdLiv3URL = "0";
							tmp.setDescURL( tmp.getDesSettore() );
						}else if( sessionAttrNav.getIdNatura().equals("0") ){
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
	
	protected NavigaAggregata initRender(RenderRequest renderRequest) {
		
		HttpSession session = PortalUtil.getHttpServletRequest(renderRequest).getSession(false);
		NavigaAggregata filtro = (session.getAttribute(SESSION_FILTRI_RICERCA)==null)? new NavigaAggregata(NavigaAggregata.NAVIGA_CLASSIFICAZIONE):(NavigaAggregata) session.getAttribute(SESSION_FILTRI_RICERCA);

		if( ! NavigaAggregata.NAVIGA_CLASSIFICAZIONE.equals( filtro.getNaviga() ) ){
			filtro = new NavigaAggregata(NavigaAggregata.NAVIGA_CLASSIFICAZIONE);
		}

		//Imposto il comune a -1 perchè per la classificazione non si può affinare la ricerca del comune
		filtro.setIdComune("-1");

		if( "-1".equals( filtro.getIdNatura() ) || filtro.getIdNatura() == null ){
			filtro.setIdNatura("0");
		}
		
		if( filtro.getIdSettoreIntervento() == null ){
			filtro.setIdSettoreIntervento("-1");
		}
		if( Integer.valueOf( filtro.getIdNatura() ) > 0 ){
			if( "-1".equals( filtro.getIdSettoreIntervento() ) ){
				filtro.setIdSettoreIntervento("0");
			}
		}
		
		if( filtro.getIdSottosettoreIntervento() == null ){
			filtro.setIdSottosettoreIntervento("-1");
		}
		if( Integer.valueOf( filtro.getIdSettoreIntervento() ) > 0 ){
			if( "-1".equals( filtro.getIdSottosettoreIntervento() ) ){
				filtro.setIdSottosettoreIntervento("0");
			}
		}
		
		if( filtro.getIdCategoriaIntervento() == null ){
			filtro.setIdCategoriaIntervento("-1");
		}
		if( Integer.valueOf( filtro.getIdSottosettoreIntervento() ) > 0 ){
			if( "-1".equals( filtro.getIdCategoriaIntervento() ) ){
				filtro.setIdCategoriaIntervento("0");
			}
		}
		
		//Salvo l'oggetto filtro in sesisone
		session.setAttribute(SESSION_FILTRI_RICERCA, filtro);
		
		return filtro;
	}
	
	protected void initRender(	RenderRequest renderRequest, 
								String[] pNavigaClassificazione, 
								String[] pFiltriRicerca, 
								String[] pFiltriRicercAnni, 
								NavigaAggregata sessionAttrClassificazione,
								String sessionName) {
		
		HttpSession session = PortalUtil.getHttpServletRequest(renderRequest).getSession(false);
		
		//Setto in sessione la navigazione tramite il processEvent della Classificazione
		if( pNavigaClassificazione != null && pNavigaClassificazione.length == 4 ){
			sessionAttrClassificazione.setIdNatura(pNavigaClassificazione[0]);
			sessionAttrClassificazione.setIdSettoreIntervento(pNavigaClassificazione[1]);
			sessionAttrClassificazione.setIdSottosettoreIntervento(pNavigaClassificazione[2]);
			sessionAttrClassificazione.setIdCategoriaIntervento(pNavigaClassificazione[3]);
		}
		
		NavigaAggregata filtro = null;
		
		//Setto in sessione i filtri impostati tramite il processEvent nella classificazione
		if( pFiltriRicerca != null && pFiltriRicerca.length == 13 ){
			filtro = new NavigaAggregata(NavigaAggregata.NAVIGA_CLASSIFICAZIONE);
			filtro.setIdRegione(pFiltriRicerca[0]);
			filtro.setIdProvincia(pFiltriRicerca[1]);
			filtro.setIdComune(pFiltriRicerca[2]);
			filtro.setIdAreaGeografica(pFiltriRicerca[3]);
			filtro.setDescStato(pFiltriRicerca[4]);
			filtro.setIdCategoriaSoggetto(pFiltriRicerca[5]);
			filtro.setIdSottoCategoriaSoggetto(pFiltriRicerca[6]);
			filtro.setIdTipologiaIntervento(pFiltriRicerca[7]);
			filtro.setIdStatoProgetto(pFiltriRicerca[8]);
			filtro.setIdNatura(pFiltriRicerca[9]);
			filtro.setIdSettoreIntervento(pFiltriRicerca[10]);
			filtro.setIdSottosettoreIntervento(pFiltriRicerca[11]);
			filtro.setIdCategoriaIntervento(pFiltriRicerca[12]);
		}
		
		//gli anni hanno la multiselezione
		if( pFiltriRicercAnni != null && pFiltriRicercAnni.length > 0 ){
			if(filtro == null){
				filtro = new NavigaAggregata(NavigaAggregata.NAVIGA_CLASSIFICAZIONE);
			}
			List<String> listAnnis = new ArrayList<String>();
			Collections.addAll(listAnnis, pFiltriRicercAnni);
			filtro.setIdAnnoDecisiones(listAnnis);
		}
		
		//Se l'oggetto filtri è stato istanziato lo salvo in sessione (ed eventualmente sovrascrivo quello presente in precedenza)
		if(filtro != null){
			session.setAttribute(sessionName, filtro);
		}
		
		//leggo dalla sessione l'oggetto filtri (potrei averlo appena caricato in sessione oppure potrebbe essere in sessione da chiamate precedenti e mantenere
		//semplicemente i filtri
		filtro = (session.getAttribute(sessionName)==null)?null:(NavigaAggregata) session.getAttribute(sessionName);
		
		//Se in sessione trovo l'oggetto copio i valori di ricerca nel session attribute che verrà usato nei controller della navigazione per classificazione
		if( filtro != null ){
			sessionAttrClassificazione.setIdRegione(filtro.getIdRegione());
			sessionAttrClassificazione.setIdProvincia(filtro.getIdProvincia());
			sessionAttrClassificazione.setIdComune(filtro.getIdComune());
			sessionAttrClassificazione.setIdAreaGeografica(filtro.getIdAreaGeografica());
			sessionAttrClassificazione.setDescStato(filtro.getDescStato());
			sessionAttrClassificazione.setIdCategoriaSoggetto(filtro.getIdCategoriaSoggetto());
			sessionAttrClassificazione.setIdSottoCategoriaSoggetto(filtro.getIdSottoCategoriaSoggetto());
			sessionAttrClassificazione.setIdTipologiaIntervento(filtro.getIdTipologiaIntervento());
			sessionAttrClassificazione.setIdStatoProgetto(filtro.getIdStatoProgetto());
			sessionAttrClassificazione.setIdAnnoDecisiones(filtro.getIdAnnoDecisiones());
			sessionAttrClassificazione.setIdNatura(filtro.getIdNatura());
			sessionAttrClassificazione.setIdSettoreIntervento(filtro.getIdSettoreIntervento());
			sessionAttrClassificazione.setIdSottosettoreIntervento(filtro.getIdSottosettoreIntervento());
			sessionAttrClassificazione.setIdCategoriaIntervento(filtro.getIdCategoriaIntervento());
		}else{
			//Creo un oggetto filtro
			filtro = new NavigaAggregata(NavigaAggregata.NAVIGA_CLASSIFICAZIONE);
		}
		
		//Copio i valori della navigazione per classificazione nell'oggetto filtro
		filtro.setIdNatura(sessionAttrClassificazione.getIdNatura());
		filtro.setIdSettoreIntervento(sessionAttrClassificazione.getIdSettoreIntervento());
		filtro.setIdSottosettoreIntervento(sessionAttrClassificazione.getIdSottosettoreIntervento());
		filtro.setIdCategoriaIntervento(sessionAttrClassificazione.getIdCategoriaIntervento());
		
		//Imposto il comune a -1 perchè per la classificazione non si può affinare la ricerca del comune
		filtro.setIdComune("-1");
				
		//Salvo l'oggetto filtro in sesisone
		session.setAttribute(sessionName, filtro);
	}
	
	protected void processEventFiltroClassificazione(EventRequest eventRequest,
			EventResponse eventResponse) {
		NavigaAggregata filtro = (NavigaAggregata) eventRequest.getEvent().getValue();
		
		String[] pFiltriRicerca = {	String.valueOf(filtro.getIdRegione()),
									String.valueOf(filtro.getIdProvincia()),
									String.valueOf(filtro.getIdComune()),
									String.valueOf(filtro.getIdAreaGeografica()),
									String.valueOf(filtro.getDescStato()),
									String.valueOf(filtro.getIdCategoriaSoggetto()),
									String.valueOf(filtro.getIdSottoCategoriaSoggetto()),
									String.valueOf(filtro.getIdTipologiaIntervento()),
									String.valueOf(filtro.getIdStatoProgetto()),
									String.valueOf(filtro.getIdNatura()),
									String.valueOf(filtro.getIdSettoreIntervento()),
									String.valueOf(filtro.getIdSottosettoreIntervento()),
									String.valueOf(filtro.getIdCategoriaIntervento()),
									
		};
		
		String[] pFiltriRicercAnni = filtro.getIdAnnoDecisiones().toArray(new String[0]);
		
		eventResponse.setRenderParameter("pFiltriRicerca", pFiltriRicerca);
		eventResponse.setRenderParameter("pFiltriRicercAnni", pFiltriRicercAnni);
	}
	
	protected void processEventNavigaClassificazione(EventRequest eventRequest, EventResponse eventResponse) {
		
		NavigaAggregata naviga = (NavigaAggregata) eventRequest.getEvent().getValue();
		
		String[] pNavigaClassificazione = {String.valueOf(naviga.getIdNatura()), 
										   String.valueOf(naviga.getIdSettoreIntervento()), 
										   String.valueOf(naviga.getIdSottosettoreIntervento()), 
										   String.valueOf(naviga.getIdCategoriaIntervento()) };
		
		eventResponse.setRenderParameter("pNavigaClassificazione", pNavigaClassificazione);
	}
	
	protected void publishEvent(ActionRequest aRequest) {
		
		HttpSession session = PortalUtil.getHttpServletRequest(aRequest).getSession(false);
		NavigaAggregata filtro = (session.getAttribute(SESSION_FILTRI_RICERCA)==null)?null:(NavigaAggregata) session.getAttribute(SESSION_FILTRI_RICERCA);
		
		if(filtro==null){
			filtro = new NavigaAggregata(NavigaAggregata.NAVIGA_CLASSIFICAZIONE);
		}
		
		filtro.setIdNatura(ParamUtil.getString(aRequest, "rowIdLiv1"));
		filtro.setIdSettoreIntervento(ParamUtil.getString(aRequest, "rowIdLiv2"));
		filtro.setIdSottosettoreIntervento(ParamUtil.getString(aRequest, "rowIdLiv3"));
		filtro.setIdCategoriaIntervento(ParamUtil.getString(aRequest, "rowIdLiv4"));
		
		session.setAttribute(SESSION_FILTRI_RICERCA, filtro);
		
	}
	
	protected void initInModelMascheraRicerca(Model model, NavigaAggregata filtro) {
		super.initInModelMascheraRicerca(model, filtro);
		filtro.setIdComune("-1");
	}
	
	
}
