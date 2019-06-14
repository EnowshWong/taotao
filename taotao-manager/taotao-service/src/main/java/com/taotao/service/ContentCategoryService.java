package com.taotao.service;

import com.taotao.pojo.EUTreeNode;
import com.taotao.pojo.TaotaoResult;

import java.util.List;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.service
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/17 10:13
 *   *
 **/
public interface ContentCategoryService {
    List<EUTreeNode> findConTentCategory(Long parentId);

    TaotaoResult addContentCategory(Long parentId, String name);

    TaotaoResult updateContentCategory(Long id, String name);

    TaotaoResult deleteContentCategory(Long id);
}
