package it.dipe.opencup.controllers;

import it.dipe.opencup.dto.JobDTO;
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
		
		String portletId = (String) renderRequest.getAttribute(WebKeys.PORTLET_ID);
	
		String batchIndCronExp = null;
		try {
			Batch batchInd = batchFacade.ricercaBatchByNome(Constants.BATCH_INDICIZZAZIONE_NOME);
			if (batchInd == null) {
				// creo una riga con le impostazioni di default
				batchInd = new Batch();
				batchInd.setId(Constants.BATCH_INDICIZZAZIONE_PK);
				batchInd.setNome(Constants.BATCH_INDICIZZAZIONE_NOME);
				batchInd.setDescrizione(Constants.BATCH_INDICIZZAZIONE_DESC);
				batchInd.setCron(Constants.BATCH_INDICIZZAZIONE_DEFAULT_CRON);
				
				batchFacade.inserisciBatch(batchInd);

				schedulaJob(Constants.BATCH_INDICIZZAZIONE_NOME, Constants.BATCH_INDICIZZAZIONE_DESC, Constants.BATCH_INDICIZZAZIONE_DEFAULT_CRON, portletId);
			}
			batchIndCronExp = batchInd.getCron();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		model.addAttribute("cronExp", batchIndCronExp);
		
		return "ricerca-configurazione-view";
	}
	

	
	
	@ActionMapping(params = "action=schedulaIndicizzazione")
	private void avviaIndicizzazione(@RequestParam("cronExp") String cronExp, ActionRequest actionRequest, ActionResponse actionResponse) {
		
		System.out.println("schedulaIndicizzazione cronExp = " + cronExp);
		
		actionResponse.setRenderParameter("show", "schedulaIndicizzazione");
		
		String portletId = (String) actionRequest.getAttribute(WebKeys.PORTLET_ID);
		
		cancellaJob(Constants.BATCH_INDICIZZAZIONE_NOME);
		
		schedulaJob(Constants.BATCH_INDICIZZAZIONE_NOME, Constants.BATCH_INDICIZZAZIONE_DESC, cronExp, portletId);
		
		
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
				
		view.addStaticAttribute("jobInd", getJob(Constants.BATCH_INDICIZZAZIONE_NOME));
		return view;
	}
	
	
	private JobDTO getJob(String jobName) {
		
		JobDTO jobDTO = new JobDTO();
		jobDTO.setStato("ASSENTE");
		
		try {
			List<SchedulerResponse> jobs = SchedulerEngineHelperUtil.getScheduledJobs(StorageType.PERSISTED);
			for (SchedulerResponse job : jobs) {
				if (job.getJobName().equals(jobName) && job.getTrigger() instanceof CronTrigger) {
					CronTrigger trigger = (CronTrigger)job.getTrigger();
					jobDTO.setCronExp(trigger.getTriggerContent());
					jobDTO.setStato(Constants.BATCH_STATUS_SCHEDULATO);
					
					Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
						jobDTO.setProssimaEsecuzione( formatter.format( SchedulerEngineHelperUtil.getNextFireTime(jobName, jobName, StorageType.PERSISTED)) );
					} catch (IllegalArgumentException iae) {
						iae.printStackTrace();
					}
					
					jobDTO.setPrecedenteEsecuzione( SchedulerEngineHelperUtil.getPreviousFireTime(job) );
					
				}
			}
			
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
//		if (progettoIndicizzatoreMessageListener.isExecuting()) {
//			
//			jobDTO.setStato("ESECUZIONE");
//			jobDTO.setTotale("" + progettoIndicizzatoreMessageListener.getTotal());
//			jobDTO.setStep("" + progettoIndicizzatoreMessageListener.getStep());
//			
//		}
		
		return jobDTO;
		
	}
	
	private void cancellaJob(String jobName) {
		try {
			SchedulerEngineHelperUtil.delete(jobName, jobName, StorageType.PERSISTED);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	
	private void schedulaJob(String jobName, String descrizione, String cron, String portletId) {
		SchedulerEntry schedulerEntry = new SchedulerEntryImpl();  
		schedulerEntry.setDescription(descrizione);  
		schedulerEntry.setEventListenerClass(ProgettoIndicizzatoreMessageListener.class.getName());
		schedulerEntry.setTimeUnit(TimeUnit.SECOND);  
		schedulerEntry.setTriggerType(TriggerType.CRON);  
		schedulerEntry.setTriggerValue(cron);  
		
		try {  
			
			SchedulerEngineHelperUtil.schedule(schedulerEntry, StorageType.PERSISTED, portletId, 0);  
		} catch (SchedulerException e) {  

			e.printStackTrace();  
		}  
	}
	
}
