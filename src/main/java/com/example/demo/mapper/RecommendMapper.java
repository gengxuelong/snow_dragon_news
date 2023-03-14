package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Auther: GengXuelong
 * @Date: 2022/7/31 17:13
 * @Description:
 */
@Mapper
@Repository
public interface RecommendMapper {
    void deleteAll();
}
