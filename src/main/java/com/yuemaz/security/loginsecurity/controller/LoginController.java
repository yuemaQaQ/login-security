package com.yuemaz.security.loginsecurity.controller;

import com.yuemaz.security.loginsecurity.entity.User;
import com.yuemaz.security.loginsecurity.resp.Result;
import com.yuemaz.security.loginsecurity.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: create by w5g
 * @date: 2022/7/20 14:57
 */
@Api(value = "登录接口", tags = "登录")
@RestController
public class LoginController {

    @Resource
    private LoginService userService;

    @ApiOperation("登录接口")
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        String token = userService.login(user);
        return Result.ok().setData(token);
    }
}
