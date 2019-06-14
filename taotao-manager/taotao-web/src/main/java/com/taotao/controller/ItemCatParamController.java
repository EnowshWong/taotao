package com.taotao.controller;

import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.service.ItemCatParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.controller
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/14 21:34
 *   *
 **/
@Controller
public class ItemCatParamController {
    @Autowired
    private ItemCatParamService itemCatParamService;

    @RequestMapping("/item/param/query/itemcatid/{itemCatId}")
    @ResponseBody
    public TaotaoResult findItemCatParam(@PathVariable Long itemCatId) {
        TaotaoResult result = itemCatParamService.findItemCatParam(itemCatId);
        return result;
    }

    @RequestMapping("/item/param/list")
    @ResponseBody
    public EUDataGridResult findItemCatParamList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "30") Integer rows) {
        EUDataGridResult euDataGridResult = itemCatParamService.findItemCatParamList(page, rows);
        return euDataGridResult;
    }

    @RequestMapping("/item/param/save/{cid}")
    @ResponseBody
    public TaotaoResult addItemCatParam(@PathVariable Long cid, String paramData) {
        TaotaoResult result = itemCatParamService.addItemCatParam(cid, paramData);
        return result;
    }
}
