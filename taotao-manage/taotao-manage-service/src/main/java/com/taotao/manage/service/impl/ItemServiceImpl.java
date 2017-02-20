package com.taotao.manage.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.ApiService;
import com.taotao.common.EasyUIResult;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.pojo.ItemParamItem;
import com.taotao.manage.service.ItemDescService;
import com.taotao.manage.service.ItemParamItemService;
import com.taotao.manage.service.ItemService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by shenchao on 2017/2/14.
 */
@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {
    @Autowired
    private ItemDescService itemDescService;

    @Autowired
    private ApiService apiService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ItemParamItemService itemParamItemService;

    @Override
    public Boolean saveItem(Item item, String desc,String itemParams) {
        try {
            item.setStatus(1);
            item.setId(null);
            this.save(item);
            ItemDesc itemDesc = new ItemDesc();
            itemDesc.setItemId(item.getId());
            itemDesc.setItemDesc(desc);
            itemDescService.save(itemDesc);
            ItemParamItem itemParamItem = new ItemParamItem();
            itemParamItem.setParamData(itemParams);
            itemParamItem.setItemId(item.getId());
            itemParamItemService.save(itemParamItem);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public EasyUIResult queryItemList(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        Example example = new Example(Item.class);
        example.setOrderByClause("created DESC");
        List<Item> items = this.mapper.selectByExample(example);
        PageInfo<Item> pageInfo = new PageInfo<>(items);
        return new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public boolean updateItem(Item item, String desc,String itemParams) {
//        try {
//            String url = "http://www.taotao.com/item/cache/" + item.getId()+".html";
//            apiService.doGet(url);
//            System.out.println("清除前端缓存了。。。。");
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("清除前端缓存失败。。。。");
//        }
        sendMsg(item.getId(),"update");
        try {
            updateSelective(item);
            ItemDesc itemDesc = new ItemDesc();
            itemDesc.setItemId(item.getId());
            itemDesc.setItemDesc(desc);
            itemDescService.update(itemDesc);
            ItemParamItem record = new ItemParamItem();
            record.setItemId(item.getId());
            ItemParamItem itemParamItem = itemParamItemService.queryOne(record);
            if (itemParamItem != null) {
                itemParamItem.setParamData(itemParams);
                itemParamItemService.updateSelective(itemParamItem);
            }else{
                record.setParamData(itemParams);
                itemParamItemService.save(record);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 发送消息到MQ中的交换机，通知
     * @param itemId
     * @param type
     */
    private void sendMsg(Long itemId, String type) {
        //发送消息到MQ中的交换机，通知
        Map<String, Object> message = new HashedMap();
        message.put("itemId", itemId);
        message.put("type",type);
        message.put("date", System.currentTimeMillis());
        try {
            String s = MAPPER.writeValueAsString(message);
            this.rabbitTemplate.convertAndSend("item.update",s);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
