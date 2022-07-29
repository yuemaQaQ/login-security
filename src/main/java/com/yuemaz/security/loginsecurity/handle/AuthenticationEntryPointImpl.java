package com.yuemaz.security.loginsecurity.handle;

import com.alibaba.fastjson2.JSON;
import com.yuemaz.security.loginsecurity.constant.HttpStatus;
import com.yuemaz.security.loginsecurity.resp.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: create by w5g -认证失败处理类 返回未授权
 * @date: 2022/7/21 15:15
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint
{

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException
    {
        int code = HttpStatus.UNAUTHORIZED;
        String msg = String.format("请求访问：%s，认证失败，无法访问系统资源", request.getRequestURI());
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(JSON.toJSONString(Result.fail().setCode(code).setMessage(msg)));

    }
}
