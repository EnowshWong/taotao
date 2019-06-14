package com.taotao.portal.service.impl;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;
import com.taotao.utils.HttpClientUtil;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.portal.service.impl
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/12/4 21:51
 *   *
 **/
@Service
public class UserServiceImpl implements UserService {

    @Value("${SSO_FIND_USER_BY_TOKEN}")
    private String SSO_FIND_USER_BY_TOKEN;

    @Value("${LOGIN_PAGE_URL}")
    public String LOGIN_PAGE_URL;

    @Override
    public TbUser findUserByToken(String token) {
        try {
            //调用httpClient向sso工程发送get请求
            String json = HttpClientUtil.doGet(SSO_FIND_USER_BY_TOKEN + "/" + token);
            TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbUser.class);
            //以状态码为判断标志
            if (taotaoResult.getStatus() == 200)
                return (TbUser) taotaoResult.getData();
            return (TbUser) taotaoResult.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
