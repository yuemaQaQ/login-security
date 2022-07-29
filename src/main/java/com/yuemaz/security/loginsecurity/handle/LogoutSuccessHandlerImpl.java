package com.yuemaz.security.loginsecurity.handle;

import com.alibaba.fastjson2.JSON;
import com.yuemaz.security.loginsecurity.entity.LoginUser;
import com.yuemaz.security.loginsecurity.resp.Result;
import com.yuemaz.security.loginsecurity.service.TokenService;
import com.yuemaz.security.loginsecurity.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: create by w5g
 * @date: 2022/7/21 15:25
 */
@Configuration
@Slf4j
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler
{
    @Resource
    private TokenService tokenService;

    /**
     * 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException
    {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser))
        {
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUser.getToken());
            log.debug("退出成功");
        }
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(JSON.toJSONString(Result.ok().setMessage("退出成功")));
    }
}
