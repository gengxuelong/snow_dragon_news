package com.example.demo;

import com.example.demo.dataCash.DataCash;
import com.example.demo.mapper.NewsLogsMapper;
import com.example.demo.mapper.NewsMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.NewsAndroid;
import com.example.demo.pojo.NewsEntity;
import com.example.demo.pojo.NewsLogsEntity;
import com.example.demo.pojo.UserEntity;
import com.example.demo.service.DataLoadingService;
import com.example.demo.utils.MyUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    NewsLogsMapper newsLogsMapper;
    @Autowired
    NewsMapper newsMapper;
    @Autowired
    DataLoadingService loadingService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    DataLoadingService dataLoadingService;
    @Test
    void contextLoads() {

       dataLoadingService.loadingNewsToDB();
    }

}
