package com.hdb.hrsguard.controller.admin.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限统一管理拦截器
 * @author Administrator
 *
 */
@Component
public class AuthorityInterceptor implements HandlerInterceptor {

    private Logger log = LoggerFactory.getLogger(AuthorityInterceptor.class);

    @Override
    public boolean  preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        String requestURI = request.getRequestURI();
        log.info("进入权限控制拦截器" + requestURI);
        log.info("该请求符合权限要求，放行" + requestURI);
        return true;
    }
}
