package com.taotao.search.controller;

import com.taotao.pojo.TaotaoResult;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.*;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.search.controller
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/23 16:46
 *   *
 **/
@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "/query")
    @ResponseBody
    public TaotaoResult search(String q, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "60") int rows) throws Exception {
        q = new String(q.getBytes("iso8859-1"), "utf-8");
        if (!StringUtils.isBlank(q)) {
            SearchResult searchResult = searchService.search(q, page, rows);
            return TaotaoResult.ok(searchResult);
        } else return TaotaoResult.build(500, "查询条件不能为空");
    }
}
