package com.hoo.mq.spring.reqres;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.support.JmsUtils;
import org.springframework.jms.support.converter.MessageConverter;
/**
 * <b>function:</b> Spring JMSTemplate 消息接收者
 */
public class Receiver {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:applicationContext-*.xml");  
        JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("jmsTemplate");  
        while(true) { 
        	Message msg=jmsTemplate.receive();
        	MessageConverter mc=jmsTemplate.getMessageConverter();//默认的
            Map<String, Object> map=null;
			try {
				map = (Map<String, Object>) mc.fromMessage(msg);
				  
			} catch (JMSException e) {
				JmsUtils.convertJmsAccessException(e);
			}  
			System.out.println("收到消息：" + map.get("message"));
			//回信
			Destination destination_res=null;
			try {
				destination_res = msg.getJMSReplyTo();
			} catch (JMSException e) {
				JmsUtils.convertJmsAccessException(e);
			}
			jmsTemplate.send(destination_res,new MessageCreator() {
	        	
	            public Message createMessage(Session session) throws JMSException {
	                MapMessage message = session.createMapMessage();
	                message.setString("message", "OK，收到了"); 
	                return message;
	            }
	        });
			
        }  
    }
}