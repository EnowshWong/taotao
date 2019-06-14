package com.taotao.service;

import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TaotaoResult;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.service
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/14 21:26
 *   *
 **/
public interface ItemCatParamService {

    TaotaoResult findItemCatParam(Long itemCatId);

    EUDataGridResult findItemCatParamList(Integer page, Integer rows);

    TaotaoResult addItemCatParam(Long cid, String paramData);
}
