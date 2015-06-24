package it.dipe.opencup.utils;

import it.dipe.opencup.dto.AggregataDTO;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.facade.AggregataFacade;
import it.dipe.opencup.model.Aggregata;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class CacheThread extends Thread{

	private AggregataFacade aggregataFacade;

	private String codiNaturaOpenCUP;

	private String paginaClassificazione;

	private String paginaSoggetto;

	private String paginaLocalizzazione;
	
	public CacheThread(	AggregataFacade aggregataFacade,
						String codiNaturaOpenCUP,
						String paginaClassificazione,
						String paginaSoggetto,
						String paginaLocalizzazione){
		
		this.aggregataFacade = aggregataFacade;
		this.codiNaturaOpenCUP = codiNaturaOpenCUP;
		this.paginaClassificazione = paginaClassificazione;
		this.paginaSoggetto = paginaSoggetto;
		this.paginaLocalizzazione = paginaLocalizzazione;
	}
	
	@Override
	public void run() {
		System.out.println("START CACHE");
		printTime();
		try {
			cacheClassificazione();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		printTime();
		try {
			cacheLocalizzazione();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		printTime();
		try {
			cacheSoggetto();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		printTime();
		try {
			cacheTestata();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		System.out.println("END CACHE");
		printTime();
	}
	
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

	public NavigaAggregata navigaAggregataTestata() {
		
		String idNatura =  (aggregataFacade.findNaturaByCod( codiNaturaOpenCUP )==null)?"0":aggregataFacade.findNaturaByCod( codiNaturaOpenCUP ).getId().toString();
		NavigaAggregata navigaAggregata = new NavigaAggregata();
		navigaAggregata.setIdNatura(idNatura);
		navigaAggregata.setPagAggregata("");
		
		return navigaAggregata;
	}

	public static void printTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println( sdf.format(cal.getTime()) );
    }

	@SuppressWarnings("unused")
	private void cacheTestata() throws CloneNotSupportedException {
		System.out.println("CACHE TESTATA");
		NavigaAggregata navigaAggregata = navigaAggregataTestata();
		List<AggregataDTO> risultati = aggregataFacade.findAggregataByNatura(navigaAggregata);
	}

	@SuppressWarnings("unused")
	private void cacheSoggetto() throws CloneNotSupportedException {
		System.out.println("CACHE SOGGETTO");
		
		System.out.println("Elaborazione di I Livello");
		//Estrazione primo livello
		NavigaAggregata navigaAggregata = navigaAggregataSoggetto();
		navigaAggregata.setOrderProperty("gerarchiaSoggetto.descAreaSoggetto");
		navigaAggregata.setOrderType("asc");
		//System.out.println( navigaAggregata.toString() );
		List<AggregataDTO> listaAggregataDTO = aggregataFacade.findAggregataByNatura(navigaAggregata);
		
		//Estrazione Secondo Livello
		System.out.println("Elaborazione II e III Livello");
		
		for(AggregataDTO tmpADto : listaAggregataDTO){
			NavigaAggregata navigaAggregataII = navigaAggregata.clone();
			navigaAggregataII.setOrderProperty("gerarchiaSoggetto.descCategSoggetto");
			navigaAggregataII.setIdAreaSoggetto(tmpADto.getIdAreaSoggetto().toString());
			navigaAggregataII.setIdCategoriaSoggetto("0");
			System.out.println("Elaborazione di II Livello");
			//System.out.println( navigaAggregataII.toString() );
			List<AggregataDTO> listaAggregataDTOII = aggregataFacade.findAggregataByNatura(navigaAggregataII);

			System.out.println("Elaborazione di III Livello");
			for( AggregataDTO tmpSSI : listaAggregataDTOII ){
				NavigaAggregata navigaAggregataIII = navigaAggregataII.clone();
				navigaAggregataIII.setOrderProperty("gerarchiaSoggetto.descSottocategSoggetto");
				navigaAggregataIII.setIdCategoriaSoggetto(tmpSSI.getIdCategSoggetto().toString());
				navigaAggregataIII.setIdSottoCategoriaSoggetto("0");
				//System.out.println( navigaAggregataIII.toString() );
				List<AggregataDTO> listaAggregataDTOIII = aggregataFacade.findAggregataByNatura(navigaAggregataIII);
			}
		}
	}

	@SuppressWarnings("unused")
	private void cacheLocalizzazione() throws CloneNotSupportedException {
		System.out.println("CACHE LOCALIZZAZIONE");
		
		System.out.println("Elaborazione per Aree Geografice");
		NavigaAggregata navigaAggregata = navigaAggregataLocalizzazioni();
		//System.out.println(navigaAggregata.toString());
		List<Aggregata> areeGeografiche = aggregataFacade.findAggregataByLocalizzazione(navigaAggregata);
		
		System.out.println("Elaborazione II e III Livello per le Aree Geografice");
		for( Aggregata tmpII : areeGeografiche){
			NavigaAggregata navigaAggregataII = navigaAggregata.clone();
			navigaAggregataII.setIdAreaGeografica(tmpII.getLocalizzazione().getAreaGeografica().getId().toString());
			navigaAggregataII.setIdRegione("0");
			System.out.println("Elaborazione di II Livello");
			//System.out.println(navigaAggregataII.toString());
			List<Aggregata> regioni = aggregataFacade.findAggregataByLocalizzazione(navigaAggregataII);
			System.out.println("Elaborazione di III Livello");
			for(Aggregata tmpIII : regioni){
				NavigaAggregata navigaAggregataIII = navigaAggregataII.clone();
				navigaAggregataIII.setIdRegione(tmpIII.getLocalizzazione().getRegione().getId().toString());
				navigaAggregataIII.setIdProvincia("0");
				//System.out.println(navigaAggregataIII.toString());
				List<Aggregata> provincie = aggregataFacade.findAggregataByLocalizzazione(navigaAggregataIII);
			}
		}
		
		System.out.println("Elaborazione per Regione Geografice");
		NavigaAggregata navigaAggregataReg = navigaAggregataLocalizzazioni();
		navigaAggregataReg.setIndicatoreNavigaLocalizzazione("R");
		navigaAggregataReg.setIdRegione("0");
		//System.out.println(navigaAggregataReg.toString());
		List<Aggregata> regioni = aggregataFacade.findAggregataByLocalizzazione(navigaAggregataReg);
		
		System.out.println("Elaborazione III Livello per le Regioni");
		for(Aggregata tmp : regioni){
			NavigaAggregata navigaAggregataRegIII = navigaAggregataReg.clone();
			navigaAggregataRegIII.setIdAreaGeografica( tmp.getLocalizzazione().getAreaGeografica().getId().toString() );
			navigaAggregataRegIII.setIdRegione( tmp.getLocalizzazione().getRegione().getId().toString() );
			navigaAggregataRegIII.setIdProvincia("0");
			//System.out.println(navigaAggregataRegIII.toString());
			List<Aggregata> provincie = aggregataFacade.findAggregataByLocalizzazione(navigaAggregataRegIII);
		}
	}

	@SuppressWarnings("unused")
	private void cacheClassificazione() throws CloneNotSupportedException {
		
		System.out.println("CACHE CLASSIFICAZIONE");
		
		System.out.println("Elaborazione di I Livello");
		//Estrazione primo livello
		NavigaAggregata navigaAggregata = navigaAggregataClassificazione();
		navigaAggregata.setOrderProperty("classificazione.descAreaIntervento");
		navigaAggregata.setOrderType("asc");
		//System.out.println( navigaAggregata.toString() );
		List<AggregataDTO> listaAggregataDTO = aggregataFacade.findAggregataByNatura(navigaAggregata);
		
		//Estrazione Secondo Livello
		System.out.println("Elaborazione II e III Livello");
		
		for(AggregataDTO tmpADto : listaAggregataDTO){
			NavigaAggregata navigaAggregataII = navigaAggregata.clone();
			navigaAggregataII.setOrderProperty("classificazione.descSottosettore");
			navigaAggregataII.setIdAreaIntervento(tmpADto.getIdArea().toString());
			navigaAggregataII.setIdSottosettoreIntervento("0");
			System.out.println("Elaborazione di II Livello");
			//System.out.println( navigaAggregataII.toString() );
			List<AggregataDTO> listaAggregataDTOII = aggregataFacade.findAggregataByNatura(navigaAggregataII);
			
			System.out.println("Elaborazione di III Livello");
			for( AggregataDTO tmpSSI : listaAggregataDTOII ){
				NavigaAggregata navigaAggregataIII = navigaAggregataII.clone();
				navigaAggregataIII.setOrderProperty("classificazione.descCategoria");
				navigaAggregataIII.setIdSottosettoreIntervento(tmpSSI.getIdSottoSettore().toString());
				navigaAggregataIII.setIdCategoriaIntervento("0");
				//System.out.println( navigaAggregataIII.toString() );
				List<AggregataDTO> listaAggregataDTOIII = aggregataFacade.findAggregataByNatura(navigaAggregataIII);
			}
		}
		
	}

}
