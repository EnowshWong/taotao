package com.taotao.sso.controller;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import com.taotao.utils.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.sso.controller
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/30 16:36
 *   *
 **/
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user/check/{param}/{type}")
    @ResponseBody
    public Object checkDataInfo(@PathVariable String param, @PathVariable Integer type, String callback) {
        if (callback == null || StringUtils.isBlank(callback)) {
            //传入参数非空判断
            if (StringUtils.isBlank(param) || type == null || (type != 1 && type != 2 && type != 3))
                return TaotaoResult.build(400, "传入参数有空值或者不合法值");
            TaotaoResult taotaoResult = userService.checkDataInfo(param, type);
            return taotaoResult;
        } else {
            TaotaoResult result = null;
            MappingJacksonValue mappingJacksonValue = null;
            //传入参数非空判断
            if (StringUtils.isBlank(param) || type == null || (type != 1 && type != 2 && type != 3)) {
                result = TaotaoResult.build(400, "传入参数有空值或者不合法值");
                mappingJacksonValue = new MappingJacksonValue(result);
                mappingJacksonValue.setJsonpFunction(callback);
                return mappingJacksonValue;
            }
            result = userService.checkDataInfo(param, type);
            mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult registerUser(TbUser tbUser) {
        try {
            TaotaoResult taotaoResult = userService.registerUser(tbUser);
            return taotaoResult;
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(400, ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        try {
            TaotaoResult login = userService.login(username, password, request, response);
            return login;
        } catch (Exception e) {
            return TaotaoResult.build(400, ExceptionUtil.getStackTrace(e));
        }
    }

    @RequestMapping("/user/token/{token}")
    @ResponseBody
    public Object findUserByToken(@PathVariable String token, String callback) {
        TaotaoResult result = null;
        try {
            result = userService.findUserByToken(token);
        } catch (Exception e) {
            return TaotaoResult.build(400, ExceptionUtil.getStackTrace(e));
        }
        if (StringUtils.isBlank(callback))
            return result;
        else {
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
    }

    @RequestMapping("/user/logout/{token}")
    @ResponseBody
    public Object logout(@PathVariable String token, String callback) {
        TaotaoResult result = null;
        try {
            result = userService.logout(token);
        } catch (Exception e) {
            return TaotaoResult.build(400, ExceptionUtil.getStackTrace(e));
        }
        if (StringUtils.isBlank(callback))
            return result;
        else {
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
    }

    @RequestMapping("/user/showLogin")
    public String showLoginPage(String redirect, Model model) {
        model.addAttribute("redirect", redirect);
        return "login";
    }

    @RequestMapping("/user/showRegister")
    public String showRegisterPage() {
        return "register";
    }
}
