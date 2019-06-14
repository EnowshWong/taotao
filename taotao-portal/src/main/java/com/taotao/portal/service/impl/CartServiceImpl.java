package com.taotao.portal.service.impl;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.HttpClientUtil;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.net.www.http.HttpClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.portal.service.impl
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/12/12 19:23
 *   *
 **/
@Service
public class CartServiceImpl implements CartService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_ITEM_INFO_URL}")
    private String REST_ITEM_INFO_URL;
    @Value("${COOKIE_CART_LIST}")
    private String COOKIE_CART_LIST;

    @Override
    public TaotaoResult addItemCart(long id, Integer num, HttpServletRequest request, HttpServletResponse response) {
        //先从cookie中取出订单列表，判断该订单中是否已经有该商品，有的话则数量+1，没有的话则新建
        //取出list,这里的第三个参数true指的是使用utf-8来编码
        List<CartItem> list = getItemCartList(request, response);
        //判断list中是否有该商品
        CartItem cartItem = null; //标志pojo
        for (CartItem item : list) {
            //这里==号比较的是对象，无法实现功能
            if (item.getItemId() == id) {
                item.setNum(item.getNum() + num);
                cartItem = item;
                break;
            }
        }
        //如果没有，则将该商品写入订单中
        if (cartItem == null) {
            //根据商品id查询商品,get请求
            String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_INFO_URL + "/" + id);
            TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItem.class);
            cartItem = new CartItem();
            if (taotaoResult.getStatus() == 200) {
                TbItem item = (TbItem) taotaoResult.getData();
                cartItem.setNum(num);
                cartItem.setImage(item.getImage() == null ? "" : item.getImage().split(",")[0]);
                cartItem.setItemId(id);
                cartItem.setPrice(item.getPrice());
                cartItem.setTitle(item.getTitle());
            }
            list.add(cartItem);
        }
        //将订单写入cookie中,以utf-8形式编码
        //以json形式数据保存在cookie中
        CookieUtils.setCookie(request, response, COOKIE_CART_LIST, JsonUtils.objectToJson(list), true);
        return TaotaoResult.ok();
    }

    @Override
    public List<CartItem> getItemCartList(HttpServletRequest request, HttpServletResponse response) {
        String cartListJson = CookieUtils.getCookieValue(request, COOKIE_CART_LIST, true);
        if (cartListJson == null)
            return new ArrayList<>();
        try {
            //这里转换失败,why? 看pojo属性有没有转换成功
            //分析：由于我在CartItem类加了getImages方法，则向cookie中存储json数据时，默认会加上images属性
            //而将json数据转为pojo列表时，多出一个images属性，而原pojo我并没有定义images属性，所以会转换失败，list为空
            List<CartItem> list = JsonUtils.jsonToList(cartListJson, CartItem.class);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public TaotaoResult deleteItemCart(long id, HttpServletRequest request, HttpServletResponse response) {
        //先从cookie中取出商品
        List<CartItem> list = getItemCartList(request, response);
        for (CartItem item : list)
            if (item.getItemId() == id) {
                list.remove(item);
                break;
            }
        CookieUtils.setCookie(request, response, COOKIE_CART_LIST, JsonUtils.objectToJson(list), true);
        return TaotaoResult.ok();
    }
}