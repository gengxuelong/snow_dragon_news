package com.example.demo.spider;

import com.example.demo.pojo.NewsEntity;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Date;
import java.util.List;


/**
 * @Auther: GengXuelong
 * <p> 类 功能描述如下:
 * @Description:
 *     爬虫工具类，获取单个新闻的具体信息
 */
public class PageHandlerSpider {

    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     获取单个新闻的具体信息,并将获取的新闻转成 News对象并放入参数newList中
     */
    public static void getInfo(String url, List<NewsEntity> newsEntityList) throws IOException {
        Connection connect = Jsoup.connect(url);
        Document document = connect.get();
        Elements select = document.select("span.title");
        String title = "";
        for (Element element : select) {
            title = element.text();
        }
        Elements select1 = document.select("div#detail");
        String content = "";
        for (Element element : select1) {
             content = element.text();
        }
        Date date = new Date();
        NewsEntity newsEntity = new NewsEntity(content,date,title,15,url);
        newsEntityList.add(newsEntity);
    }
}
