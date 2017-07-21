package com.ymg.framework.activemq.demo;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;


/**
 * Created by yumg on 2017/7/6.
 */
public class JMSProducer {

    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;

    //发送的数量
    private static final int SEND_NUM = 10;

    public static void main(String[] args) {
        ConnectionFactory connectionFactory;
        Connection connection = null;
        Session session;
        Destination destination;
        MessageProducer messageProducer;
        //生产地址和密码
        connectionFactory = new ActiveMQConnectionFactory("yumg","yumg10","tcp://120.26.202.235:61616");
//        connectionFactory = new ActiveMQConnectionFactory(JMSProducer.USERNAME,JMSProducer.PASSWORD,JMSProducer.BROKEURL);

        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("Hello 于美光");
            messageProducer = session.createProducer(destination);
            sendMessage(session,messageProducer);
            session.commit();

        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            if (connection != null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void sendMessage(Session session, MessageProducer messageProducer) throws JMSException {
        for (int i = 0; i < JMSProducer.SEND_NUM; i++) {
            TextMessage message  = session.createTextMessage("ActiveMQ发送消息"+i);
            System.out.println("发送消息：ActiveMQ发送消息"+i);
            messageProducer.send(message);
        }

    }


}
