package com.taotao.service;

import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.service
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/17 14:54
 *   *
 **/
public interface ContentService {
    EUDataGridResult findContentList(int page, int rows, Long categoryId);

    TaotaoResult addContent(TbContent content);
}
