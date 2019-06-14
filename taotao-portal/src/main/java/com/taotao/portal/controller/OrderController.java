package com.taotao.portal.controller;

import com.taotao.pojo.TbUser;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.OrderService;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
 *   * @CreateDate:     2018/2/1 11:24
 *   *
 **/
@Controller
public class OrderController {
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;

    @RequestMapping("/order/order-cart")
    public String showOrderCart(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<CartItem> itemCartList = cartService.getItemCartList(request, response);
        model.addAttribute("cartList", itemCartList);
        return "order-cart";
    }

    @RequestMapping("/order/create")
    public String createOrder(Order order, Model model, HttpServletRequest request) {
        //从request取出用户信息
        TbUser user = (TbUser) request.getAttribute("user");
        //补全order信息
        order.setBuyerNick(user.getUsername());
        order.setUserId(user.getId());
        String orderId = orderService.createOrder(order);
        model.addAttribute("orderId", orderId);
        model.addAttribute("payment", order.getPayment());
        model.addAttribute("date", new DateTime().plusDays(2).toString("YYYY-MM-DD"));
        return "success";
    }
}
