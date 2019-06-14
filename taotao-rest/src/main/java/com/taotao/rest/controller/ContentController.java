package com.taotao.rest.controller;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.rest.service.ContentService;
import com.taotao.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.rest.controller
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/17 16:35
 *   *
 **/
@Controller
public class ContentController {
    @Autowired
    private ContentService contentService;

    //自己定义url发布服务,返回json数据，可以使用TaoTaoResult包装
    @RequestMapping("/content/list/{contentCategoryId}")
    @ResponseBody
    public TaotaoResult findContentListByCategoryId(@PathVariable Long contentCategoryId) {
        try {
            List<TbContent> list = contentService.findContentListByCategoryId(contentCategoryId);
            return TaotaoResult.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
}
