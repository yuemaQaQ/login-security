package com.yuemaz.security.loginsecurity.controller;

import com.yuemaz.security.loginsecurity.resp.Result;
import com.yuemaz.security.loginsecurity.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: create by w5g
 * @date: 2022/7/21 17:30
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户接口", tags = "用户信息类")
public class UserController {

    @Resource
    private UserService userService;

    @ApiOperation("根据ID查询用户")
    @PreAuthorize("@ss.hasPermi('system:user:list1')")
    @GetMapping("/get/{id}")
    public Result selectById(@PathVariable("id") Long id) {
        return Result.ok().setData(userService.selectById(id));
    }
}
