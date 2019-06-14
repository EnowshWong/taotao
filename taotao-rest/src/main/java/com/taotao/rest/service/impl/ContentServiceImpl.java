package com.taotao.rest.service.impl;

import com.mysql.jdbc.StringUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.dao.impl.JedisClientSingle;
import com.taotao.rest.service.ContentService;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.rest.service.impl
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/17 16:32
 *   *
 **/
@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private TbContentMapper tbContentMapper;

    @Value("${INDEX_CONTENT_REDIS_KEY}")
    private String INDEX_CONTENT_REDIS_KEY;

    @Autowired
    private JedisClient jedisClient;

    @Override
    public List<TbContent> findContentListByCategoryId(Long categoryId) {
        //从缓存中取内容
        try {
            String hget = jedisClient.hget(INDEX_CONTENT_REDIS_KEY, categoryId + "");
            //如果hget不是空
            if (!org.apache.commons.lang3.StringUtils.isBlank(hget)) {
                List<TbContent> list = JsonUtils.jsonToList(hget, TbContent.class);
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //从数据库中查找
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> list = tbContentMapper.selectByExample(example);
        //向缓存中添加内容,redis中存的是key，value形式，都是字符串类型
        try {
            //把list转化位字符串
            String cacheList = JsonUtils.objectToJson(list);
            //使用hset，可以实现分类，hkey相当于一个主key，可在属性文件中配置，key相当于标识即为categoryId，value就是存入的内容
            jedisClient.hset(INDEX_CONTENT_REDIS_KEY, categoryId + "", cacheList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
