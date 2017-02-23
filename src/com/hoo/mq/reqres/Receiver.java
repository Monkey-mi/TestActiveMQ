package com.hoo.mq.reqres;

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

public class Receiver {
	// tcp 地址
    public static final String BROKER_URL = "tcp://localhost:61618";
    // 目标，在ActiveMQ管理员控制台创建 http://localhost:8161/admin/queues.jsp
    public static final String DESTINATION = "hoo.mq.queue";
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
            MessageConsumer consumer = session.createConsumer(destination);
            
            //消息
            consumer.setMessageListener(new MessageListenerImpl(session));

            // 提交会话
            session.commit();
            Thread.sleep(10*1000);
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
    	Receiver.run();
    }
    
}
class MessageListenerImpl implements MessageListener{
	private Session session;
	public MessageListenerImpl(){
		super();
	}
	public MessageListenerImpl(Session session){
		super();
		this.session=session;
	}
	@Override
	public void onMessage(Message msg) {
		if (msg != null) {
        	TextMessage textmsg = (TextMessage) msg;
        	
			try {
				//接收信息
				System.out.println("Receiver:"+textmsg.getText());
				//msg.acknowledge();//接收响应
				//回信
				MapMessage map = session.createMapMessage();
				map.setString("text", "返回信息：'我是接受者，收到   "+textmsg.getText()+"'");
                map.setLong("time", System.currentTimeMillis());
                
                map.setJMSCorrelationID(msg.getJMSCorrelationID());
                //返回消息队列
                Destination destination_res = msg.getJMSReplyTo();
                // 创建消息制作者
                MessageProducer producer = session.createProducer(destination_res);
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
                producer.send(destination_res, map);
                session.commit();
			} catch (JMSException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
	}
	
}