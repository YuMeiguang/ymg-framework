package com.ymg.framework.activemq.practice.consumer.topic;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by yumg on 2017/7/13.
 */
public class TopicReceiver2 implements MessageListener{

    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            System.out.println("TopicReceiver1接收到的消息："+textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
