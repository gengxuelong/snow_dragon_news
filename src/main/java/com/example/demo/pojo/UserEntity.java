package com.example.demo.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @Auther: GengXuelong
 * @Description:
 * 和数据库交互的实体类
 */
@Data
public class UserEntity {
    public Long id;
    public final String pref_list = "{\"1\":{},\"2\":{},\"3\":{},\"4\":{},\"5\":{},\"6\":{},\"7\":{},\"8\":{},\"9\":{},\"10\":{},\"11\":{},\"12\":{},\"13\":{},\"14\":{},\"15\":{},\"16\":{},\"17\":{}}" ;
    public Date latest_log_time;
    public String name;

    public UserEntity(Long id) {
        this.id = id;
        latest_log_time = new Date();
        name = "用户"+id;
    }
}
