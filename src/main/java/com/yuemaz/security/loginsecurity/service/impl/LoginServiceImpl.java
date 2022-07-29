package com.yuemaz.security.loginsecurity.service.impl;

import com.yuemaz.security.loginsecurity.entity.LoginUser;
import com.yuemaz.security.loginsecurity.entity.User;
import com.yuemaz.security.loginsecurity.mapper.UserMapper;
import com.yuemaz.security.loginsecurity.service.LoginService;
import com.yuemaz.security.loginsecurity.service.TokenService;
import com.yuemaz.security.loginsecurity.utils.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author: create by w5g
 * @date: 2022/7/20 14:52
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private AuthenticationManager manager;

    @Resource
    private TokenService tokenService;

    /**
     * 登录
     *
     * @param user
     * @return
     */
    @Override
    public String login(User user) {
        //AuthenticationManager authenticate进行用户认证，调用UserDetailsServiceImpl
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword());
        Authentication authenticate = manager.authenticate(authenticationToken);
        //不通过给出提示
        if (StringUtils.isNull(authenticate)) {
            throw new RuntimeException("登陆失败");
        }
        //通过生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        recordLoginInfo(loginUser.getUser().getId());
        return tokenService.createToken(loginUser);
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    private void recordLoginInfo(Long userId)
    {
        User user = new User();
        user.setId(userId);
        user.setLoginTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
    }
}
