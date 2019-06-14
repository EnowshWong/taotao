package com.taotao.controller;

import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;
import com.taotao.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.controller
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/9 18:36
 *   *
 **/
@Controller
public class ItemController {
    @Autowired
    private PictureService pictureService;

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{id}")
    public @ResponseBody
    TbItem findItemById(@PathVariable Long id) throws Exception {
        TbItem tbItem = itemService.findItemById(id);
        return tbItem;
    }

    //分页查询商品列表,返回的是json数据
    @RequestMapping("/item/list")
    @ResponseBody
    public EUDataGridResult findItemList(Integer page, Integer rows) throws Exception {
        //构造返回对象，返回json数据即可
        EUDataGridResult euDataGridResult = itemService.findItemList(page, rows);
        return euDataGridResult;
    }

    //添加商品，接受pojo参数，返回json
    @RequestMapping(value = "/item/save", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult addItem(TbItem tbItem, String desc, String itemParams) throws Exception {
        return itemService.addItem(tbItem, desc, itemParams);
    }

}
