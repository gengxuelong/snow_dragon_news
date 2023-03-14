package com.example.demo.controller;

import com.example.demo.mapper.NewsMapper;
import com.example.demo.pojo.NewsAndroid;
import com.example.demo.pojo.NewsEntity;
import com.example.demo.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @Auther: GengXuelong
 * <p> 类 功能描述如下:
 * @Description:
 *     新闻展示和查询页面
 */
@Controller
@RequestMapping("query")
public class QueryNewsPageController {

    /**
     * @Auther: GengXuelong
     * <p> 变量描述如下:
     * @Description:
     *     和数据库的交互
     */
    @Autowired
    NewsMapper mapper;

    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     前往密码修改页
     */
    @RequestMapping("/to_query")
    public String to_query(){
        return "show_news";
    }

    /**
     * @Auther: GengXuelong
     * <p> 方法功能描述如下：
     * @Description:
     *      通过id查询功能的实现
     *
     */
    @RequestMapping("/query_by_id")
    public String query_by_id(@RequestParam("id") String idString,
                            Model model,
                          HttpSession session){

        Long id ;
        try{
            id = Long.valueOf(idString.trim());
            System.out.println(id);
        }catch (Exception e){
            id = -1L;
        }
        NewsEntity news = mapper.getNewsById(id);
        List<NewsEntity> newsList = new LinkedList<>();
        if(news !=null){
            System.out.println(news.toString());
            newsList.add(news);
        }else{
            System.out.println("id错误，没查到");
        }
        List<NewsAndroid> newsAndroidList = MyUtils.convertEntityToAndroidInNews(newsList);
        session.setAttribute("newsList",newsAndroidList);
        session.setAttribute("yeshu",1);
        session.setAttribute("yejiao",1);
        session.setAttribute("way","query_by_id");
        session.setAttribute("clue",null);
        return "show_news";
    }


    /**
     * @Auther: GengXuelong
     * <p> 方法功能描述如下：
     * @Description:
     *      通过tag查询功能的实现
     *
     * @param tag
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/query_by_tag")
    public String query_by_tag(
            @RequestParam("tag") String tag,
            Model model,
            HttpSession session){
        int module = MyUtils.getModuleIdByTag(tag);
        System.out.println(module);
        int num = mapper.getNumByModule_id(module);
        int yeshu = num/30 + 1;
        List<NewsEntity> newsList = mapper.getNewsByModule_idByLimit(module,0,30);
        List<NewsAndroid> newsAndroidList = MyUtils.convertEntityToAndroidInNews(newsList);
        session.setAttribute("newsList",newsAndroidList);
        session.setAttribute("yeshu",yeshu);
        session.setAttribute("way","query_by_tag");
        session.setAttribute("clue",tag);
        session.setAttribute("yejiao",1);
        return "show_news";
    }


    /**
     * @Auther: GengXuelong
     * <p> 方法功能描述如下：
     * @Description:
     *      通过title模糊查询功能的实现
     *
     * @param title
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/query_by_title_like")
    public String query_by_title_like(
            @RequestParam("title") String title,
            Model model,
            HttpSession session){

        if(title==null||"".equals(title)||" ".equals(title)||"  ".equals(title)){
            return "show_news";
        }
        int num = mapper.getNumByTitleLike("%"+title+"%");
        int yeshu = num/30 + 1;
        List<NewsEntity> newsList = mapper.getNewsByTitleByLimit("%"+title+"%",0,30);
        List<NewsAndroid> newsAndroidList = MyUtils.convertEntityToAndroidInNews(newsList);
        session.setAttribute("newsList",newsAndroidList);
        session.setAttribute("yeshu",yeshu);
        session.setAttribute("way","query_by_title_like");
        session.setAttribute("clue","%"+title+"%");
        session.setAttribute("yejiao",1);
        return "show_news";
    }


    /**
     * @Auther: GengXuelong
     * <p> 方法功能描述如下：
     * @Description:
     *      通过输入页脚 跳转到指定分页的功能的实现
     *
     * @param
     * @param session
     * @return
     */
    @RequestMapping("/jump")
    public String jump(@RequestParam("yejiao") String yejiaoString,
                       HttpSession session){

        int yejiao;
        try{
            yejiao = Integer.parseInt(yejiaoString.trim());
        }catch (Exception e){
            return "show_news";
        }
        String way = (String)session.getAttribute("way");
        String clue = (String)session.getAttribute("clue");
        System.out.println(way);
        System.out.println(clue);
        int yeshu = (int)session.getAttribute("yeshu");
        System.out.println(yeshu);
        if(way==null){
            return "show_news";
        }else if(way.equals("query_by_title_like")){
            List<NewsEntity> newsList1 = mapper.getNewsByTitleByLimit(clue,(yejiao-1)*30,30);
            List<NewsAndroid> newsAndroidList = MyUtils.convertEntityToAndroidInNews(newsList1);
            session.setAttribute("newsList",newsAndroidList);
            session.setAttribute("yejiao",yejiao);
            return "show_news";
        }else if(way.equals("query_by_tag")){
            List<NewsEntity> newsList1 = mapper.getNewsByModule_idByLimit(MyUtils.getModuleIdByTag(clue),(yejiao-1)*30,30);
            List<NewsAndroid> newsAndroidList = MyUtils.convertEntityToAndroidInNews(newsList1);
            session.setAttribute("newsList",newsAndroidList);
            session.setAttribute("yejiao",yejiao);
            return "show_news";
        }

        else{
            return "show_news";
        }
    }


    /**
     * @Auther: GengXuelong
     * <p> 方法功能描述如下：
     * @Description:
     *      分页展示中 ，“下一页”功能的实现
     *
     * @param session
     * @return
     */
    @RequestMapping("/jump/next")
    public String jump_next(HttpSession session){

        System.out.println("进入jump_next函数");
        String way = (String)session.getAttribute("way");
        String clue = (String)session.getAttribute("clue");
        int yeshu = (int)session.getAttribute("yeshu");
        int yejiao = (int)session.getAttribute("yejiao")+1;
        System.out.println(way);
        System.out.println(clue);
        System.out.println(yeshu);
        System.out.println(yejiao);
        if(way==null||yejiao>yeshu){
            return "show_news";
        }else if(way.equals("query_by_title_like")){
            List<NewsEntity> newsList1 = mapper.getNewsByTitleByLimit(clue,(yejiao-1)*30,30);
            List<NewsAndroid> newsAndroidList = MyUtils.convertEntityToAndroidInNews(newsList1);
            session.setAttribute("newsList",newsAndroidList);
            session.setAttribute("yejiao",yejiao);
                return "show_news";
        }else if(way.equals("query_by_tag")){
            List<NewsEntity> newsList1 = mapper.getNewsByModule_idByLimit(MyUtils.getModuleIdByTag(clue),(yejiao-1)*30,30);
            List<NewsAndroid> newsAndroidList = MyUtils.convertEntityToAndroidInNews(newsList1);
            session.setAttribute("newsList",newsAndroidList);
            session.setAttribute("yejiao",yejiao);
            return "show_news";
        }else{
            return "show_news";
        }
    }

    /**
     * @Auther: GengXuelong
     * <p> 方法功能描述如下：
     * @Description:
     *      分页展示中 ，“上一页”功能的实现
     *
     * @param session
     * @return
     */
    @RequestMapping("/jump/last")
    public String jump_last(HttpSession session){

        System.out.println("进入jump_last函数");
        String way = (String)session.getAttribute("way");
        String clue = (String)session.getAttribute("clue");
        int yeshu = (int)session.getAttribute("yeshu");
        int yejiao = (int)session.getAttribute("yejiao")-1;
        System.out.println(way);
        System.out.println(clue);
        System.out.println(yeshu);
        if(way==null||yejiao<1){
            return "show_news";
        }else if(way.equals("query_by_title_like")){
            List<NewsEntity> newsList1 = mapper.getNewsByTitleByLimit(clue,(yejiao-1)*30,30);
            List<NewsAndroid> newsAndroidList = MyUtils.convertEntityToAndroidInNews(newsList1);
            session.setAttribute("newsList",newsAndroidList);
            session.setAttribute("yejiao",yejiao);
            return "show_news";
        }else if(way.equals("query_by_tag")){
            List<NewsEntity> newsList1 = mapper.getNewsByModule_idByLimit(MyUtils.getModuleIdByTag(clue),(yejiao-1)*30,30);
            List<NewsAndroid> newsAndroidList = MyUtils.convertEntityToAndroidInNews(newsList1);
            session.setAttribute("newsList",newsAndroidList);
            session.setAttribute("yejiao",yejiao);
            return "show_news";
        }else{
            return "show_news";
        }
    }
    /**
     * @Auther: GengXuelong
     * <p> 方法功能描述如下：
     * @Description:
     *    进入正文 按钮的功能实现
     *
     * @param id
     * @param request
     * @param response
     */
    @GetMapping("/to_zhengwen/{id}")
    public String  zhengwen(@PathVariable("id") Long id,
                         Model model,
                         HttpServletRequest request,
                         HttpServletResponse response){

        System.out.println("进入正文函数");
        NewsEntity news = mapper.getNewsById(id);
        model.addAttribute("text",news.getContent());
       return "zhengwen";
    }

    /**
     * @Auther: GengXuelong
     * <p> 方法功能描述如下：
     * @Description:
     *      展示类表中，删除按钮 功能的实现
     *
     * @param id
     * @param model
     * @param request
     * @param response
     * @throws ServletException
     */
    @GetMapping("/delete/{id}")
    public void deleteNews_2(@PathVariable("id") Long id,
                             Model model,
                             HttpServletRequest request,
                             HttpServletResponse response) throws ServletException {

        int res = 0;
        NewsEntity news = mapper.getNewsById(id);
        try{
            res = mapper.deleteNews(id);
        }catch (Exception ignored){
        }
        List<NewsAndroid> newsList = (List<NewsAndroid>) request.getSession().getAttribute("newsList");
        if(newsList !=null) {
            newsList.remove(MyUtils.convertEntityToAndroidInNews(news));
        } else{
            System.out.println("newsList是空的");
        }
        request.getSession().setAttribute("newsList",newsList);
        try {
            request.getRequestDispatcher("/query/to_query").forward(request,response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
