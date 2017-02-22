package com.taotao.cart.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.cart.pojo.User;
import com.taotao.common.ApiService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by shenchao on 2017/2/19.
 */
@Service
public class UserService {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Autowired
    private ApiService apiService;
    public User queryByToken(String token) {
        try {
            String url = "http://sso.taotao.com/service/user/"+token;
            String s = apiService.doGet(url);
            if (StringUtils.isEmpty(s)) {
                return null;
            }
            return MAPPER.readValue(s, User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
