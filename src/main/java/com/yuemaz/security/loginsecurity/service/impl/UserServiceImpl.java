package com.yuemaz.security.loginsecurity.service.impl;

import com.yuemaz.security.loginsecurity.entity.User;
import com.yuemaz.security.loginsecurity.mapper.UserMapper;
import com.yuemaz.security.loginsecurity.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: create by w5g
 * @date: 2022/7/21 17:29
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 根据ID查询用户信息
     *
     * @param id 用户ID
     * @return
     */
    @Override
    public User selectById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
