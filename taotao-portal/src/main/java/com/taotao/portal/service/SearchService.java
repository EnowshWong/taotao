package com.taotao.portal.service;

import com.taotao.pojo.TaotaoResult;
import com.taotao.portal.pojo.SearchResult;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.portal.service
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/24 8:17
 *   *
 **/
public interface SearchService {
    SearchResult search(String queryString, Integer page, Integer rows);
}
