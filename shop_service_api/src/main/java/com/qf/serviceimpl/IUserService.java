package com.qf.serviceimpl;

import com.qf.entity.User;

public interface IUserService {
    //查询用户
    User queryUserByNameAndPassword(String name,String password);
}
