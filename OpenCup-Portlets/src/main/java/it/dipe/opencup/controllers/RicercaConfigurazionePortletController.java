package it.dipe.opencup.controllers;

import it.dipe.opencup.facade.BatchFacade;
import it.dipe.opencup.facade.ProgettoFacade;
import it.dipe.opencup.model.Batch;
import it.dipe.opencup.utils.Constants;
import it.dipe.opencup.utils.ProgettoIndicizzatoreMessageListener;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.liferay.portal.kernel.scheduler.CronTrigger;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerType;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.util.WebKeys;

@Controller
@RequestMapping("VIEW")
public class RicercaConfigurazionePortletController  {
	
	private static Log logger = LogFactory.getLog(RicercaConfigurazionePortletController.class);
	
	@Autowired
	private ProgettoFacade progettoFacade;
	
	@Autowired 
	private BatchFacade batchFacade;
	
	@PostConstruct
	void init() {
		try {
			SchedulerEngineHelperUtil.resume(Constants.BATCH_INDICIZZAZIONE_NOME, StorageType.PERSISTED);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	
	@RenderMapping
	public String defaultRender(RenderRequest renderRequest, 
			RenderResponse renderResponse,
			Model model) {
	
		Batch batch = ricercaJob(Constants.BATCH_INDICIZZAZIONE_NOME, true);
			
		model.addAttribute("cronExp", batch.getCron());
		
		return "ricerca-configurazione-view";
	}
	

	
	
	@ActionMapping(params = "action=schedulaIndicizzazione")
	private void schedulaIndicizzazione(@RequestParam("cronExp") String cronExp, ActionRequest actionRequest, ActionResponse actionResponse) {
		
		String portletId = (String) actionRequest.getAttribute(WebKeys.PORTLET_ID);
		
		cancellaJob(Constants.BATCH_INDICIZZAZIONE_NOME);
		
		try {
			boolean esito = schedulaJob(Constants.BATCH_INDICIZZAZIONE_NOME, Constants.BATCH_INDICIZZAZIONE_DESC, cronExp, portletId);
			if (esito) {
				actionResponse.setRenderParameter("show", "schedulaIndicizzazione");
			} else {
				actionResponse.setRenderParameter("show", "cancellaSchedulazione");
			}
		} catch (Exception e) {
		}
		
		
	}
	
	@RenderMapping(params = "show=schedulaIndicizzazione")
	public String showSchedulandicizzazione(RenderRequest renderRequest, 
			RenderResponse renderResponse,
			Model model) {	
		
		SessionMessages.add(renderRequest, "indicizzazione-schedulata");
		
		return defaultRender(renderRequest, renderResponse, model);
	}
	
	
	@ActionMapping(params = "action=cancellaSchedulazione")
	private void cancellaSchedulazione(ActionRequest actionRequest, ActionResponse actionResponse) {
		
		actionResponse.setRenderParameter("show", "cancellaSchedulazione");
		
		cancellaJob(Constants.BATCH_INDICIZZAZIONE_NOME);
	}
	
	@RenderMapping(params = "show=cancellaSchedulazione")
	public String showCancellaSchedulazione(RenderRequest renderRequest, 
			RenderResponse renderResponse,
			Model model) {	
		
		SessionMessages.add(renderRequest, "indicizzazione-non-schedulata");
		
		return defaultRender(renderRequest, renderResponse, model);
	}
	
	
	@ResourceMapping(value =  "loadSChedulazione")	
	protected View loadSChedulazione() {
		
		MappingJackson2JsonView view = new MappingJackson2JsonView();
				
		view.addStaticAttribute("jobInd", ricercaJob(Constants.BATCH_INDICIZZAZIONE_NOME, false));
		return view;
	}
	
	
	private Batch ricercaJob(String jobName, boolean update) {
		
		try {
			Batch batch = batchFacade.ricercaBatchByNome(jobName);
			if (batch == null) {  
				// creo una riga con le impostazioni di default
				batch = new Batch();
				batch.setNome(jobName);
				batch.setDescrizione(jobName);
				batch.setStato(Constants.BATCH_STATUS_ASSENTE);
				batchFacade.inserisciBatch(batch);
			}
			
			List<SchedulerResponse> jobs = SchedulerEngineHelperUtil.getScheduledJobs(StorageType.PERSISTED);
			for (SchedulerResponse job : jobs) {
				if (job.getJobName().equals(jobName) && job.getTrigger() instanceof CronTrigger) {
					CronTrigger trigger = (CronTrigger)job.getTrigger();
					batch.setCron(trigger.getTriggerContent());
	
					if (!Constants.BATCH_STATUS_ESECUZIONE.equals(batch.getStato())) {
						batch.setStato(Constants.BATCH_STATUS_SCHEDULATO);
					}
					
					Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					batch.setProssimaEsecuzione( formatter.format( SchedulerEngineHelperUtil.getNextFireTime(jobName, jobName, StorageType.PERSISTED)) );
					batch.setPrecedenteEsecuzione( SchedulerEngineHelperUtil.getPreviousFireTime(job) );
				}
			}
			
			batchFacade.aggiornaBatch(batch);
			
			return batch;
		} catch (Exception e) {
			return null;
		}
	}
	
	private void cancellaJob(String jobName) {
		try {
			
			Batch batch = batchFacade.ricercaBatchByNome(jobName);
			batch.setStato(Constants.BATCH_STATUS_ASSENTE);
			batchFacade.aggiornaBatch(batch);
			
			SchedulerEngineHelperUtil.delete(jobName, jobName, StorageType.PERSISTED);
			
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	
	private boolean schedulaJob(String jobName, String descrizione, String cron, String portletId) {
	
		try {		
			SchedulerEntry schedulerEntry = new SchedulerEntryImpl();  
			schedulerEntry.setDescription(descrizione);  
			schedulerEntry.setEventListenerClass(ProgettoIndicizzatoreMessageListener.class.getName());
			schedulerEntry.setTimeUnit(TimeUnit.SECOND);  
			schedulerEntry.setTriggerType(TriggerType.CRON);  
			schedulerEntry.setTriggerValue(cron);  
		
			SchedulerEngineHelperUtil.schedule(schedulerEntry, StorageType.PERSISTED, portletId, 0);
			
			Batch batch = batchFacade.ricercaBatchByNome(jobName);
			if (Constants.BATCH_STATUS_ASSENTE.equals(batch.getStato())) {
				return false;
			}
			
			return true;			
			
		} catch (SchedulerException e) {
			e.printStackTrace();
			return false;
		}

	}
}
