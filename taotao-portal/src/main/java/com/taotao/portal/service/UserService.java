package com.taotao.portal.service;

import com.taotao.pojo.TbUser;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.portal.service
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/12/4 21:51
 *   *
 **/
public interface UserService {
    TbUser findUserByToken(String token);
}
