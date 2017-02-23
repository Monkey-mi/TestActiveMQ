package com.tgb.SpringActivemq.mq.producer.queue;

import java.util.UUID;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import com.tgb.SpringActivemq.model.UserInfo;

/**
 * 
 * @author liang
 * @description  队列消息生产者，发送消息到队列
 * 
 */
@Component("queueSender")
public class QueueSender {
	
	@Autowired
	@Qualifier("jmsQueueTemplate")
	private JmsTemplate jmsTemplate;//通过@Qualifier修饰符来注入对应的bean
	
	/**
	 * 发送一条消息到指定的队列（目标）
	 * @param queueName 队列名称
	 * @param message 消息内容
	 */
	public void send(String queueName,final String message){
			jmsTemplate.send(queueName, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				Message msg=session.createTextMessage(message);
				// 创建一个消息响应队列
	            Destination destination_res = session.createQueue("test.queue.response");
				msg.setJMSReplyTo(destination_res);
				msg.setJMSCorrelationID(UUID.randomUUID().toString());
				return msg;
			}
		});
	}
	/**
	 * 发送一条消息到指定的队列（目标）
	 * @param queueName 队列名称
	 * @param message 消息内容
	 */
	public void sendResponse(Destination destination_res,final String message){
			jmsTemplate.send(destination_res, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				Message msg=session.createTextMessage(message);
				return msg;
			}
		});
	}
	/**
	 * 发送一条消息到指定的队列（目标）
	 * @param queueName 队列名称
	 * @param message 消息内容
	 */
	public void send(String queueName,final UserInfo user){
			jmsTemplate.send(queueName, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage(user);
			}
		});
	}
}
