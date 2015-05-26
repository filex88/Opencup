package it.dipe.opencup.utils;

import it.dipe.opencup.facade.BatchFacade;
import it.dipe.opencup.facade.ProgettoFacade;

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;

public class ProgettoIndicizzatoreMessageListener implements MessageListener {
	
	@Override
	public void receive(Message message) throws MessageListenerException {
	
		
		ProgettoFacade progettoFacade = (ProgettoFacade)SpringContextUtil.getApplicationContext().getBean("progettoFacade");
		BatchFacade batchFacade = (BatchFacade)SpringContextUtil.getApplicationContext().getBean("batchFacade");
		
		
		System.out.println("BATCH IN ESECUZIONE: id:" + this.toString());
		
		int total = progettoFacade.countProgettiIndicizzazione();
		System.out.println("----- nr. rec. :" + total);
		
		
		System.out.println("...FINE BATCH");
	}

}
