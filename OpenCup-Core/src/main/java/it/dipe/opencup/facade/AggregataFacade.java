package it.dipe.opencup.facade;

import it.dipe.opencup.dao.AggregataDAO;
import it.dipe.opencup.dao.ClassificazioneDAO;
import it.dipe.opencup.dao.LocalizzazioneDAO;
import it.dipe.opencup.dao.NaturaDAO;
import it.dipe.opencup.dao.ProgettiDAO;
import it.dipe.opencup.dto.AggregataDTO;
import it.dipe.opencup.dto.NavigaAggregata;
import it.dipe.opencup.model.Aggregata;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("aggregataFacade")
public class AggregataFacade {
	
	@Autowired
	private ProgettiDAO progettiDAO;
	
	@Autowired
	private AggregataDAO aggregataDAO;
	
	@Autowired
	private NaturaDAO naturaDAO;
	
	@Autowired
	private ClassificazioneDAO classificazioneDAO;
	
	@Autowired
	private LocalizzazioneDAO localizzazioneDAO;
	
	private Criteria bildCriteriaByNatura(NavigaAggregata navigaAggregata) {
		Criteria criteria = aggregataDAO.newCriteria();
		
		criteria.createAlias("classificazione", "classificazione");
		criteria.createAlias("localizzazione", "localizzazione");
		criteria.createAlias("annoDecisione", "annoDecisione");
		criteria.createAlias("localizzazione.stato", "stato");
		
		if( navigaAggregata.getIdAnnoDecisione().equals("0") ){
			criteria.add( Restrictions.ge("annoDecisione.id", Integer.valueOf(navigaAggregata.getIdAnnoDecisione())) );
		}else{
			criteria.add( Restrictions.eq("annoDecisione.id", Integer.valueOf(navigaAggregata.getIdAnnoDecisione())) );
		}
		
		
		
		if( navigaAggregata.getIdProvincia().equals("0") ){
			criteria.add( Restrictions.ge("localizzazione.provincia.id", Integer.valueOf(navigaAggregata.getIdProvincia())) );
		}else{
			criteria.add( Restrictions.eq("localizzazione.provincia.id", Integer.valueOf(navigaAggregata.getIdProvincia())) );
		}
		
		if( navigaAggregata.getIdRegione().equals("0") ){
			criteria.add( Restrictions.ge("localizzazione.regione.id", Integer.valueOf(navigaAggregata.getIdRegione())) );
		}else{
			criteria.add( Restrictions.eq("localizzazione.regione.id", Integer.valueOf(navigaAggregata.getIdRegione())) );
		}
		
		if( navigaAggregata.getIdAreaGeografica().equals("0") ){
			criteria.add( Restrictions.ge("localizzazione.areaGeografica.id", Integer.valueOf(navigaAggregata.getIdAreaGeografica())) );
		}else{
			criteria.add( Restrictions.eq("localizzazione.areaGeografica.id", Integer.valueOf(navigaAggregata.getIdAreaGeografica())) );
		}

		criteria.add( Restrictions.eq("stato.descStato", navigaAggregata.getDescStato() ) );
			
		
		
		if( navigaAggregata.getIdNatura().equals("0") ){
			criteria.add( Restrictions.ge("classificazione.natura.id", Integer.valueOf(navigaAggregata.getIdNatura())) );
		}else{
			criteria.add( Restrictions.eq("classificazione.natura.id", Integer.valueOf(navigaAggregata.getIdNatura())) );
		}
		
		if( navigaAggregata.getIdSettoreInternvanto().equals("0") ){
			criteria.add( Restrictions.ge("classificazione.settoreInternvanto.id", Integer.valueOf(navigaAggregata.getIdSettoreInternvanto())) );
		}else{
			criteria.add( Restrictions.eq("classificazione.settoreInternvanto.id", Integer.valueOf(navigaAggregata.getIdSettoreInternvanto())) );
		}
		
//		System.out.println("sottosettoreIntervento = " + sottosettoreIntervento);
		
		if( navigaAggregata.getIdSottosettoreIntervento().equals("0") ){
			criteria.add( Restrictions.ge("classificazione.sottosettoreIntervento.id", Integer.valueOf(navigaAggregata.getIdSottosettoreIntervento())) );
		}else{
			criteria.add( Restrictions.eq("classificazione.sottosettoreIntervento.id", Integer.valueOf(navigaAggregata.getIdSottosettoreIntervento())) );
		}
		
//		System.out.println("categoriaIntervento = " + categoriaIntervento);
		
		if( navigaAggregata.getIdCategoriaIntervento().equals("0") ){
			criteria.add( Restrictions.ge("classificazione.categoriaIntervento.id", Integer.valueOf(navigaAggregata.getIdCategoriaIntervento())) );
		}else{
			criteria.add( Restrictions.eq("classificazione.categoriaIntervento.id", Integer.valueOf(navigaAggregata.getIdCategoriaIntervento())) );
		}	
		
		return criteria;
	}
	
	public List<AggregataDTO> findAggregataByNatura(NavigaAggregata navigaAggregata) {
		List<AggregataDTO> retval = new ArrayList<AggregataDTO>();
		List<Aggregata> aggregata = aggregataDAO.findByCriteria(bildCriteriaByNatura(navigaAggregata));
		AggregataDTO ele = null;
		for( Aggregata tmp: aggregata ){
			ele = new AggregataDTO(tmp);
			retval.add(ele);
		}
		return retval;
	}
	
	public int countAggregataByNatura(NavigaAggregata navigaAggregata) { 
		return aggregataDAO.countByCriteria(bildCriteriaByNatura(navigaAggregata));
	}
	
	
	public List<AggregataDTO> findAggregataByNatura(NavigaAggregata navigaAggregata, int page, String orderByCol, String orderByType) {
		List<AggregataDTO> retval = new ArrayList<AggregataDTO>();
		Criteria criteriaByNatura = bildCriteriaByNatura(navigaAggregata);
		if("asc".equals(orderByType))
			criteriaByNatura.addOrder(Order.asc(orderByCol));
		else
			criteriaByNatura.addOrder(Order.desc(orderByCol));

		List<Aggregata> aggregata = aggregataDAO.findByCriteria(criteriaByNatura, page);
		
		AggregataDTO ele = null;
		for( Aggregata tmp: aggregata ){
			ele = new AggregataDTO(tmp);
			retval.add(ele);
		}
		return retval;
	}
	
	
	

//	public List<Natura> findNaturaAll() {
//		List<String> order = new ArrayList<String>();
//		order.add("descNatura");
//		return naturaDAO.findAll(order);
//	}
//	
//	public List<Natura> findNaturaAll(int page) {
//		List<String> order = new ArrayList<String>();
//		order.add("descNatura");
//		return naturaDAO.findAll(page, order);
//	}
//	
//	public int countNaturaAll() {
//		return naturaDAO.countAll();
//	}

//	public List<AggregataDTO> findAggregataByNatura() {
//		List<AggregataDTO> retval = new ArrayList<AggregataDTO>();
//		Localizzazione italia = findLocalizzazioneItalia();
//		for( Natura tmp: findNaturaAll()  ){
//			aggregatiPerNatura(retval, italia, tmp);
//		}
//		return retval;
//	}
	
//	public List<AggregataDTO> findAggregataByNatura(int page, String orderByCol, String orderByType) {
//		List<AggregataDTO> retval = new ArrayList<AggregataDTO>();
//		Localizzazione italia = findLocalizzazioneItalia();
//		for( Natura tmp: findNaturaAll(page)  ){
//			aggregatiPerNatura(retval, italia, tmp);
//		}
//		return retval;
//	}

//	private Localizzazione findLocalizzazioneItalia() {
//		Criteria criteria;
//		criteria = localizzazioneDAO.newCriteria();
//		dmUtils.criteriaBinder(criteria,  Constants.LOCALIZZAZIONE_AREA_GEOGRAFICA_ID, -1, Constants.LOCALIZZAZIONE_STATO_ID, Constants.LOCALIZZAZIONE_ID_ITALIA, Constants.LOCALIZZAZIONE_REGIONE_ID, -1, Constants.LOCALIZZAZIONE_PROVINCIA_ID, -1);
//		Localizzazione italia = localizzazioneDAO.findByCriteria(criteria).get(0);
//		return italia;
//	}
//
//	private void aggregatiPerNatura(List<AggregataDTO> retval, Localizzazione italia, Natura tmp) {
//		AggregataDTO ele;
//		List<Aggregata> lista_aggregata;
//		List<Classificazione> lista_classificazione;
//		Criteria criteria;
//		criteria = classificazioneDAO.newCriteria();
//		dmUtils.criteriaBinder(criteria, Constants.CLASSIFICAZIONE_NATURA_ID, tmp.getId(), Constants.CLASSIFICAZIONE_CATEGORIA_INTERVANTO_NATURA_ID, -1, Constants.CLASSIFICAZIONE_SETTORE_INTERVANTO_NATURA_ID, -1, Constants.CLASSIFICAZIONE_SOTTOSETTORE_INTERVANTO_NATURA_ID, -1);
//		lista_classificazione = classificazioneDAO.findByCriteria(criteria);
//		
//		if( lista_classificazione.size() > 0 ){
//			criteria = aggregataDAO.newCriteria();
//			dmUtils.criteriaBinder( criteria, Constants.LOCALIZZAZIONE_ID, italia.getId() , Constants.CLASSIFICAZIONE_ID, lista_classificazione.get(0).getId(), Constants.ANNO_DECISIONE_ID, -1 );
//			lista_aggregata = aggregataDAO.findByCriteria(criteria);
//			if(lista_aggregata.size() > 0){
//				ele = new AggregataDTO(lista_aggregata.get(0));
//				retval.add(ele);
//			}
//		}
//	}
	
}
