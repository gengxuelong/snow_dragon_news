package com.example.demo.controller;

import com.example.demo.mapper.AdminMapper;
import com.example.demo.pojo.Admin;
import com.example.demo.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 登录页面的功能控制
 */
@Controller
@RequestMapping("/login/")
public class LoginPageController {

    @Autowired
    AdminMapper mapper;


    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     前往登录页
     */
    @RequestMapping("/to_login")
    public String to_login(HttpSession session){
        session.invalidate();
        return "index";
    }

    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description: 用户登录功能的实现
     */
    @PostMapping("/do_login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletResponse response,
                        HttpServletRequest request,
                        HttpSession session,
                        Model model) {

        String msg = null;
        if ("".equals(username)) {//username 永不等于null
            msg = "小主，用户名还没写呢";
            model.addAttribute("msg", msg);
            return "index";
        } else if ("".equals(password)) {
            msg = "小主，密码还没写呢";
            model.addAttribute("msg", msg);
            return "index";
        }
        password = MD5Util.zeroToTwoPass(password);
        List<Admin> adminList = mapper.getAdminList();
        for (Admin admin : adminList) {
            if (admin.getName().equals(username)) {
                if (admin.getPassword().equals(password)) {
                    session.setAttribute("username", username);
                    return "main";
                } else {
                    msg = "小主，密码错误了哦";
                    model.addAttribute("msg", msg);
                    return "index";
                }
            }
        }
        msg = "小主，用户名都错误了哦，所填用户不存在呢";
        model.addAttribute("msg", msg);
        return "index";
    }
}
