package com.example.demo.mapper;

import com.example.demo.pojo.NewsAndroid;
import com.example.demo.pojo.NewsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Auther: GengXuelong
 * @Date: 2022/7/31 15:14
 * @Description:
 */

@Mapper
@Repository
public interface NewsMapper {
    int addAll(List<NewsEntity> newsEntityList);
    int addOne(NewsEntity newsEntity);
    int deleteAll();
    int deleteNews(Long id);
    int getNum();
    int getNumByTitleLike(String title);
    int getNumByModule_id(int module_id);
    NewsEntity getNewsById(Long id);
    List<NewsEntity> getNewsByLimit(int start,int num);
    List<NewsEntity> getNewsByPrecise(Long user_id);
    List<NewsEntity> getNewsByTitleByLimit(String title,int start,int num);
    List<NewsEntity> getNewsByModule_idByLimit(int module_id, int start, int num);
    int updateNews(NewsEntity newsEntity);
    List<NewsEntity> getNewsByModule_idByLimitByTime(int module_id,  int num);
    List<NewsEntity> getNewsByLimitByTime(int num);
}
