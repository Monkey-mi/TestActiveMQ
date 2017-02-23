package com.hoo.mq.spring.reqres;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
/**
 * <b>function:</b> Spring JMSTemplate 消息发送者
 */
public class Sender {
    public static void main(String[] args) {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:applicationContext-*.xml");
        JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("jmsTemplate");
        Destination destination_res=(Destination)ctx.getBean("destination_res");
        jmsTemplate.send(new MessageCreator() {
        	private Destination destination_res;
        	
            public Message createMessage(Session session) throws JMSException {
                MapMessage message = session.createMapMessage();
                message.setString("message", "current system time: " + new Date().getTime());
                message.setJMSReplyTo(destination_res);
                message.setJMSCorrelationID(UUID.randomUUID().toString());
                return message;
            }
            public MessageCreator setDestinationRes(Destination destination_res){
            	this.destination_res=destination_res;
            	return this;
            }
        }.setDestinationRes(destination_res));
        //TODO:写回信消费
        @SuppressWarnings("unchecked")
		Map<String, Object> map=(Map<String, Object>) jmsTemplate.receiveAndConvert(destination_res);
        System.out.println("收到回信：" + map.get("message"));
        try {
			Thread.sleep(10*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//等待10秒，为了收到回信
    }
}