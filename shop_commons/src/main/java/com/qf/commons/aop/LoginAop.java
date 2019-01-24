package com.qf.commons.aop;


import com.qf.entity.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;

/**
 * @Author CZF
 * @Date 2019/1/23
 * @Version 1.0
 */
@Aspect
public class LoginAop {

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 切面方法
     * 切点表达式  当前的切面作用于哪些目标方法
     * @return
     */
    @Around("@annotation(IsLogin)")
    public Object isLogin(ProceedingJoinPoint proceedingJoinPoint){
        //判断当前用户是否登录
        //获得request
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        String token=null;
        //通过request获得cookie
        Cookie[] cookies = request.getCookies();
            if(cookies!=null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("login_token")){
                    token=cookie.getValue();
                    break;
                }
            }
        }
        System.out.println(token);
        User user=null;

        //根据token，从redis中获取用户信息
        if(token!=null){
            user= (User) redisTemplate.opsForValue().get(token);
        }
        System.out.println(user);

        if(user==null){
            //未登录

            //需要强制跳转到登录页面

            //无需跳转到登录页面，目标的User对象为空就行了

            //获取@isLogin注解，判断是否要强制跳转
            MethodSignature signature= (MethodSignature) proceedingJoinPoint.getSignature();
            //获得method方法对应的method对象
            Method method=signature.getMethod();
            //从目标方法上获得注解
            IsLogin isLogin = method.getAnnotation(IsLogin.class);
            //获得注解上面的方法返回值
            boolean flag = isLogin.tologin();
            if(flag){
                //获取当前的url
                String returnUrl=request.getRequestURL()+ "?" +request.getQueryString();
                try {
                    returnUrl= URLEncoder.encode(returnUrl,"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //强制跳转到登陆页
                return "redirect:http://localhost:8086/sso/tologin?returnUrl=";
            }
        }
        //已登录的情况
        //先获得原来的参数列表
        Object[] args = proceedingJoinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if(args[i].getClass()==User.class){
                args[i]=user;
            }
        }

        //中间运行目标方法，同时让目标方法中的一个User形式参数,变成当前登录用户的信息
        Object result=null;
        try {
            //按照新的参数列表运行目标方法
            result= proceedingJoinPoint.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }
}
