package com.yuemaz.security.loginsecurity.service.impl;

import com.yuemaz.security.loginsecurity.entity.LoginUser;
import com.yuemaz.security.loginsecurity.entity.User;
import com.yuemaz.security.loginsecurity.entity.UserExample;
import com.yuemaz.security.loginsecurity.mapper.JointTableMapper;
import com.yuemaz.security.loginsecurity.mapper.UserMapper;
import com.yuemaz.security.loginsecurity.utils.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: create by w5g
 * @date: 2022/7/19 16:14
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private JointTableMapper jointTableMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //查询用户信息
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<User> userList = userMapper.selectByExample(userExample);
        if (StringUtils.isEmpty(userList)) {
            throw new RuntimeException("用户名或密码错误");
        }
        User user = userList.get(0);
        //TODO 查询所对应的权限
        Set<String> permissions = new HashSet<>();
        //permissions.add("test");
        //permissions.add("admin");
        if (user.getId() == 1L) {
            permissions.add("*:*:*");
        }else {
            permissions = jointTableMapper.selectPermsByUserId(user.getId());
        }
        return new LoginUser(user, permissions);
    }
}
