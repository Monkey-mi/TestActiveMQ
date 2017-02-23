
package com.tgb.SpringActivemq.mq.consumer.queue;

import javax.annotation.Resource;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.springframework.stereotype.Component;

import com.tgb.SpringActivemq.model.UserInfo;
import com.tgb.SpringActivemq.mq.producer.queue.QueueSender;


/**
 * 
 * @author liang
 * @description  队列消息监听器
 * 
 */
@Component
public class QueueReceiver1 implements MessageListener {
	@Resource 
	QueueSender queueSender;
	@Override
	public void onMessage(Message message) {
		try {
			System.out.println("QueueReceiver1接收到消息:"+((TextMessage)message).getText()+"ID:"+message.getJMSCorrelationID());
			/*ObjectMessage objmsg=(ObjectMessage) message;
			 UserInfo user=(UserInfo)objmsg.getObject();
			System.out.println("QueueReceiver1接收到消息:"+user.getName());*/
			
            //返回消息队列
            Destination destination_res = message.getJMSReplyTo();
            queueSender.sendResponse(destination_res, "回信ID："+message.getJMSCorrelationID());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
