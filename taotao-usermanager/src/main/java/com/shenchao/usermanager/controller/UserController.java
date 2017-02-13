package com.shenchao.usermanager.controller;

import com.shenchao.usermanager.bean.EasyUIResult;
import com.shenchao.usermanager.entity.User;
import com.shenchao.usermanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by shenchao on 2017/2/13.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 查询user的列表的分页
     * @param rows 每一页的大小
     * @param page 第几页
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public EasyUIResult queryUserList(@RequestParam("rows") int rows, @RequestParam("page") int page){
        EasyUIResult<User> result = userService.queryUserList(page, rows);
        System.out.println(result+"----------------------------------------");
        return result;
    }
}
