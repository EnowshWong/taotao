package com.taotao.order.service;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;

import java.util.List;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.order.service
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2018/1/29 9:48
 *   *
 **/
public interface OrderService {
    TaotaoResult createOrder(TbOrder tbOrder, List<TbOrderItem> orderItems, TbOrderShipping orderShipping);
}
