package com.mahendra.app;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


/**
 * Message-Driven Bean implementation class for: MessageReader
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "orderQueue"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue")
		}, 
		mappedName = "orderQueue")
public class MessageReader implements MessageListener {

    /**
     * Default constructor. 
     */
    public MessageReader() {
        // TODO Auto-generated constructor stub
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
    	System.out.println("Incoming message : ");
    	if(message instanceof TextMessage) {
    		TextMessage txt = (TextMessage) message;
    		try {
				System.out.println(txt.getText());
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }

}
