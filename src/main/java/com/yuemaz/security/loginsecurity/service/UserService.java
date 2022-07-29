package com.yuemaz.security.loginsecurity.service;

import com.yuemaz.security.loginsecurity.entity.User;

/**
 * @author: create by w5g
 * @date: 2022/7/21 17:27
 */
public interface UserService {

    /**
     * 根据ID查询用户信息
     * @param id 用户ID
     * @return
     */
    User selectById(Long id);
}
