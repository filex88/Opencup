package it.dipe.opencup.controllers;

import java.util.List;

import it.dipe.opencup.facade.ProgettoFacade;
import it.dipe.opencup.utils.ProgettoIndicizzatoreMessageListener;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.scheduler.CronTrigger;
import com.liferay.portal.kernel.scheduler.IntervalTrigger;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerType;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetLocalServiceUtil;

@Controller
@RequestMapping("VIEW")
public class RicercaConfigurazionePortletController  {
	
	private static Log logger = LogFactory.getLog(RicercaConfigurazionePortletController.class);
	
	@Autowired
	private ProgettoFacade progettoFacade;
	
	@Autowired
	private ProgettoIndicizzatoreMessageListener progettoIndicizzatoreMessageListener;
	
	@RenderMapping
	public String defaultRender(RenderRequest renderRequest, 
			RenderResponse renderResponse,
			Model model) {
		
//		DDLRecord ddlRecordConfig = null;
//		
//		try {
//			DynamicQuery dynamicQuery = DDLRecordSetLocalServiceUtil.dynamicQuery();
//			Criterion criterion = RestrictionsFactoryUtil.like("name", "%configurazione_ricerca%");
//			dynamicQuery.add(criterion).setLimit(0, 1);
//			DDLRecordSet recordSetConfig = (DDLRecordSet) DDLRecordSetLocalServiceUtil.dynamicQuery(dynamicQuery).get(0);
//			ddlRecordConfig = DDLRecordLocalServiceUtil.getRecords(recordSetConfig.getRecordSetId()).get(0);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		if (ddlRecordConfig == null) {
//			SessionErrors.add(renderRequest, "config-mancante");
//		}
		
	    
		
		
		
//		if (config == null) {
//			
//			DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDLRecord.class);
//			try {
//				
//				System.out.println("################################## 1");
//				
//				List<DDLRecord> records = DDLRecordLocalServiceUtil.dynamicQuery(dynamicQuery);
//				
//				System.out.println("################################## 2");
//				
//				boolean found = false;
//				for (DDLRecord record : records) {
//					Iterator<Field> iterator = record.getFields().iterator();
//	
//					System.out.println("################################## 3 record id: " + record.getRecordId() + ", model: " + record.getModelClassName());
//				
//					while (iterator.hasNext()) {
//						
//						Field field = iterator.next();
//						if (field.getName().equals("Identificativo") && field.getValue().equals("CONFIG")) {
//							config = record;
//							found = true;
//							break;
//						};
//						System.out.println("-- " +  field.getName() + ": " + field.getValue() );
//					}
//					
//					if (found) {
//						break;
//					}
//					
//				}
//			} catch (SystemException e) {
//				e.printStackTrace();
//			} catch (PortalException e) {
//				e.printStackTrace();
//			}
//		}
		
		
		// test

//		try {
//			Field field = config.getField("Prova");
//			field.setValue("OK");
//							
//			config.getFields().put(field);
//			config.setNew(true);
//			
//			DDLRecordLocalServiceUtil.updateDDLRecord(config);
//			
//			
//			
//			
//		} catch (PortalException e) {
//
//			e.printStackTrace();
//		} catch (SystemException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		String cronExp = null;
		try {
			List<SchedulerResponse> jobs = SchedulerEngineHelperUtil.getScheduledJobs(StorageType.PERSISTED);
			for (SchedulerResponse job : jobs) {
				if (job.getJobName().equals("it.dipe.opencup.utils.ProgettoIndicizzatoreMessageListener") && job.getTrigger() instanceof CronTrigger) {
					CronTrigger trigger = (CronTrigger)job.getTrigger();
					cronExp = trigger.getTriggerContent();
				}
			}
			
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("cronExp", cronExp);
		
		return "ricerca-configurazione-view";
	}
	

	
	
	@ActionMapping(params = "action=avviaIndicizzazione")
	private void avviaIndicizzazione(@RequestParam("cronExp") String cronExp, ActionRequest actionRequest, ActionResponse actionResponse) {
		
		System.out.println("avviaIndicizzazione cronExp = " + cronExp);
		
		actionResponse.setRenderParameter("show", "avviaIndicizzazione");
		
		String portletId = (String) actionRequest.getAttribute(WebKeys.PORTLET_ID);
		
		
		SchedulerEntry schedulerEntry = new SchedulerEntryImpl();  
		schedulerEntry.setDescription("This is a programatically created process");  
		//schedulerEntry.setEventListenerClass(ProgettoIndicizzatoreMessageListener.class.getName());
		schedulerEntry.setEventListener(progettoIndicizzatoreMessageListener);
		schedulerEntry.setTimeUnit(TimeUnit.SECOND);  
		schedulerEntry.setTriggerType(TriggerType.CRON);  
		schedulerEntry.setTriggerValue(cronExp);  
		
		try {  
			
			SchedulerEngineHelperUtil.delete("it.dipe.opencup.utils.ProgettoIndicizzatoreMessageListener", "it.dipe.opencup.utils.ProgettoIndicizzatoreMessageListener", StorageType.PERSISTED);
			
			SchedulerEngineHelperUtil.schedule(schedulerEntry, StorageType.PERSISTED, portletId, 0);  
		} catch (SchedulerException e) {  

			e.printStackTrace();  
		}  
		
	}
	
	@RenderMapping(params = "show=avviaIndicizzazione")
	public String showAvviaIndicizzazione(RenderRequest renderRequest, 
			RenderResponse renderResponse,
			Model model) {	
		
		SessionMessages.add(renderRequest, "indicizzazione-avviata");
		
		return defaultRender(renderRequest, renderResponse, model);
	}
}
