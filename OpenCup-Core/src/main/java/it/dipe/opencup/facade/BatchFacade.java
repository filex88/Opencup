package it.dipe.opencup.facade;

import java.util.List;

import it.dipe.opencup.dao.BatchDAO;
import it.dipe.opencup.model.Batch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("batchFacade")
public class BatchFacade {
	
	@Autowired
	private BatchDAO batchDAO;
	
	private static final Log log = LogFactory.getLog(BatchFacade.class);
	
	public Batch ricercaBatchById(Integer id) {
		return batchDAO.findById(id);
	}
	
	public Batch ricercaBatchByNome(String nome) {
		List<Batch> results = batchDAO.findByProperty("nome", nome);
		if (results != null && results.size() > 0) {
			return results.get(0);
		} else {
			return null;
		}
	}
	
	
	public Integer inserisciBatch(Batch batch) {
		return (Integer) batchDAO.save(batch);
	}
	
}
