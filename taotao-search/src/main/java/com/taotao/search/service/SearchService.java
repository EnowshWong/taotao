package com.taotao.search.service;

import com.taotao.search.pojo.SearchResult;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.search.service
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/23 16:31
 *   *
 **/
public interface SearchService {
    SearchResult search(String queryString, int page, int rows) throws Exception;
}
