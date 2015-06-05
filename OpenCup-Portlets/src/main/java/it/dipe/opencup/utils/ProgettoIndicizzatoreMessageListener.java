package it.dipe.opencup.utils;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.dipe.opencup.facade.BatchFacade;
import it.dipe.opencup.facade.ProgettoFacade;
import it.dipe.opencup.model.Batch;
import it.dipe.opencup.model.Progetto;

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;

public class ProgettoIndicizzatoreMessageListener implements MessageListener {

	private static Log logger = LogFactory.getLog(ProgettoIndicizzatoreMessageListener.class);	
	
	@Override
	public void receive(Message message) throws MessageListenerException {
		
		ProgettoFacade progettoFacade = (ProgettoFacade)SpringContextUtil.getApplicationContext().getBean("progettoFacade");
		BatchFacade batchFacade = (BatchFacade)SpringContextUtil.getApplicationContext().getBean("batchFacade");
		
		Indexer indexer = IndexerRegistryUtil.getIndexer(Progetto.class);
		
		System.out.println("BATCH IN ESECUZIONE: id:" + this.toString());
		
		Batch batch = batchFacade.ricercaBatchByNome(Constants.BATCH_INDICIZZAZIONE_NOME);
		batch.setStato(Constants.BATCH_STATUS_ESECUZIONE);
		
		int currentPage = 0;
		int pageSize = 30;
		int total = progettoFacade.countProgettiIndicizzazione() / pageSize;
		batch.setTotale(total);
		
		boolean stop = false;
		while (!stop) {
			List<Progetto> progetti = progettoFacade.findProgettiIndicizzazione(currentPage * pageSize, pageSize);
			if (progetti != null && progetti.size() > 0) {
				batch.setStep(currentPage);
				currentPage ++;
				
				batchFacade.aggiornaBatch(batch);
				
				for (Progetto progetto : progetti) {
					try {
						indexer.reindex(progetto);
					} catch (SearchException e) {
						logger.error("Errore nella indicizzazione del progetto " + progetto.getId() + ": " + e.getMessage());
					}
				}
			} else {
				stop = true;
			}
		}
				
		batch.setStato(Constants.BATCH_STATUS_SCHEDULATO);
		batchFacade.aggiornaBatch(batch);
		
		
		System.out.println("...FINE BATCH");
	}
}
