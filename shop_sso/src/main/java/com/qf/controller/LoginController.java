package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.qf.entity.User;
import com.qf.serviceimpl.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @Author CZF
 * @Date 2019/1/22
 * @Version 1.0
 */
@Controller
@RequestMapping("/sso")
public class LoginController {

    //注入服务
    @Reference
    private IUserService userService;

    //注入redis
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 登录界面
     * @return
     */
    @RequestMapping("/tologin")
    public String toLogin(String returnUrl, Model model){
        model.addAttribute("returnUrl",returnUrl);
        return "login";
    }

    /**
     * 登录
     */
    @RequestMapping("/login")
    public String login(String username, String password, HttpServletResponse response,String returnUrl){
        User user = userService.queryUserByNameAndPassword(username, password);
        System.out.println(user+username+password);
        if(user!=null){
            //记录登录状态
            if(returnUrl==null || "".equals(returnUrl)){
                returnUrl="http://localhost:8082";
            }
            String uuid= UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(uuid,user);
            //回写cookie到用户的浏览器,因为cookie在不同的浏览器
            Cookie cookie=new Cookie("login_token",uuid);
            cookie.setMaxAge(60 * 60 * 24);
            cookie.setPath("/");
            response.addCookie(cookie);
            System.out.println("登录成功");
            return "redirect:"+returnUrl;
        }
        System.out.println("登录失败");
        return "login";
    }

    /**
     * 验证是否登录
     * @return
     */
    @RequestMapping("/islogin")
    @ResponseBody
    public String isLogin(@CookieValue(value = "login_token",required = false) String token){
        //SpringMVC会自动从请求里面回去一个名叫login_token的值 .
        //首先获取用户浏览器里面的UUID
        System.out.println("获取用户浏览器中的token的值："+token);
        User user=null;
        if(token!=null){
            user = (User) redisTemplate.opsForValue().get(token);
        }

        //通过UUID验证redis里面有没有用户信息
        return  user!=null?"islogin("+new Gson().toJson(user)+")" : "islogin(null)";
    }
    /**
     * 注销
     */
    @RequestMapping("/logout")
    public String logout(@CookieValue(value = "login_token",required = false) String token,HttpServletResponse response){
            //删除redis
            redisTemplate.delete(token);
            Cookie cookie=new Cookie("login_token",null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return "login";
    }
}
