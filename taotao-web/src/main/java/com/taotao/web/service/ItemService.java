package com.taotao.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.taotao.common.ApiService;
import com.taotao.common.RedisService;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.pojo.ItemParamItem;
import com.taotao.web.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        String key = REDIS_KEY+item;
        try {
            String s = redisService.get(key);
            if (!StringUtils.isEmpty(s)) {
                System.out.println("从缓存中读取......");
                return MAPPER.readValue(s, Item.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String result = apiService.doGet(url);
            redisService.set(key, result, REDIS_TIME);
            System.out.println("从后台系统读取。。。。");
            return MAPPER.readValue(result, Item.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ItemDesc queryDescByItemId(Long itemId) {
        String url = "http://manage.taotao.com/rest/api/item/desc/"+itemId;
        try {
            String result = apiService.doGet(url);
            return MAPPER.readValue(result, ItemDesc.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String queryItemParamByItemId(Long itemId) {
        String url = "http://manage.taotao.com/rest/api/item/param/item/"+itemId;
        try {
            String result = apiService.doGet(url);
            ItemParamItem itemParamItem = MAPPER.readValue(result, ItemParamItem.class);
            //取规格参数
            String paramData = itemParamItem.getParamData();
            //把规格参数json格式的数据转换成java对象
            ArrayNode arrayNode = (ArrayNode) MAPPER.readTree(paramData);
            //根据list生成html
            StringBuffer sb = new StringBuffer();
            sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
            sb.append("     <tbody>\n");
            for(JsonNode param:arrayNode) {
                sb.append("          <tr>\n");
                sb.append("               <th class=\"tdTitle\" colspan=\"2\">"+param.get("group")+"</th>\n");
                sb.append("          </tr>\n");
                //取规格项
                ArrayNode params = (ArrayNode) param.get("params");
                for (JsonNode p : params) {
                    sb.append("          <tr>\n");
                    sb.append("               <td class=\"tdTitle\">"+p.get("k").asText()+"</td>\n");
                    sb.append("               <td>"+p.get("v").asText()+"</td>\n");
                    sb.append("          </tr>\n");
                }
            }
            sb.append("     </tbody>\n");
            sb.append("</table>");
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
