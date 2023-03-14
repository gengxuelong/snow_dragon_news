package com.example.demo.controller;


import com.example.demo.mapper.AdminMapper;
import com.example.demo.pojo.Admin;
import com.example.demo.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * 控制管理者信息的修改，既修改密码页面
 */
@Controller
@RequestMapping("modify")
public class ModifyAdminPageController {

    /**
     * @Auther: GengXuelong
     * <p> 变量描述如下:
     * @Description:
     *     数据库交互
     */
    @Autowired
    AdminMapper mapper;


    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     前往信息修改页
     */
    @RequestMapping("/to_modify")
    public String to_modify(){
        return "password_modify";
    }

    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     实现更新密码功能
     */
    @PostMapping("/modify_admin")
    public String update_admin(@RequestParam("oldPassword") String oldPassword,
                               @RequestParam("newPassword") String newPassword,
                               @RequestParam("reNewPassword") String reNewPassword,
                               Model model,
                               HttpServletRequest request,
                               HttpServletResponse response){

        oldPassword = MD5Util.zeroToTwoPass(oldPassword);
        String name =  (String)request.getSession().getAttribute("username");
        Admin admin1 = mapper.getAdmin(name);
        if(!admin1.getPassword().equals(oldPassword)){
            model.addAttribute("msg","原密码不符合，无法更改");
            return "password_modify";
        }
        if(newPassword!=null&&newPassword.equals(reNewPassword)){
            newPassword = MD5Util.zeroToTwoPass(newPassword);
            Admin admin = new Admin(name,newPassword);
            int res = 0;
            res = mapper.updateAdmin(admin);
            if(res>0){
                model.addAttribute("msg","更改成功");
                return "password_modify";
            }else{
                model.addAttribute("msg","更新失败");
                return "password_modify";
            }
        }else {
            model.addAttribute("msg", "新旧密码不符合，无法更改");
            return "password_modify";
        }
    }
}
