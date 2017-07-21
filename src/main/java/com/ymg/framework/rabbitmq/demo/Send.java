package com.ymg.framework.rabbitmq.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by yumg on 2017/7/9.
 */
public class Send {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        /**
         * 创建连接到RabbitMQ
         */
        ConnectionFactory factory = new ConnectionFactory();

        //设置MabbitMQ所在主机ip或者主机名
        factory.setHost("localhost");

        //创建一个连接
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare();
        //发送的消息
        String message = "Hello 于美光";
        //往队列中发出一条消息
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        //关闭频道 和 连接
        channel.close();
        connection.close();
    }

}
