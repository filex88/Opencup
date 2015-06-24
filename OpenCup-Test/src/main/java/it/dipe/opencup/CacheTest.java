package it.dipe.opencup;

import it.dipe.opencup.dto.AggregataDTO;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.facade.AggregataFacade;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testContext.xml" , "classpath:applicationContext.xml" })
public class CacheTest {
	
	@Autowired
	AggregataFacade aggregataFacade;
	
	@Value("#{config['codice.natura.open.cup']}")
	private String codiNaturaOpenCUP;
	
	@Value("#{config['pagina.classificazione']}")
	private String paginaClassificazione;
	
	@Value("#{config['pagina.soggetto']}")
	private String paginaSoggetto;
	
	@Value("#{config['pagina.localizzazione']}")
	private String paginaLocalizzazione;
	
	public NavigaAggregata navigaAggregataClassificazione() {
		
		String idNatura =  (aggregataFacade.findNaturaByCod( codiNaturaOpenCUP )==null)?"0":aggregataFacade.findNaturaByCod( codiNaturaOpenCUP ).getId().toString();
		NavigaAggregata navigaAggregata = new NavigaAggregata();
		navigaAggregata.setIdNatura(idNatura);
		navigaAggregata.setIdAreaIntervento("0");
		navigaAggregata.setDistribuzione("");
		navigaAggregata.setPagAggregata(paginaClassificazione);
		
		return navigaAggregata;
	}

	public NavigaAggregata navigaAggregataSoggetto() {
		
		String idNatura =  (aggregataFacade.findNaturaByCod( codiNaturaOpenCUP )==null)?"0":aggregataFacade.findNaturaByCod( codiNaturaOpenCUP ).getId().toString();
		NavigaAggregata navigaAggregata = new NavigaAggregata();
		navigaAggregata.setIdNatura(idNatura);
		navigaAggregata.setPagAggregata(paginaSoggetto);
		navigaAggregata.setIdAreaSoggetto("0");
		navigaAggregata.setIdCategoriaSoggetto("-1");
		navigaAggregata.setIdSottoCategoriaSoggetto("-1");
		navigaAggregata.setDistribuzione("");
		
		return navigaAggregata;
	}
	
	
	public NavigaAggregata navigaAggregataLocalizzazioni() {
		
		String idNatura =  (aggregataFacade.findNaturaByCod( codiNaturaOpenCUP )==null)?"0":aggregataFacade.findNaturaByCod( codiNaturaOpenCUP ).getId().toString();
		NavigaAggregata navigaAggregata = new NavigaAggregata();
		navigaAggregata.setIdNatura(idNatura);
		navigaAggregata.setPagAggregata(paginaLocalizzazione);
		navigaAggregata.setIdAreaGeografica("0");
		navigaAggregata.setDistribuzione("");
		
		return navigaAggregata;
	}

	@Test
	public void testEhCache() {
		System.out.println("START CACHE");
		/*
		try {
			cacheClassificazione();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		*/
		cacheLocalizzazione();
		/*
		try {
			cacheSoggetto();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		*/
		System.out.println("END CACHE");
	}

	private void cacheSoggetto() throws CloneNotSupportedException {
		System.out.println("CACHE SOGGETTO");
		
		System.out.println("Elaborazione di I Livello");
		//Estrazione primo livello
		NavigaAggregata navigaAggregata = navigaAggregataSoggetto();
		navigaAggregata.setOrderProperty("gerarchiaSoggetto.descAreaSoggetto");
		navigaAggregata.setOrderType("asc");
		System.out.println( navigaAggregata.toString() );
		List<AggregataDTO> listaAggregataDTO = aggregataFacade.findAggregataByNatura(navigaAggregata);
		
		//Estrazione Secondo Livello
		System.out.println("Elaborazione II e III Livello");
		
		for(AggregataDTO tmpADto : listaAggregataDTO){
			NavigaAggregata navigaAggregataII = navigaAggregata.clone();
			navigaAggregataII.setOrderProperty("gerarchiaSoggetto.descCategSoggetto");
			navigaAggregataII.setIdAreaSoggetto(tmpADto.getIdAreaSoggetto().toString());
			navigaAggregataII.setIdCategoriaSoggetto("0");
			System.out.println("Elaborazione di II Livello");
			System.out.println( navigaAggregataII.toString() );
			List<AggregataDTO> listaAggregataDTOII = aggregataFacade.findAggregataByNatura(navigaAggregataII);

			System.out.println("Elaborazione di III Livello");
			for( AggregataDTO tmpSSI : listaAggregataDTOII ){
				NavigaAggregata navigaAggregataIII = navigaAggregataII.clone();
				navigaAggregataIII.setOrderProperty("gerarchiaSoggetto.descSottocategSoggetto");
				navigaAggregataIII.setIdCategoriaSoggetto(tmpSSI.getIdCategSoggetto().toString());
				navigaAggregataIII.setIdSottoCategoriaSoggetto("0");
				System.out.println( navigaAggregataIII.toString() );
				List<AggregataDTO> listaAggregataDTOIII = aggregataFacade.findAggregataByNatura(navigaAggregataIII);
			}
		}
	}

	private void cacheLocalizzazione() {
		System.out.println("CACHE LOCALIZZAZIONE");
		
		System.out.println("Elaborazione Aree Geografice");
		NavigaAggregata navigaAggregata = navigaAggregataLocalizzazioni();
		
		
	}

	private void cacheClassificazione() throws CloneNotSupportedException {
		
		System.out.println("CACHE CLASSIFICAZIONE");
		
		System.out.println("Elaborazione di I Livello");
		//Estrazione primo livello
		NavigaAggregata navigaAggregata = navigaAggregataClassificazione();
		navigaAggregata.setOrderProperty("classificazione.descAreaIntervento");
		navigaAggregata.setOrderType("asc");
		System.out.println( navigaAggregata.toString() );
		List<AggregataDTO> listaAggregataDTO = aggregataFacade.findAggregataByNatura(navigaAggregata);
		
		//Estrazione Secondo Livello
		System.out.println("Elaborazione II e III Livello");
		
		for(AggregataDTO tmpADto : listaAggregataDTO){
			NavigaAggregata navigaAggregataII = navigaAggregata.clone();
			navigaAggregataII.setOrderProperty("classificazione.descSottosettore");
			navigaAggregataII.setIdAreaIntervento(tmpADto.getIdArea().toString());
			navigaAggregataII.setIdSottosettoreIntervento("0");
			System.out.println("Elaborazione di II Livello");
			System.out.println( navigaAggregataII.toString() );
			List<AggregataDTO> listaAggregataDTOII = aggregataFacade.findAggregataByNatura(navigaAggregataII);
			
			System.out.println("Elaborazione di III Livello");
			for( AggregataDTO tmpSSI : listaAggregataDTOII ){
				NavigaAggregata navigaAggregataIII = navigaAggregataII.clone();
				navigaAggregataIII.setOrderProperty("classificazione.descCategoria");
				navigaAggregataIII.setIdSottosettoreIntervento(tmpSSI.getIdSottoSettore().toString());
				navigaAggregataIII.setIdCategoriaIntervento("0");
				System.out.println( navigaAggregataIII.toString() );
				List<AggregataDTO> listaAggregataDTOIII = aggregataFacade.findAggregataByNatura(navigaAggregataIII);
			}
		}
		
	}
}
