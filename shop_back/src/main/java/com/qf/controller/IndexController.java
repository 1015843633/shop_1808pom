package com.qf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author CZF
 * @Date 2019/1/16
 * @Version 1.0
 */
@Controller
public class IndexController {

    //跳转到任意页面的重启请求
    @RequestMapping("/topage/{page}")
    public  String toPage(@PathVariable("page")String page){
        return page;
    }

}
