package com.taotao.manage.service;

import com.taotao.manage.pojo.ContentCategory;

/**
 * Created by shenchao on 2017/2/15.
 */
public interface ContentCategoryService extends BaseService<ContentCategory> {
    void deleteAll(ContentCategory contentCategory);
}
