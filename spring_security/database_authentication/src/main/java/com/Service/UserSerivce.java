package com.Service;

import com.entity.User;
import com.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSerivce {

    @Autowired
    private UserMapper userMapper;

    public User getUserByUsername(String username){
        return userMapper.getUserByUsername(username);
    }
}
