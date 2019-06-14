package com.taotao.service;

import com.taotao.pojo.EUDataGridResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.service
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/9 18:31
 *   *
 **/
public interface ItemService {

    //根据商品id查询商品
    TbItem findItemById(Long id) throws Exception;

    //分页显示商品列表，使用easyUI,
    EUDataGridResult findItemList(int page, int rows) throws Exception;

    //添加商品
    TaotaoResult addItem(TbItem tbItem, String desc, String itemParam) throws Exception;

    //添加商品规格参数
    TaotaoResult addItemParam(Long itemId, String itemParam);
}
