package com.hoo.mq.spring.topic;
import java.util.Map;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
/**
 * <b>function:</b> Spring JMSTemplate 消息接收者
 */
public class Receiver2 {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:applicationContext-*.xml");  
        JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("jmsTemplateTopic");  
        while(true) {  
            Map<String, Object> map =  (Map<String, Object>) jmsTemplate.receiveAndConvert();  
            System.out.println("2收到消息：" + map.get("message"));  
        }  
    }
}