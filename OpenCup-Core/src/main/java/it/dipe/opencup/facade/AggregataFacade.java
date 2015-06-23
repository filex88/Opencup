package it.dipe.opencup.facade;

import it.dipe.opencup.dao.AggregataDAO;
import it.dipe.opencup.dao.AnnoAggregatoDAO;
import it.dipe.opencup.dao.AnnoDecisioneDAO;
import it.dipe.opencup.dao.AreaGeograficaDAO;
import it.dipe.opencup.dao.AreaInterventoDAO;
import it.dipe.opencup.dao.AreaSoggettoDAO;
import it.dipe.opencup.dao.CategoriaInterventoDAO;
import it.dipe.opencup.dao.CategoriaSoggettoDAO;
import it.dipe.opencup.dao.ClassificazioneDAO;
import it.dipe.opencup.dao.ComuneDAO;
import it.dipe.opencup.dao.GerarchiaSoggettoDAO;
import it.dipe.opencup.dao.LocalizzazioneDAO;
import it.dipe.opencup.dao.NaturaDAO;
import it.dipe.opencup.dao.NaturaSettoreDAO;
import it.dipe.opencup.dao.ProgettiTotaliDAO;
import it.dipe.opencup.dao.ProgettoDAO;
import it.dipe.opencup.dao.ProvinciaDAO;
import it.dipe.opencup.dao.RegioneDAO;
import it.dipe.opencup.dao.SettoreInterventoDAO;
import it.dipe.opencup.dao.SottocategoriaSoggettoDAO;
import it.dipe.opencup.dao.SottosettoreInterventoDAO;
import it.dipe.opencup.dao.StatoProgettoDAO;
import it.dipe.opencup.dao.TipologiaInterventoDAO;
import it.dipe.opencup.dto.AggregataDTO;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.model.Aggregata;
import it.dipe.opencup.model.AnnoAggregato;
import it.dipe.opencup.model.AnnoDecisione;
import it.dipe.opencup.model.AreaGeografica;
import it.dipe.opencup.model.AreaIntervento;
import it.dipe.opencup.model.AreaSoggetto;
import it.dipe.opencup.model.CategoriaIntervento;
import it.dipe.opencup.model.CategoriaSoggetto;
import it.dipe.opencup.model.Comune;
import it.dipe.opencup.model.GerarchiaSoggetto;
import it.dipe.opencup.model.Natura;
import it.dipe.opencup.model.NaturaSettore;
import it.dipe.opencup.model.ProgettiTotali;
import it.dipe.opencup.model.Provincia;
import it.dipe.opencup.model.Regione;
import it.dipe.opencup.model.SettoreIntervento;
import it.dipe.opencup.model.SottocategoriaSoggetto;
import it.dipe.opencup.model.SottosettoreIntervento;
import it.dipe.opencup.model.StatoProgetto;
import it.dipe.opencup.model.TipologiaIntervento;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component("aggregataFacade")
public class AggregataFacade {
	
	@Autowired
	private ProgettoDAO progettoDAO;
	
	@Autowired
	private AggregataDAO aggregataDAO;
	
	@Autowired
	private NaturaDAO naturaDAO;
	
	@Autowired
	private ClassificazioneDAO classificazioneDAO;
	
	@Autowired
	private RegioneDAO regioneDAO;
	
	@Autowired
	private AreaGeograficaDAO areaGeograficaDAO;
	
	@Autowired
	private ProvinciaDAO provinciaDAO;
	
	@Autowired
	private ComuneDAO comuneDAO;
	
	@Autowired
	private LocalizzazioneDAO localizzazioneDAO;
	
	@Autowired
	private StatoProgettoDAO statoProgettoDAO;
	
	@Autowired
	private AnnoDecisioneDAO annoDecisioneDAO;	
	
	@Autowired
	private AnnoAggregatoDAO annoAggregatoDAO;	
	
	@Autowired
	private AreaInterventoDAO areaInterventoDAO;
	
	@Autowired
	private TipologiaInterventoDAO tipologiaInterventoDAO;		
	
	@Autowired
	private CategoriaSoggettoDAO categoriaSoggettoDAO;
	
	@Autowired
	private SottocategoriaSoggettoDAO sottocategoriaSoggettoDAO;

	@Autowired
	private SettoreInterventoDAO settoreInterventoDAO;
		
	@Autowired
	private NaturaSettoreDAO naturaSettoreDAO;

	@Autowired
	private SottosettoreInterventoDAO sottosettoreInterventoDAO;

	@Autowired
	private CategoriaInterventoDAO categoriaInterventoDAO;
	
	@Autowired
	private AreaSoggettoDAO areaSoggettoDAO;
	
	@Autowired
	private ProgettiTotaliDAO progettiTotaliDAO;
	
	@Autowired
	private GerarchiaSoggettoDAO gerarchiaSoggettoDAO;
	
//	private Criteria buildCriteria4Testata(NavigaAggregata navigaAggregata){
//		Criteria criteria = aggregataDAO.newCriteria();
//		
//		criteria.createAlias("classificazione", "classificazione");
//		criteria.createAlias("localizzazione", "localizzazione");
//		criteria.createAlias("annoAggregato", "annoAggregato");
//		criteria.createAlias("gerarchiaSoggetto", "gerarchiaSoggetto");
//		
//		criteria.add( Restrictions.eq("annoAggregato.id", -1 ) );
//		
//		if( navigaAggregata.getIdProvincia().equals("0") ){
//			navigaAggregata.setIdProvincia("-1");
//		}
//		criteria.add( Restrictions.eq("localizzazione.provincia.id", Integer.valueOf(navigaAggregata.getIdProvincia())) );
//		
//		if( navigaAggregata.getIdRegione().equals("0") ){
//			navigaAggregata.setIdRegione("-1");
//		}
//		criteria.add( Restrictions.eq("localizzazione.regione.id", Integer.valueOf(navigaAggregata.getIdRegione())) );
//		
//		if( navigaAggregata.getIdAreaGeografica().equals("0") ){
//			navigaAggregata.setIdAreaGeografica("-1");
//		}
//		criteria.add( Restrictions.eq("localizzazione.areaGeografica.id", Integer.valueOf(navigaAggregata.getIdAreaGeografica())) );
//
//		criteria.add( Restrictions.eq("localizzazione.descStato", navigaAggregata.getDescStato() ) );
//		
//		if( navigaAggregata.getIdNatura().equals("0") ){
//			navigaAggregata.setIdNatura("-1");
//		}
//		criteria.add( Restrictions.eq("classificazione.natura.id", Integer.valueOf(navigaAggregata.getIdNatura())) );
//		
//		if( navigaAggregata.getIdAreaIntervento().equals("0") ){
//			navigaAggregata.setIdAreaIntervento("-1");
//		}
//		criteria.add( Restrictions.eq("classificazione.areaIntervento.id", Integer.valueOf(navigaAggregata.getIdAreaIntervento())) );
//		
//		if( navigaAggregata.getIdSottosettoreIntervento().equals("0") ){
//			navigaAggregata.setIdSottosettoreIntervento("-1");
//		}
//		criteria.add( Restrictions.eq("classificazione.sottosettoreIntervento.id", Integer.valueOf(navigaAggregata.getIdSottosettoreIntervento())) );
//		
//		if( navigaAggregata.getIdCategoriaIntervento().equals("0") ){
//			navigaAggregata.setIdCategoriaIntervento("-1");
//		}
//		criteria.add( Restrictions.eq("classificazione.categoriaIntervento.id", Integer.valueOf(navigaAggregata.getIdCategoriaIntervento())) );
//		
//		if( navigaAggregata.getIdStatoProgetto().equals("0") ){
//			navigaAggregata.setIdStatoProgetto("-1");
//		}
//		criteria.add( Restrictions.eq("statoProgetto.id", Integer.valueOf(navigaAggregata.getIdStatoProgetto())) );
//		
//		if( navigaAggregata.getIdTipologiaIntervento().equals("0") ){
//			navigaAggregata.setIdTipologiaIntervento("-1");
//		}
//		criteria.add( Restrictions.eq("tipologiaIntervento.id", Integer.valueOf(navigaAggregata.getIdTipologiaIntervento())) );
//		
//		if( navigaAggregata.getIdCategoriaSoggetto().equals("0") ){
//			navigaAggregata.setIdCategoriaSoggetto("-1");
//		}
//		criteria.add( Restrictions.eq("gerarchiaSoggetto.categoriaSoggetto.id", Integer.valueOf(navigaAggregata.getIdCategoriaSoggetto())) );	
//
//		if( navigaAggregata.getIdSottoCategoriaSoggetto().equals("0") ){
//			navigaAggregata.setIdSottoCategoriaSoggetto("-1");
//		}
//		criteria.add( Restrictions.eq("gerarchiaSoggetto.sottocategoriaSoggetto.id", Integer.valueOf(navigaAggregata.getIdSottoCategoriaSoggetto())) );
//		
//		if( navigaAggregata.getIdAreaSoggetto().equals("0") ){
//			navigaAggregata.setIdAreaSoggetto("-1");
//		}
//		criteria.add( Restrictions.eq("gerarchiaSoggetto.areaSoggetto.id", Integer.valueOf(navigaAggregata.getIdAreaSoggetto())) );
//		
//		return criteria;
//	}
	
	private Criteria buildCriteria(NavigaAggregata navigaAggregata) {

		if( navigaAggregata.getIdAnnoDecisiones() != null && navigaAggregata.getIdAnnoDecisiones().size() > 0){
			List<String> idAnnoAggregatos = new ArrayList<String>();
			for( String tmp : navigaAggregata.getIdAnnoDecisiones() ){
				idAnnoAggregatos.add((annoDecisioneDAO.findById( Integer.valueOf(tmp) )).getAnnoAggregato().getId().toString());
			}
			navigaAggregata.setIdAnnoAggregatos( idAnnoAggregatos );
		}
		
		Criteria criteria = aggregataDAO.newCriteria();

		if( navigaAggregata.getIdAnnoAggregatos() != null && navigaAggregata.getIdAnnoAggregatos().size() > 0 ){
			if( navigaAggregata.getIdAnnoAggregatos().contains("-1") ){
				criteria.createAlias("annoAggregato", "annoAggregato");
				criteria.add( Restrictions.eq("annoAggregato.id", -1 ) );
			}else if( navigaAggregata.getIdAnnoAggregatos().contains("0") ){
				criteria.createAlias("annoAggregato", "annoAggregato");
				criteria.add( Restrictions.ge("annoAggregato.id", 0 ) );
			}else{
				criteria.createAlias("annoAggregato", "annoAggregato");
				Disjunction or = Restrictions.disjunction();
				
				Disjunction orId = Restrictions.disjunction();
				
				Disjunction orYear = Restrictions.disjunction();
				
				for( String tmp : navigaAggregata.getIdAnnoAggregatos() ){
					orId.add(Restrictions.eq("annoAggregato.id", Integer.valueOf( tmp )) );
					orYear.add(Restrictions.eq("annoAggregato.annoAggregato", tmp ) );
				}
				
				or.add(orId);
				or.add(orYear);
				
				criteria.add(or);
				
				
			}
		}else{
			criteria.createAlias("annoAggregato", "annoAggregato");
			criteria.add( Restrictions.eq("annoAggregato.id", -1 ) );
		}

		boolean addAliasLocalizzazione = false;
		if( navigaAggregata.getIdProvincia().equals("0") ){
			addAliasLocalizzazione = true;
			criteria.add( Restrictions.ge("localizzazione.provincia.id", Integer.valueOf(navigaAggregata.getIdProvincia())) );
		}else{
			addAliasLocalizzazione = true;
			criteria.add( Restrictions.eq("localizzazione.provincia.id", Integer.valueOf(navigaAggregata.getIdProvincia())) );
		}
		
		if( navigaAggregata.getIdRegione().equals("0") ){
			addAliasLocalizzazione = true;
			criteria.add( Restrictions.ge("localizzazione.regione.id", Integer.valueOf(navigaAggregata.getIdRegione())) );
		}else{
			addAliasLocalizzazione = true;
			criteria.add( Restrictions.eq("localizzazione.regione.id", Integer.valueOf(navigaAggregata.getIdRegione())) );
		}
		
		if( navigaAggregata.getIdAreaGeografica().equals("0") ){
			addAliasLocalizzazione = true;
			criteria.add( Restrictions.ge("localizzazione.areaGeografica.id", Integer.valueOf(navigaAggregata.getIdAreaGeografica())) );
		}else{
			addAliasLocalizzazione = true;
			criteria.add( Restrictions.eq("localizzazione.areaGeografica.id", Integer.valueOf(navigaAggregata.getIdAreaGeografica())) );
		}
		addAliasLocalizzazione = true;
		criteria.add( Restrictions.eq("localizzazione.descStato", navigaAggregata.getDescStato() ) );
		
		if(addAliasLocalizzazione){
			criteria.createAlias("localizzazione", "localizzazione");
		}
		
		
		//criteria.add( Restrictions.eq("stato.descStato", navigaAggregata.getDescStato() ) );
		boolean addAliasClassificazione = false;
		if( navigaAggregata.getIdNatura().equals("0") ){
			addAliasClassificazione = true;
			criteria.add( Restrictions.ge("classificazione.natura.id", Integer.valueOf(navigaAggregata.getIdNatura())) );
		}else{
			addAliasClassificazione = true;
			criteria.add( Restrictions.eq("classificazione.natura.id", Integer.valueOf(navigaAggregata.getIdNatura())) );
		}
		
		if( navigaAggregata.getIdAreaIntervento().equals("0") ){
			addAliasClassificazione = true;
			criteria.add( Restrictions.ge("classificazione.areaIntervento.id", Integer.valueOf(navigaAggregata.getIdAreaIntervento())) );
		}else{
			addAliasClassificazione = true;
			criteria.add( Restrictions.eq("classificazione.areaIntervento.id", Integer.valueOf(navigaAggregata.getIdAreaIntervento())) );
		}
		
		if( navigaAggregata.getIdSottosettoreIntervento().equals("0") ){
			addAliasClassificazione = true;
			criteria.add( Restrictions.ge("classificazione.sottosettoreIntervento.id", Integer.valueOf(navigaAggregata.getIdSottosettoreIntervento())) );
		}else{
			addAliasClassificazione = true;
			criteria.add( Restrictions.eq("classificazione.sottosettoreIntervento.id", Integer.valueOf(navigaAggregata.getIdSottosettoreIntervento())) );
		}
		
		if( navigaAggregata.getIdCategoriaIntervento().equals("0") ){
			addAliasClassificazione = true;
			criteria.add( Restrictions.ge("classificazione.categoriaIntervento.id", Integer.valueOf(navigaAggregata.getIdCategoriaIntervento())) );
		}else{
			addAliasClassificazione = true;
			criteria.add( Restrictions.eq("classificazione.categoriaIntervento.id", Integer.valueOf(navigaAggregata.getIdCategoriaIntervento())) );
		}	
		
		if(addAliasClassificazione){
			criteria.createAlias("classificazione", "classificazione");
		}
		
		if( navigaAggregata.getIdStatoProgetto().equals("0") ){
			criteria.createAlias("statoProgetto", "statoProgetto");
			criteria.add( Restrictions.ge("statoProgetto.id", Integer.valueOf(navigaAggregata.getIdStatoProgetto())) );
		}else{
			criteria.createAlias("statoProgetto", "statoProgetto");
			criteria.add( Restrictions.eq("statoProgetto.id", Integer.valueOf(navigaAggregata.getIdStatoProgetto())) );
		}	
		
		if( navigaAggregata.getIdTipologiaIntervento().equals("0") ){
			criteria.add( Restrictions.ge("tipologiaIntervento.id", Integer.valueOf(navigaAggregata.getIdTipologiaIntervento())) );
		}else{
			criteria.add( Restrictions.eq("tipologiaIntervento.id", Integer.valueOf(navigaAggregata.getIdTipologiaIntervento())) );
		}	
		
		boolean addGerarchiaSoggetto = false; 
		if( navigaAggregata.getIdCategoriaSoggetto().equals("0") ){
			addGerarchiaSoggetto = true;
			criteria.add( Restrictions.ge("gerarchiaSoggetto.categoriaSoggetto.id", Integer.valueOf(navigaAggregata.getIdCategoriaSoggetto())) );
		}else{
			addGerarchiaSoggetto = true;
			criteria.add( Restrictions.eq("gerarchiaSoggetto.categoriaSoggetto.id", Integer.valueOf(navigaAggregata.getIdCategoriaSoggetto())) );
		}	

		if( navigaAggregata.getIdSottoCategoriaSoggetto().equals("0") ){
			addGerarchiaSoggetto = true;
			criteria.add( Restrictions.ge("gerarchiaSoggetto.sottocategoriaSoggetto.id", Integer.valueOf(navigaAggregata.getIdSottoCategoriaSoggetto())) );
		}else{
			addGerarchiaSoggetto = true;
			criteria.add( Restrictions.eq("gerarchiaSoggetto.sottocategoriaSoggetto.id", Integer.valueOf(navigaAggregata.getIdSottoCategoriaSoggetto())) );
		}
		
		if( navigaAggregata.getIdAreaSoggetto().equals("0") ){
			addGerarchiaSoggetto = true;
			criteria.add( Restrictions.ge("gerarchiaSoggetto.areaSoggetto.id", Integer.valueOf(navigaAggregata.getIdAreaSoggetto())) );
		}else{
			addGerarchiaSoggetto = true;
			criteria.add( Restrictions.eq("gerarchiaSoggetto.areaSoggetto.id", Integer.valueOf(navigaAggregata.getIdAreaSoggetto())) );
		}
		
		if(addGerarchiaSoggetto){
			criteria.createAlias("gerarchiaSoggetto", "gerarchiaSoggetto");
		}
		
		/*
		criteria.setProjection(Projections.sum("numeProgetti"));
		criteria.setProjection(Projections.sum("impoCostoProgetti"));
		criteria.setProjection(Projections.sum("impoImportoFinanziato"));
		*/
		return criteria;
	}
		
	private List<AggregataDTO> listaAggregataToListaAggregataDTO( 	NavigaAggregata navigaAggregata, 
																	List<Aggregata> listaAggregata) {
		List<AggregataDTO> retval = new ArrayList<AggregataDTO>();

		List<Integer> listaIdElementiEleborati = new ArrayList<Integer>();

		if(navigaAggregata.getIdAnnoAggregatos().size() > 1 && navigaAggregata.isFlagAggregaAnni()){

			//E' stata effettuata una ricerca per più anni, devo aggregare i risultati per anni diversi		
			for( Aggregata tmpAggregata : listaAggregata ){

				//Verifico che il record corrente non sia già stato aggregato ad uno precedente e quindi già presente nella newListaAggregata
				boolean daAggregare = ! listaIdElementiEleborati.contains( tmpAggregata.getId() );

				if(daAggregare){
 
					//Salvo l'id dell'elemento tra quelli già aggregati
					listaIdElementiEleborati.add(tmpAggregata.getId());
					
					//Uso il primo elemento trovato
					Long numeProgetti = tmpAggregata.getNumeProgetti();
					Double impoCostoProgetti = tmpAggregata.getImpoCostoProgetti();
					Double impoImportoFinanziato = tmpAggregata.getImpoImportoFinanziato();
					
					for( Aggregata tmpAggregata2 : listaAggregata ){
						
						if( tmpAggregata.getClassificazione().equals( tmpAggregata2.getClassificazione() ) 
								&&
								(! tmpAggregata.getAnnoAggregato().equals( tmpAggregata2.getAnnoAggregato() ) )
								){
							
							//Salvo l'id dell'elemento tra quelli già aggregati
							listaIdElementiEleborati.add(tmpAggregata2.getId());
							
							//sommo i valori in un unico record
							numeProgetti = numeProgetti + tmpAggregata2.getNumeProgetti();
							impoCostoProgetti = impoCostoProgetti + tmpAggregata2.getImpoCostoProgetti();
							impoImportoFinanziato = impoImportoFinanziato + tmpAggregata2.getImpoImportoFinanziato();
	
						}
					}
					
					tmpAggregata.setImpoCostoProgetti( impoCostoProgetti );
					tmpAggregata.setImpoImportoFinanziato( impoImportoFinanziato );
					tmpAggregata.setNumeProgetti(numeProgetti);
					
					retval.add(new AggregataDTO(tmpAggregata));
				
				}
			}

		}else{
			for( Aggregata tmp: listaAggregata ){
				retval.add(new AggregataDTO(tmp));
			}
		}

		return retval;

	}
	
	@Cacheable(value = "AggregataDTO")
	public List<AggregataDTO> findAggregataByNatura(NavigaAggregata navigaAggregata) {
		Criteria criteria = buildCriteria(navigaAggregata);
		if( ! StringUtils.isEmpty(navigaAggregata.getOrderProperty()) ){
			if("asc".equals(navigaAggregata.getOrderType())){
				criteria.addOrder(Order.asc(navigaAggregata.getOrderProperty()));
			}else{
				criteria.addOrder(Order.desc(navigaAggregata.getOrderProperty()));
			}
		}
		List<Aggregata> listaAggregata = aggregataDAO.findByCriteria(criteria);
		return listaAggregataToListaAggregataDTO(navigaAggregata, listaAggregata);

	}
	
	@Cacheable(value = "AggregataDTO")
	public List<ProgettiTotali> findDatiCUPByNatura(Integer idNatura) {
		List<ProgettiTotali> retVal = new ArrayList<ProgettiTotali>();
		Criteria criteria = progettiTotaliDAO.newCriteria();
		criteria.add( Restrictions.eq("idNatura", idNatura) );
		retVal.addAll(progettiTotaliDAO.findByCriteria(criteria));
		return retVal;
	}
	
	@Cacheable(value = "AggregataDTO")
	public List<ProgettiTotali> findDatiCUPAll() {
		List<ProgettiTotali> retVal = new ArrayList<ProgettiTotali>();
		retVal.addAll(progettiTotaliDAO.findAll());
		return retVal;
	}
	
	@Cacheable(value = "Aggregata")
	public List<Aggregata> findAggregataByLocalizzazione(NavigaAggregata navigaAggregata) {		

		Criteria criteria = buildCriteria(navigaAggregata);
		if( ! StringUtils.isEmpty(navigaAggregata.getOrderProperty()) ){
			if("asc".equals(navigaAggregata.getOrderType())){
				criteria.addOrder(Order.asc(navigaAggregata.getOrderProperty()));
			}else{
				criteria.addOrder(Order.desc(navigaAggregata.getOrderProperty()));
			}
		}
		return aggregataDAO.findByCriteria(criteria);
	}
	
//	@Cacheable(value = "AggregataDTO")
//	public List<AggregataDTO> findAggregataByNatura(NavigaAggregata navigaAggregata, String orderByCol, String orderByType) {
//		
//		
//		Criteria criteria = buildCriteria(navigaAggregata);
//		
//		if("asc".equals(orderByType)){
//			criteria.addOrder(Order.asc(orderByCol));
//		}else{
//			criteria.addOrder(Order.desc(orderByCol));
//		}
//
//		List<Aggregata> listaAggregata = aggregataDAO.findByCriteria(criteria);
//		
//		return listaAggregataToListaAggregataDTO(navigaAggregata, listaAggregata);
//
//	}

	@Cacheable(value = "Regione")
	public List<Regione> findRegioni() {

		List<Regione> retval = new ArrayList<Regione>();

		Criteria criteria = regioneDAO.newCriteria();
		criteria.add( Restrictions.ne("codiRegione", "00") );
		criteria.add( Restrictions.ne("id", -1) );
		criteria.addOrder(Order.asc("descRegione"));
		//Estraggo le altre regioni ordinate per nome
		retval.addAll(regioneDAO.findByCriteria(criteria));

		return retval;
	}
	
	@Cacheable(value = "Provincia")
	public List<Provincia> findProvinciaByIdRegione(Integer idRegione) {

		List<Provincia> retval = new ArrayList<Provincia>();
		
		Criteria criteria = provinciaDAO.newCriteria();
		criteria.createAlias("regione", "regione");
		criteria.add( Restrictions.ne("codiProvincia", "00") );
		criteria.add( Restrictions.ne("id", -1) );
		criteria.add( Restrictions.eq("regione.id", idRegione) );
		criteria.addOrder(Order.asc("descProvincia"));
	
		//Estraggo le altre regioni ordinate per nome
		retval.addAll(provinciaDAO.findByCriteria(criteria));
		
		return retval;
	}
	
	@Cacheable(value = "Comune")
	public List<Comune> findComuneByIdProvincia(Integer idProvincia) {

		List<Comune> retval = new ArrayList<Comune>();
		
		Criteria criteria = comuneDAO.newCriteria();
		criteria.createAlias("provincia", "provincia");
		criteria.add( Restrictions.ne("codiComune", "00") );
		criteria.add( Restrictions.ne("id", -1) );
		criteria.add( Restrictions.eq("provincia.id", idProvincia) );
		criteria.addOrder(Order.asc("descComune"));
	
		//Estraggo le altre regioni ordinate per nome
		retval.addAll(comuneDAO.findByCriteria(criteria));
		
		return retval;
	}
	
	@Cacheable(value = "StatoProgetto")
	public List<StatoProgetto> findStatoProgetto() {

		List<StatoProgetto> retval = new ArrayList<StatoProgetto>();
		
		Criteria criteria = statoProgettoDAO.newCriteria();
		criteria.add( Restrictions.ne("id", -1) );
		criteria.addOrder(Order.asc("descStatoProgetto"));
		
		retval = statoProgettoDAO.findByCriteria(criteria);
		
		return retval;
	}
	
	@Cacheable(value = "AnnoDecisione")
	public List<AnnoDecisione> findAnnoDecisioneDAO() {

		List<AnnoDecisione> retval = new ArrayList<AnnoDecisione>();
		
		Criteria criteria = annoDecisioneDAO.newCriteria();
		criteria.add( Restrictions.ne("id", -1) );
		criteria.addOrder(Order.asc("annoDadeAnnoDecisione"));
		
		retval = annoDecisioneDAO.findByCriteria(criteria);
		
		return retval;
	}
	
	@Cacheable(value = "AnnoAggregato")
	public List<AnnoAggregato> findAnnoAggregato() {

		List<AnnoAggregato> retval = new ArrayList<AnnoAggregato>();
		
		Criteria criteria = annoAggregatoDAO.newCriteria();
		criteria.add( Restrictions.ne("id", -1) );
		criteria.addOrder(Order.asc("annoAggregato"));
		
		retval = annoAggregatoDAO.findByCriteria(criteria);
		
		return retval;
	}

	@Cacheable(value = "TipologiaIntervento")
	public List<TipologiaIntervento> findTipologiaIntervento() {

		List<TipologiaIntervento> retval = new ArrayList<TipologiaIntervento>();
				
		Criteria criteria = tipologiaInterventoDAO.newCriteria();
		criteria.add( Restrictions.ne("id", -1) );
		criteria.addOrder(Order.asc("descTipologiaIntervento"));
		
		retval = tipologiaInterventoDAO.findByCriteria(criteria);
		
		return retval;
	}

	@Cacheable(value = "AnnoDecisione")
	public List<AnnoDecisione> findAnniDecisione() {
		
		List<AnnoDecisione> retval = new ArrayList<AnnoDecisione>();
		
		Criteria criteria = annoDecisioneDAO.newCriteria();
		criteria.add( Restrictions.ne("id", -1) );
		//Modifica Daniele - 
		criteria.add(Restrictions.le("annoDadeAnnoDecisione", getYearFromDate(new Date()).toString()));
		//End - Daniele
		criteria.addOrder(Order.desc("annoDadeAnnoDecisione"));
		
		retval = annoDecisioneDAO.findByCriteria(criteria);
		
		return retval;
	}

	@Cacheable(value = "CategoriaSoggetto")
	public List<CategoriaSoggetto> findCategoriaSoggetto() {

		List<CategoriaSoggetto> retval = new ArrayList<CategoriaSoggetto>();
		
		Criteria criteria = categoriaSoggettoDAO.newCriteria();
		criteria.add( Restrictions.ne("id", -1) );
		criteria.addOrder(Order.asc("descCategoriaSoggetto"));
		
		retval = categoriaSoggettoDAO.findByCriteria(criteria);
		
		return retval;
	}

	
	@Cacheable(value = "Natura")
	public List<Natura> findNatura() {

		List<Natura> retval = new ArrayList<Natura>();
		
		Criteria criteria = naturaDAO.newCriteria();
		criteria.add( Restrictions.ne("id", -1) );

		criteria.addOrder(Order.asc("descNatura"));
		
		retval = naturaDAO.findByCriteria(criteria);
		
		return retval;
	}
	
	@Cacheable(value = "Natura")
	public Natura findNaturaByCod(String codiNatura) {

		Natura retval = null;
		
		Criteria criteria = naturaDAO.newCriteria();
		
		criteria.add( Restrictions.eq("codiNatura", codiNatura) );
		
		List<Natura> l = naturaDAO.findByCriteria(criteria);
		
		if(l!=null && l.size()>0){
			retval = l.get(0);
		}
		
		return retval;
	}
	
	@Cacheable(value = "SettoreIntervento")
	public List<SettoreIntervento> findSettoreByNatura(Integer idNatura) {

		List<SettoreIntervento> retval = new ArrayList<SettoreIntervento>();
		
		Criteria criteria = naturaSettoreDAO.newCriteria();
		criteria.createAlias("natura", "natura");
		criteria.createAlias("settoreIntervento", "settoreIntervento");
		criteria.add( Restrictions.ne("id", -1) );
		criteria.add( Restrictions.eq("natura.id", idNatura) );
		criteria.addOrder(Order.asc("settoreIntervento.descSettoreIntervento"));

		for(NaturaSettore tmp : naturaSettoreDAO.findByCriteria(criteria)  ){
			retval.add( tmp.getSettoreIntervento() );
		}
		return retval;
	}
	
	
	@Cacheable(value = "AreaIntervento")
	public List<AreaIntervento> findAreaIntervento() {

		List<AreaIntervento> retval = new ArrayList<AreaIntervento>();
		
		Criteria criteria = areaInterventoDAO.newCriteria();
		criteria.add( Restrictions.ne("id", -1) );

		criteria.addOrder(Order.asc("descAreaIntervento"));
		
		retval = areaInterventoDAO.findByCriteria(criteria);
		
		return retval;
	}
	
	@Cacheable(value = "SottosettoreIntervento")
	public List<SottosettoreIntervento> findSottosettoreByArea(Integer idArea) {

		List<SottosettoreIntervento> retval = new ArrayList<SottosettoreIntervento>();
		
		Criteria criteria = sottosettoreInterventoDAO.newCriteria();
		criteria.createAlias("settoreIntervento.areaIntervento", "area");
		criteria.add( Restrictions.ne("id", -1) );
		criteria.add( Restrictions.eq("area.id", idArea) );
		criteria.addOrder(Order.asc("descSottosettoreInt"));

		retval = sottosettoreInterventoDAO.findByCriteria(criteria);
		
		return retval;
	}
	
	@Cacheable(value = "CategoriaIntervento")
	public List<CategoriaIntervento> findCategoriaInterventoByAreaSottosettore(Integer idArea, Integer idSottosettore) {
		
		List<CategoriaIntervento> retval = new ArrayList<CategoriaIntervento>();
		
		Criteria criteria = categoriaInterventoDAO.newCriteria();
		criteria.createAlias("settoreIntervento.areaIntervento", "area");
		criteria.createAlias("sottosettoreIntervento", "sottosettoreIntervento");
		criteria.add( Restrictions.ne("id", -1) );
		criteria.add( Restrictions.eq("area.id", idArea) );
		criteria.add( Restrictions.eq("sottosettoreIntervento.id", idSottosettore) );
		criteria.addOrder(Order.asc("descCategoriaIntervento"));

		retval = categoriaInterventoDAO.findByCriteria(criteria);
		
		return retval;
	}
	
	@Cacheable(value = "SottocategoriaSoggetto")
	public List<SottocategoriaSoggetto> findSottocategoriaSoggetto() {

		List<SottocategoriaSoggetto> retval = new ArrayList<SottocategoriaSoggetto>();
		
		Criteria criteria = sottocategoriaSoggettoDAO.newCriteria();
		criteria.add( Restrictions.ne("id", -1) );
		criteria.addOrder(Order.asc("descSottocategSoggetto"));
		
		retval = sottocategoriaSoggettoDAO.findByCriteria(criteria);
		
		return retval;
	}

	@Cacheable(value = "Regione")
	public List<Regione> findRegioniByIdAreaGeografica(Integer idAreaGeografica) {

		List<Regione> retval = new ArrayList<Regione>();

		Criteria criteria = regioneDAO.newCriteria();
		
		criteria.createAlias("areaGeografica", "areaGeografica");
		
		criteria.add( Restrictions.ne("codiRegione", "00") );
		criteria.add( Restrictions.ne("id", -1) );
		
		criteria.add( Restrictions.eq("areaGeografica.id", idAreaGeografica) );
		
		criteria.addOrder(Order.asc("descRegione"));
		//Estraggo le altre regioni ordinate per nome
		retval.addAll(regioneDAO.findByCriteria(criteria));

		return retval;
	}

	@Cacheable(value = "AreaGeografica")
	public List<AreaGeografica> findAreaGeografica() {

		List<AreaGeografica> retval = new ArrayList<AreaGeografica>();

		Criteria criteria = areaGeograficaDAO.newCriteria();
		
		criteria.add( Restrictions.ne("codiAreaGeografica", "T") );
		criteria.add( Restrictions.ne("id", -1) );
	
		criteria.addOrder(Order.asc("descAreaGeografica"));
		retval.addAll(areaGeograficaDAO.findByCriteria(criteria));

		return retval;
	}

	@Cacheable(value = "AreaGeografica")
	public AreaGeografica findAreaGeografica(Integer valueOf) {

		return areaGeograficaDAO.findById(valueOf);
	}

	@Cacheable(value = "Regione")
	public Regione findRegione(Integer valueOf) {

		return regioneDAO.findById(valueOf);
	}

	@Cacheable(value = "Provincia")
	public Provincia findProvincia(Integer valueOf) {

		return provinciaDAO.findById(valueOf);
	}

	@Cacheable(value = "Comune")
	public Comune findComune(Integer valueOf) {

		return comuneDAO.findById(valueOf);
	}

	@Cacheable(value = "TipologiaIntervento")
	public TipologiaIntervento findTipologiaIntervento(Integer valueOf) {

		return tipologiaInterventoDAO.findById(valueOf);
	}

	@Cacheable(value = "StatoProgetto")
	public StatoProgetto findStatoProgetto(Integer valueOf) {

		return statoProgettoDAO.findById(valueOf);
	}

	@Cacheable(value = "CategoriaSoggetto")
	public CategoriaSoggetto findCategoriaSoggetto(Integer valueOf) {

		return categoriaSoggettoDAO.findById(valueOf);
	}

	@Cacheable(value = "SottocategoriaSoggetto")
	public SottocategoriaSoggetto findSottoCategoriaSoggetto(Integer valueOf) {

		return sottocategoriaSoggettoDAO.findById(valueOf);
	}

	@Cacheable(value = "Natura")
	public Natura findNatura(Integer valueOf) {

		return naturaDAO.findById(valueOf);
	}

	@Cacheable(value = "SettoreIntervento")
	public SettoreIntervento findSettoreIntervento(Integer valueOf) {

		return settoreInterventoDAO.findById(valueOf);
	}

	@Cacheable(value = "AreaIntervento")
	public AreaIntervento findAreaIntervento(Integer valueOf) {
		
		
		
		return areaInterventoDAO.findById(valueOf);
	}
	
	@Cacheable(value = "SottosettoreIntervento")
	public SottosettoreIntervento findSottosettoreIntervento(Integer valueOf) {

		return sottosettoreInterventoDAO.findById(valueOf);
	}

	@Cacheable(value = "CategoriaIntervento")
	public CategoriaIntervento findCategoriaIntervento(Integer valueOf) {

		return categoriaInterventoDAO.findById(valueOf);
	}

	@Cacheable(value = "AnnoDecisione")
	public AnnoDecisione findAnniDecisione(Integer valueOf) {

		return annoDecisioneDAO.findById(valueOf);
	}
	
	@Cacheable(value = "SottocategoriaSoggetto")
	public List<SottocategoriaSoggetto> findSottocategoriaSoggetto(Integer idCategoriaSoggetto) {

		List<SottocategoriaSoggetto> retval = new ArrayList<SottocategoriaSoggetto>();

		Criteria criteria = sottocategoriaSoggettoDAO.newCriteria();
		
		criteria.createAlias("categoriaSoggetto", "categoriaSoggetto");
		
		criteria.add( Restrictions.ne("codiSottocategSoggetto", "0000") );
		criteria.add( Restrictions.ne("id", -1) );
		
		criteria.add( Restrictions.eq("categoriaSoggetto.id", idCategoriaSoggetto) );
		
		criteria.addOrder(Order.asc("descSottocategSoggetto"));
		//Estraggo le altre regioni ordinate per nome
		retval.addAll(sottocategoriaSoggettoDAO.findByCriteria(criteria));

		return retval;
	}
	
	@Cacheable(value = "AreaGeografica")
	public AreaGeografica findAreaGeograficaByCodiceArea(String codAreaGeografica) {

		AreaGeografica areaGeograficaRecuperata=null;
		List<AreaGeografica> retval = new ArrayList<AreaGeografica>();
		Criteria criteria = areaGeograficaDAO.newCriteria();
		criteria.add( Restrictions.ne("id", -1) );
		criteria.add( Restrictions.eq("codiAreaGeografica", codAreaGeografica) );
		retval = areaGeograficaDAO.findByCriteria(criteria);
		if (retval!=null && retval.size()>0){
			areaGeograficaRecuperata=retval.get(0);
		}
		return areaGeograficaRecuperata;
	}
	
	@Cacheable(value = "Regione")
	public Regione findRegionebyCodice(String codRegione) {

		Regione regioneRecuperata=null;
		List<Regione> retval = new ArrayList<Regione>();
		Criteria criteria = regioneDAO.newCriteria();
		criteria.add( Restrictions.ne("id", -1) );
		criteria.add( Restrictions.eq("codiRegione", codRegione) );
		retval = regioneDAO.findByCriteria(criteria);
		if (retval!=null && retval.size()>0){
			regioneRecuperata=retval.get(0);
		}
		return regioneRecuperata;
	}
	
	@Cacheable(value = "AreaSoggetto")
	public List<AreaSoggetto> findAreaSoggetto() {

		List<AreaSoggetto> retval = new ArrayList<AreaSoggetto>();
		
		Criteria criteria = areaSoggettoDAO.newCriteria();
		criteria.add( Restrictions.ne("id", -1) );
		criteria.addOrder(Order.asc("descAreaSoggetto"));
		
		retval = areaSoggettoDAO.findByCriteria(criteria);
		
		return retval;
	}
	
	@Cacheable(value = "CategoriaSoggetto")
	public List<CategoriaSoggetto> findCategoriaSoggettoByIdAreaSoggetto(Integer idAreaSoggetto) {
		List<CategoriaSoggetto> retval = new ArrayList<CategoriaSoggetto>();
		List<String> listDescCatSoggetto = new ArrayList<String>();
		
		Criteria criteria = gerarchiaSoggettoDAO.newCriteria();
		criteria.createAlias("areaSoggetto", "areaSoggetto");
		criteria.createAlias("categoriaSoggetto", "categoriaSoggetto");
		criteria.add( Restrictions.ne("categoriaSoggetto.codiCategoriaSoggetto", "0000") );
		criteria.add( Restrictions.ne("categoriaSoggetto.id", -1) );
		
		criteria.add( Restrictions.eq("areaSoggetto.id", idAreaSoggetto) );
		
		criteria.addOrder(Order.asc("categoriaSoggetto.descCategoriaSoggetto"));
		
		List<GerarchiaSoggetto> list =  gerarchiaSoggettoDAO.findByCriteria(criteria);
		for(GerarchiaSoggetto gSoggetto :list){
			if(!listDescCatSoggetto.contains(gSoggetto.getCategoriaSoggetto().getDescCategoriaSoggetto())){
				listDescCatSoggetto.add(gSoggetto.getCategoriaSoggetto().getDescCategoriaSoggetto());
				retval.add(gSoggetto.getCategoriaSoggetto());
			}
		}
		
		return retval;
	}
	
	@Cacheable(value = "CategoriaSoggetto")
	public List<CategoriaSoggetto> findCategoriaSoggettoByIdAreaSoggetto() {

		List<CategoriaSoggetto> retval = new ArrayList<CategoriaSoggetto>();
		
		Criteria criteria = categoriaSoggettoDAO.newCriteria();
		
		criteria.add( Restrictions.ne("codiCategoriaSoggetto", "0000") );
		criteria.add( Restrictions.ne("id", -1) );
		
		criteria.addOrder(Order.asc("descCategoriaSoggetto"));
		
		retval = categoriaSoggettoDAO.findByCriteria(criteria);
		
		return retval;
	}
	
//	@Cacheable(value = "AggregataDTO")
//	public List<AggregataDTO> findAggregata4Testata(NavigaAggregata navigaAggregata) {
//		List<Aggregata> listaAggregata = aggregataDAO.findByCriteria(buildCriteria(navigaAggregata));
//		return listaAggregataToListaAggregataDTO(navigaAggregata, listaAggregata);
//	}
	
	public static Integer getYearFromDate(Date data) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		int anno = cal.get(Calendar.YEAR);
		return anno;
	}
	
}
