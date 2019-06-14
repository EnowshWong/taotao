package com.taotao.controller;

import com.taotao.pojo.EUTreeNode;
import com.taotao.pojo.TaotaoResult;
import com.taotao.service.ContentCategoryService;
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
 *   * @CreateDate:     2017/11/17 10:20
 *   *
 **/
@Controller
public class ContentCategoryController {
    @Autowired
    private ContentCategoryService categoryService;

    @RequestMapping("/content/category/list")
    @ResponseBody
    //开始时parentId为0为首页
    //easyUI的异步Tree返回类型
    public List<EUTreeNode> findContentCategoryList(@RequestParam(value = "id", defaultValue = "0") Long id) {
        List<EUTreeNode> resultList = categoryService.findConTentCategory(id);
        return resultList;
    }

    @RequestMapping("/content/category/create")
    @ResponseBody
    public TaotaoResult addContentCategoy(Long parentId, String name) {
        TaotaoResult result = categoryService.addContentCategory(parentId, name);
        return result;
    }

    @RequestMapping("/content/category/update")
    @ResponseBody
    public TaotaoResult updateContentCategory(Long id, String name) {
        TaotaoResult result = categoryService.updateContentCategory(id, name);
        return result;
    }

    @RequestMapping("/content/category/delete")
    @ResponseBody
    public TaotaoResult deleteContentCategory(Long id) {
        TaotaoResult result = categoryService.deleteContentCategory(id);
        return result;
    }
}
