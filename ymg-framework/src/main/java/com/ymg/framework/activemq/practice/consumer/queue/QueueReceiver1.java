package com.ymg.framework.activemq.practice.consumer.queue;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by yumg on 2017/7/13.
 */
public class QueueReceiver1 implements MessageListener{

    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            System.out.println("QueueReciver1接收到的消息是："+textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void test(){
        System.out.println("this is a test");
    }


}
