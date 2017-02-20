package com.taotao.web.mq.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.RedisService;
import com.taotao.web.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by shenchao on 2017/2/20.
 */
public class ItemMQHandler {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Autowired
    private RedisService redisService;

    public void execute(String msg) {
        JsonNode jsonNode = null;
        try {
            System.out.println("执行了删除缓存。。。。。。");
            jsonNode = MAPPER.readTree(msg);
            Long id = jsonNode.get("itemId").asLong();
            String key = ItemService.REDIS_KEY + id;
            this.redisService.delete(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
