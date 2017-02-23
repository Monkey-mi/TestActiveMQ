package com.tgb.SpringActivemq.mq.consumer.queue;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.stereotype.Component;
@Component
public class QueueReceiver1Res implements MessageListener {
	
	@Override
	public void onMessage(Message message) {
		try {
			System.out.println("QueueReceiver1Res接收到消息:"+((TextMessage)message).getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
