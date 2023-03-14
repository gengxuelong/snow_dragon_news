package com.example.demo.controller;

import com.example.demo.dataCash.DataCash;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.UserPhoneMapper;
import com.example.demo.pojo.AndroidText;
import com.example.demo.pojo.NewsLogsEntity;
import com.example.demo.pojo.UserEntity;
import com.example.demo.service.DataLoadingService;
import com.example.demo.utils.MyJDBCUtils;
import com.example.demo.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: GengXuelong
 * @Date: 2022/7/31 11:00
 * @Description:
 */
@RestController
@RequestMapping("/function")
public class AndroidDataLoadController {

    /**
     * @Auther: GengXuelong
     * <p> 变量描述如下:
     * @Description:
     *     提供各类数据的加载服务
     */
    @Autowired
    DataLoadingService dataLoadingService;

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
     *     记录安卓端用户的浏览信息
     */
    @RequestMapping("/skim")
    public List<AndroidText> getInfoOfUserSkim(@RequestParam("user_id") Long user_id,
                                               @RequestParam("news_id") Long news_id){

        System.out.println("进入data2/skim");
        System.out.println(user_id+"   "+news_id);
        NewsLogsEntity newsLogsEntity = new NewsLogsEntity(user_id,news_id,new Date());
        dataLoadingService.addLog(newsLogsEntity);
        List<AndroidText> androidTexts = new ArrayList<>();
       androidTexts.add( new AndroidText("ok"));
       return androidTexts;
    }

    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     处理安卓端用户的登录请求
     */
    @RequestMapping("/login")
    public List<AndroidText> login(@RequestParam("user_id") Long user_id){

        System.out.println("进入data2/login");
        System.out.println(user_id+"   "+user_id);
        try{
            dataLoadingService.addUser(new UserEntity(user_id));
        }catch (Exception Exception){
            //如果用户手机号之前登录过，由于主键的唯一性，直接取消插入
        }
        List<AndroidText> androidTexts = new ArrayList<>();
        androidTexts.add( new AndroidText("ok"));
        return androidTexts;
    }
}
