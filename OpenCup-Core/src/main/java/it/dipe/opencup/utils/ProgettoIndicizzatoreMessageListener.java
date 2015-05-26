package it.dipe.opencup.utils;

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;

public class ProgettoIndicizzatoreMessageListener implements MessageListener {
	
	private int countEsecuzizione = 0;
	
	private boolean isExecuting = false;
	
	@Override
	public void receive(Message message) throws MessageListenerException {
	
		isExecuting = true;
		System.out.println("BATCH IN ESECUZIONE: " + (++countEsecuzizione));

		
		isExecuting = false;
	}

}
