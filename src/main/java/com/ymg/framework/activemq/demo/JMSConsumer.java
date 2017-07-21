package com.ymg.framework.activemq.demo;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by yumg on 2017/7/6.
 */
public class JMSConsumer {

    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;


    public static void main(String[] args) {
        ConnectionFactory connectionFactory;
        Connection connection = null;
        Session session;
        Destination destination;
        MessageConsumer messageConsumer;

        connectionFactory = new ActiveMQConnectionFactory("yumg","yumg10","tcp://120.26.202.235:61616");
//        connectionFactory = new ActiveMQConnectionFactory(JMSConsumer.USERNAME,JMSConsumer.PASSWORD,JMSConsumer.BROKEURL);

        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("Hello 于美光");

            messageConsumer = session.createConsumer(destination);

            while (true){
                TextMessage textMessage = (TextMessage) messageConsumer.receive();
                if (textMessage != null){
                    System.out.println("收到的消息是："+textMessage.getText());
                }else{
                    break;
                }
            }

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
