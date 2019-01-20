package com.qf.modul4;

import com.qf.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @Author CZF
 * @Date 2019/1/19
 * @Version 1.0
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        //获取连接
        Connection con = ConnectionUtils.getConnection();
        //获取管道
        Channel channel = con.createChannel();
        //声明交换机
        channel.exchangeDeclare("simple_exchange","direct");
        //声明两个队列
        channel.queueDeclare("queue1",false,false,false,null);
        channel.queueDeclare("queue2",false,false,false,null);
        //将队列和交换机绑定
        channel.queueBind("queue1","simple_exchange","add");
        channel.queueBind("queue1","simple_exchange","update");

        channel.queueBind("queue2","simple_exchange","update");
        channel.queueBind("queue2","simple_exchange","query");

        //发送消息
        String str="hello world!!!";
        channel.basicPublish("simple_exchange","query",null,str.getBytes("UTF-8"));
        //关闭连接
        con.close();
    }
}
