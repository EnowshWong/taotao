package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.controller
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    页面跳转，打开首页    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/9 20:01
 *   *
 **/
@Controller
public class PageController {

    @RequestMapping("/")
    public String showIndex() {
        return "index";
    }

    //这个action接受任何以/**的url，在index首页有这种url
    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page) {
        return page;
    }
}
