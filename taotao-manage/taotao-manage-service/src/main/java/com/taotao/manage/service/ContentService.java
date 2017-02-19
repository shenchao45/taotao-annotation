package com.taotao.manage.service;

import com.taotao.common.EasyUIResult;
import com.taotao.manage.pojo.Content;

/**
 * Created by shenchao on 2017/2/15.
 */
public interface ContentService extends BaseService<Content> {
    EasyUIResult queryContentList(Long categoryId, Integer page, Integer rows);
}
