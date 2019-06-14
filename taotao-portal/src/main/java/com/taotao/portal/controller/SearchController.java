package com.taotao.portal.controller;

import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.portal.controller
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/24 9:03
 *   *
 **/
@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    @RequestMapping("/search")
    public String search(String q, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10  ") Integer rows, Model model) {
        try {
            if (q != null && q.length() > 0)
                q = new String(q.getBytes("iso8859-1"), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SearchResult searchResult = searchService.search(q, page, rows);
        model.addAttribute("query", q);
        model.addAttribute("totalPages", searchResult.getTotalPage());
        model.addAttribute("itemList", searchResult.getItemList());
        model.addAttribute("page", page);
        return "search";
    }
}
