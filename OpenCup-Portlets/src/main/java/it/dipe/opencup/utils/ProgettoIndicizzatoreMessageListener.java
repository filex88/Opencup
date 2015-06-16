package it.dipe.opencup.utils;

import java.util.Date;
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
		
		logger.info("BATCH IN ESECUZIONE: id:" + this.toString());
		
		
		ProgettoFacade progettoFacade = (ProgettoFacade)SpringContextUtil.getApplicationContext().getBean("progettoFacade");
		BatchFacade batchFacade = (BatchFacade)SpringContextUtil.getApplicationContext().getBean("batchFacade");
		
		Indexer indexer = IndexerRegistryUtil.getIndexer(Progetto.class);
		
		
		Batch batch = batchFacade.ricercaBatchByNome(Constants.BATCH_INDICIZZAZIONE_NOME);
		String lastStato = batch.getStato();
		batch.setStato(Constants.BATCH_STATUS_ESECUZIONE);
		
		logger.info("BATCH conteggio totale progetti...");
		
		int currentPage = 0;
		int pageSize = 500;
		int total = progettoFacade.countProgettiIndicizzazione() / pageSize;
		batch.setTotale(total);
		batchFacade.aggiornaBatch(batch);
		
		logger.info("BATCH totale progetti da indicizzare: " + total * pageSize);
		
		boolean stop = false;
		while (!stop) {
			List<Progetto> progetti = progettoFacade.findProgettiIndicizzazione(currentPage * pageSize, pageSize);
		
			logger.info("BATCH progetti da: " + currentPage * pageSize);
			
			if (progetti != null && progetti.size() > 0) {
				
				batch = batchFacade.ricercaBatchByNome(Constants.BATCH_INDICIZZAZIONE_NOME);
				batch.setStep(currentPage);
				batchFacade.aggiornaBatch(batch);
				
				currentPage ++;
				
				for (Progetto progetto : progetti) {
					try {
						logger.debug("BATCH indicizzazione progetto id : " + progetto.getId());
						
						indexer.reindex(progetto);
					} catch (SearchException e) {
						logger.error("Errore nella indicizzazione del progetto " + progetto.getId() + ": " + e.getMessage());
					}
				}
			} else {
				stop = true;
			}
		}
		
		batch = batchFacade.ricercaBatchByNome(Constants.BATCH_INDICIZZAZIONE_NOME);
		batch.setStato(lastStato);
		batch.setDataUltimaEsecuzione(new Date());
		batchFacade.aggiornaBatch(batch);
		
		
		logger.info("...FINE BATCH");
	}
}
