package it.dipe.opencup.facade;

import it.dipe.opencup.model.AnnoDecisione;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class TestFacade {
	
	private static final Log log = LogFactory.getLog(TestFacade.class);
	
	@Cacheable(value = "portletCache")
	public AnnoDecisione ricercaAnnoDecisione(int anno) {
		
		System.out.println("ricercaAnnoDecisione anno = " + anno);
		
		AnnoDecisione annoDec = new AnnoDecisione();
		annoDec.setAnnoDadeAnnoDecisione("" + anno);
		
		return annoDec;
	}
	
}
