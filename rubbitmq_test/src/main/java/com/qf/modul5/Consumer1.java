package com.qf.modul5;

import com.qf.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Author CZF
 * @Date 2019/1/19
 * @Version 1.0
 */
public class Consumer1 {
    public static void main(String[] args) throws IOException {
        Connection con = ConnectionUtils.getConnection();
        Channel channel = con.createChannel();
        channel.basicConsume("queue1",true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费1消费了:"+new String(body));
            }
        });
    }
}
