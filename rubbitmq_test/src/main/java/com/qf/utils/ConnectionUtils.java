package com.qf.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author CZF
 * @Date 2019/1/19
 * @Version 1.0
 */
public class ConnectionUtils {
    private static  ConnectionFactory factory;
    static {
        /**
         * 封装rabbitmq过去连接封装
         */
        //连接rabbitmq
        factory=new ConnectionFactory();
        factory.setHost("192.168.154.199");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setVirtualHost("/admin");
    }
    public static Connection  getConnection(){

        //通过工厂获取连接
        Connection con = null;
        try {
            con = factory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return con;
    }
}
