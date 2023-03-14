package com.example.demo.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @Auther: GengXuelong
 * @Description:
 *      和数据库交互的实体类
 */
@Data
public class NewsLogsEntity {
    private final Long id = -1L;
    private Long user_id;
    private Long news_id;
    private Date view_time;
    private final int prefer_degree = 1;

    public NewsLogsEntity(Long user_id, Long news_id, Date view_time) {
        this.user_id = user_id;
        this.news_id = news_id;
        this.view_time = view_time;
    }
}
