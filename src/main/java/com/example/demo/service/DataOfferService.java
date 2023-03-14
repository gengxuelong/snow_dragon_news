package com.example.demo.service;

import com.example.demo.pojo.AndroidText;
import com.example.demo.pojo.NewsAndroid;

import java.util.List;

/**
 * @Auther: GengXuelong
 * <p> 类 功能描述如下:
 * @Description:
 *     提供数据呈现服务
 */
public interface DataOfferService {
    /**
     * 得到若干条当天发生的新闻
     * @return
     */
    List<? extends Object> offerTodayNewsSegment();

    List<NewsAndroid> offerAllNewsSegment();

    List<NewsAndroid> offerNewsByPrecise(Long user_id);

    List<NewsAndroid> offerIndividuationNews(int[] intarray);
    List<NewsAndroid> offerIndividuationNews();

    List<NewsAndroid> offerNewsByTitle(String title);

    List<NewsAndroid> offerNewsByTag(String tag);

    List<AndroidText> offerAPIServer(String base64);
}
