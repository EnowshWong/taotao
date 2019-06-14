package com.taotao.search.controller;

import com.taotao.pojo.TaotaoResult;
import com.taotao.search.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.search.controller
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/23 15:13
 *   *
 **/
@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("/manager/importAll")
    @ResponseBody
    public TaotaoResult importAllItems() {
        TaotaoResult taotaoResult = itemService.importAllItems();
        return taotaoResult;
    }
}
