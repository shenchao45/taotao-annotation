package com.taotao.web.controller;

import com.taotao.web.UserThreadLocal;
import com.taotao.web.pojo.Item;
import com.taotao.web.pojo.Order;
import com.taotao.web.pojo.User;
import com.taotao.web.service.ItemService;
import com.taotao.web.service.OrderService;
import com.taotao.web.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenchao on 2017/2/19.
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> submit(Order order) {
        Map<String, Object> result = new HashMap<>();
        String orderId = null;
        try {
            User user = UserThreadLocal.get();
            order.setUserId(user.getId());
            order.setBuyerNick(user.getUsername());
            orderId = this.orderService.submit(order);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(orderId)) {
            result.put("status", 500);
        }else{
            result.put("status", 200);
            result.put("data", orderId);
        }
        return result;
    }

    @RequestMapping(value = "/{itemId}",method = RequestMethod.GET)
    public ModelAndView toOrder(@PathVariable("itemId")Long itemId){
        ModelAndView mv = new ModelAndView("order");
        Item item = itemService.queryById(itemId);
        mv.addObject("item", item);
        return mv;
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public ModelAndView success(@RequestParam("id") String orderId) {
        ModelAndView mv = new ModelAndView("success");
        Order order = this.orderService.queryByOrderId(orderId);
        mv.addObject("order", order);
        mv.addObject("date", new DateTime().plusDays(2).toString("MM月dd日"));
        return mv;
    }
}
