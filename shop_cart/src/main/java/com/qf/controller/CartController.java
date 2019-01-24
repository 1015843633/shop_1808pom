package com.qf.controller;

import com.qf.commons.aop.IsLogin;
import com.qf.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author CZF
 * @Date 2019/1/23
 * @Version 1.0
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    /**
     * 添加购物车
     * @return
     */
    @IsLogin(tologin = true)
    @RequestMapping("/add")
    public String addCart(Integer gid,Integer gnumber,User user){

        System.out.println(gid+"==="+gnumber);
        //判断是否登录
        System.out.println("是否登录:"+user);
        return "success";
    }
}
