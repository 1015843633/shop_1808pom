package com.qf.modul1;

import com.qf.utils.ConnectionUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author CZF
 * @Date 2019/1/19
 * @Version 1.0
 */
public class Provider {
    public static void main(String[] args) throws IOException, TimeoutException {
       Connection con= ConnectionUtils.getConnection();
        //获取管道，后续的所有操作都是基于管道实现的
        Channel channel = con.createChannel();
        //基于管道创建队列
        //声明了一个队列，名称为simple_queue
        channel.queueDeclare("simple_queue",false,false,false,null);
        //发布消息，放到队列中去
        String str="hello rabbitmq!!!";
        channel.basicPublish("","simple_queue",null,str.getBytes("UTF-8"));
        //关闭连接
        con.close();
    }
}
