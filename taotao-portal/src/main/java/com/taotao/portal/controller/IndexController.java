package com.taotao.portal.controller;

import com.taotao.pojo.TaotaoResult;
import com.taotao.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.portal.controller
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/15 21:27
 *   *
 **/
@Controller
public class IndexController {
    @Autowired
    private ContentService contentService;

    //默认请求index的url
    @RequestMapping("/index")
    public String showIndex(Model model) {
        String json = contentService.getContentList();
        model.addAttribute("ad1", json);
        return "index";
    }

    @RequestMapping(value = "/httpclient/post", method = RequestMethod.POST)
    @ResponseBody
    public String testPost(String name, String password) {

        return "ok" + name + " " + password;
    }
}
