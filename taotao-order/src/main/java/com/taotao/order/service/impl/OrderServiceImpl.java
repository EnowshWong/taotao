package com.taotao.order.service.impl;

import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.dao.JedisClient;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.order.service.impl
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2018/1/29 9:50
 *   *
 **/
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private TbOrderMapper tbOrderMapper;
    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;
    @Autowired
    private TbOrderShippingMapper tbOrderShippingMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${REDIS_ORDER_ID}")
    private String REDIS_ORDER_ID;
    @Value("${REDIS_ORDER_ID_INIT}")
    private String REDIS_ORDER_ID_INIT;
    @Value("${REDIS_ORDER_ITEM_ID}")
    private String REDIS_ORDER_ITEM_ID;

    @Override
    public TaotaoResult createOrder(TbOrder tbOrder, List<TbOrderItem> orderItems, TbOrderShipping orderShipping) {
        //插入tbOrder
        //生成orderId
        //使用redis自增长策略生成orderId
        //先向redis查询
        String string = jedisClient.get(REDIS_ORDER_ID);
        if (StringUtils.isBlank(string)) {
            jedisClient.set(REDIS_ORDER_ID, REDIS_ORDER_ID_INIT);
        }
        long orderId = jedisClient.incr(REDIS_ORDER_ID);
        tbOrder.setOrderId(orderId + "");
        //补全pojo
        //1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭'
        tbOrder.setStatus(1);
        Date date = new Date();
        tbOrder.setCreateTime(date);
        tbOrder.setUpdateTime(date);
        tbOrder.setBuyerRate(0);
        tbOrderMapper.insert(tbOrder);
        //插入orderItems
        for (TbOrderItem orderItem : orderItems) {
            long orderItemId = jedisClient.incr(REDIS_ORDER_ITEM_ID);
            orderItem.setId(orderId + "");
            orderItem.setOrderId(orderId + "");
            tbOrderItemMapper.insert(orderItem);
        }
        //插入orderShipping
        orderShipping.setOrderId(orderId + "");
        orderShipping.setCreated(date);
        orderShipping.setUpdated(date);
        tbOrderShippingMapper.insert(orderShipping);
        return TaotaoResult.ok(orderId);
    }
}
