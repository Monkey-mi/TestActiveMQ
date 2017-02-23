package com.hoo.mq.reqres;

import java.util.UUID;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Sender {
	
	 // 发送次数
    public static final int SEND_NUM = 5;
    // tcp 地址
    public static final String BROKER_URL = "tcp://localhost:61618";
    // 目标，在ActiveMQ管理员控制台创建 http://localhost:8161/admin/queues.jsp
    public static final String DESTINATION = "hoo.mq.queue";
    public static final String DESTINATION_RES = "hoo.mq.queue.res";
    public static void sendMessage(Session session, MessageProducer producer,Destination destination_res) throws Exception {
        for (int i = 0; i < SEND_NUM; i++) {
            String message = "发送textmessage消息第" + (i + 1) + "条";
            TextMessage text = session.createTextMessage(message); 
            text.setJMSReplyTo(destination_res);
            text.setJMSCorrelationID(UUID.randomUUID().toString());
            System.out.println(message);
            producer.send(text);
        }
    }

    public static void run() throws Exception {

        Connection connection = null;
        Session session = null;
        try {
            // 创建链接工厂
            ConnectionFactory factory = new ActiveMQConnectionFactory("admin", "topsun", BROKER_URL);
            // 通过工厂创建一个连接
            connection = factory.createConnection();
            // 启动连接
            connection.start();
            // 创建一个session会话

            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            // 创建一个消息队列
            Destination destination = session.createQueue(DESTINATION);
            // 创建消息制作者
            MessageProducer producer = session.createProducer(destination);
            
            // 创建一个消息响应队列
            Destination destination_res = session.createQueue(DESTINATION_RES);
            MessageConsumer consumer_res = session.createConsumer(destination_res);
            consumer_res.setMessageListener(new MessageListener() { 
            	private Session session;
            	@Override
            	public void onMessage(Message msg) {
                 	if (msg != null) {
                     	
                         MapMessage map = (MapMessage) msg;
                         try {
                             System.out.println(map.getLong("time") + "接收回信#" + map.getString("text"));
                             //msg.acknowledge();//接收响应
                             session.commit(); 
                         } catch (JMSException e) {
                             e.printStackTrace();
                         }
                     }
            	}
            	public MessageListener setSesseion(Session session){
            		this.session=session;
            		return this;
            	}
            }.setSesseion(session));
            // 设置持久化模式
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            sendMessage(session, producer,destination_res);
            
           
            // 提交会话
            session.commit();    
            Thread.sleep(30*1000);
        } catch (Exception e) {
            throw e;
        } finally {
            // 关闭释放资源
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public static void main(String[] args) throws Exception {
    	Sender.run();
    }
}