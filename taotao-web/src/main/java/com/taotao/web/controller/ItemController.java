package com.taotao.web.controller;

import com.taotao.common.RedisService;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.web.pojo.Item;
import com.taotao.web.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by shenchao on 2017/2/16.
 */
@RequestMapping("/item")
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired(required = false)
    private RedisService redisService;

    @RequestMapping(value = "/{itemId}",method = RequestMethod.GET)
    public ModelAndView itemDetail(@PathVariable("itemId")Long itemId){
        ModelAndView mv = new ModelAndView("item");
        Item item = itemService.queryById(itemId);
        ItemDesc itemDesc = itemService.queryDescByItemId(itemId);
        mv.addObject("itemDesc", itemDesc);
        mv.addObject("item", item);
        try {
            String itemParam = itemService.queryItemParamByItemId(itemId);
            mv.addObject("itemParam",itemParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }
    @RequestMapping(value = "/cache/{itemId}")
    public ResponseEntity<Void> deleteCache(@PathVariable("itemId")Long id){
        try {
            String key = ItemService.REDIS_KEY + id;
            this.redisService.delete(key);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
