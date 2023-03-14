package com.example.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: GengXuelong
 * @Description:
 * 和数据库交互的实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPhoneEntity {
    private Long phone;
    private String code;
}
