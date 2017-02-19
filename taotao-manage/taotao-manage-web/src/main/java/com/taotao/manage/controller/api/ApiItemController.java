package com.taotao.manage.controller.api;

import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by shenchao on 2017/2/16.
 */
@Controller
@RequestMapping("/api/item")
public class ApiItemController {
    @Autowired
    private ItemService itemService;
    @RequestMapping(value = "/{itemId}",method = RequestMethod.GET)
    public ResponseEntity<Item> queryById(@PathVariable("itemId") Long itemId) {
        try {
            Item item = itemService.queryById(itemId);
            return ResponseEntity.ok(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
