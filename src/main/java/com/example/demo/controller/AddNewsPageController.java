package com.example.demo.controller;

import com.example.demo.mapper.NewsMapper;
import com.example.demo.pojo.NewsAndroid;
import com.example.demo.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: GengXuelong
 * <p> 类 功能描述如下:
 * @Description:
 *     新闻添加页面功能控制
 */
@Controller
@RequestMapping("add")
public class AddNewsPageController {
    /**
     * @Auther: GengXuelong
     * <p> 变量描述如下:
     * @Description:
     *     数据库交互
     */
    @Autowired
    NewsMapper mapper;

    @RequestMapping("/test")
    public void test(){
        int a = 1/0;
    }

    @RequestMapping("/to_add")
    public String to_add(){
        return "add_news";
    }

    /**
     * @Auther: GengXuelong
     * <p> 方法功能描述如下：
     * @Description:
     *      实现新闻添加的功能
     * @param title
     * @param tag
     * @param article
     * @param model
     * @return
     */
    @PostMapping("/do_add")
    public String add_news(
            @RequestParam("title") String title,
            @RequestParam("tag") String tag,
            @RequestParam("article") String article,
            Model model){

        System.out.println("进入add_news函数");
        String msg = null;
        if("".equals(title)){
            msg = "标题是必须填的哦小主";
            model.addAttribute("msg",msg);
            return "add_news";
        }else if("".equals(tag)){
            msg = "标签是必须选的哦小主";
            model.addAttribute("msg",msg);
            return "add_news";
        }else if("".equals(article)||" ".equals(article)){
            msg = "一个新闻怎么能没有正文呢小主";
            model.addAttribute("msg",msg);
            return "add_news";
        }
        NewsAndroid news = new NewsAndroid(null,title,tag,article);
        int a = mapper.addOne(MyUtils.convertAndroidToEntityInNews(news));
        if(a == 1){
            msg = "小主，添加成功！";
            model.addAttribute("msg",msg);
            return "add_news";
        }else{
            msg = "对不起小主，由于系统原因，添加失败";
            model.addAttribute("msg",msg);
            return "add_news";
        }

    }

    /**
     * @Auther: GengXuelong
     * <p> 方法功能描述如下：
     * @Description:
     *      实现新闻添加的功能
     *      另一种页面跳转方式
     *
     * @param title
     * @param tag
     * @param article
     * @param request
     * @param response
     */
    @GetMapping("/do_add1")
    public void add_news1(
            @RequestParam("title") String title,
            @RequestParam("tag") String tag,
            @RequestParam("article") String article,
            HttpServletRequest request,
            HttpServletResponse response){

        System.out.println("进入add_news1函数");
        String msg = null;
        if("".equals(title)){
            msg = "标题是必须填的哦小主";
        }else if("".equals(tag)){
            msg = "标签是必须选的哦小主";
        }else if("".equals(article)||" ".equals(article)){
            msg = "一个新闻怎么能没有正文呢小主";
        }else{
            NewsAndroid news = new NewsAndroid(null,title,tag,article);
            int a = mapper.addOne(MyUtils.convertAndroidToEntityInNews(news));
            if(a == 1){
                msg = "小主，添加成功！";
            }else{
                msg = "对不起小主，由于系统原因，添加失败";
            }
        }
        request.setAttribute("msg",msg);//post方法不支持
        try {
            //forward()是request中的参数继续传递，redirect()则是重新生成request了。
            request.getRequestDispatcher("/add").forward(request,response);//重定向，路径显示还是直接输入路径：/add/add_news1
        } catch (ServletException | IOException e) {
            System.out.println("跳转失败");
        }
    }
}
