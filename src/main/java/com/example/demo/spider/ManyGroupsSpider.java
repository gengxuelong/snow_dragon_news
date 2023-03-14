package com.example.demo.spider;

import com.example.demo.pojo.NewsEntity;
import com.example.demo.utils.MyUtils;
import org.assertj.core.util.DateUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: GengXuelong
 * <p> 类 功能描述如下:
 * @Description:
 *     爬虫类，从互联网中获取当天的时事新闻
 */
public class ManyGroupsSpider {
    /**
     * @Auther: GengXuelong
     * <p> 变量描述如下:
     * @Description:
     *     爬去新闻的地址
     */
    private static final String  url = "http://xinhuanet.com";

    /**
     * @Auther: GengXuelong
     * <p> 变量描述如下:
     * @Description:
     *     爬去新闻的主要分类子地址
     */
    private static final Map<String, String> hrefsMap = new LinkedHashMap<>();

    static {
        hrefsMap.put("world","/worldpro/");
        hrefsMap.put("mil","/milpro/");
        hrefsMap.put("fortune","/fortunepro/");
        hrefsMap.put("politics","/politicspro/");
    }

    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     得到爬取的时事新闻
     */
    public static List<NewsEntity> getTodayNews()  {

        Date date = DateUtil.yesterday();
        String today = DateUtil.formatAsDatetime(date);
        String[] todays = today.split("-");
        String year = todays[0];
        String month = todays[1];
        String day = todays[2].substring(0,2);
        List<NewsEntity> newsEntityList = new ArrayList<>();
        for(Map.Entry<String,String> mapping : hrefsMap.entrySet()) {
            try {
                handleMapping(mapping, newsEntityList,year,month,day);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        MyUtils.killRepeat(newsEntityList);
        return newsEntityList;
    }

    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     getTodayNews() 的一个辅助工具函数
     */
    public static void handleMapping(Map.Entry<String,String> mapping,
                                     List<NewsEntity> newsEntityList, String year,
                                     String month, String day) throws IOException {

        String href = url + mapping.getValue();
        String split = mapping.getKey();
        URL url = new URL(href);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        if (connection.getResponseCode() == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            Pattern p = Pattern.compile("<a.*?href=[\"']?((https?://)?/?[^\"']+)[\"']?.*?>(.+)</a>");
            Matcher matcher = null;
            String line = "";
            while ((line = reader.readLine()) != null) {
                matcher = p.matcher(line);
                if (matcher.find()) {
                    String newLinkParent = matcher.group(0).trim();
                    String[] strings = newLinkParent.split("</a>");
                    for (int i = 0; i < strings.length; i++) {
                        strings[i] = strings[i]+"</a>";
                        matcher = p.matcher(strings[i]);
                        if(matcher.find()){
                            strings[i] = matcher.group(1).trim();
                            String newLink = strings[i];
                            boolean b = newLink.startsWith("http://www.news.cn/" + split + "/" + year + "-" + month + "/" + day);
                            if (b) {
                                System.out.println("链接:"+newLink);
                                PageHandlerSpider.getInfo(newLink, newsEntityList);
                            }
                        }
                    }
                }
            }
        }
    }
}
