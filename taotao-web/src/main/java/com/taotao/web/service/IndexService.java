package com.taotao.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.taotao.common.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shenchao on 2017/2/15.
 */
@Service
public class IndexService {
    @Autowired
    private ApiService apiService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public String queryIndexAD1() {
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            String url = "http://manage.taotao.com/rest/content?categoryId=70&page=1&rows=6";
            String jsonData = apiService.doGet(url);
            if (StringUtils.isEmpty(jsonData)) {
                return null;
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            ArrayNode rows = (ArrayNode) jsonNode.get("rows");
            for (JsonNode node : rows) {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("srcB", node.get("pic").asText());
                map.put("height", 240);
                map.put("alt", node.get("title").asText());
                map.put("width", 670);
                map.put("src", node.get("pic").asText());
                map.put("widthB", 550);
                map.put("href", node.get("url").asText());
                map.put("heightB", 240);
                result.add(map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return MAPPER.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
