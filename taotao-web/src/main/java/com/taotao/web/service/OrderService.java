package com.taotao.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.ApiService;
import com.taotao.common.HttpResult;
import com.taotao.web.pojo.Order;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by shenchao on 2017/2/19.
 */
@Service
public class OrderService {
    @Autowired
    private ApiService apiService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public String submit(Order order) throws IOException {
        String url = "http://order.taotao.com/order/create";
        HttpResult httpResult = this.apiService.doPostJson(url, MAPPER.writeValueAsString(order));
        if (200 == httpResult.getCode().intValue()) {
            String body = httpResult.getBody();
            JsonNode jsonNode = MAPPER.readTree(body);
            if (jsonNode.get("status").asInt() == 200) {
                return jsonNode.get("data").asText();
            }
        }
        return null;
    }

    public Order queryByOrderId(String orderId) {
        String url = "http://order.taotao.com/order/query/"+orderId;
        try {
            String jsonData = this.apiService.doGet(url);
            if (StringUtils.isEmpty(jsonData)) {
                return null;
            }
            return MAPPER.readValue(jsonData, Order.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
