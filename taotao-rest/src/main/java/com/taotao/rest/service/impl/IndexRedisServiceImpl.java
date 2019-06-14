package com.taotao.rest.service.impl;

import com.taotao.pojo.TaotaoResult;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.IndexRedisService;
import com.taotao.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.rest.service.impl
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/22 16:52
 *   *
 **/
@Service
public class IndexRedisServiceImpl implements IndexRedisService {
    @Autowired
    private JedisClient jedisClient;

    @Value("${INDEX_CONTENT_REDIS_KEY}")
    private String INDEX_CONTENT_REDIS_KEY;

    @Override
    //当对数据库数据修改时，需要调用缓存逻辑，删除对应的key
    public TaotaoResult syncContentRedis(Long categoryId) {
        try {
            jedisClient.hdel(INDEX_CONTENT_REDIS_KEY, categoryId + "");
            return TaotaoResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
}
