package com.taotao.service;

import com.taotao.pojo.EUTreeNode;

import java.util.List;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.service
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/10 9:14
 *   *
 **/
public interface ItemCatService {
    //分类别查询商品
    List<EUTreeNode> findItemCat(Long parent_id);
}
