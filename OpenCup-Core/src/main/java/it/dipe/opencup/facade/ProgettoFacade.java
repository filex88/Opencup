package it.dipe.opencup.facade;

import it.dipe.opencup.dao.AnagraficaCupDAO;
import it.dipe.opencup.dao.ComuneDAO;
import it.dipe.opencup.dao.CupCoperturaFinanziariaDAO;
import it.dipe.opencup.dao.NaturaDAO;
import it.dipe.opencup.dao.ProgettoDAO;
import it.dipe.opencup.dao.ProvinciaDAO;
import it.dipe.opencup.dao.RegioneDAO;
import it.dipe.opencup.dto.NavigaProgetti;
import it.dipe.opencup.dto.SizeDTO;
import it.dipe.opencup.model.AnagraficaCup;
import it.dipe.opencup.model.CupCoperturaFinanziaria;
import it.dipe.opencup.model.Progetto;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component("progettoFacade")
public class ProgettoFacade {
	
	
	@Autowired
	private ProgettoDAO progettoDAO;
	
	@Autowired
	private AnagraficaCupDAO anagraficaCupDAO;
	
	@Autowired
	private RegioneDAO regioneDAO;
	
	@Autowired
	private ProvinciaDAO provinciaDAO;
	
	@Autowired
	private ComuneDAO comuneDAO;
	
	@Autowired
	private NaturaDAO naturaDAO;
	
	
	@Autowired
	private CupCoperturaFinanziariaDAO cupCoperturaFinanziariaDAO;
	
	private Criteria buildCriteria(NavigaProgetti navigaProgetti) {
		
		Criteria criteria = progettoDAO.newCriteria();
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
//		criteria.setProjection(	Projections.projectionList()
//				.add(Projections.groupProperty("id"))
//				.add(Projections.groupProperty("annoDecisione"))
//				.add(Projections.groupProperty("soggettoTitolare"))
//				.add(Projections.groupProperty("unitaOrganizzativa"))
//				.add(Projections.groupProperty("natura"))
//				.add(Projections.groupProperty("tipologiaIntervento"))
//				.add(Projections.groupProperty("settoreIntervento"))
//				.add(Projections.groupProperty("sottosettoreIntervento"))
//				.add(Projections.groupProperty("categoriaIntervento"))
//				.add(Projections.groupProperty("strumentoProgr"))
//				.add(Projections.groupProperty("statoProgetto"))
//				.add(Projections.groupProperty("gruppoAteco"))
//				.add(Projections.groupProperty("anagraficaCup"))
//				.add(Projections.groupProperty("areaIntervento"))
//				.add(Projections.groupProperty("impoCostoProgetto"))
//				.add(Projections.groupProperty("impoImportoFinanziato"))
//				.add(Projections.groupProperty("annoAnnoDecisione"))
//				);      
		
		if( ! StringUtils.isEmpty( navigaProgetti.getCup() ) ){
			criteria.add( Restrictions.eq("anagraficaCup.codiCup", navigaProgetti.getCup() ).ignoreCase() );
		}
		if( ! StringUtils.isEmpty( navigaProgetti.getDescrizione() ) ){
			criteria.add( Restrictions.like("anagraficaCup.descCup", navigaProgetti.getDescrizione(), MatchMode.ANYWHERE ).ignoreCase() );
		}
		
		//CLASSIFICAZIONE
		criteria.createAlias("natura", "natura");
		criteria.add( Restrictions.eq("natura.id", Integer.valueOf(navigaProgetti.getIdNatura())) );
		 
		if( navigaProgetti.getIdAreaIntervento().equals("0") ){
			criteria.createAlias("areaIntervento", "areaIntervento");
			criteria.add( Restrictions.ge("areaIntervento.id", Integer.valueOf(navigaProgetti.getIdAreaIntervento())) );
		}else if(!"-1".equals(navigaProgetti.getIdAreaIntervento())){
			criteria.createAlias("areaIntervento", "areaIntervento");
			criteria.add( Restrictions.eq("areaIntervento.id", Integer.valueOf(navigaProgetti.getIdAreaIntervento())) );
		}
		
		if( navigaProgetti.getIdSottosettoreIntervento().equals("0") ){
			criteria.createAlias("sottosettoreIntervento", "sottosettoreIntervento");
			criteria.add( Restrictions.ge("sottosettoreIntervento.id", Integer.valueOf(navigaProgetti.getIdSottosettoreIntervento())) );
		}else if(!"-1".equals(navigaProgetti.getIdSottosettoreIntervento())){
			criteria.createAlias("sottosettoreIntervento", "sottosettoreIntervento");
			criteria.add( Restrictions.eq("sottosettoreIntervento.id", Integer.valueOf(navigaProgetti.getIdSottosettoreIntervento())) );
		}
		
		if( navigaProgetti.getIdCategoriaIntervento().equals("0") ){
			criteria.createAlias("categoriaIntervento", "categoriaIntervento");
			criteria.add( Restrictions.ge("categoriaIntervento.id", Integer.valueOf(navigaProgetti.getIdCategoriaIntervento())) );
		}else if(!"-1".equals(navigaProgetti.getIdCategoriaIntervento())){
			criteria.createAlias("categoriaIntervento", "categoriaIntervento");
			criteria.add( Restrictions.eq("categoriaIntervento.id", Integer.valueOf(navigaProgetti.getIdCategoriaIntervento())) );
		}
		
		//GERARCHIA SOGGETTO
		//if( ( !"-1".equals(navigaProgetti.getIdCategoriaSoggetto()) ) || (!"-1".equals(navigaProgetti.getIdSottoCategoriaSoggetto())) || (!"-1".equals(navigaProgetti.getIdAreaSoggetto())) ){
		criteria.createAlias("soggettoTitolare", "soggettoTitolare");
		criteria.createAlias("soggettoTitolare.sottocategoriaSoggetto", "sottocategoriaSoggetto");
		//}
			
		if( ! StringUtils.isEmpty( navigaProgetti.getSoggettoResponsabile() ) ){
			criteria.add( Restrictions.like("soggettoTitolare.descSoggettoTitolare", navigaProgetti.getSoggettoResponsabile(), MatchMode.ANYWHERE ).ignoreCase() );
		}
		if( ! StringUtils.isEmpty( navigaProgetti.getCfPiSoggettoResponsabile() ) ){
			criteria.add( Restrictions.eq("soggettoTitolare.codiCodfiscalePiva", navigaProgetti.getCfPiSoggettoResponsabile() ).ignoreCase() );
		}

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
	
		if( navigaProgetti.getIdAreaSoggetto().equals("0") ){
			criteria.add( Restrictions.ge("sottocategoriaSoggetto.areaSoggetto.id", Integer.valueOf(navigaProgetti.getIdAreaSoggetto())) );
		}else if(!"-1".equals(navigaProgetti.getIdAreaSoggetto())){
			criteria.add( Restrictions.eq("sottocategoriaSoggetto.areaSoggetto.id", Integer.valueOf(navigaProgetti.getIdAreaSoggetto())) );
		}

		//LOALIZZAZIONE
		criteria.createAlias("anagraficaCup", "anagraficaCup");
		criteria.createAlias("anagraficaCup.cupLocalizzazioneList", "anagraficaCup.cupLocalizzazioneList");
		
		criteria.createAlias("anagraficaCup.cupLocalizzazioneList.stato", "stato");
		criteria.add( Restrictions.eq("stato.descStato", navigaProgetti.getDescStato() ) );
		
		if( navigaProgetti.getIdAreaGeografica().equals("0") ){
			criteria.createAlias("anagraficaCup.cupLocalizzazioneList.areaGeografica", "areaGeografica");
			criteria.add( Restrictions.ge("areaGeografica.id", Integer.valueOf(navigaProgetti.getIdAreaGeografica())) );
		}else if(!"-1".equals(navigaProgetti.getIdAreaGeografica())){
			criteria.createAlias("anagraficaCup.cupLocalizzazioneList.areaGeografica", "areaGeografica");
			criteria.add( Restrictions.eq("areaGeografica.id", Integer.valueOf(navigaProgetti.getIdAreaGeografica())) );
		} 
		
		if( navigaProgetti.getIdRegione().equals("0") ){
			criteria.createAlias("anagraficaCup.cupLocalizzazioneList.regione", "regione");
			criteria.add( Restrictions.ge("regione.id", Integer.valueOf(navigaProgetti.getIdRegione())) );
		}else if(!"-1".equals(navigaProgetti.getIdRegione())){
			criteria.createAlias("anagraficaCup.cupLocalizzazioneList.regione", "regione");
			criteria.add( Restrictions.eq("regione.id", Integer.valueOf(navigaProgetti.getIdRegione())) );
		} 
		
		if( navigaProgetti.getIdProvincia().equals("0") ){
			criteria.createAlias("anagraficaCup.cupLocalizzazioneList.provincia", "provincia");
			criteria.add( Restrictions.ge("provincia.id", Integer.valueOf(navigaProgetti.getIdProvincia())) );
		}else if(!"-1".equals(navigaProgetti.getIdProvincia())){
			criteria.createAlias("anagraficaCup.cupLocalizzazioneList.provincia", "provincia");
			criteria.add( Restrictions.eq("provincia.id", Integer.valueOf(navigaProgetti.getIdProvincia())) );
		}
		
		if( navigaProgetti.getIdComune().equals("0") ){
			criteria.createAlias("anagraficaCup.cupLocalizzazioneList.comune", "comune");
			criteria.add( Restrictions.ge("comune.id", Integer.valueOf(navigaProgetti.getIdComune())) );
		}else if(!"-1".equals(navigaProgetti.getIdComune())){
			criteria.createAlias("anagraficaCup.cupLocalizzazioneList.comune", "comune");
			criteria.add( Restrictions.eq("comune.id", Integer.valueOf(navigaProgetti.getIdComune())) );
		}
		
		//ANNO DECISIONE
		if( navigaProgetti.getIdAnnoDecisiones() != null && navigaProgetti.getIdAnnoDecisiones().size() > 0 ){
			if( ! navigaProgetti.getIdAnnoDecisiones().contains("-1") ){
				criteria.createAlias("annoDecisione", "annoDecisione");
				Disjunction or = Restrictions.disjunction();
				for( String tmp : navigaProgetti.getIdAnnoDecisiones() ){
					or.add(Restrictions.eq("annoDecisione.id", Integer.valueOf( tmp )) );
				}
				criteria.add(or);
			}
		}
		
		//TIPOLOGIA INTERVENTO
		if( navigaProgetti.getIdTipologiaIntervento().equals("0") ){
			criteria.createAlias("tipologiaIntervento", "tipologiaIntervento");
			criteria.add( Restrictions.ge("tipologiaIntervento.id", Integer.valueOf(navigaProgetti.getIdTipologiaIntervento())) );
		}else if(!"-1".equals(navigaProgetti.getIdTipologiaIntervento())){
			criteria.createAlias("tipologiaIntervento", "tipologiaIntervento");
			criteria.add( Restrictions.eq("tipologiaIntervento.id", Integer.valueOf(navigaProgetti.getIdTipologiaIntervento())) );
		}	
		
		//STATO INTERVENTO
		if( navigaProgetti.getIdStatoProgetto().equals("0") ){
			criteria.createAlias("statoProgetto", "statoProgetto");
			criteria.add( Restrictions.ge("statoProgetto.id", Integer.valueOf(navigaProgetti.getIdStatoProgetto())) );
		}else if(!"-1".equals(navigaProgetti.getIdStatoProgetto())){
			criteria.createAlias("statoProgetto", "statoProgetto");
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
	
	@Cacheable(value = "SizeDTO")
	public SizeDTO sizeElencoProgetti(NavigaProgetti filtri) {
		Criteria criteria = buildCriteria(filtri);	
		return new SizeDTO(progettoDAO.countByCriteria(criteria));
	}

	@Cacheable(value = "Progetto")
	public List<Progetto> findElencoProgetti( NavigaProgetti filtri ) {
		
		String orderByCol = filtri.getOrderByCol();
		String orderByType = filtri.getOrderByType();

		Integer startResult = filtri.getStart();
		Integer endResult = filtri.getDelta();
		
		Criteria criteria = buildCriteria(filtri)
				.setFirstResult(startResult.intValue())
				.setMaxResults(endResult.intValue());

		if("asc".equals(orderByType))
			criteria.addOrder(Order.asc(orderByCol));
		else
			criteria.addOrder(Order.desc(orderByCol));
		
		List<Progetto> progetti = progettoDAO.findByCriteria(criteria);
		
		List<Progetto> retval = new ArrayList<Progetto>();
		
		for( Progetto tmp : progetti ){
			if( tmp.getAnagraficaCup().getFkDcupDcupIdMaster() != null ){
				tmp.getAnagraficaCup().setAnagraficaCup(
						anagraficaCupDAO.findById( tmp.getAnagraficaCup().getFkDcupDcupIdMaster() ) );
			}
			retval.add(tmp);
		}
		
		return retval;
	}

	@Cacheable(value = "Progetto")
	public Progetto findProgettoById(Integer id) {
		Progetto p = progettoDAO.findById(id);
		
		Criteria criteria = cupCoperturaFinanziariaDAO.newCriteria();
		criteria.createAlias("anagraficaCup", "anagraficaCup");
		criteria.add( Restrictions.eq("anagraficaCup.id", p.getAnagraficaCup().getId() ));
		
		List<CupCoperturaFinanziaria> cupCoperturaFinanziaria = cupCoperturaFinanziariaDAO.findByCriteria(criteria);
		p.getAnagraficaCup().setCupCoperturaFinanziaria(cupCoperturaFinanziaria);
		
		if( p.getAnagraficaCup().getFkDcupDcupIdMaster() != null ){
			p.getAnagraficaCup().setAnagraficaCup(
			anagraficaCupDAO.findById( p.getAnagraficaCup().getFkDcupDcupIdMaster() ) );
		}
		
		return p;
	}	
	
	
	public int countProgettiIndicizzazione() {
		
		String codiNatura = "03"; // natura lavori pubblici
		
		NavigaProgetti filtro = new NavigaProgetti();
		filtro.setIdNatura( naturaDAO.findByProperty("codiNatura", codiNatura).get(0).getId().toString() );
		
		Criteria criteria = buildCriteria(filtro);
		
		return progettoDAO.countByCriteria(criteria);
	}
	
	public List<Progetto> findProgettiIndicizzazione(int firstRecord, int pageSize) {
		
		String codiNatura = "03"; // natura lavori pubblici
		
		NavigaProgetti filtro = new NavigaProgetti();
		filtro.setIdNatura( naturaDAO.findByProperty("codiNatura", codiNatura).get(0).getId().toString() );
		
		Criteria criteria = buildCriteria(filtro);
		criteria.setFirstResult(firstRecord);
		criteria.setMaxResults(pageSize);
		
		return progettoDAO.findByCriteria(criteria);
		
	}

	public AnagraficaCup findAnagraficaCupById(Integer fkDcupDcupIdMaster) {
		return anagraficaCupDAO.findById( fkDcupDcupIdMaster );
	}
	
}
