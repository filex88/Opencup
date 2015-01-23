package it.dipe.opencup;


import it.dipe.opencup.facade.TestFacade;
import it.dipe.opencup.model.AnnoDecisione;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testContext.xml" , "classpath:applicationContext.xml" })
public class UnitTest {

	@Autowired
	TestFacade testFacade;
	
	
	@Test
	public void testEhCache() {
		
		AnnoDecisione anno = testFacade.ricercaAnnoDecisione(2014);
		System.out.println("ANNO = " + anno.getAnnoDadeAnnoDecisione());
		anno = testFacade.ricercaAnnoDecisione(2014);
		System.out.println("ANNO = " + anno.getAnnoDadeAnnoDecisione());
	}
}
