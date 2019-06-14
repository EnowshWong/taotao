package com.taotao.portal.service.impl;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.portal.service.ContentService;
import com.taotao.utils.HttpClientUtil;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.portal.service.impl
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/18 13:16
 *   *
 **/
@Service
public class ContentServiceImpl implements ContentService {
    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_INDEX_BIGAD_URL}")
    private String REST_INDEX_BIGAD_URL;

    //返回结果是包含map列表的json串
    @Override
    public String getContentList() {
        //调用服务层，返回结果为json格式
        String result = HttpClientUtil.doGet(REST_BASE_URL + REST_INDEX_BIGAD_URL);
        //将json转为TaoTaoResult,并且将TaoTaoResult中的data属性转化为pojo列表
        try {
            TaotaoResult taotaoResult = TaotaoResult.formatToList(result, TbContent.class);
            //取出pojo列表
            List<TbContent> list = (List<TbContent>) taotaoResult.getData();
            //创建map列表
            List<Map> mapList = new ArrayList<>();
            for (TbContent tbContent : list) {
                Map map = new HashMap();
                map.put("srcB", tbContent.getPic2());
                map.put("height", 240);
                map.put("alt", tbContent.getSubTitle());
                map.put("width", 670);
                map.put("src", tbContent.getPic());
                map.put("widthB", 550);
                map.put("href", tbContent.getUrl());
                map.put("heightB", 240);
                mapList.add(map);
            }
            return JsonUtils.objectToJson(mapList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
