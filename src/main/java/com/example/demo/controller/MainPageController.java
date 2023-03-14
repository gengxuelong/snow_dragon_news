package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Auther: GengXuelong
 * @Date: 2022/7/31 22:27
 * @Description:
 */
@Controller
@RequestMapping("main")
public class MainPageController {
    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     前往主页
     */
    @RequestMapping("/to_main")
    public String to_main(){
        return  "main";
    }

}
