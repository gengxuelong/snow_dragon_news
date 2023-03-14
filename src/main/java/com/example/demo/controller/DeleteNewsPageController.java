package com.example.demo.controller;

import com.example.demo.algorithm.ztl.model.News;
import com.example.demo.mapper.NewsMapper;
import com.example.demo.pojo.NewsAndroid;
import com.example.demo.pojo.NewsEntity;
import com.example.demo.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Auther: GengXuelong
 * <p> 类 功能描述如下:
 * @Description:
 *     新闻删除页面
 */
@RequestMapping("delete")
@Controller
public class DeleteNewsPageController {
    @Autowired
    NewsMapper mapper;


    @RequestMapping("/to_delete")
    public String to_delete(){
        return "delete_news";
    }
    /**
     * @Auther: GengXuelong
     * <p> 方法功能描述如下：
     * @Description:
     *      新闻删除功能的实现
     *
     */
    @PostMapping("/delete_news")
    public String deleteNews(@RequestParam("id")String idString,
                             Model model,
                             HttpSession session
                             )  {
        Long id ;
        try{
            id = Long.valueOf(idString.trim());
        }catch (Exception e){
            id = -1L;
        }
                int res = 0;
        NewsEntity news = null;
        try{
             news = mapper.getNewsById(id);
            res = mapper.deleteNews(id);
        }catch (Exception ignored){
        }
        if(res != 1){
            model.addAttribute("msg","删除失败，新闻号不存在，请检查新闻号是否正确");
            return "delete_news";
        }else {
            model.addAttribute("msg", "恭喜小主，删除成功");
            List<NewsAndroid> newsList = (List<NewsAndroid>)session.getAttribute("newsList");
            if(newsList != null){
                newsList.remove(MyUtils.convertEntityToAndroidInNews(news));
            }
            session.setAttribute("newsList",newsList);
            return "delete_news";
        }
    }


}
