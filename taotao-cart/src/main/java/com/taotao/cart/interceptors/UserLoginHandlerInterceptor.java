package com.taotao.cart.interceptors;

import com.taotao.cart.UserThreadLocal;
import com.taotao.cart.pojo.User;
import com.taotao.cart.service.UserService;
import com.taotao.common.CookieUtils;
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
        if (StringUtils.isEmpty(token)) {
            return true;
        }
        User user = this.userService.queryByToken(token);
        if (null == user) {
            return true;
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
