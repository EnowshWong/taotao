package com.taotao.portal.service.impl;

import com.taotao.pojo.TaotaoResult;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.OrderService;
import com.taotao.utils.HttpClientUtil;
import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.portal.service.impl
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2018/2/1 12:39
 *   *
 **/
@Service
public class OrderServiceImpl implements OrderService {
    @Value("${ORDER_BASE_URL}")
    private String ORDER_BASE_URL;
    @Value("${ORDER_CREATE_ORDER}")
    private String ORDER_CREATE_ORDER;

    @Override
    public String createOrder(Order order) {
        //调用order服务向数据库存储表单post请求
        String json = HttpClientUtil.doPostJson(ORDER_BASE_URL + ORDER_CREATE_ORDER, JsonUtils.objectToJson(order));
        //如果成功返回订单号
        if (StringUtils.isBlank(json))
            return "订单创建失败";
        TaotaoResult result = JsonUtils.jsonToPojo(json, TaotaoResult.class);
        return result.getData() + "";
    }
}
