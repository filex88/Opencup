package it.dipe.opencup.controllers.common;

import it.dipe.opencup.dto.AggregataDTO;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.facade.AggregataFacade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

public class NaturaPortletCommonController {

	@Value("#{config['paginazione.risultatiPerPagina']}")
	protected int maxResult;
	
	@Autowired
	protected AggregataFacade aggregataFacade;
	
	protected static final String SESSION_FILTRI_CLASSIFICAZIONE = "SESSION_FILTRI_CLASSIFICAZIONE";
	
	//Array che contiene i nomi delle pagine per la navigazione nella Classificazione
	protected final String[] pageLiv = {"/natsettoreinterventoprogetto", "/natsottosettoreintervento", "/natelencoprogetti", "/natprogetto"};
	
	//Array per la personalizzazione della voce di navigazione
	protected final String[] navigaPer = {"Settore", "Sottosettore", "Categoria Intervento", "Natura"};
	
	//Metodo per la creazione del session attribute
	protected NavigaAggregata sessionAttr() {
		NavigaAggregata sessionAttr = new NavigaAggregata();
		sessionAttr.setIdNatura("0");
		return sessionAttr;
	}
	
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
	
	protected void initRender(	RenderRequest renderRequest, 
								String[] pNavigaClassificazione, 
								String[] pFiltriRicerca, 
								String[] pFiltriRicercAnni, 
								NavigaAggregata sessionAttrClassificazione,
								String sessionName) {
		
		HttpSession session = PortalUtil.getHttpServletRequest(renderRequest).getSession(false);
		//PortletSession session = renderRequest.getPortletSession(false);
		
		//Setto in sessione la navigazione tramite il processEvent della Classificazione
		if( pNavigaClassificazione != null && pNavigaClassificazione.length == 4 ){
			sessionAttrClassificazione.setIdNatura(pNavigaClassificazione[0]);
			sessionAttrClassificazione.setIdSettoreIntervento(pNavigaClassificazione[1]);
			sessionAttrClassificazione.setIdSottosettoreIntervento(pNavigaClassificazione[2]);
			sessionAttrClassificazione.setIdCategoriaIntervento(pNavigaClassificazione[3]);
		}
		
		
		NavigaAggregata filtri = null;
		
		//Setto in sessione i filtri impostati tramite il processEvent nella classificazione
		if( pFiltriRicerca != null && pFiltriRicerca.length == 10 ){
			filtri = new NavigaAggregata();
			filtri.setIdAnnoDecisione(pFiltriRicerca[0]);
			filtri.setIdRegione(pFiltriRicerca[1]);
			filtri.setIdProvincia(pFiltriRicerca[2]);
			filtri.setIdComune(pFiltriRicerca[3]);
			filtri.setIdAreaGeografica(pFiltriRicerca[4]);
			filtri.setDescStato(pFiltriRicerca[5]);
			filtri.setIdCategoriaSoggetto(pFiltriRicerca[6]);
			filtri.setIdSottoCategoriaSoggetto(pFiltriRicerca[7]);
			filtri.setIdTipologiaInterventi(pFiltriRicerca[8]);
			filtri.setIdStatoProgetto(pFiltriRicerca[9]);
		}
		
		//gli anni hanno la multiselezione
		if( pFiltriRicercAnni != null && pFiltriRicercAnni.length > 0 ){
			if(filtri == null){
				filtri = new NavigaAggregata();
			}
			List<String> listAnnis = new ArrayList<String>();
			Collections.addAll(listAnnis, pFiltriRicercAnni);
			filtri.setIdAnnoDecisiones(listAnnis);
		}
		
		//Se l'oggetto filtri è stato istanziato lo salvo in sessione (ed eventualmente sovrascrivo quello presente in precedenza)
		if(filtri != null){
			//session.setAttribute("sessionFiltri", sessionFiltri , PortletSession.PORTLET_SCOPE);
			session.setAttribute(sessionName, filtri);
		}
		
		//leggo dalla sessione l'oggetto filtri (potrei averlo appena caricato in sessione oppure potrebbe essere in sessione da chiamate precedenti e mantenere
		//semplicemente i filtri
		//sessionFiltri = (NavigaAggregata) session.getAttribute("sessionFiltri ", PortletSession.PORTLET_SCOPE);
		filtri = (session.getAttribute(sessionName)==null)?null:(NavigaAggregata) session.getAttribute(sessionName);
		
		//Se in sessione trovo l'oggetto copio i valori di ricerca nel session attribute che verrà usato nei controller della navigazione per classificazione
		if( filtri != null ){
			sessionAttrClassificazione.setIdAnnoDecisione(filtri.getIdAnnoDecisione());
			sessionAttrClassificazione.setIdRegione(filtri.getIdRegione());
			sessionAttrClassificazione.setIdProvincia(filtri.getIdProvincia());
			sessionAttrClassificazione.setIdComune(filtri.getIdComune());
			sessionAttrClassificazione.setIdAreaGeografica(filtri.getIdAreaGeografica());
			sessionAttrClassificazione.setDescStato(filtri.getDescStato());
			sessionAttrClassificazione.setIdCategoriaSoggetto(filtri.getIdCategoriaSoggetto());
			sessionAttrClassificazione.setIdSottoCategoriaSoggetto(filtri.getIdSottoCategoriaSoggetto());
			sessionAttrClassificazione.setIdTipologiaInterventi(filtri.getIdTipologiaInterventi());
			sessionAttrClassificazione.setIdStatoProgetto(filtri.getIdStatoProgetto());
			sessionAttrClassificazione.setIdAnnoDecisiones(filtri.getIdAnnoDecisiones());
		}else{
			//Creo un oggetto filtro
			filtri = new NavigaAggregata();
		}
		
		//Copio i valori della navigazione per classificazione nell'oggetto filtro
		filtri.setIdNatura(sessionAttrClassificazione.getIdNatura());
		filtri.setIdSettoreIntervento(sessionAttrClassificazione.getIdSettoreIntervento());
		filtri.setIdSottosettoreIntervento(sessionAttrClassificazione.getIdSottosettoreIntervento());
		filtri.setIdCategoriaIntervento(sessionAttrClassificazione.getIdCategoriaIntervento());
		
		//Salvo l'oggetto filtro in sesisone
		session.setAttribute(sessionName, filtri);
	}
	
	protected void processEventFiltroClassificazione(EventRequest eventRequest,
			EventResponse eventResponse) {
		NavigaAggregata filtro = (NavigaAggregata) eventRequest.getEvent().getValue();
		
		String[] pFiltriRicerca = {	String.valueOf(filtro.getIdAnnoDecisione()),
									String.valueOf(filtro.getIdRegione()),
									String.valueOf(filtro.getIdProvincia()),
									String.valueOf(filtro.getIdComune()),
									String.valueOf(filtro.getIdAreaGeografica()),
									String.valueOf(filtro.getDescStato()),
									String.valueOf(filtro.getIdCategoriaSoggetto()),
									String.valueOf(filtro.getIdSottoCategoriaSoggetto()),
									String.valueOf(filtro.getIdTipologiaInterventi()),
									String.valueOf(filtro.getIdStatoProgetto()) };
		
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
	
	protected void publishEvent(	ActionRequest aRequest, 
									ActionResponse aResponse,
									Model model, 
									String nomeAttr, 
									QName eventName) {
		
		//Leggo la query String per determinare il link di navigazione
		NavigaAggregata sessionAttrNaturaNav = new NavigaAggregata();
		sessionAttrNaturaNav.setIdNatura(ParamUtil.getString(aRequest, "rowIdLiv1"));
		sessionAttrNaturaNav.setIdSettoreIntervento(ParamUtil.getString(aRequest, "rowIdLiv2"));
		sessionAttrNaturaNav.setIdSottosettoreIntervento(ParamUtil.getString(aRequest, "rowIdLiv3"));
		sessionAttrNaturaNav.setIdCategoriaIntervento(ParamUtil.getString(aRequest, "rowIdLiv4"));
		
		model.addAttribute(nomeAttr, sessionAttrNaturaNav);
		
		//Setto l'evento con i parametri letti dalla Query string 
		aResponse.setEvent( eventName, sessionAttrNaturaNav );
	}
	
	
}
