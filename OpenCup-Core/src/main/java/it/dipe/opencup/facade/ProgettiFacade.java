package it.dipe.opencup.facade;

import it.dipe.opencup.dao.ComuneDAO;
import it.dipe.opencup.dao.ProgettiDAO;
import it.dipe.opencup.dao.ProvinciaDAO;
import it.dipe.opencup.dao.RegioneDAO;
import it.dipe.opencup.dto.NavigaProgetti;
import it.dipe.opencup.model.Progetti;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.KeyGenerator;

@Component("progettiFacade")
public class ProgettiFacade {
	
	@Autowired
	private ProgettiDAO progettiDAO;
	
	@Autowired
	private RegioneDAO regioneDAO;
	
	@Autowired
	private ProvinciaDAO provinciaDAO;
	
	@Autowired
	private ComuneDAO comuneDAO;
	
	private Criteria buildCriteria(NavigaProgetti navigaProgetti) {
		
		Criteria criteria = progettiDAO.newCriteria();
		
		criteria.setProjection(	Projections.projectionList()
				.add(Projections.groupProperty("id"))
				.add(Projections.groupProperty("annoDecisione"))
				.add(Projections.groupProperty("soggettoTitolare"))
				.add(Projections.groupProperty("unitaOrganizzativa"))
				.add(Projections.groupProperty("natura"))
				.add(Projections.groupProperty("tipologiaIntervento"))
				.add(Projections.groupProperty("settoreIntervento"))
				.add(Projections.groupProperty("sottosettoreIntervento"))
				.add(Projections.groupProperty("categoriaIntervento"))
				.add(Projections.groupProperty("strumentoProgr"))
				.add(Projections.groupProperty("statoProgetto"))
				.add(Projections.groupProperty("impoCostoProgetto"))
				.add(Projections.groupProperty("impoImportoFinanziato"))
				.add(Projections.groupProperty("gruppoAteco"))
//				.add(Projections.groupProperty("anagraficaCup"))
				);      
		
		//CLASSIFICAZIONE
		criteria.createAlias("natura", "natura");
		criteria.createAlias("areaIntervento", "areaIntervento");
		criteria.createAlias("sottosettoreIntervento", "sottosettoreIntervento");
		criteria.createAlias("categoriaIntervento", "categoriaIntervento");
		
		//GERARCHIA SOGETTO
		criteria.createAlias("soggettoTitolare", "soggettoTitolare");
		
		//LOALIZZAZIONE
		criteria.createAlias("anagraficaCup", "anagraficaCup");
		criteria.createAlias("anagraficaCup.cupLocalizzazioneList", "anagraficaCup.cupLocalizzazioneList");
		criteria.createAlias("anagraficaCup.cupLocalizzazioneList.stato", "stato");
		criteria.createAlias("anagraficaCup.cupLocalizzazioneList.regione", "regione");
		criteria.createAlias("anagraficaCup.cupLocalizzazioneList.provincia", "provincia");
		criteria.createAlias("anagraficaCup.cupLocalizzazioneList.comune", "comune");
		
		//ANNO DECISIONE
		criteria.createAlias("annoDecisione", "annoDecisione");
		
		//TIPOLOGIA INTERVENTO
		criteria.createAlias("tipologiaIntervento", "tipologiaIntervento");
		
		//STATO INTERVENTO
		criteria.createAlias("statoProgetto", "statoProgetto");
		
		//CLASSIFICAZIONE
		criteria.add( Restrictions.eq("natura.id", Integer.valueOf(navigaProgetti.getIdNatura())) );
		 
		if( navigaProgetti.getIdAreaIntervento().equals("0") ){
			criteria.add( Restrictions.ge("areaIntervento.id", Integer.valueOf(navigaProgetti.getIdAreaIntervento())) );
		}else if(!"-1".equals(navigaProgetti.getIdAreaIntervento())){
			criteria.add( Restrictions.eq("areaIntervento.id", Integer.valueOf(navigaProgetti.getIdAreaIntervento())) );
		}
		
		if( navigaProgetti.getIdSottosettoreIntervento().equals("0") ){
			criteria.add( Restrictions.ge("sottosettoreIntervento.id", Integer.valueOf(navigaProgetti.getIdSottosettoreIntervento())) );
		}else if(!"-1".equals(navigaProgetti.getIdSottosettoreIntervento())){
			criteria.add( Restrictions.eq("sottosettoreIntervento.id", Integer.valueOf(navigaProgetti.getIdSottosettoreIntervento())) );
		}
		
		if( navigaProgetti.getIdCategoriaIntervento().equals("0") ){
			criteria.add( Restrictions.ge("categoriaIntervento.id", Integer.valueOf(navigaProgetti.getIdCategoriaIntervento())) );
		}else if(!"-1".equals(navigaProgetti.getIdCategoriaIntervento())){
			criteria.add( Restrictions.eq("categoriaIntervento.id", Integer.valueOf(navigaProgetti.getIdCategoriaIntervento())) );
		}
		
		
		//GERARCHIA SOGETTO
		if( navigaProgetti.getIdCategoriaSoggetto().equals("0") ){
			criteria.add( Restrictions.ge("soggettoTitolare.categoriaSoggetto.id", Integer.valueOf(navigaProgetti.getIdCategoriaSoggetto())) );
		}else if(!"-1".equals(navigaProgetti.getIdCategoriaSoggetto())){
			criteria.add( Restrictions.eq("soggettoTitolare.categoriaSoggetto.id", Integer.valueOf(navigaProgetti.getIdCategoriaSoggetto())) );
		}	

		if( navigaProgetti.getIdSottoCategoriaSoggetto().equals("0") ){
			criteria.add( Restrictions.ge("soggettoTitolare.sottocategoriaSoggetto.id", Integer.valueOf(navigaProgetti.getIdSottoCategoriaSoggetto())) );
		}else if(!"-1".equals(navigaProgetti.getIdSottoCategoriaSoggetto())){
			criteria.add( Restrictions.eq("soggettoTitolare.sottocategoriaSoggetto.id", Integer.valueOf(navigaProgetti.getIdSottoCategoriaSoggetto())) );
		}
		
		//LOALIZZAZIONE
		criteria.add( Restrictions.eq("stato.descStato", navigaProgetti.getDescStato() ) );
		
		if( navigaProgetti.getIdRegione().equals("0") ){
			criteria.add( Restrictions.ge("regione.id", Integer.valueOf(navigaProgetti.getIdRegione())) );
		}else if(!"-1".equals(navigaProgetti.getIdRegione())){
			criteria.add( Restrictions.eq("regione.id", Integer.valueOf(navigaProgetti.getIdRegione())) );
		}
		
		if( navigaProgetti.getIdProvincia().equals("0") ){
			criteria.add( Restrictions.ge("provincia.id", Integer.valueOf(navigaProgetti.getIdProvincia())) );
		}else if(!"-1".equals(navigaProgetti.getIdProvincia())){
			criteria.add( Restrictions.eq("provincia.id", Integer.valueOf(navigaProgetti.getIdProvincia())) );
		}
		
		if( navigaProgetti.getIdComune().equals("0") ){
			criteria.add( Restrictions.ge("comune.id", Integer.valueOf(navigaProgetti.getIdComune())) );
		}else if(!"-1".equals(navigaProgetti.getIdComune())){
			criteria.add( Restrictions.eq("comune.id", Integer.valueOf(navigaProgetti.getIdComune())) );
		}

		
		//ANNO DECISIONE
		if( navigaProgetti.getIdAnnoDecisiones() != null && navigaProgetti.getIdAnnoDecisiones().size() > 0 ){
			if( ! navigaProgetti.getIdAnnoDecisiones().contains("-1") ){
				Disjunction or = Restrictions.disjunction();
				for( String tmp : navigaProgetti.getIdAnnoDecisiones() ){
					or.add(Restrictions.eq("annoDecisione.id", Integer.valueOf( tmp )) );
				}
				criteria.add(or);
			}
		}
		
		//TIPOLOGIA INTERVENTO
		if( navigaProgetti.getIdTipologiaIntervento().equals("0") ){
			criteria.add( Restrictions.ge("tipologiaIntervento.id", Integer.valueOf(navigaProgetti.getIdTipologiaIntervento())) );
		}else if(!"-1".equals(navigaProgetti.getIdTipologiaIntervento())){
			criteria.add( Restrictions.eq("tipologiaIntervento.id", Integer.valueOf(navigaProgetti.getIdTipologiaIntervento())) );
		}	
		
		//STATO INTERVENTO
		if( navigaProgetti.getIdStatoProgetto().equals("0") ){
			criteria.add( Restrictions.ge("statoProgetto.id", Integer.valueOf(navigaProgetti.getIdStatoProgetto())) );
		}else if(!"-1".equals(navigaProgetti.getIdStatoProgetto())){
			criteria.add( Restrictions.eq("statoProgetto.id", Integer.valueOf(navigaProgetti.getIdStatoProgetto())) );
		}	
		
		
		

		
//		if( navigaProgetti.getIdComune().equals("0") ){
//			criteria.add( Restrictions.ge("cupLocalizzazioni.comune.id", Integer.valueOf(navigaProgetti.getIdComune())) );
//		}else{
//			criteria.add( Restrictions.eq("cupLocalizzazioni.comune.id", Integer.valueOf(navigaProgetti.getIdComune())) );
//		}
//		
//		if( navigaProgetti.getIdProvincia().equals("0") ){
//			criteria.add( Restrictions.ge("cupLocalizzazioni.provincia.id", Integer.valueOf(navigaProgetti.getIdProvincia())) );
//		}else{
//			criteria.add( Restrictions.eq("cupLocalizzazioni.provincia.id", Integer.valueOf(navigaProgetti.getIdProvincia())) );
//		}
//		
//		if( navigaProgetti.getIdRegione().equals("0") ){
//			criteria.add( Restrictions.ge("cupLocalizzazioni.regione.id", Integer.valueOf(navigaProgetti.getIdRegione())) );
//		}else{
//			criteria.add( Restrictions.eq("cupLocalizzazioni.regione.id", Integer.valueOf(navigaProgetti.getIdRegione())) );
//		}
//		
//		if( navigaProgetti.getIdAreaGeografica().equals("0") ){
//			criteria.add( Restrictions.ge("cupLocalizzazioni.areaGeografica.id", Integer.valueOf(navigaProgetti.getIdAreaGeografica())) );
//		}else{
//			criteria.add( Restrictions.eq("cupLocalizzazioni.areaGeografica.id", Integer.valueOf(navigaProgetti.getIdAreaGeografica())) );
//		}
		
//		criteria.add( Restrictions.eq("textStato", navigaProgetti.getDescStato() ) );
		
//		if( navigaProgetti.getIdNatura().equals("0") ){
//			criteria.add( Restrictions.ge("natura.id", Integer.valueOf(navigaProgetti.getIdNatura())) );
//		}else{
//			criteria.add( Restrictions.eq("natura.id", Integer.valueOf(navigaProgetti.getIdNatura())) );
//		}

		return criteria;
	}
	
//	public List<Progetti> findElencoProgetti(NavigaProgetti filtri) {
//		
//		return this.findElencoProgetti(filtri, "id", "desc");
//		
//	}
	
	@Cacheable(cacheName = "portletCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator"))
	public int sizeElencoProgetti(NavigaProgetti filtri) {
		Criteria criteria = buildCriteria(filtri);	
		return progettiDAO.countByCriteria(criteria);
	}

	@Cacheable(cacheName = "portletCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator"))
	public List<Progetti> findElencoProgetti(NavigaProgetti filtri, String orderByCol, String orderByType, int startResult, int endResult) {
		
		Criteria criteria = buildCriteria(filtri);

		if("asc".equals(orderByType))
			criteria.addOrder(Order.asc(orderByCol));
		else
			criteria.addOrder(Order.desc(orderByCol));

		List<Progetti> retval = progettiDAO.findByCriteria(criteria, startResult, endResult );
		
//		List<Progetti> retval = new ArrayList<Progetti>(); 		
//		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		int index = 0;
//		
//		Progetti p = null;
//		
//		SoggettoTitolare st = new SoggettoTitolare();
//		st.setDescSoggettoTitolare("PARCO DELLA MUSICA - REALIZZAZIONE I LOTTO: SALA GRANDE / TEATRO LIRICO");
//		
//		CategoriaIntervento categoriaIntervento = new CategoriaIntervento();
//		categoriaIntervento.setDescCategoriaIntervento("CATEGORIA INTERVENTO");
//		
//		AnnoDecisione annoDecisione = new AnnoDecisione();
//		annoDecisione.setAnnoDadeAnnoDecisione("2013");
//		
//		Comune comune = new Comune();
//		comune.setDescComune("ROMA");
//		
//		CupLocalizzazione cupLocalizzazione = new CupLocalizzazione();
//		cupLocalizzazione.setComune(comune);
//		
//		List<CupLocalizzazione> lCupLocalizzazione = new ArrayList<CupLocalizzazione>();
//		
//		AnagraficaCup anagraficaCup = new AnagraficaCup();
//		lCupLocalizzazione.add(cupLocalizzazione);
//		anagraficaCup.setCupLocalizzazione(lCupLocalizzazione);
//		
//		
//		for(int i=0 ; i<10 ; i++){
//			p = new Progetti();
//			p.setId(index++);
//			p.setSoggettoTitolare(st);
//			p.setImpoCostoProgetto(1.1);
//			p.setImpoImportoFinanziato(2);
//			p.setCategoriaIntervento(categoriaIntervento);
//			p.setAnnoDecisione(annoDecisione);
//			p.setAnagraficaCup(anagraficaCup);
//			retval.add(p);
//	
//			p = new Progetti();
//			p.setId(index++);
//			p.setSoggettoTitolare(st);
//			p.setImpoCostoProgetto(3.3);
//			p.setImpoImportoFinanziato(4);
//			p.setCategoriaIntervento(categoriaIntervento);
//			p.setAnnoDecisione(annoDecisione);
//			p.setAnagraficaCup(anagraficaCup);
//			retval.add(p);
//	
//			p = new Progetti();
//			p.setId(index++);
//			p.setSoggettoTitolare(st);
//			p.setImpoCostoProgetto(5.5);
//			p.setImpoImportoFinanziato(6);
//			p.setCategoriaIntervento(categoriaIntervento);
//			p.setAnnoDecisione(annoDecisione);
//			p.setAnagraficaCup(anagraficaCup);
//			retval.add(p);
//		}
//		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		return retval;
	}

	@Cacheable(cacheName = "portletCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator"))
	public Progetti findProgettoById(Integer id) {
		Progetti p = progettiDAO.findById(id);
		
//		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		
//		SoggettoTitolare st = new SoggettoTitolare();
//		st.setDescSoggettoTitolare("PARCO DELLA MUSICA - REALIZZAZIONE I LOTTO: SALA GRANDE / TEATRO LIRICO");
//		
//		CategoriaIntervento categoriaIntervento = new CategoriaIntervento();
//		categoriaIntervento.setDescCategoriaIntervento("CATEGORIA INTERVENTO");
//		
//		AnnoDecisione annoDecisione = new AnnoDecisione();
//		annoDecisione.setAnnoDadeAnnoDecisione("2013");
//		
//		Comune comune = new Comune();
//		comune.setDescComune("ROMA");
//		
//		CupLocalizzazione cupLocalizzazione = new CupLocalizzazione();
//		cupLocalizzazione.setComune(comune);
//		
//		List<CupLocalizzazione> lCupLocalizzazione = new ArrayList<CupLocalizzazione>();
//		
//		AnagraficaCup anagraficaCup = new AnagraficaCup();
//		lCupLocalizzazione.add(cupLocalizzazione);
//		anagraficaCup.setCupLocalizzazione(lCupLocalizzazione);
//		
//		p = new Progetti();
//		p.setId(id);
//		p.setSoggettoTitolare(st);
//		p.setImpoCostoProgetto(1.1);
//		p.setImpoImportoFinanziato(2);
//		p.setCategoriaIntervento(categoriaIntervento);
//		p.setAnnoDecisione(annoDecisione);
//		p.setAnagraficaCup(anagraficaCup);
//		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		return p;
	}	
	
}
