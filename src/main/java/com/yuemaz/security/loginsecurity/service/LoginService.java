package com.yuemaz.security.loginsecurity.service;

import com.yuemaz.security.loginsecurity.entity.User;

/**
 * @author: create by w5g
 * @date: 2022/7/20 14:51
 */
public interface LoginService {

    /**
     * 登录
     * @param user
     * @return
     */
    String login(User user);
}
