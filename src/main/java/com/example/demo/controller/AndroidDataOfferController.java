package com.example.demo.controller;

import com.example.demo.mapper.UserPhoneMapper;
import com.example.demo.pojo.AndroidText;
import com.example.demo.pojo.NewsAndroid;
import com.example.demo.service.DataOfferService;
import com.example.demo.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: GengXuelong
 * @Date: 2022/7/31 15:46
 * @Description:
 */
@RestController
@RequestMapping("/data")
public class AndroidDataOfferController {
    /**
     * @Auther: GengXuelong
     * <p> 变量描述如下:
     * @Description:
     *     提供向安卓端呈现数据的服务
     */
    @Autowired
    DataOfferService dataOfferService;
    /**
     * @Auther: GengXuelong
     * <p> 变量描述如下:
     * @Description:
     *     提供和数据库交互的服务
     */
    @Autowired
    UserPhoneMapper userPhoneMapper;




    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     传送每天的时事新闻
     */
    @RequestMapping("/new_news")
    public List<?> someTodayNews(){
        return dataOfferService.offerTodayNewsSegment();
    }

    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *    更具用户的已读信息， 向用户传递基于内容智能推荐算法得出的个性化推荐新闻
     */
    @RequestMapping("/precise")
    public List<NewsAndroid> precise(@RequestParam("user_id") Long user_id){

        return dataOfferService.offerNewsByPrecise(user_id);
    }

    /**
     * @Auther: GengXuelong
     * <p> 功能描述
     * @Description:
     *   根据用户的已读信息，通过基于标题推荐系统向提供用户个性化的新闻数据，数量为100条
     */
    @RequestMapping("/individuation")
    public List<NewsAndroid> individuation(@RequestParam("intarray")int[] intarray){
        return dataOfferService.offerIndividuationNews(intarray);
    }

    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     测试接口
     */
    @RequestMapping("/test")
    public List<NewsAndroid> individuation_test(){
        return dataOfferService.offerIndividuationNews();
    }


    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     通过标题查询新闻，默认为模糊查询
     */
    @RequestMapping("/query/title")
    public  List<NewsAndroid> query_title(@RequestParam("title") String title){
        return dataOfferService.offerNewsByTitle(title);
    }

    /**
     * @Auther: GengXuelong
     * <p> 功能描述
     * @Description:
     *   按标签查询，得到100条随机数据
     */
    @RequestMapping("/query/tag")
    public  List<NewsAndroid> query_tag(@RequestParam("tag") String tag){
         return dataOfferService.offerNewsByTag(tag);
    }
    /**
     * @Auther: GengXuelong
     * <p> 功能描述
     * @Description:
     *   通过图片识别文字功能的实现
     */
    @PostMapping("/api")
    public List<AndroidText> api(@RequestBody String base64){
        return dataOfferService.offerAPIServer(base64);
    }





}
