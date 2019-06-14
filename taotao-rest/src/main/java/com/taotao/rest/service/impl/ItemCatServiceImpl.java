package com.taotao.rest.service.impl;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.CatNode;
import com.taotao.pojo.CatResult;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.rest.service.impl
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/16 10:47
 *   *
 **/
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper itemCatMapper;


    @Override
    public CatResult getItemCatList() {
        //主方法中调用其他方法
        CatResult result = new CatResult();
        result.setData(getCatListByParentId(0));
        return result;
    }

    //此方法通过递归获取树形数据
    private List<?> getCatListByParentId(long parentId) {
        //首先通过parentId查询所有的节点
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        //定义返回结果
        List resultList = new ArrayList();
        int count = 0;
        for (TbItemCat tbItemCat : list) {
            //判断是否是非叶子节点
            if (tbItemCat.getIsParent()) {
                CatNode catNode = new CatNode();
                catNode.setUrl("/products/" + tbItemCat.getId() + ".html");
                //如果该节点的parentId为0，那么catNode的name属性应该为“<a href='/products/161.html'>电脑、办公</a>”
                if (tbItemCat.getParentId() == 0) {
                    catNode.setName("<a href='/products/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a>");
                } else {
                    catNode.setName(tbItemCat.getName());
                }
                //递归实现tree结构，如果不是叶子节点就一直按照上述规则（相当于一个m叉树）递归下去，递归结束条件：是叶子节点

                /*这里的递归的算法思想就是：找一个分界点，分出特殊的节点和一般的节点
                这里的特殊节点为叶子节点，叶子节点就是递归的结束
                一般节点为非叶子节点，则要递归下去，直到遇见叶子节点*/
                /*
                    程序的实现：先使用for循环遍历所有的节点
                               再进行一个if判断，判断是否是叶子节点
                               不是叶子节点，按照非叶子节点的规则填充结果对象属性，在要递归的属性中使用递归
                               是叶子节点的话，按照叶子节点的规则填充结果对象属性，一般这里递归结束
                 */
                catNode.setItem(getCatListByParentId(tbItemCat.getId()));
                resultList.add(catNode);
                count++;
                if (count > 13 && tbItemCat.getParentId() == 0)
                    break;
            } else { //如果是叶子节点，直接添加字符串/products/251.html|清洁，递归的截止条件
                resultList.add("/products/" + tbItemCat.getId() + ".html|" + tbItemCat.getName() + "");
            }
        }
        return resultList;
    }

}
