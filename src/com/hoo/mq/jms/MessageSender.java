package com.hoo.mq.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * <b>function:</b> 
 * JMS方式发送接收消息
 * 消息发送者
 */

public class MessageSender {

    // 发送次数
    public static final int SEND_NUM = 5;
    // tcp 地址
    public static final String BROKER_URL = "tcp://localhost:61618";
    // 目标，在ActiveMQ管理员控制台创建 http://localhost:8161/admin/queues.jsp
    public static final String DESTINATION = "hoo.mq.queue";

    public static void sendMessage(Session session, MessageProducer producer) throws Exception {
        for (int i = 0; i < SEND_NUM; i++) {
            String message = "发送textmessage消息第" + (i + 1) + "条";
            TextMessage text = session.createTextMessage(message);          
            System.out.println(message);
            producer.send(text);
        }
    }

    public static void run() throws Exception {

        Connection connection = null;
        Session session = null;
        try {
            // 创建链接工厂 ActiveMQConnection.DEFAULT_USER ActiveMQConnection.DEFAULT_PASSWORD
            ConnectionFactory factory = new ActiveMQConnectionFactory("admin", "topsun", BROKER_URL);
            // 通过工厂创建一个连接
            connection = factory.createConnection();
            // 启动连接
            connection.start();
            // 创建一个session会话
            /**http://blog.csdn.net/ntc10095/article/details/51073302
			createSession(paramA,paramB);
			
			paramA 取值有 : true or false 表示是否支持事务 
			paramB 取值有:Session.AUTO_ACKNOWLEDGE，Session.CLIENT_ACKNOWLEDGE，DUPS_OK_ACKNOWLEDGE，SESSION_TRANSACTED
			
			createSession(paramA,paramB); 
			paramA是设置事务的，paramB设置acknowledgment mode 
			paramA设置为false时：paramB的值可为Session.AUTO_ACKNOWLEDGE，Session.CLIENT_ACKNOWLEDGE，DUPS_OK_ACKNOWLEDGE其中一个。 
			paramA设置为true时：paramB的值忽略， acknowledgment mode被jms服务器设置为SESSION_TRANSACTED 。 
			Session.AUTO_ACKNOWLEDGE为自动确认，客户端发送和接收消息不需要做额外的工作。 
			Session.CLIENT_ACKNOWLEDGE为客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会删除消息。 
			DUPS_OK_ACKNOWLEDGE允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认。在需要考虑资源使用时，这种模式非常有效。
			*/
            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            // 创建一个消息队列
            Destination destination = session.createQueue(DESTINATION);
            // 创建消息制作者
            MessageProducer producer = session.createProducer(destination);
            // 设置持久化模式
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            sendMessage(session, producer);
            // 提交会话
            session.commit();            
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
        MessageSender.run();
    }
}