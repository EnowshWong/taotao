package com.taotao.portal.service;

import com.taotao.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.portal.service
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/12/12 19:21
 *   *
 **/
public interface CartService {
    TaotaoResult addItemCart(long id, Integer num, HttpServletRequest request, HttpServletResponse response);

    List<CartItem> getItemCartList(HttpServletRequest request, HttpServletResponse response);

    TaotaoResult deleteItemCart(long id, HttpServletRequest request, HttpServletResponse response);
}
