package com.taotao.portal.interceptor;

import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;
import com.taotao.portal.service.impl.UserServiceImpl;
import com.taotao.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.portal.interceptor
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/12/4 22:10
 *   *
 **/
public class LoginInterceptor implements HandlerInterceptor {

    //这里相当于controller的注入,也可以注入service的实现类，都可以当成bean来进行管理
    @Autowired
    private UserServiceImpl userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //第一步，先从cookie中取出token
        String token = CookieUtils.getCookieValue(request, "TT_COOKIE");
        //第二步，根据token，调用服务接口取出用户
        TbUser tbUser = userService.findUserByToken(token);
        //第三步，判断用户是否存在，存在则放行，返回true，不存在重定向到登陆页面，并把拦截的url传递给登陆页面，返回false
        if (tbUser == null) {
            response.sendRedirect(userService.LOGIN_PAGE_URL + "?redirect=" + request.getRequestURL());
            return false;
        }
        //将用户信息存入request中，添加到order表中
        request.setAttribute("user", tbUser);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
