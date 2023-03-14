package com.example.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: GengXuelong
 * <p> 类 功能描述如下:
 * @Description:
 *     管理员 实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    public String name;
    public String password;
}
