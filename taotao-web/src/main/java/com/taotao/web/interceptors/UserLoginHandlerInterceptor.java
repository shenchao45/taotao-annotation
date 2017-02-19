package com.taotao.web.interceptors;

import com.taotao.common.CookieUtils;
import com.taotao.web.UserThreadLocal;
import com.taotao.web.pojo.User;
import com.taotao.web.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by shenchao on 2017/2/19.
 */
public class UserLoginHandlerInterceptor implements HandlerInterceptor{
    @Autowired
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserThreadLocal.set(null);
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String loginUrl = "http://sso.taotao.com/user/login.html";
        if (StringUtils.isEmpty(token)) {
            response.sendRedirect(loginUrl);
            return false;
        }
        User user = this.userService.queryByToken(token);
        if (null == user) {
            response.sendRedirect(loginUrl);
            return false;
        }
        UserThreadLocal.set(user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
