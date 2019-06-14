package com.taotao.rest.service;

import com.taotao.pojo.TbContent;

import java.util.List;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.rest.service
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/17 16:31
 *   *
 **/
public interface ContentService {

    List<TbContent> findContentListByCategoryId(Long categoryId);
}
