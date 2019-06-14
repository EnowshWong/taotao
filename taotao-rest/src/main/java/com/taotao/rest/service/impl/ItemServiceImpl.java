package com.taotao.rest.service.impl;

import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.*;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ItemService;
import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
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
 *   * @CreateDate:     2017/11/24 15:19
 *   *
 **/
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;


    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;

    @Value("${REDIS_ITEM_EXPIRE}")
    private Integer REDIS_ITEM_EXPIRE;

    @Override
    public TaotaoResult getItemById(Long id) {
        //查询缓存
        try {
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + id + ":" + "base");
            if (!StringUtils.isBlank(json)) {
                TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
                return TaotaoResult.ok(tbItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
        TaotaoResult taotaoResult = TaotaoResult.ok(tbItem);
        //写入缓存
        try {
            jedisClient.set(REDIS_ITEM_KEY + ":" + id + ":" + "base", JsonUtils.objectToJson(tbItem));
            //设置商品的有效期
            jedisClient.expire(REDIS_ITEM_KEY + ":" + id + ":" + "base", REDIS_ITEM_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taotaoResult;
    }

    @Override
    public TaotaoResult getItemDescById(Long id) {
        //查询缓存
        try {
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + id + ":" + "desc");
            if (!StringUtils.isBlank(json)) {
                TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return TaotaoResult.ok(tbItemDesc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(id);
        //写入缓存
        try {
            jedisClient.set(REDIS_ITEM_KEY + ":" + id + ":" + "desc", JsonUtils.objectToJson(tbItemDesc));
            //设置商品的有效期
            jedisClient.expire(REDIS_ITEM_KEY + ":" + id + ":" + "desc", REDIS_ITEM_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TaotaoResult.ok(tbItemDesc);
    }

    @Override
    public TaotaoResult getItemParamById(Long id) {
        //查询缓存
        try {
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + id + ":" + "param");
            if (!StringUtils.isBlank(json)) {
                TbItemParamItem tbItemParamItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
                return TaotaoResult.ok(tbItemParamItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(id);
        List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
        if (list != null && list.size() > 0) {
            TbItemParamItem tbItemParamItem = list.get(0);
            //写入缓存
            try {
                jedisClient.set(REDIS_ITEM_KEY + ":" + id + ":" + "param", JsonUtils.objectToJson(tbItemParamItem));
                //设置商品的有效期
                jedisClient.expire(REDIS_ITEM_KEY + ":" + id + ":" + "param", REDIS_ITEM_EXPIRE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return TaotaoResult.ok(tbItemParamItem);
        }
        return TaotaoResult.build(400, "无此商品规格信息");
    }
}
