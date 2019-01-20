package com.qf.modul2;

import com.qf.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Author CZF
 * @Date 2019/1/19
 * @Version 1.0
 */
public class Consumer2 {
    public static void main(String[] args) throws IOException {
        Connection con= ConnectionUtils.getConnection();
        //通过连接过得管道
        Channel channel = con.createChannel();
        //基于管道创建队列
        //声明了一个队列，名称为simple_queue
        channel.queueDeclare("simple_queue2",false,false,false,null);
        //监听队列
        channel.basicConsume("simple_queue2",true,new DefaultConsumer(channel){
            /**
             * 消息消费的方法
             * @param consumerTag
             * @param envelope
             * @param properties
             * @param body
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("消费者接受到消息:"+new String(body,"UTF-8"));
            }
        });


    }
}
