package com.example.demo.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: GengXuelong
 * <p> 类 功能描述如下:
 * @Description:
 *     自定义拦截器
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {
    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     拦截判断函数，判断管理员是否已经登录，只有已登录的管理员才能进入管理页面
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String name = (String)request.getSession().getAttribute("username");
        if(name ==null){
            request.setAttribute("msg","没有权限，请先登录哟");
            request.getRequestDispatcher("/login/to_login").forward(request,response);
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
