package solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        solr
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/23 15:42
 *   *
 **/
public class SolrTest {
    @Test
    public void queryItems() throws Exception {
        //创建一个连接连接solr服务
        SolrServer solrServer = new HttpSolrServer("http://192.168.73.128:8080/solr");
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("*:*");
        solrQuery.setStart(30);
        solrQuery.setRows(10);
        QueryResponse query = solrServer.query(solrQuery);
        //取出查询结果
        SolrDocumentList results = query.getResults();
        System.out.println("共" + results.getNumFound() + "条记录");
        for (SolrDocument solrDocument : results) {
            System.out.print(solrDocument.get("id"));
            System.out.print(solrDocument.get("item_title"));
            System.out.print(solrDocument.get("item_sell_point"));
            System.out.print(solrDocument.get("item_price"));
            System.out.print(solrDocument.get("item_image"));
            System.out.print(solrDocument.get("item_category_name"));
            System.out.print(solrDocument.get("item_desc"));
            System.out.println();
        }
    }
}
