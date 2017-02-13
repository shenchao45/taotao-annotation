package com.shenchao.usermanager.service.impl;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shenchao.usermanager.bean.EasyUIResult;
import com.shenchao.usermanager.entity.User;
import com.shenchao.usermanager.mapper.UserMapper;
import com.shenchao.usermanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shenchao on 2017/2/13.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public EasyUIResult<User> queryUserList(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        Example example = new Example(User.class);
        example.setOrderByClause("updated DESC");
        List<User> result = userMapper.selectByExample(example);
        PageInfo<User> pageInfo = new PageInfo<>(result);
        return new EasyUIResult<>(pageInfo.getTotal(), pageInfo.getList());
    }
}
