package com.taotao.search.mapper;

import com.taotao.search.pojo.Item;

import java.util.List;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.search.mapper
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/23 10:47
 *   *
 **/
public interface ItemMapper {
    List<Item> getItemList();
}
