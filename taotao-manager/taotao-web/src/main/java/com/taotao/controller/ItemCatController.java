package com.taotao.controller;

import com.taotao.pojo.EUTreeNode;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.controller
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/10 9:27
 *   *
 **/
@Controller
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    /*这是用easyUI查询商品的分类列表
        传入：id，对应数据库中的parent_id，默认值为0，这里的默认值指点击查询分类按钮的时候，没有传入任何分类的id，可以查看数据库的parent_id
        传出：包含List<EUTreeNode>的json数据
        url:tree的url:/item/cat/list
     */
    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EUTreeNode> findItemCat(@RequestParam(value = "id", defaultValue = "0") Long parent_id) {
        List<EUTreeNode> list = itemCatService.findItemCat(parent_id);
        return list;
    }

}
