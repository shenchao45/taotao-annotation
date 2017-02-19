package com.taotao.manage.controller.api;

import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by shenchao on 2017/2/16.
 */
@Controller
@RequestMapping("/api/item/desc")
public class ApiItemDescController {
    @Autowired
    private ItemDescService itemDescService;

    @RequestMapping(value = "/{itemId}", method = RequestMethod.GET)
    public ResponseEntity<ItemDesc> queryByItemId(@PathVariable("itemId") Long itemId) {
        ItemDesc itemDesc = itemDescService.queryById(itemId);
        return ResponseEntity.ok(itemDesc);
    }
}

