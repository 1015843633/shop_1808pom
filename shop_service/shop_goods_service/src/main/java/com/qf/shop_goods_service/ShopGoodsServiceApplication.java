package com.qf.shop_goods_service;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.core.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.qf")
@MapperScan("com.qf.dao")
@DubboComponentScan("com.qf.serviceimpl")
@EnableTransactionManagement
public class ShopGoodsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopGoodsServiceApplication.class, args);
	}

	//声明队列
	@Bean
	public Queue getQueue(){
		return new Queue("goods_queue");
	}

	//声明交换机
	@Bean
	public FanoutExchange getExchange(){
		return  new FanoutExchange("goods_exchange");
	}

	//队列绑定交换机
	@Bean
	public Binding getBinExchangeBinding(Queue getQueue,FanoutExchange getExchange){
		return BindingBuilder.bind(getQueue).to(getExchange);
	}
}

