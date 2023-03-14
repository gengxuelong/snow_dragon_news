package com.example.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: GengXuelong
 * @Description:
 *  和安卓端交互的过渡类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsAndroid {
    public String id;
    public String title;
    public String tag;
    public String article;
}
