package it.dipe.opencup.facade;

import it.dipe.opencup.dao.ComuneDAO;
import it.dipe.opencup.dao.ProgettiDAO;
import it.dipe.opencup.dao.ProvinciaDAO;
import it.dipe.opencup.dao.RegioneDAO;
import it.dipe.opencup.dto.NavigaAggregata;
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
	
	private Criteria bildCriteria(NavigaAggregata filtri) {
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
				.add(Projections.groupProperty("textCoperFinanz"))
				.add(Projections.groupProperty("textComune"))
				.add(Projections.groupProperty("textProvincia"))
				.add(Projections.groupProperty("textRegione"))
				.add(Projections.groupProperty("textStato"))
				.add(Projections.groupProperty("annoAnnoDecisione"))
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
		
	
		criteria.createAlias("natura", "natura");
		criteria.createAlias("settoreIntervento", "settoreIntervento");
		criteria.createAlias("sottosettoreIntervento", "sottosettoreIntervento");
		criteria.createAlias("categoriaIntervento", "categoriaIntervento");
		criteria.createAlias("tipologiaIntervento", "tipologiaIntervento");
		criteria.createAlias("soggettoTitolare", "soggettoTitolare");
		criteria.createAlias("statoProgetto", "statoProgetto");
		
		
		
		criteria.createAlias("cupLocalizzazioni", "cupLocalizzazioni");
		
		if( filtri.getIdAnnoDecisiones() != null && filtri.getIdAnnoDecisiones().size() > 0 ){
			if( ! filtri.getIdAnnoDecisiones().contains("-1") ){
				Disjunction or = Restrictions.disjunction();
				for( String tmp : filtri.getIdAnnoDecisiones() ){
					or.add(Restrictions.eq("annoDecisione.id", Integer.valueOf( tmp )) );
				}
				criteria.add(or);
			}else{
				criteria.add( Restrictions.ge("annoDecisione.id", -1 ) );
			}
		}else{
			criteria.add( Restrictions.ge("annoDecisione.id", -1 ) );
		}
		
		if( filtri.getIdComune().equals("0") ){
			criteria.add( Restrictions.ge("cupLocalizzazioni.comune.id", Integer.valueOf(filtri.getIdComune())) );
		}else{
			criteria.add( Restrictions.eq("cupLocalizzazioni.comune.id", Integer.valueOf(filtri.getIdComune())) );
		}
		
		if( filtri.getIdProvincia().equals("0") ){
			criteria.add( Restrictions.ge("cupLocalizzazioni.provincia.id", Integer.valueOf(filtri.getIdProvincia())) );
		}else{
			criteria.add( Restrictions.eq("cupLocalizzazioni.provincia.id", Integer.valueOf(filtri.getIdProvincia())) );
		}
		
		if( filtri.getIdRegione().equals("0") ){
			criteria.add( Restrictions.ge("cupLocalizzazioni.regione.id", Integer.valueOf(filtri.getIdRegione())) );
		}else{
			criteria.add( Restrictions.eq("cupLocalizzazioni.regione.id", Integer.valueOf(filtri.getIdRegione())) );
		}
		
		if( filtri.getIdAreaGeografica().equals("0") ){
			criteria.add( Restrictions.ge("cupLocalizzazioni.areaGeografica.id", Integer.valueOf(filtri.getIdAreaGeografica())) );
		}else{
			criteria.add( Restrictions.eq("cupLocalizzazioni.areaGeografica.id", Integer.valueOf(filtri.getIdAreaGeografica())) );
		}
		
		criteria.add( Restrictions.eq("textStato", filtri.getDescStato() ) );
		
		if( filtri.getIdNatura().equals("0") ){
			criteria.add( Restrictions.ge("natura.id", Integer.valueOf(filtri.getIdNatura())) );
		}else{
			criteria.add( Restrictions.eq("natura.id", Integer.valueOf(filtri.getIdNatura())) );
		}
		
		if( filtri.getIdSettoreIntervento().equals("0") ){
			criteria.add( Restrictions.ge("settoreIntervento.id", Integer.valueOf(filtri.getIdSettoreIntervento())) );
		}else{
			criteria.add( Restrictions.eq("settoreIntervento.id", Integer.valueOf(filtri.getIdSettoreIntervento())) );
		}
		
		if( filtri.getIdSottosettoreIntervento().equals("0") ){
			criteria.add( Restrictions.ge("sottosettoreIntervento.id", Integer.valueOf(filtri.getIdSottosettoreIntervento())) );
		}else{
			criteria.add( Restrictions.eq("sottosettoreIntervento.id", Integer.valueOf(filtri.getIdSottosettoreIntervento())) );
		}
		
		if( filtri.getIdCategoriaIntervento().equals("0") ){
			criteria.add( Restrictions.ge("categoriaIntervento.id", Integer.valueOf(filtri.getIdCategoriaIntervento())) );
		}else{
			criteria.add( Restrictions.eq("categoriaIntervento.id", Integer.valueOf(filtri.getIdCategoriaIntervento())) );
		}
		
		if( filtri.getIdStatoProgetto().equals("0") ){
			criteria.add( Restrictions.ge("statoProgetto.id", Integer.valueOf(filtri.getIdStatoProgetto())) );
		}else{
			criteria.add( Restrictions.eq("statoProgetto.id", Integer.valueOf(filtri.getIdStatoProgetto())) );
		}	
		
		if( filtri.getIdTipologiaInterventi().equals("0") ){
			criteria.add( Restrictions.ge("tipologiaIntervento.id", Integer.valueOf(filtri.getIdTipologiaInterventi())) );
		}else{
			criteria.add( Restrictions.eq("tipologiaIntervento.id", Integer.valueOf(filtri.getIdTipologiaInterventi())) );
		}	
		
		if( filtri.getIdCategoriaSoggetto().equals("0") ){
			criteria.add( Restrictions.ge("soggettoTitolare.categoriaSoggetto.id", Integer.valueOf(filtri.getIdCategoriaSoggetto())) );
		}else{
			criteria.add( Restrictions.eq("soggettoTitolare.categoriaSoggetto.id", Integer.valueOf(filtri.getIdCategoriaSoggetto())) );
		}	

		if( filtri.getIdSottoCategoriaSoggetto().equals("0") ){
			criteria.add( Restrictions.ge("soggettoTitolare.sottocategoriaSoggetto.id", Integer.valueOf(filtri.getIdSottoCategoriaSoggetto())) );
		}else{
			criteria.add( Restrictions.eq("soggettoTitolare.sottocategoriaSoggetto.id", Integer.valueOf(filtri.getIdSottoCategoriaSoggetto())) );
		}
		
		
		return criteria;
	}
	
	@Cacheable(cacheName = "portletCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator"))
	public List<Progetti> findElencoProgetti(NavigaAggregata filtri) {
		
		Criteria criteria = bildCriteria(filtri);
		
		criteria.addOrder(Order.desc("id"));
		
		List<Progetti> retval = progettiDAO.findByCriteria(criteria);
		
		return retval;
	}
	
	
}
