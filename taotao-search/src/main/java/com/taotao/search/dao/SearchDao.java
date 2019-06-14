package com.taotao.search.dao;

import com.taotao.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.search.dao
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/23 16:02
 *   *
 **/
public interface SearchDao {
    SearchResult search(SolrQuery query) throws Exception;
}
