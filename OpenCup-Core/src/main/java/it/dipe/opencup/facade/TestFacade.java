package it.dipe.opencup.facade;

import it.dipe.opencup.model.AnnoDecisione;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.googlecode.ehcache.annotations.Cacheable;

@Component
public class TestFacade {
	
	private static final Log log = LogFactory.getLog(TestFacade.class);
	
	@Cacheable(cacheName = "portletCache")
	public AnnoDecisione ricercaAnnoDecisione(int anno) {
		
		System.out.println("ricercaAnnoDecisione anno = " + anno);
		
		AnnoDecisione annoDec = new AnnoDecisione();
		annoDec.setAnnoDadeAnnoDecisione("" + anno);
		
		return annoDec;
	}
	
}
