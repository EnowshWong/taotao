package com.taotao.portal.service.impl;

import com.taotao.pojo.TaotaoResult;
import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
import com.taotao.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.portal.service.impl
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/24 8:21
 *   *
 **/
@Service
public class SearchServiceImpl implements SearchService {

    @Value("${SEARCH_BASE_URL}")
    private String SEARCH_BASE_URL;

    @Value("${SEARCH_SERVICE_URL}")
    private String SEARCH_SERVICE_URL;

    @Override
    public SearchResult search(String queryString, Integer page, Integer rows) {
        //调用search层服务返回json数据
        Map<String, String> param = new HashMap<>();
        param.put("q", queryString);
        param.put("page", page + "");
        param.put("rows", rows + "");
        String result = HttpClientUtil.doGet(SEARCH_BASE_URL + SEARCH_SERVICE_URL, param);
        //将json转换为TaoTaoResult
        try {
            TaotaoResult taotaoResult = TaotaoResult.formatToPojo(result, SearchResult.class);
            if (taotaoResult.getStatus() == 200)
                return (SearchResult) taotaoResult.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
