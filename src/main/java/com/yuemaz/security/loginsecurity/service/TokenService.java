package com.yuemaz.security.loginsecurity.service;

import com.yuemaz.security.loginsecurity.entity.LoginUser;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: create by w5g
 * @date: 2022/7/21 13:25
 */
public interface TokenService {

    /**
     * 创建令牌
     *
     * @param loginUser 用户信息
     * @return 令牌
     */
    String createToken(LoginUser loginUser);

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param loginUser
     * @return 令牌
     */
    void verifyToken(LoginUser loginUser);

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    LoginUser getLoginUser(HttpServletRequest request);

    /**
     * 设置用户身份信息
     */
    void setLoginUser(LoginUser loginUser);

    /**
     * 删除用户身份信息
     */
    void delLoginUser(String token);
}
