package com.shenchao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.ItemCatResult;
import com.taotao.manage.service.ItemCatService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * Created by shenchao on 2017/2/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml","classpath:spring/applicationContext-mybatis.xml"})
public class ItemCatServiceTest {

    @Autowired
    private ItemCatService itemCatService;
    @Test
    public void testItemCatToTree() throws IOException {
        ItemCatResult itemCatResult = itemCatService.queryAllToTree();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(System.out,itemCatResult);
    }
}
