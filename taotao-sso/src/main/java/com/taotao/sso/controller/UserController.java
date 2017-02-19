package com.taotao.sso.controller;

import com.taotao.common.CookieUtils;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shenchao on 2017/2/18.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String register(){
        return "register";
    }
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){
        return "login";
    }
    @RequestMapping(value = "/{param}/{type}",method = RequestMethod.GET)
    public ResponseEntity<Boolean> check(@PathVariable("param")String param,@PathVariable("type")Integer type){
        try {
            Boolean b = userService.check(param, type);
            if (b == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            return ResponseEntity.ok(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(value = "/doRegister", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doRegister(@Valid User user, BindingResult bindingResult) {
        Map<String, Object> result =new HashMap<>();
        if (bindingResult.hasErrors()) {
            result.put("status", 400);
            List<String> messages = new ArrayList<>();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError error : allErrors) {
                String defaultMessage = error.getDefaultMessage();
                messages.add(defaultMessage);
            }
            result.put("data", "参数有误！"+ StringUtils.join(messages,","));
            return result;
        }
        try {
            Boolean bool = this.userService.save(user);
            if (bool) {
                result.put("status", 200);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.put("status", 500);
        result.put("data", "注册失败，请重新注册，哈哈哈哈哈");
        return result;
    }
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doLogin(User user, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result =new HashMap<>();
        String token = null;
        try {
            token = this.userService.doLogin(user.getUsername(), user.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", 500);
            return result;
        }
        if (StringUtils.isEmpty(token)) {
            result.put("status", 500);
            return result;
        }
        CookieUtils.setCookie(request, response, "TT_TOKEN", token);
        result.put("status", 200);
        return result;
    }

    @RequestMapping(value = "/{token}", method = RequestMethod.GET)
    public ResponseEntity<User> queryUserByToken(@PathVariable("token") String token) {
        User user = this.userService.queryUserByToken(token);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(user);
    }
}
