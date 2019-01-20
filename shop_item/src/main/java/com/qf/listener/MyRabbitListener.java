package com.qf.listener;

import com.qf.entity.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author CZF
 * @Date 2019/1/19
 * @Version 1.0
 */
@Component
@RabbitListener(queues = "goods_queue")
public class MyRabbitListener {

    @Autowired
    private Configuration configuration;

    @RabbitHandler
    public void hanlderMsg(Goods goods){
        System.out.println(goods);
        //生成静态页面
        //将对象存到map中去
        Map<String,Goods> data=new HashMap<>();
        data.put("goods",goods);
        //准备输出流
        //获取 classpath路径
        String path=this.getClass().getResource("/static/page/").getPath()+goods.getId()+".html";
        System.out.println("静态页上传的路径:"+path);
        try (
                Writer out=new FileWriter(path);   //会在流写完后自动关闭
        ){
            //准备商品模板
            Template template=configuration.getTemplate("goods.ftl");
            //生成静态页面
            template.process(data,out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
