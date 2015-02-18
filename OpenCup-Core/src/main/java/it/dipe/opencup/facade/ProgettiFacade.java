package it.dipe.opencup.facade;

import it.dipe.opencup.dao.ComuneDAO;
import it.dipe.opencup.dao.ProgettiDAO;
import it.dipe.opencup.dao.ProvinciaDAO;
import it.dipe.opencup.dao.RegioneDAO;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.model.Comune;
import it.dipe.opencup.model.Progetti;
import it.dipe.opencup.model.Provincia;
import it.dipe.opencup.model.Regione;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
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
	
	private Regione findRegioneById(Integer id){
		return regioneDAO.findById(id);
	}
	
	private Provincia findProvinciaById(Integer id){
		return provinciaDAO.findById(id);
	}
	
	private Comune findComuneById(Integer id){
		return comuneDAO.findById(id);
	}
	
	private Criteria bildCriteria(NavigaAggregata filtri) {
		Criteria criteria = progettiDAO.newCriteria();
		
		criteria.createAlias("natura", "natura");
		criteria.createAlias("settoreIntervento", "settoreIntervento");
		criteria.createAlias("sottosettoreIntervento", "sottosettoreIntervento");
		criteria.createAlias("categoriaIntervento", "categoriaIntervento");
		
		criteria.createAlias("annoDecisione", "annoDecisione");
		
		if( filtri.getIdAnnoDecisione().equals("0") ){
			criteria.add( Restrictions.ge("annoDecisione.id", Integer.valueOf(filtri.getIdAnnoDecisione())) );
		}else{
			criteria.add( Restrictions.eq("annoDecisione.id", Integer.valueOf(filtri.getIdAnnoDecisione())) );
		}
		
		if( ! filtri.getIdRegione().equals("0") ){
			Regione regione = findRegioneById( Integer.valueOf( filtri.getIdRegione() ) );
			criteria.add( Restrictions.like("textRegione", regione.getDescRegione() ) );
		}
		
		if( ! filtri.getIdProvincia().equals("0") ){
			Provincia provincia = findProvinciaById( Integer.valueOf( filtri.getIdProvincia() ) );
			criteria.add( Restrictions.like("textProvincia", provincia.getDescProvincia() ) );
		}
		
		if( ! filtri.getIdComune().equals("0") ){
			Comune comune = findComuneById( Integer.valueOf( filtri.getIdComune() ) );
			criteria.add( Restrictions.like("textComune", comune.getDescComune() ) );
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
