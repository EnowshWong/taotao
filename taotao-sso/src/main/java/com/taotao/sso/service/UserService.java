package com.taotao.sso.service;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.sso.service
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/30 16:21
 *   *
 **/
public interface UserService {
    TaotaoResult checkDataInfo(String param, Integer type);

    TaotaoResult registerUser(TbUser tbUser);

    TaotaoResult login(String username, String password, HttpServletRequest request, HttpServletResponse response);

    TaotaoResult findUserByToken(String token);

    TaotaoResult logout(String token);
}
