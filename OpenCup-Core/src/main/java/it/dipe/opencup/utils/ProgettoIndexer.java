package it.dipe.opencup.utils;

import it.dipe.opencup.model.Progetto;

import java.util.Locale;

import javax.portlet.PortletURL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;

public class ProgettoIndexer extends BaseIndexer {
	
	private static Log logger = LogFactory.getLog(ProgettoIndexer.class);

    public static final String[] CLASS_NAMES = {
        Progetto.class.getName()
    };
	
	@Override
    public String[] getClassNames() {

        return CLASS_NAMES;
    }

	@Override
	public String getPortletId() {

		return Constants.RICERCALIBERA_PORTLET_ID;
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		// TODO Auto-generated method stub
		
		logger.debug("DO DELETE ##################");
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		
		logger.debug("DO GET DOCUMENT ##################");
		
		Progetto progetto = (Progetto)obj;
		
		Document document = new DocumentImpl();
		
		// impostazione uuid
		document.addUID(Constants.RICERCALIBERA_PORTLET_ID, progetto.getId());
	
		// impostazione 
		
		document.addDate(Field.MODIFIED_DATE, progetto.getAnagraficaCup().getDataUltimaModUtente());
		document.addKeyword(Field.PORTLET_ID, Constants.RICERCALIBERA_PORTLET_ID);
		document.addKeyword(Field.COMPANY_ID, PortalUtil.getDefaultCompanyId());
		document.addKeyword(Field.GROUP_ID, Constants.RICERCALIBERA_GROUP_ID);
		document.addKeyword(Field.ENTRY_CLASS_NAME, Progetto.class.getName());
		document.addKeyword(Field.ENTRY_CLASS_PK, progetto.getId());
		document.addText(Field.TITLE, progetto.getAnagraficaCup().getDescCup());
		document.addText(Field.CONTENT, progetto.getAnagraficaCup().getDescCup());
		//document.addText(Field.DESCRIPTION, "PROGETTO DESCRIZIONE"); // progetto.getAnagraficaCup().getDescCup());
		document.addText(Constants.RICERCALIBERA_FIELD_LOCALIZZAZIONE, progetto.getComuniProgetto() + " " + progetto.getRegioneProgetto() + " " + progetto.getProvinceProgetto());
		document.addText(Constants.RICERCALIBERA_FIELD_SEARCH, progetto.getAreaIntervento().getDescAreaIntervento() + " " + progetto.getCategoriaIntervento().getDescCategoriaIntervento() + " " +
				progetto.getNatura().getDescNatura() + " " + progetto.getSettoreIntervento().getDescSettoreIntervento() + " " +
				progetto.getSottosettoreIntervento() + " " + progetto.getAreaIntervento().getDescAreaIntervento() + " " +
				progetto.getAnnoDecisione().getAnnoDadeAnnoDecisione());
		document.addText(Constants.RICERCALIBERA_FIELD_CODICE_CUP, progetto.getAnagraficaCup().getCodiCup());
		
		
		document.addText(Constants.RICERCALIBERA_FIELD_CATEGORIA, progetto.getCategoriaIntervento().getDescCategoriaIntervento());
		document.addText(Constants.RICERCALIBERA_FIELD_COMUNE, progetto.getComuniProgetto());
		document.addText(Constants.RICERCALIBERA_FIELD_ANNO_DECISIONE, progetto.getAnnoAnnoDecisione());
		
		document.addNumber(Constants.RICERCALIBERA_FIELD_COSTO, progetto.getImpoCostoProgetto());
		document.addNumber(Constants.RICERCALIBERA_FIELD_IMPORTO, progetto.getImpoImportoFinanziato());

		logger.debug("indicizzo questo testo: " + progetto.getAnagraficaCup().getDescCup() + " codice: " + progetto.getAnagraficaCup().getCodiCup());
		
		return document;
	}

	@Override
	protected Summary doGetSummary(Document document, Locale locale,
			String snippet, PortletURL portletURL) throws Exception {

//		logger.debug("DO GET SUMMARY ##################");
		
		String title = document.get(Field.TITLE);
		String content = snippet;
		if (Validator.isNull(snippet)) {
			content = document.get(Field.DESCRIPTION);
			if (Validator.isNull(content)) {
				content = StringUtil.shorten(document.get(Field.CONTENT), 200);
			}
		}

		
		String resourcePrimKey = document.get(Field.ENTRY_CLASS_PK);
		portletURL.setParameter("show", "summary");
		portletURL.setParameter("resourcePrimKey", resourcePrimKey);
		return new Summary(title, content, portletURL);
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		
		logger.debug("DO RE-INDEX SUMMARY ##################");
		
		Progetto progetto = (Progetto)obj;
		Document document = doGetDocument(progetto);
		SearchEngineUtil.updateDocument(SearchEngineUtil.SYSTEM_ENGINE_ID, PortalUtil.getDefaultCompanyId(), document );
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String getPortletId(SearchContext searchContext) {
		
		return Constants.RICERCALIBERA_PORTLET_ID;
	}

}
