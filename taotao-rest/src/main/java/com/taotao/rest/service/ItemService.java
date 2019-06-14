package com.taotao.rest.service;

import com.taotao.pojo.TaotaoResult;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.rest.service
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/24 15:18
 *   *
 **/
public interface ItemService {
    TaotaoResult getItemById(Long id);

    TaotaoResult getItemDescById(Long id);

    TaotaoResult getItemParamById(Long id);
}
