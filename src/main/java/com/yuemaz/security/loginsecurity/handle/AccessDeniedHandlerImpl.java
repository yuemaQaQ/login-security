package com.yuemaz.security.loginsecurity.handle;

import com.alibaba.fastjson2.JSON;
import com.yuemaz.security.loginsecurity.constant.HttpStatus;
import com.yuemaz.security.loginsecurity.resp.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: create by w5g
 * @date: 2022/7/26 15:55
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException
    {
        int code = HttpStatus.FORBIDDEN;
        String msg = String.format("请求访问：%s，授权失败，无法访问系统资源", request.getRequestURI());
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(JSON.toJSONString(Result.fail().setCode(code).setMessage(msg)));
    }
}
