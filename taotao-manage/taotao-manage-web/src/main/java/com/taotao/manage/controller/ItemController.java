package com.taotao.manage.controller;

import com.taotao.common.EasyUIResult;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by shenchao on 2017/2/14.
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);
    @Autowired
    private ItemService itemService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveItem(Item item, @RequestParam("desc") String desc,@RequestParam("itemParams")String itemParams) {
        try {
            LOGGER.info("新增商品，item = {},desc = {}",item,desc);
            if (StringUtils.isEmpty(item.getTitle())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            if (itemService.saveItem(item, desc,itemParams)) {
                LOGGER.info("新增商品成功，itemId = {}",item.getId());
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
            LOGGER.info("新增商品失败，item = {},desc = {}",item,desc);
        } catch (Exception e) {
            LOGGER.error("新增商品出错! item="+item,e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryItemList(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "30") Integer rows
            ) {
        try {
            EasyUIResult easyUIResult = itemService.queryItemList(page, rows);
            return ResponseEntity.status(HttpStatus.OK).body(easyUIResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateItem(Item item, @RequestParam("desc") String desc,@RequestParam("itemParams")String itemParams) {
        try {
            LOGGER.info("修改商品，item = {},desc = {}",item,desc);
            if (StringUtils.isEmpty(item.getTitle())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            if (itemService.updateItem(item, desc,itemParams)) {
                LOGGER.info("修改商品成功，itemId = {}",item.getId());
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            LOGGER.info("修改商品失败，item = {},desc = {}",item,desc);
        } catch (Exception e) {
            LOGGER.error("修改商品出错! item="+item,e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
