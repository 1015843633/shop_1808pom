package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.IUserDao;
import com.qf.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author CZF
 * @Date 2019/1/22
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements IUserService{
    @Autowired
    private IUserDao userDao;
    @Override
    public User queryUserByNameAndPassword(String username, String password) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("username",username);
        wrapper.eq("password",password);
        return userDao.selectOne(wrapper);
    }
}
