package com.taotao.portal.service;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.portal.pojo.ItemInfo;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.portal.service
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/24 15:25
 *   *
 **/
public interface ItemService {
    ItemInfo getItemById(Long id);

    String getItemDescById(Long id);

    String getItemParamById(Long id);
}
