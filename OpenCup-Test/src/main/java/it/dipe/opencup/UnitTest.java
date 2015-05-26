package it.dipe.opencup;


import java.util.List;

import it.dipe.opencup.dto.AggregataDTO;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.facade.AggregataFacade;
import it.dipe.opencup.facade.TestFacade;
import it.dipe.opencup.model.AnnoDecisione;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testContext.xml" , "classpath:applicationContext.xml" })
public class UnitTest {

	@Autowired
	TestFacade testFacade;
	
	@Autowired
	AggregataFacade aggregataFacade;
	
	@Test
	public void testEhCache() {
		
		AnnoDecisione anno = testFacade.ricercaAnnoDecisione(2014);
		System.out.println("ANNO = " + anno.getAnnoDadeAnnoDecisione());
		anno = testFacade.ricercaAnnoDecisione(2014);
		System.out.println("ANNO = " + anno.getAnnoDadeAnnoDecisione());
	}
	
	
	@Test
	public void testAggregata2() {
		String codiNaturaOpenCUP = "03";
		String idNatura = (aggregataFacade.findNaturaByCod( codiNaturaOpenCUP )==null)?"0":aggregataFacade.findNaturaByCod( codiNaturaOpenCUP ).getId().toString();
		
		NavigaAggregata navigaAggregata = new NavigaAggregata();
		navigaAggregata.setIdNatura(idNatura);
		navigaAggregata.setPagAggregata("natura");
		navigaAggregata.setIdAreaIntervento("0");
		
		System.out.print( "N1" );
		List<AggregataDTO> listaAggregataDTO = aggregataFacade.findAggregataByNatura(navigaAggregata);
		
		navigaAggregata.setIdNatura(idNatura);
		navigaAggregata.setPagAggregata("natura");
		navigaAggregata.setIdAreaIntervento("0");
		navigaAggregata.rimuoviZero();
		System.out.print( "N2" );
		List<AggregataDTO> risultati = aggregataFacade.findAggregataByNatura(navigaAggregata);
		
		navigaAggregata.setIdNatura(idNatura);
		navigaAggregata.setPagAggregata("natura");
		navigaAggregata.setIdAreaIntervento("0");
		navigaAggregata.rimuoviZero();
		navigaAggregata.setOrderProperty("statoProgetto.descStatoProgetto");
		navigaAggregata.setOrderType("asc");
		System.out.print( "N3" );
		List<AggregataDTO> risultati4Stato = aggregataFacade.findAggregataByNatura(navigaAggregata);

		navigaAggregata.setIdNatura(idNatura);
		navigaAggregata.setPagAggregata("natura");
		navigaAggregata.setIdAreaIntervento("0");
		navigaAggregata.rimuoviZero();
		navigaAggregata.setOrderProperty("annoAggregato.annoAggregato");
		navigaAggregata.setOrderType("asc");
		System.out.print( "N4" );
		List<AggregataDTO> tmpRisultati4Anno = aggregataFacade.findAggregataByNatura(navigaAggregata);

		System.out.println( "///////////////////////////////////////////////////////////////" );
		
		NavigaAggregata navigaAggregataX = new NavigaAggregata();
		navigaAggregataX.setIdNatura(idNatura);
		navigaAggregataX.setPagAggregata("natura");
		navigaAggregataX.setIdAreaIntervento("0");
		
		System.out.print( "N1X" );
		List<AggregataDTO> listaAggregataDTOX = aggregataFacade.findAggregataByNatura(navigaAggregataX);

		navigaAggregataX.setIdNatura(idNatura);
		navigaAggregataX.setPagAggregata("natura");
		navigaAggregataX.setIdAreaIntervento("0");
		navigaAggregataX.rimuoviZero();
		System.out.print( "N2X" );
		List<AggregataDTO> risultatiX = aggregataFacade.findAggregataByNatura(navigaAggregataX);
		
		navigaAggregataX.setIdNatura(idNatura);
		navigaAggregataX.setPagAggregata("natura");
		navigaAggregataX.setIdAreaIntervento("0");
		navigaAggregataX.rimuoviZero();
		navigaAggregataX.setOrderProperty("statoProgetto.descStatoProgetto");
		navigaAggregataX.setOrderType("asc");
		System.out.print( "N3X" );
		List<AggregataDTO> risultati4StatoX = aggregataFacade.findAggregataByNatura(navigaAggregataX);

		navigaAggregataX.setIdNatura(idNatura);
		navigaAggregataX.setPagAggregata("natura");
		navigaAggregataX.setIdAreaIntervento("0");
		navigaAggregataX.rimuoviZero();
		navigaAggregataX.setOrderProperty("annoAggregato.annoAggregato");
		navigaAggregataX.setOrderType("asc");
		System.out.print( "N4X" );
		List<AggregataDTO> tmpRisultati4AnnoX = aggregataFacade.findAggregataByNatura(navigaAggregataX);
		
	}
	
	
	@Test
	public void testAggregata() {
		String codiNaturaOpenCUP = "03";
		String idNatura = (aggregataFacade.findNaturaByCod( codiNaturaOpenCUP )==null)?"0":aggregataFacade.findNaturaByCod( codiNaturaOpenCUP ).getId().toString();
		
		NavigaAggregata navigaAggregata1 = new NavigaAggregata();
		navigaAggregata1.setIdNatura(idNatura);
		navigaAggregata1.setPagAggregata("natura");
		navigaAggregata1.setIdAreaIntervento("0");
		
		//System.out.println( navigaAggregata1.hashCode() );
		
		System.out.print( "N1" );
		List<AggregataDTO> listaAggregataDTO = aggregataFacade.findAggregataByNatura(navigaAggregata1);
		
		NavigaAggregata navigaAggregata2 = new NavigaAggregata();
		navigaAggregata2.setIdNatura(idNatura);
		navigaAggregata2.setPagAggregata("natura");
		navigaAggregata2.setIdAreaIntervento("0");
		navigaAggregata2.rimuoviZero();
//		System.out.println( navigaAggregata2.hashCode() );
		System.out.print( "N2" );
		List<AggregataDTO> risultati = aggregataFacade.findAggregataByNatura(navigaAggregata2);
		
		NavigaAggregata navigaAggregata3 = new NavigaAggregata();
		navigaAggregata3.setIdNatura(idNatura);
		navigaAggregata3.setPagAggregata("natura");
		navigaAggregata3.setIdAreaIntervento("0");
		navigaAggregata3.rimuoviZero();
		navigaAggregata3.setOrderProperty("statoProgetto.descStatoProgetto");
		navigaAggregata3.setOrderType("asc");
//		System.out.println( navigaAggregata3.hashCode() );
		System.out.print( "N3" );
		List<AggregataDTO> risultati4Stato = aggregataFacade.findAggregataByNatura(navigaAggregata3);

		NavigaAggregata navigaAggregata4 = new NavigaAggregata();
		navigaAggregata4.setIdNatura(idNatura);
		navigaAggregata4.setPagAggregata("natura");
		navigaAggregata4.setIdAreaIntervento("0");
		navigaAggregata4.rimuoviZero();
		navigaAggregata4.setOrderProperty("annoAggregato.annoAggregato");
		navigaAggregata4.setOrderType("asc");
//		System.out.println( navigaAggregata4.hashCode() );
		System.out.print( "N4" );
		List<AggregataDTO> tmpRisultati4Anno = aggregataFacade.findAggregataByNatura(navigaAggregata4);

		System.out.println( "///////////////////////////////////////////////////////////////" );
		
		NavigaAggregata navigaAggregata1X = new NavigaAggregata();
		navigaAggregata1X.setIdNatura(idNatura);
		navigaAggregata1X.setPagAggregata("natura");
		navigaAggregata1X.setIdAreaIntervento("0");
		
//		System.out.println( navigaAggregata1X.hashCode() );
		
		System.out.print( "N1X" );
		List<AggregataDTO> listaAggregataDTOX = aggregataFacade.findAggregataByNatura(navigaAggregata1X);
//		System.out.println(listaAggregataDTOX.equals(listaAggregataDTO));
		
		NavigaAggregata navigaAggregata2X = new NavigaAggregata();
		navigaAggregata2X.setIdNatura(idNatura);
		navigaAggregata2X.setPagAggregata("natura");
		navigaAggregata2X.setIdAreaIntervento("0");
		navigaAggregata2X.rimuoviZero();
//		System.out.println( navigaAggregata2X.hashCode() );
		System.out.print( "N2X" );
		List<AggregataDTO> risultatiX = aggregataFacade.findAggregataByNatura(navigaAggregata2X);
//		System.out.println(navigaAggregata2X.equals(navigaAggregata2));
		
		NavigaAggregata navigaAggregata3X = new NavigaAggregata();
		navigaAggregata3X.setIdNatura(idNatura);
		navigaAggregata3X.setPagAggregata("natura");
		navigaAggregata3X.setIdAreaIntervento("0");
		navigaAggregata3X.rimuoviZero();
		navigaAggregata3X.setOrderProperty("statoProgetto.descStatoProgetto");
		navigaAggregata3X.setOrderType("asc");
//		System.out.println( navigaAggregata3X.hashCode() );
		System.out.print( "N3X" );
		List<AggregataDTO> risultati4StatoX = aggregataFacade.findAggregataByNatura(navigaAggregata3X);
//		System.out.println(navigaAggregata3X.equals(navigaAggregata3));
		
		NavigaAggregata navigaAggregata4X = new NavigaAggregata();
		navigaAggregata4X.setIdNatura(idNatura);
		navigaAggregata4X.setPagAggregata("natura");
		navigaAggregata4X.setIdAreaIntervento("0");
		navigaAggregata4X.rimuoviZero();
		navigaAggregata4X.setOrderProperty("annoAggregato.annoAggregato");
		navigaAggregata4X.setOrderType("asc");
//		System.out.println( navigaAggregata4X.hashCode() );
		System.out.print( "N4X" );
		List<AggregataDTO> tmpRisultati4AnnoX = aggregataFacade.findAggregataByNatura(navigaAggregata4X);
//		System.out.println(navigaAggregata4X.equals(navigaAggregata4));
		
	}
}
