package solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Collection;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        solr
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2018/2/12 20:00
 *   *
 **/
public class SolrCloudTest {
    //zookeeper地址
    private String zkHost = "192.168.73.128:2181,192.168.73.128:2182,192.168.73.128:2183";
    //collection字集名
    private String defalutCollection = "collection2";
    //声明cloudServer
    private CloudSolrServer cloudSolrServer;

    @Before
    public void init() {
        cloudSolrServer = new CloudSolrServer(zkHost);
        cloudSolrServer.setDefaultCollection(defalutCollection);
        cloudSolrServer.connect();
    }

    //创建索引
    @Test
    public void testCreateIndexOfSolrCloud() throws IOException, SolrServerException {
        //创建字段对象
        SolrInputDocument solrInputDocument = new SolrInputDocument();
        //添加字段
        solrInputDocument.addField("id", "1001");
        solrInputDocument.addField("name", "zhangsan");
        //将字段对象放在cloudServer里执行
        cloudSolrServer.add(solrInputDocument);
        //执行
        cloudSolrServer.commit();
    }

    //查询索引
    @Test
    public void testQuerySolrCloud() throws SolrServerException {
        //创建查询对象
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("*:*");
        QueryResponse queryResponse = cloudSolrServer.query(solrQuery);
        int qTime = queryResponse.getQTime();
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        for (SolrDocument solrDocument : solrDocumentList) {
            String name = (String) solrDocument.getFieldValue("name");
            String id = (String) solrDocument.getFieldValue("id");
            System.out.println(name + " " + id);
        }
    }

    //删除索引
    @Test
    public void testDeleteIndexSolrCloud() throws IOException, SolrServerException {
        cloudSolrServer.deleteById("1001");
        cloudSolrServer.commit();
    }
}
