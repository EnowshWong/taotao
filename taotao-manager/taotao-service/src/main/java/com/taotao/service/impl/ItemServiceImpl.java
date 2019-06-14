package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.*;
import com.taotao.mapper.TbItemMapper;
import com.taotao.service.ItemService;
import com.taotao.utils.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.service.impl
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/9 18:31
 *   *
 **/
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;

    @Override
    public TbItem findItemById(Long id) throws Exception {
        return itemMapper.selectByPrimaryKey(id);
    }

    //分页显示商品列表，使用easyUI,
    //传入的是page和rows：page：指定当前的页 rows：指定每页的记录数
    //返回的是包含total和rows列表的pojo,该列表是pojo
    //total属性是总记录数，rows属性是pojo的列表
    //注意在jsp页面中的pojo属性要和传入json的pojo属性对应
    @Override
    public EUDataGridResult findItemList(int page, int rows) throws Exception {
        TbItemExample example = new TbItemExample();
        //设置分页
        PageHelper.startPage(page, rows);
        //返回查询列表
        List<TbItem> list = itemMapper.selectByExample(example);
        //构造此对象用来获取total
        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
        //构造返回类型对象
        EUDataGridResult euDataGridResult = new EUDataGridResult();
        euDataGridResult.setRows(list);
        euDataGridResult.setTotal(pageInfo.getTotal());
        return euDataGridResult;
    }

    @Override
    public TaotaoResult addItem(TbItem tbItem, String desc, String itemParam) throws Exception {
        //设置id
        Long id = IDUtils.genItemId();
        //设置状态1-正常，2-下架，3-删除',

        //设置创建和更新时间为当前时间
        Date date = new Date();
        tbItem.setId(id);
        tbItem.setCreated(date);
        tbItem.setUpdated(date);
        tbItem.setStatus((byte) 1);
        itemMapper.insert(tbItem);
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setCreated(date);
        tbItemDesc.setUpdated(date);
        tbItemDesc.setItemId(id);
        tbItemDescMapper.insert(tbItemDesc);
        TaotaoResult result = TaotaoResult.ok();
        if (result.getStatus() != 200) {
            throw new Exception("添加错误");
        }
        result = addItemParam(tbItem.getId(), itemParam);
        if (result.getStatus() != 200) {
            throw new Exception("添加错误");
        }
        return result;
    }

    @Override
    public TaotaoResult addItemParam(Long itemId, String itemParam) {
        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setItemId(itemId);
        tbItemParamItem.setParamData(itemParam);
        tbItemParamItem.setCreated(new Date());
        tbItemParamItem.setUpdated(new Date());
        tbItemParamItemMapper.insert(tbItemParamItem);
        return TaotaoResult.ok();
    }
}
