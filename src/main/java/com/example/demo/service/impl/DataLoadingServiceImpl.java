package com.example.demo.service.impl;

import com.example.demo.algorithm.ztl.main.AIRunner;
import com.example.demo.dataCash.DataCash;
import com.example.demo.mapper.*;
import com.example.demo.pojo.*;
import com.example.demo.service.DataLoadingService;
import com.example.demo.spider.ManyGroupsSpider;
import com.example.demo.utils.MyContainer;
import com.example.demo.utils.MyUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @Auther: GengXuelong
 * @Date: 2022/7/31 10:35
 * @Description:
 */
@Service
public class DataLoadingServiceImpl implements DataLoadingService {
    @Autowired
    NewsMapper newsMapper;
    @Autowired
    RecommendMapper recommendMapper;
    @Autowired
    NewsLogsMapper newsLogsMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ModulesMapper modulesMapper;
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    public void AI_Push(){
        recommendMapper.deleteAll();
        new AIRunner().runData();
    }


    public void loadingNewsToDB(){
        MyUtils.readTxt("D:\\file\\news",newsMapper);
    }


    @PostConstruct
    public void loadingModules(){
        System.out.println("开始加载module至内存");
        DataCash.moduleEntities = modulesMapper.getAll();
    }

    @Override
    public void loadingFreshNews() {
        redisTemplate.delete("todayNews");
        List<NewsEntity> newsEntityList = ManyGroupsSpider.getTodayNews();
        List<NewsEntity> removed = new ArrayList<>();
        for (NewsEntity newsEntity : newsEntityList) {//除去空标题
            if(StringUtils.isEmpty(newsEntity.getTitle())||StringUtils.isEmpty(newsEntity.getContent())){
                removed.add(newsEntity);
            }
        }
        newsEntityList.removeAll(removed);
        System.out.println("读出的数量：："+newsEntityList.size());
        MyContainer myContainer = new MyContainer(newsEntityList.size());
        myContainer.addAll(MyUtils.convertEntityToAndroidInNews(newsEntityList));
        int num = newsMapper.addAll(MyUtils.convertAndroidToEntityInNews(myContainer.getNewsLinkedList()));
        System.out.println("成功存入的数量：："+num);
        System.out.println("其差值为 重复的数量");
        Date yesterday = DateUtil.yesterday();
        newsEntityList = newsMapper.getNewsByLimitByTime(200);
        List<NewsAndroid> newsAndroidList = MyUtils.convertEntityToAndroidInNews(newsEntityList);
        DataCash.myContainer.addAll(newsAndroidList);
        redisTemplate.opsForValue().set("todayNews",newsAndroidList);
    }

    public int addLog(NewsLogsEntity newsLogsEntity){
       return newsLogsMapper.addLog(newsLogsEntity);
    }

    @Override
    public int addUser(UserEntity userEntity) {
        return userMapper.addUser(userEntity);
    }

    @Override
    public void refreshTheMain() {

        System.out.println("start refresh the main");
        int i = 0;
        DataCash.clearRepertory();
        int size = DataCash.SIZE;
        Random random = new Random();
        for (ModuleEntity moduleEntity : DataCash.moduleEntities) {
            int module_id = MyUtils.getModuleIdByTag(moduleEntity.getName());
           int  index = module_id-1;
            int ceiling = newsMapper.getNumByModule_id(module_id)-size;
            List<NewsAndroid> temp = DataCash.getLittleRepertory(module_id);
            if( temp!=null){
                if(ceiling<=0){
                    temp.addAll( MyUtils.convertEntityToAndroidInNews( newsMapper.getNewsByModule_idByLimit(module_id,0,size+ceiling)));
                }else{
                    temp.addAll( MyUtils.convertEntityToAndroidInNews( newsMapper.getNewsByModule_idByLimit(module_id,random.nextInt(ceiling),size)));
                }
            }
            System.out.println("refreshed over :"+i++);
        }
        System.out.println("all over");
    }


}
