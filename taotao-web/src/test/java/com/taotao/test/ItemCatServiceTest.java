package com.taotao.test;

import com.taotao.web.service.ApiServiceBack;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by shenchao on 2017/2/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml","classpath:spring/applicationContext-httpclient.xml"})
public class ItemCatServiceTest {
    @Autowired
    private BeanFactory beanFactory;
    @Test
    public void testDependence(){
        ApiServiceBack bean1 = beanFactory.getBean(ApiServiceBack.class);
        ApiServiceBack bean2 = beanFactory.getBean(ApiServiceBack.class);
        System.out.println(bean1.closeableHttpClient);
        System.out.println(bean2.closeableHttpClient);
        System.out.println(bean1);
        System.out.println(bean2);
    }

}
