package it.dipe.opencup.facade;

import it.dipe.opencup.dao.ComuneDAO;
import it.dipe.opencup.dao.ProgettiDAO;
import it.dipe.opencup.dao.ProvinciaDAO;
import it.dipe.opencup.dao.RegioneDAO;
import it.dipe.opencup.dto.NavigaProgetti;
import it.dipe.opencup.model.AnagraficaCup;
import it.dipe.opencup.model.AnnoDecisione;
import it.dipe.opencup.model.CategoriaIntervento;
import it.dipe.opencup.model.Comune;
import it.dipe.opencup.model.CupLocalizzazione;
import it.dipe.opencup.model.Progetti;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
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
				.add(Projections.groupProperty("cpv"))
				.add(Projections.groupProperty("anagraficaCup"))
//				.add(Projections.groupProperty("textCoperFinanz"))
//				.add(Projections.groupProperty("textComune"))
//				.add(Projections.groupProperty("textProvincia"))
//				.add(Projections.groupProperty("textRegione"))
//				.add(Projections.groupProperty("textStato"))
//				.add(Projections.groupProperty("annoAnnoDecisione"))
				);
				
//		criteria.setProjection(	Projections.projectionList()
//				                
//								.add(Projections.groupProperty("someColumn"))
//				                
//				                
//				                
//				                .add(Projections.max("someColumn"))
//				                .add(Projections.min("someColumn"))
//				                .add(Projections.count("someColumn")));          
		
	
//		criteria.createAlias("natura", "natura");
		criteria.createAlias("areaIntervento", "areaIntervento");
		criteria.createAlias("sottosettoreIntervento", "sottosettoreIntervento");
		criteria.createAlias("categoriaIntervento", "categoriaIntervento");
		criteria.createAlias("tipologiaIntervento", "tipologiaIntervento");
		criteria.createAlias("soggettoTitolare", "soggettoTitolare");
		criteria.createAlias("statoProgetto", "statoProgetto");
		
//		criteria.createAlias("cupLocalizzazioni", "cupLocalizzazioni");
		
//		if( filtri.getIdAnnoAggregatos() != null && filtri.getIdAnnoAggregatos().size() > 0 ){
//			if( ! filtri.getIdAnnoAggregatos().contains("-1") ){
//				Disjunction or = Restrictions.disjunction();
//				for( String tmp : filtri.getIdAnnoAggregatos() ){
//					or.add(Restrictions.eq("annoDecisione.id", Integer.valueOf( tmp )) );
//				}
//				criteria.add(or);
//			}else{
//				criteria.add( Restrictions.ge("annoDecisione.id", -1 ) );
//			}
//		}else{
//			criteria.add( Restrictions.ge("annoDecisione.id", -1 ) );
//		}
		
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
		
		if( navigaProgetti.getIdAreaIntervento().equals("0") ){
			criteria.add( Restrictions.ge("areaIntervento.id", Integer.valueOf(navigaProgetti.getIdAreaIntervento())) );
		}else{
			criteria.add( Restrictions.eq("areaIntervento.id", Integer.valueOf(navigaProgetti.getIdAreaIntervento())) );
		}
		
		if( navigaProgetti.getIdSottosettoreIntervento().equals("0") ){
			criteria.add( Restrictions.ge("sottosettoreIntervento.id", Integer.valueOf(navigaProgetti.getIdSottosettoreIntervento())) );
		}else{
			criteria.add( Restrictions.eq("sottosettoreIntervento.id", Integer.valueOf(navigaProgetti.getIdSottosettoreIntervento())) );
		}
		
		if( navigaProgetti.getIdCategoriaIntervento().equals("0") ){
			criteria.add( Restrictions.ge("categoriaIntervento.id", Integer.valueOf(navigaProgetti.getIdCategoriaIntervento())) );
		}else{
			criteria.add( Restrictions.eq("categoriaIntervento.id", Integer.valueOf(navigaProgetti.getIdCategoriaIntervento())) );
		}
		
		if( navigaProgetti.getIdStatoProgetto().equals("0") ){
			criteria.add( Restrictions.ge("statoProgetto.id", Integer.valueOf(navigaProgetti.getIdStatoProgetto())) );
		}else{
			criteria.add( Restrictions.eq("statoProgetto.id", Integer.valueOf(navigaProgetti.getIdStatoProgetto())) );
		}	
		
		if( navigaProgetti.getIdTipologiaIntervento().equals("0") ){
			criteria.add( Restrictions.ge("tipologiaIntervento.id", Integer.valueOf(navigaProgetti.getIdTipologiaIntervento())) );
		}else{
			criteria.add( Restrictions.eq("tipologiaIntervento.id", Integer.valueOf(navigaProgetti.getIdTipologiaIntervento())) );
		}	
		
		if( navigaProgetti.getIdCategoriaSoggetto().equals("0") ){
			criteria.add( Restrictions.ge("soggettoTitolare.categoriaSoggetto.id", Integer.valueOf(navigaProgetti.getIdCategoriaSoggetto())) );
		}else{
			criteria.add( Restrictions.eq("soggettoTitolare.categoriaSoggetto.id", Integer.valueOf(navigaProgetti.getIdCategoriaSoggetto())) );
		}	

		if( navigaProgetti.getIdSottoCategoriaSoggetto().equals("0") ){
			criteria.add( Restrictions.ge("soggettoTitolare.sottocategoriaSoggetto.id", Integer.valueOf(navigaProgetti.getIdSottoCategoriaSoggetto())) );
		}else{
			criteria.add( Restrictions.eq("soggettoTitolare.sottocategoriaSoggetto.id", Integer.valueOf(navigaProgetti.getIdSottoCategoriaSoggetto())) );
		}
		
		
		return criteria;
	}
	
	public List<Progetti> findElencoProgetti(NavigaProgetti filtri) {
		
		return this.findElencoProgetti(filtri, "id", "desc");
		
	}

	@Cacheable(cacheName = "portletCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator"))
	public List<Progetti> findElencoProgetti(NavigaProgetti filtri, String orderByCol, String orderByType) {
		
		Criteria criteria = buildCriteria(filtri);

		if("asc".equals(orderByType))
			criteria.addOrder(Order.asc(orderByCol));
		else
			criteria.addOrder(Order.desc(orderByCol));
		
		List<Progetti> retval = progettiDAO.findByCriteria(criteria);
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		int index = 0;
		
		Progetti p = null;
		CategoriaIntervento categoriaIntervento = new CategoriaIntervento();
		categoriaIntervento.setDescCategoriaIntervento("CATEGORIA INTERVENTO");
		
		AnnoDecisione annoDecisione = new AnnoDecisione();
		annoDecisione.setAnnoDadeAnnoDecisione("2013");
		
		Comune comune = new Comune();
		comune.setDescComune("ROMA");
		
		CupLocalizzazione cupLocalizzazione = new CupLocalizzazione();
		cupLocalizzazione.setComune(comune);
		
		List<CupLocalizzazione> lCupLocalizzazione = new ArrayList<CupLocalizzazione>();
		
		AnagraficaCup anagraficaCup = new AnagraficaCup();
		lCupLocalizzazione.add(cupLocalizzazione);
		anagraficaCup.setCupLocalizzazione(lCupLocalizzazione);
		
		
		for(int i=0 ; i<10 ; i++){
			p = new Progetti();
			p.setId(index++);
			p.setImpoCostoProgetto(1.1);
			p.setImpoImportoFinanziato(2);
			p.setCategoriaIntervento(categoriaIntervento);
			p.setAnnoDecisione(annoDecisione);
			p.setAnagraficaCup(anagraficaCup);
			retval.add(p);
	
			p = new Progetti();
			p.setId(index++);
			p.setImpoCostoProgetto(3.3);
			p.setImpoImportoFinanziato(4);
			p.setCategoriaIntervento(categoriaIntervento);
			p.setAnnoDecisione(annoDecisione);
			p.setAnagraficaCup(anagraficaCup);
			retval.add(p);
	
			p = new Progetti();
			p.setId(index++);
			p.setImpoCostoProgetto(5.5);
			p.setImpoImportoFinanziato(6);
			p.setCategoriaIntervento(categoriaIntervento);
			p.setAnnoDecisione(annoDecisione);
			p.setAnagraficaCup(anagraficaCup);
			retval.add(p);
		}
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		return retval;
	}
	
	
}
