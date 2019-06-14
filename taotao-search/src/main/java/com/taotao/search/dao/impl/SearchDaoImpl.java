package com.taotao.search.dao.impl;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.Item;
import com.taotao.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.search.dao.impl
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/23 16:04
 *   *
 **/
@Repository
public class SearchDaoImpl implements SearchDao {

    @Autowired
    private SolrServer solrServer;

    @Override
    public SearchResult search(SolrQuery solrQuery) throws Exception {
        QueryResponse queryResponse = solrServer.query(solrQuery);
        //包含要查询目标的文档列表
        SolrDocumentList results = queryResponse.getResults();
        //第一个参数String是主标识，第二个参数map的第一个参数String指要高亮显示的项，map的第二个参数指高亮显示的结果列表，
        //因为一个item中可能有多个高亮显示的项
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        List<Item> itemList = new ArrayList<>();
        for (SolrDocument document : results) {
            Item item = new Item();
            item.setId((String) document.get("id"));
            List<String> list = null;
            //注意这里是document.get("id")得到主标识的id
            list = highlighting.get(document.get("id")).get("item_title");
            String title = "";
            if (list != null && list.size() > 0)
                title = list.get(0);
            else title = (String) document.get("item_title");
            item.setTitle(title);
            item.setPrice((Long) document.get("item_price"));
            item.setSell_point((String) document.get("item_sell_point"));
            item.setCategoryName((String) document.get("item_category_name"));
            item.setItem_des((String) document.get("item_desc"));
            item.setImage((String) document.get("item_image"));
            itemList.add(item);
        }
        SearchResult searchResult = new SearchResult();
        searchResult.setItemList(itemList);
        searchResult.setTotalNum((int) results.getNumFound());
        return searchResult;
    }
}
