package com.taotao.rest.service;

import com.taotao.pojo.TaotaoResult;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.rest.service
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/22 16:51
 *   *
 **/
public interface IndexRedisService {
    TaotaoResult syncContentRedis(Long categoryId);
}
