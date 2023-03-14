package com.example.demo.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Auther: GengXuelong
 * @Description:
 * 和数据库交互的实体类
 */
@Data
@NoArgsConstructor
public class NewsEntity {
    private  Long id = -1L;
    private String content;
    private Date news_time;
    private String title;
    private int module_id;
    private String url;


    public NewsEntity(String content, Date news_time, String title, int module_id, String url) {
        this.content = content;
        this.news_time = news_time;
        this.title = title;
        this.module_id = module_id;
        this.url = url;
    }

    public NewsEntity(Long id, String content, Date news_time, String title, int module_id, String url) {
        this.id = id;
        this.content = content;
        this.news_time = news_time;
        this.title = title;
        this.module_id = module_id;
        this.url = url;
    }
}
