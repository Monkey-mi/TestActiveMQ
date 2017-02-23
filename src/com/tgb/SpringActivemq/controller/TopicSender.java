package com.tgb.SpringActivemq.controller;

import javax.jms.DeliveryMode;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * <b>function:</b> 
 * Topic主题发布和订阅消息
 */

public class TopicSender {

 
    // tcp 地址
    public static final String BROKER_URL = "tcp://localhost:61618";
    // 目标，在ActiveMQ管理员控制台创建 http://localhost:8161/admin/queues.jsp
    public static final String DESTINATION = "test.topic";  

    public static void sendMessage(TopicSession session, TopicPublisher publisher) throws Exception {
        
            String message = "Topic主题发布,发送消息,客户端发送。";            
            Message msg = session.createTextMessage(message);
            System.out.println(msg);
            publisher.send(msg);
        
    }

    

    public static void run() throws Exception {
        TopicConnection connection = null;
        TopicSession session = null;
       try {
            // 创建链接工厂
            TopicConnectionFactory factory = new ActiveMQConnectionFactory("admin", "topsun", BROKER_URL);
            // 通过工厂创建一个连接
            connection = factory.createTopicConnection();
            // 启动连接
            connection.start();
            // 创建一个session会话
            session = connection.createTopicSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            // 创建一个消息队列
            Topic topic = session.createTopic(DESTINATION);
            // 创建消息发送者
            TopicPublisher publisher = session.createPublisher(topic);
            // 设置持久化模式
            publisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            sendMessage(session, publisher);
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
        TopicSender.run();
    }

}