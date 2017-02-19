package com.taotao.manage.controller.api;

import com.taotao.common.ItemCatData;
import com.taotao.common.ItemCatResult;
import com.taotao.manage.service.ItemCatService;
import org.apache.http.client.config.RequestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenchao on 2017/2/15.
 */
@Controller
@RequestMapping("/api/item/cat")
public class ApiItemCatController {

    @Autowired
    private ItemCatService itemCatService;
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ItemCatResult> queryItemCatList(){
        try {
            ItemCatResult itemCatResult = itemCatService.queryAllToTree();
            List<ItemCatData> itemCats = itemCatResult.getItemCats();
            List<ItemCatData> itemCatsActure = new ArrayList<>();
            for(int i=0;i<14;i++) {
                itemCatsActure.add(itemCats.get(i));
            }
            itemCatResult.setItemCats(itemCatsActure);
            return ResponseEntity.ok(itemCatResult);
        } catch (Exception e) {
            e.printStackTrace();
            RequestConfig config = RequestConfig.custom().setConnectTimeout(1000).setConnectionRequestTimeout(500).setSocketTimeout(10*1000).setStaleConnectionCheckEnabled(true).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
/*
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Object queryItemCatList(
            @RequestParam(value = "callback",required = false)String callback
    ){
        try {
            ItemCatResult itemCatResult = itemCatService.queryAllToTree();
            if (!StringUtils.isEmpty(callback)) {
                MappingJacksonValue value = new MappingJacksonValue(itemCatResult);
                value.setJsonpFunction(callback);
                return value;
            }
            return ResponseEntity.ok(itemCatResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
*/

}
