package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.service.ContentService;
import com.taotao.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.service.impl
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/17 14:58
 *   *
 **/
@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private TbContentMapper tbContentMapper;

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${REST_CONTENT_REDIS_SYNC_URL}")
    private String REST_CONTENT_REDIS_SYNC_URL;

    @Override
    public EUDataGridResult findContentList(int page, int rows, Long categoryId) {
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        PageHelper.startPage(page, rows);
        List<TbContent> list = tbContentMapper.selectByExample(example);
        PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(list);
        EUDataGridResult euDataGridResult = new EUDataGridResult();
        euDataGridResult.setRows(list);
        euDataGridResult.setTotal(pageInfo.getTotal());
        return euDataGridResult;
    }

    @Override
    public TaotaoResult addContent(TbContent content) {

        content.setPic("http://localhost:8080" + content.getPic());
        content.setPic2("http://localhost:8080" + content.getPic2());
        content.setCreated(new Date());
        content.setUpdated(new Date());
        tbContentMapper.insert(content);

        //调用缓存同步
        try {
            HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_REDIS_SYNC_URL + content.getCategoryId());
        } catch (Exception e) {
            e.printStackTrace();
            //这里也可以通知管理员，可以调用发短信和发邮件的逻辑

        }
        return TaotaoResult.ok();
    }
}
