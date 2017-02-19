package com.taotao.sso.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.RedisService;
import com.taotao.sso.mapper.UserMapper;
import com.taotao.sso.pojo.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by shenchao on 2017/2/19.
 */
@Service
public class UserService {

    @Autowired
    private RedisService redisService;
    @Autowired
    private UserMapper userMapper;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public Boolean check(String param, Integer type) {
        User record = new User();
        switch (type) {
            case 1:
                record.setUsername(param);
                break;
            case 2:
                record.setPhone(param);
                break;
            case 3:
                record.setEmail(param);
                break;
            default:
                return null;
        }
        User user = userMapper.selectOne(record);
        return user != null;
    }

    public Boolean save(User user) {
        user.setId(null);
        user.setCreated(new Date());
        user.setUpdated(user.getCreated());
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        return this.userMapper.insert(user) == 1;
    }

    public String doLogin(String username, String password) throws Exception {
        User record = new User();
        record.setUsername(username);
        User user = this.userMapper.selectOne(record);
        if (null == user) {
            return null;
        }
        if (!StringUtils.equals(DigestUtils.md5Hex(password), user.getPassword())) {
            return null;
        }
        String token = DigestUtils.md5Hex(username + System.currentTimeMillis());
        this.redisService.set("TOKEN_" + token, MAPPER.writeValueAsString(record), 60 * 30);
        return token;
    }

    public User queryUserByToken(String token) {
        String key = "TOKEN_" + token;
        String jsonData = this.redisService.get(key);
        if (StringUtils.isEmpty(jsonData)) {
            return  null;
        }
        this.redisService.set(key, jsonData, 60 * 30);
        try {
            return MAPPER.readValue(jsonData, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
