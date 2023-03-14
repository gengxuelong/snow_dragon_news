package com.example.demo.controller;

import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Auther: GengXuelong
 * @Date: 2022/8/1 08:17
 * @Description:
 *  用户信息展示页
 */
@Controller
@RequestMapping("/user")
public class UserPageController {

    @Autowired
    UserMapper userMapper;

    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     前往用户新闻展示页面
     */
    @RequestMapping("/to_user")
    public String to_user(Model model){
        List<UserEntity> userEntityList = userMapper.getAll();
        model.addAttribute("userList",userEntityList);
        return "user";
    }
    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     删除用户的操作
     */
    @RequestMapping("/delete/{id}")
    public String delete(Model model, @PathVariable("id")Long id){
        userMapper.deleteUser(id);
        List<UserEntity> userEntityList = userMapper.getAll();
        model.addAttribute("userList",userEntityList);
        return "user";
    }


}
