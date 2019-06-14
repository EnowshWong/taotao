package com.taotao.service.impl;

import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.EUTreeNode;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.service.impl
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/17 10:14
 *   *
 **/
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;


    @Override
    public List<EUTreeNode> findConTentCategory(Long parentId) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        List<EUTreeNode> treeNodes = new ArrayList<>();
        for (TbContentCategory contentCategory : list) {
            EUTreeNode treeNode = new EUTreeNode();
            treeNode.setId(contentCategory.getId());
            treeNode.setText(contentCategory.getName());
            treeNode.setState(contentCategory.getIsParent() ? "closed" : "open");
            treeNodes.add(treeNode);
        }
        return treeNodes;
    }

    /*
        分类添加
        传入参数：parentId，name
        传出TaoTaoResult
     */
    @Override
    public TaotaoResult addContentCategory(Long parentId, String name) {
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setParentId(parentId);
        contentCategory.setName(name);
        contentCategory.setStatus(1);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        contentCategory.setSortOrder(1);
        contentCategory.setIsParent(false);
        contentCategoryMapper.insert(contentCategory);
        //修改分类为parentId的isParent属性
        //先查询再修改
        TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
        if (parent.getIsParent() != true) {
            parent.setIsParent(true);
            contentCategoryMapper.updateByPrimaryKey(parent);
        }
        //由于新增的分类id是自增长的，所以需要主键返回
        return TaotaoResult.ok(contentCategory);
    }

    @Override
    public TaotaoResult updateContentCategory(Long id, String name) {
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setId(id);
        contentCategory.setName(name);
        contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteContentCategory(Long id) {
        Long parentId = contentCategoryMapper.selectByPrimaryKey(id).getParentId();
        contentCategoryMapper.deleteByPrimaryKey(id);
        TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
        //根据parentId查询出所有子节点，如果子节点个数为0，则设置isParent为false
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        if (list == null || list.size() == 0)
            parent.setIsParent(false);
        contentCategoryMapper.updateByPrimaryKeySelective(parent);
        return TaotaoResult.ok();
    }
}
