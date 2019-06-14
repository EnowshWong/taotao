package com.taotao.rest.controller;

import com.taotao.pojo.TaotaoResult;
import com.taotao.rest.service.IndexRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.rest.controller
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/22 18:02
 *   *
 **/
@Controller
@RequestMapping("/cache/sync")
public class IndexRedisController {
    @Autowired
    private IndexRedisService indexRedisService;

    @RequestMapping("/content/{categoryId}")
    //responseBody不能少，不然会报404，找不到页面
    @ResponseBody
    public TaotaoResult syncContentRedis(@PathVariable Long categoryId) {
        return indexRedisService.syncContentRedis(categoryId);
    }
}
