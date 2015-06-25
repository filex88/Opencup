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
import it.dipe.opencup.dto.TotaliDTO;
import it.dipe.opencup.model.AnagraficaCup;
import it.dipe.opencup.model.AnnoDecisione;
import it.dipe.opencup.model.AreaIntervento;
import it.dipe.opencup.model.CategoriaIntervento;
import it.dipe.opencup.model.CupCoperturaFinanziaria;
import it.dipe.opencup.model.GruppoAteco;
import it.dipe.opencup.model.Natura;
import it.dipe.opencup.model.Progetto;
import it.dipe.opencup.model.SettoreIntervento;
import it.dipe.opencup.model.SoggettoTitolare;
import it.dipe.opencup.model.SottosettoreIntervento;
import it.dipe.opencup.model.StatoProgetto;
import it.dipe.opencup.model.StrumentoProgr;
import it.dipe.opencup.model.TipologiaIntervento;
import it.dipe.opencup.model.UnitaOrganizzativa;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
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
		//criteria.createAlias("soggettoTitolare", "soggettoTitolare");
		//criteria.createAlias("soggettoTitolare.sottocategoriaSoggetto", "sottocategoriaSoggetto");
		//}
		
		boolean addSoggettoTitolareAlias = true;
		if(
				( ! StringUtils.isEmpty( navigaProgetti.getSoggettoResponsabile() ) )
				|| ( ! StringUtils.isEmpty( navigaProgetti.getCfPiSoggettoResponsabile() ) )
				|| ( navigaProgetti.getIdCategoriaSoggetto().equals("0") )
				|| (!"-1".equals(navigaProgetti.getIdCategoriaSoggetto()))
				|| ( navigaProgetti.getIdSottoCategoriaSoggetto().equals("0") )
				|| (!"-1".equals(navigaProgetti.getIdSottoCategoriaSoggetto()))
				){
			criteria.createAlias("soggettoTitolare", "soggettoTitolare");
			addSoggettoTitolareAlias = false;
		}
			
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
			if(addSoggettoTitolareAlias){
				criteria.createAlias("soggettoTitolare", "soggettoTitolare");
			}
			criteria.createAlias("soggettoTitolare.sottocategoriaSoggetto", "sottocategoriaSoggetto");
			criteria.add( Restrictions.ge("sottocategoriaSoggetto.areaSoggetto.id", Integer.valueOf(navigaProgetti.getIdAreaSoggetto())) );
		}else if(!"-1".equals(navigaProgetti.getIdAreaSoggetto())){
			if(addSoggettoTitolareAlias){
				criteria.createAlias("soggettoTitolare", "soggettoTitolare");
			}
			criteria.createAlias("soggettoTitolare.sottocategoriaSoggetto", "sottocategoriaSoggetto");
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
	
	@SuppressWarnings("unchecked")
	public TotaliDTO sommaImpElencoProgetti(NavigaProgetti filtri) {
	
		Criteria criteria = buildCriteria(filtri);	
		criteria.setProjection(Projections.distinct(Projections.property("id")));
		List<Integer> idPj = criteria.list();
		
		Criteria criteriaP = progettoDAO.newCriteria();
		Criteria criteriaF = progettoDAO.newCriteria();
		criteriaP.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteriaF.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		Disjunction or = Restrictions.disjunction();
		for( Integer tmp : idPj ){
			or.add(Restrictions.eq("id", tmp ));
		}
		criteriaP.add(or);
		criteriaF.add(or);
		
		criteriaP.setProjection(Projections.sum("impoCostoProgetto"));
		Double impoCostoProgetto = (Double) criteriaP.uniqueResult();

		criteriaF.setProjection(Projections.sum("impoImportoFinanziato"));
		Double impoImportoFinanziato = (Double) criteriaF.uniqueResult();

		TotaliDTO retval = new TotaliDTO();
		retval.setImpoImportoFinanziato(impoImportoFinanziato);
		retval.setImpoCostoProgetto(impoCostoProgetto);
		return retval;
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
			simulaGet4ListaProgetti(tmp);
			if( tmp.getAnagraficaCup().getFkDcupDcupIdMaster() != null ){
				tmp.getAnagraficaCup().setAnagraficaCup(
						anagraficaCupDAO.findById( tmp.getAnagraficaCup().getFkDcupDcupIdMaster() ) );
			}
			retval.add(tmp);
		}
		
		return retval;
	}

	private void simulaGet4ListaProgetti(Progetto progetto) {
		AnagraficaCup ac = progetto.getAnagraficaCup();
		System.out.println(ac);
		AnnoDecisione ad = progetto.getAnnoDecisione();
		System.out.println(ad);
		CategoriaIntervento ci = progetto.getCategoriaIntervento();
		System.out.println(ci);
		String cp = progetto.getComuniProgetto();
		System.out.println(cp);
	}

	@Cacheable(value = "Progetto")
	public Progetto findProgettoById(Integer id) {
		
		Progetto p = progettoDAO.findById(id);
		similaGet4Dettaglio(p);
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
	
	private void similaGet4Dettaglio(Progetto progetto) {
		AnnoDecisione ad = progetto.getAnnoDecisione();
		System.out.println(ad);
		SoggettoTitolare st = progetto.getSoggettoTitolare();
		System.out.println(st);
		UnitaOrganizzativa uo = progetto.getUnitaOrganizzativa();
		System.out.println(uo);
		Natura n = progetto.getNatura();
		System.out.println(n);
		TipologiaIntervento ti = progetto.getTipologiaIntervento();
		System.out.println(ti);
		SettoreIntervento si = progetto.getSettoreIntervento();
		System.out.println(si);
		SottosettoreIntervento ssi = progetto.getSottosettoreIntervento();
		System.out.println(ssi);
		CategoriaIntervento ci = progetto.getCategoriaIntervento();
		System.out.println(ci);
		StrumentoProgr sp = progetto.getStrumentoProgr();
		System.out.println(sp);
		StatoProgetto stp = progetto.getStatoProgetto();
		System.out.println(stp);
		GruppoAteco ga = progetto.getGruppoAteco();
		System.out.println(ga);
		AnagraficaCup ac = progetto.getAnagraficaCup();
		System.out.println(ac);
		String cp = progetto.getComuniProgetto();
		System.out.println(cp);
		String pp = progetto.getProvinceProgetto();
		System.out.println(pp);
		String rp = progetto.getRegioneProgetto();
		System.out.println(rp);
		String ag = progetto.getAreaGeografica();
		System.out.println(ag);
		AreaIntervento ai = progetto.getAreaIntervento();
		System.out.println(ai);
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
