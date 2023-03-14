package com.example.demo.controller;

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
 *     新闻更新页面
 */
@Controller
@RequestMapping("update")
public class UpdateNewsPageController {
    @Autowired
    NewsMapper mapper;
    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     前往内容更新页面
     */
    @RequestMapping("/to_update")
    public String to_update(){
        return "update_news";
    }

    /**
     * @Auther: GengXuelong
     * <p> 方法功能描述如下：
     * @Description:
     *          更新功能的实现
     */
    @PostMapping("/update_news")
    public String updateNews(@RequestParam("id")String idString,
                             @RequestParam("title") String title,
                             @RequestParam("tag") String tag,
                             @RequestParam("article")String article,
                             Model model,
                             HttpSession session)  {

        Long id;
        try{
            id = Long.valueOf(idString.trim());
        System.out.println(id);
        }catch (Exception e){
            id = -1L;
        }
        NewsEntity news = new NewsEntity(id,article,null,title, MyUtils.getModuleIdByTag(tag),null);
        int res = 0;
        try{
            res = mapper.updateNews(news);
        }catch (Exception ignored){
        }
        NewsAndroid newsAndroid = MyUtils.convertEntityToAndroidInNews(news);
        if(res != 1){
            model.addAttribute("msg","更新失败，请检查新闻号是否正确");
            return "update_news";
        }else {
            model.addAttribute("msg", "恭喜小主，更新成功");
            List<NewsAndroid> newsList = (List<NewsAndroid>)session.getAttribute("newsList");
            if(newsList!=null){
                for (NewsAndroid news1 : newsList) {
                    if(news1.getId().equals(newsAndroid.getId())){
                         news1.setArticle(newsAndroid.getArticle());
                         news1.setTag(newsAndroid.getTag());
                         news1.setTitle(newsAndroid.getTitle());
                         break;
                    }
                }
            }
            session.setAttribute("newsList",newsList);
            return "update_news";
        }
    }
}
