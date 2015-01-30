package it.dipe.opencup.facade;

import it.dipe.opencup.dao.AggregataDAO;
import it.dipe.opencup.dao.ClassificazioneDAO;
import it.dipe.opencup.dao.LocalizzazioneDAO;
import it.dipe.opencup.dao.NaturaDAO;
import it.dipe.opencup.dao.ProgettiDAO;
import it.dipe.opencup.dto.AggregataDTO;
import it.dipe.opencup.model.Aggregata;
import it.dipe.opencup.model.Classificazione;
import it.dipe.opencup.model.Localizzazione;
import it.dipe.opencup.model.Natura;
import it.dipe.opencup.utils.Constants;
import it.dipe.opencup.utils.DMUtils;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
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
	
	@Autowired
	private DMUtils dmUtils;

	public List<Natura> findNaturaAll() {
		List<String> order = new ArrayList<String>();
		order.add("descNatura");
		return naturaDAO.findAll(order);
	}
	
	public List<Natura> findNaturaAll(int page) {
		List<String> order = new ArrayList<String>();
		order.add("descNatura");
		return naturaDAO.findAll(page, order);
	}
	
	public int countNaturaAll() {
		return naturaDAO.countAll();
	}

	public List<AggregataDTO> findAggregataByNatura() {
		List<AggregataDTO> retval = new ArrayList<AggregataDTO>();
		Localizzazione italia = findLocalizzazioneItalia();
		for( Natura tmp: findNaturaAll()  ){
			aggregatiPerNatura(retval, italia, tmp);
		}
		return retval;
	}
	
	public List<AggregataDTO> findAggregataByNatura(int page) {
		List<AggregataDTO> retval = new ArrayList<AggregataDTO>();
		Localizzazione italia = findLocalizzazioneItalia();
		for( Natura tmp: findNaturaAll(page)  ){
			aggregatiPerNatura(retval, italia, tmp);
		}
		return retval;
	}

	private Localizzazione findLocalizzazioneItalia() {
		Criteria criteria;
		criteria = localizzazioneDAO.newCriteria();
		dmUtils.criteriaBinder(criteria,  Constants.LOCALIZZAZIONE_AREA_GEOGRAFICA_ID, -1, Constants.LOCALIZZAZIONE_STATO_ID, Constants.LOCALIZZAZIONE_ID_ITALIA, Constants.LOCALIZZAZIONE_REGIONE_ID, -1, Constants.LOCALIZZAZIONE_PROVINCIA_ID, -1);
		Localizzazione italia = localizzazioneDAO.findByCriteria(criteria).get(0);
		return italia;
	}

	private void aggregatiPerNatura(List<AggregataDTO> retval, Localizzazione italia, Natura tmp) {
		AggregataDTO ele;
		List<Aggregata> lista_aggregata;
		List<Classificazione> lista_classificazione;
		Criteria criteria;
		criteria = classificazioneDAO.newCriteria();
		dmUtils.criteriaBinder(criteria, Constants.CLASSIFICAZIONE_NATURA_ID, tmp.getId(), Constants.CLASSIFICAZIONE_CATEGORIA_INTERVANTO_NATURA_ID, -1, Constants.CLASSIFICAZIONE_SETTORE_INTERVANTO_NATURA_ID, -1, Constants.CLASSIFICAZIONE_SOTTOSETTORE_INTERVANTO_NATURA_ID, -1);
		lista_classificazione = classificazioneDAO.findByCriteria(criteria);
		if( lista_classificazione.size() > 0 ){
			criteria = aggregataDAO.newCriteria();
			dmUtils.criteriaBinder( criteria, Constants.LOCALIZZAZIONE_ID, italia.getId() , Constants.CLASSIFICAZIONE_ID, lista_classificazione.get(0).getId(), Constants.ANNO_DECISIONE_ID, -1 );
			lista_aggregata = aggregataDAO.findByCriteria(criteria);
			if(lista_aggregata.size() > 0){
				ele = new AggregataDTO(lista_aggregata.get(0));
				retval.add(ele);
			}
		}
	}
	
}
