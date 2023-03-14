package com.example.demo.timer.impl;

import com.example.demo.service.DataLoadingService;
import com.example.demo.timer.MyTimer;
import org.assertj.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Auther: GengXuelong
 * <p> 类 功能描述如下:
 * @Description:
 *     用来提供定时的数据刷新
 */
@Service
public class MyTimerImpl implements MyTimer {
    /**
     * @Auther: GengXuelong
     * <p> 变量描述如下:
     * @Description:
     *     提供数据获取的函数
     */
    @Autowired
    DataLoadingService dataLoadingService;


    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     定时运行函数，每24小时刷新一遍缓存和 redis 中的数据,并从互联网获取时事新闻
     */
   @PostConstruct
    public  void startRun(){
        SimpleDateFormat fTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date d1 = new Date();
        Date date = DateUtil.now();
        String today = DateUtil.formatAsDatetime(date);
        String[] todays = today.split("-");
        String year = todays[0];
        String month = todays[1];
        String day = todays[2].substring(0,2);
        try {
            d1 = fTime.parse(year+"/"+month+"/"+day+" 00:00:01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Timer timer = new Timer();
        TimerTask task =new TimerTask(){
            public void run(){
               System.out.println("开始运行");
               //dataLoadingService.loadingFreshNews();
              // dataLoadingService.refreshTheMain();
              // dataLoadingService.AI_Push();
            }
        };
        timer.scheduleAtFixedRate(task, d1,1000*60*60*24);
    }
}
