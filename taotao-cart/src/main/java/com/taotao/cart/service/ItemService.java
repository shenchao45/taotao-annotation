package com.taotao.cart.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.cart.pojo.Item;
import com.taotao.common.ApiService;
import com.taotao.common.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by shenchao on 2017/2/16.
 */
@Service
public class ItemService {
    @Autowired
    private ApiService apiService;
    @Autowired(required = false)
    private RedisService redisService;
    public static final String REDIS_KEY = "TAOTAO_WEB_ITEM_DETAIL_";
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Integer REDIS_TIME = 60 * 60 * 24;
    /**
     * 根据商品Id查询商品数据
     * 通过后台系统提供的接口服务进行查询
     * @param item
     * @return
     */
    public Item queryById(Long item) {
        String url = "http://manage.taotao.com/rest/api/item/"+item;
        try {
            String result = apiService.doGet(url);
            return MAPPER.readValue(result, Item.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
