
package com.tgb.SpringActivemq.mq.consumer.queue;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.springframework.stereotype.Component;

import com.tgb.SpringActivemq.model.UserInfo;

/**
 * 
 * @author liang
 * @description  队列消息监听器
 * 
 */
@Component
public class QueueReceiver2 implements MessageListener {

	@Override
	public void onMessage(Message message) {
		try {
			System.out.println("QueueReceiver2接收到消息:"+((TextMessage)message).getText());
			/*ObjectMessage objmsg=(ObjectMessage) message;
			 UserInfo user=(UserInfo)objmsg.getObject();
			System.out.println("QueueReceiver1接收到消息:"+user.getName());*/
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
