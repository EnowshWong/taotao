package com.taotao.sso.service.impl;

import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.sso.dao.JedisClient;
import com.taotao.sso.service.UserService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.sso.service.impl
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/30 16:22
 *   *
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper tbUserMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_USER_SESSION}")
    private String REDIS_USER_SESSION;

    @Value("${REDIS_USER_SESSION_EXPIRE}")
    private Integer REDIS_USER_SESSION_EXPIRE;

    @Value("${COOKIE_NAME}")
    private String COOKIE_NAME;

    //该方法检查数据是否重复，即从数据库中按条件查询数据，如果没有查询出来数据，则说明没有重复
    @Override
    public TaotaoResult checkDataInfo(String param, Integer type) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        if (1 == type) {
            criteria.andUsernameEqualTo(param);
        } else if (2 == type) {
            criteria.andPhoneEqualTo(param);
        } else if (3 == type) {
            criteria.andEmailEqualTo(param);
        }
        List<TbUser> tbUsers = tbUserMapper.selectByExample(example);
        if (tbUsers != null && tbUsers.size() > 0) {
            return TaotaoResult.build(200, "OK", false);
        } else return TaotaoResult.build(200, "OK", true);
    }

    @Override
    public TaotaoResult registerUser(TbUser tbUser) {
        tbUser.setCreated(new Date());
        tbUser.setUpdated(new Date());
        //md5加密
        tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));
        tbUserMapper.insert(tbUser);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> tbUsers = tbUserMapper.selectByExample(example);
        if (tbUsers == null || tbUsers.size() == 0) {
            return TaotaoResult.build(400, "用户名或密码错误");
        }
        TbUser tbUser = tbUsers.get(0);
        //如果密码错误
        if (!tbUser.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
            return TaotaoResult.build(400, "用户名或密码错误");
        }
        String token = UUID.randomUUID().toString();
        //把用户信息写入缓存
        //写入之前，先把密码清空
        tbUser.setPassword(null);
        //用户信息转为json串保存
        jedisClient.set(REDIS_USER_SESSION + ":" + token, JsonUtils.objectToJson(tbUser));
        //设置过期时间
        jedisClient.expire(REDIS_USER_SESSION + ":" + token, REDIS_USER_SESSION_EXPIRE);
        //设置cookie
        CookieUtils.setCookie(request, response, COOKIE_NAME, token);
        return TaotaoResult.ok(token);
    }

    @Override
    public TaotaoResult findUserByToken(String token) {
        //在缓存库中查询token
        String value = jedisClient.get(REDIS_USER_SESSION + ":" + token);
        if (org.apache.commons.lang3.StringUtils.isBlank(value))
            return TaotaoResult.build(400, "没有此token或者session已过期");
        //用户处于活动状态，重置过期时间
        jedisClient.expire(REDIS_USER_SESSION + ":" + token, REDIS_USER_SESSION_EXPIRE);

        return TaotaoResult.ok(JsonUtils.jsonToPojo(value, TbUser.class));
    }

    @Override
    public TaotaoResult logout(String token) {
        long del = jedisClient.del(REDIS_USER_SESSION + ":" + token);
        if (del == 0)
            return TaotaoResult.build(400, "不存在此token");
        return TaotaoResult.ok();

    }
}
