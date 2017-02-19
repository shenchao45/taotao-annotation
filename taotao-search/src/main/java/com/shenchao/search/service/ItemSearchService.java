package com.shenchao.search.service;

import com.shenchao.search.bean.Item;
import com.shenchao.search.bean.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by shenchao on 2017/2/19.
 */
@Service
public class ItemSearchService {

    @Autowired
    private HttpSolrServer httpSolrServer;

    public SearchResult search(String keyWords, Integer page, Integer rows) throws SolrServerException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("title:" + keyWords + " AND status:1");
        solrQuery.setStart((Math.max(page, 1) - 1) * rows);
        solrQuery.setRows(rows);
        boolean isHighlighting = !StringUtils.equals("*", keyWords) && StringUtils.isNoneEmpty(keyWords);
        if (isHighlighting) {
            solrQuery.setHighlight(true);
            solrQuery.addHighlightField("title");
            solrQuery.setHighlightSimplePre("<em>");
            solrQuery.setHighlightSimplePost("</em>");
        }
        // 执行查询
        QueryResponse queryResponse = this.httpSolrServer.query(solrQuery);
        List<Item> items = queryResponse.getBeans(Item.class);
        if (isHighlighting) {
            // 将高亮的标题数据写回到数据对象中
            Map<String, Map<String, List<String>>> map = queryResponse.getHighlighting();
            for (Map.Entry<String, Map<String, List<String>>> highlighting : map.entrySet()) {
                for (Item item : items) {
                    if (!highlighting.getKey().equals(item.getId().toString())) {
                        continue;
                    }
                    item.setTitle(StringUtils.join(highlighting.getValue().get("title"), ""));
                    break;
                }
            }
        }
        return new SearchResult(queryResponse.getResults().getNumFound(),items);
    }

}
