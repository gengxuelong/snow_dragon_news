package com.example.demo.service;

import com.example.demo.pojo.NewsLogsEntity;
import com.example.demo.pojo.UserEntity;

/**
 * @Auther: GengXuelong
 * @Description:
 *      提供信息加载服务
 */
public interface DataLoadingService {
    void loadingFreshNews();
    void loadingModules();
    void loadingNewsToDB();
    void AI_Push();
    int addLog(NewsLogsEntity newsLogsEntity);
    int addUser(UserEntity userEntity);
    void refreshTheMain();
}
