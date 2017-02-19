package com.shenchao.search.controller;

import com.shenchao.search.bean.SearchResult;
import com.shenchao.search.service.ItemSearchService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

/**
 * Created by shenchao on 2017/2/19.
 */
@Controller
public class SearchController {

    public static final Integer ROWS=32;

    @Autowired
    private ItemSearchService itemSearchService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView search(@RequestParam("q") String keyWords,@RequestParam(value = "page",defaultValue = "1")Integer page) {
        SearchResult searchResult = null;
        try {
            searchResult = itemSearchService.search(keyWords,page,ROWS);
        } catch (SolrServerException e) {
            e.printStackTrace();
            searchResult = new SearchResult(0l, new ArrayList<>());
        }
        int total = searchResult.getTotal().intValue();
        int pages = (total-1)/ROWS+1;
        ModelAndView mv = new ModelAndView("search");
        mv.addObject("query", keyWords);
        mv.addObject("itemList", searchResult.getList());
        mv.addObject("page", page);
        mv.addObject("pages", pages);
        return mv;
    }
}
