package com.taotao.manage.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by shenchao on 2017/2/14.
 */
@Service
public class PropertieService {
    @Value("${REPOSTORY_PATH}")
    public String REPOSTORY_PATH;
    @Value("${IMAGE_BASE_URL}")
    public String IMAGE_BASE_URL;
}
