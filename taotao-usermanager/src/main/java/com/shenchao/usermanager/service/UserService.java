package com.shenchao.usermanager.service;

import com.shenchao.usermanager.bean.EasyUIResult;
import com.shenchao.usermanager.entity.User;

/**
 * Created by shenchao on 2017/2/13.
 */
public interface UserService {
    EasyUIResult<User> queryUserList(Integer page, Integer rows);

    User queryUserById(Long id);

    boolean saveUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(Long id);
}
