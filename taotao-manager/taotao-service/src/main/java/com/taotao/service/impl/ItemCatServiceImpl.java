package com.taotao.service.impl;

import com.taotao.pojo.EUTreeNode;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.service.impl
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/10 9:18
 *   *
 **/
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    /*
        此方法实现查询商品的分类，
        传入：parent_id
        功能：根据parent_id来查询子分类，并按照TreeNode的形式返回
        返回：返回一个TreeNode的list，在controller端以json数据格式返回
     */
    @Override
    public List<EUTreeNode> findItemCat(Long parent_id) {
        //使用条件查询
        TbItemCatExample tbItemCatExample = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = tbItemCatExample.createCriteria();
        criteria.andParentIdEqualTo(parent_id);
        //返回查询的结果
        List<TbItemCat> list = tbItemCatMapper.selectByExample(tbItemCatExample);
        //新建一个TreeNode的list
        List<EUTreeNode> treeNodes = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (TbItemCat tbItemCat : list) {
                EUTreeNode treeNode = new EUTreeNode();
                treeNode.setId(tbItemCat.getId());
                treeNode.setText(tbItemCat.getName());
                treeNode.setState(tbItemCat.getIsParent() ? "closed" : "open");
                treeNodes.add(treeNode);
            }
        }
        return treeNodes;
    }
}
