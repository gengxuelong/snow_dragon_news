package com.example.demo.mapper;

import com.example.demo.pojo.NewsLogsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Auther: GengXuelong
 * @Date: 2022/7/31 17:30
 * @Description:
 */
@Mapper
@Repository
public interface NewsLogsMapper {
    int addLog(NewsLogsEntity log);
}
