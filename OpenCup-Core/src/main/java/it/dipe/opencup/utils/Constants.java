package it.dipe.opencup.utils;

public class Constants {
	
	public static final String RICERCALIBERA_PORTLET_ID = "ricercaliberaportlet_WAR_OpenCupPortletsportlet";
	
	public static final long RICERCALIBERA_GROUP_ID = 1;
	
	public static final String RICERCALIBERA_FIELD_LOCALIZZAZIONE = "localizzazione";
	
	public static final String RICERCALIBERA_FIELD_SEARCH = "search";
	
	public static final String RICERCALIBERA_FIELD_CODICE_CUP = "codicecup";
	
	public static final String RICERCALIBERA_FIELD_ANNO_DECISIONE = "annodecisione";
	
	public static final String RICERCALIBERA_FIELD_COMUNE = "comune";
	
	public static final String RICERCALIBERA_FIELD_CATEGORIA = "categoria";
	
	public static final String RICERCALIBERA_FIELD_COSTO = "costo";
	
	public static final String RICERCALIBERA_FIELD_IMPORTO = "importo";
	
	public static final int BATCH_INDICIZZAZIONE_PK = 888;
	
	public static final String BATCH_STATUS_SCHEDULATO = "SCHEDULATO";
	public static final String BATCH_STATUS_ASSENTE = "ASSENTE";
	public static final String BATCH_STATUS_ESECUZIONE = "ESECUZIONE";
	
	public static final String BATCH_INDICIZZAZIONE_DEFAULT_CRON = "0 38 16 * * ?";
	public static final String BATCH_INDICIZZAZIONE_NOME = "it.dipe.opencup.utils.ProgettoIndicizzatoreMessageListener";
	public static final String BATCH_INDICIZZAZIONE_DESC = "Indicizzazione progetti Opencup";
}
