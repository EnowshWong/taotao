package com.taotao.portal.controller;

import com.taotao.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.portal.controller
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/12/12 21:16
 *   *
 **/
@Controller
public class CartController {
    @Autowired
    private CartService cartService;

    @RequestMapping("/cart/add/{itemId}")
    public String addItemCart(Model model, @PathVariable long itemId, @RequestParam(defaultValue = "1") Integer num, HttpServletRequest request, HttpServletResponse response) {
        TaotaoResult taotaoResult = cartService.addItemCart(itemId, num, request, response);
        if (taotaoResult.getStatus() != 200)
            return "error";
        //添加完成之后，返回购物车列表页面
        //这样做是错误的，因为一次请求既包含了创建cookie又包含了访问cookie
        //很有可能访问时，cookie还没有传到浏览器，导致访问不到，所以最好分开两个请求
//        List<CartItem> itemCartList = cartService.getItemCartList(request, response);
//        model.addAttribute("cartList",itemCartList);
//        return "cart";
        return "cartSuccess";
    }

    @RequestMapping("/cart/showCart")
    public String showCart(Model model, HttpServletRequest request, HttpServletResponse response) {
        List<CartItem> itemCartList = cartService.getItemCartList(request, response);
        model.addAttribute("cartList", itemCartList);
        return "cart";
    }

    @RequestMapping("/cart/delete/{itemId}")
    public String deleteCart(Model model, @PathVariable long itemId, HttpServletRequest request, HttpServletResponse response) {
        cartService.deleteItemCart(itemId, request, response);
        //重定向到这个url
        return "redirect:/cart/showCart.html";
    }
}
