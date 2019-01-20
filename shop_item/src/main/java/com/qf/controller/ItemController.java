package com.qf.controller;

import com.google.gson.Gson;
import com.qf.entity.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author CZF
 * @Date 2019/1/19
 * @Version 1.0
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private Configuration configuration;

    @RequestMapping("/createhtml")
    @ResponseBody   //返回一个响应体
    public String  createHtml(@RequestBody Goods goods)  {
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
        return  "ok";
    }
//    public void test(){
//        //准备模板
//        Template template = configuration.getTemplate("hello.ftl");
//        //准备数据
//        Map map=new HashMap();
//        map.put("name","Freemarker!!!");
//        //组合生成静态页
//        Writer out=new FileWriter("C:\\Users\\Administrator\\Desktop\\hello.html");//文件输出字符流
//        template.process(map,out);
//        out.close();
//    }
}
