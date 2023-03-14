package com.example.demo.pojo;

import lombok.Data;

/**
 * @Auther: GengXuelong
 * @Description:
 *      向安卓端传送信息的封装类
 */
@Data
public class AndroidText {
    String text;

    public AndroidText(String text) {
        this.text = text;
    }
}
