package com.example.demo.dataCash;

import com.example.demo.algorithm.ztl.model.News;
import com.example.demo.mapper.UserPhoneMapper;
import com.example.demo.pojo.ModuleEntity;
import com.example.demo.pojo.NewsAndroid;
import com.example.demo.pojo.UserPhoneEntity;
import com.example.demo.utils.MyContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: GengXuelong
 * @Description:
 *      缓存数据存放地
 */
public class DataCash {
    public static MyContainer myContainer = new MyContainer(180);
    public static List<ModuleEntity> moduleEntities = new ArrayList<>();

    /**
     * @Auther: GengXuelong
     * <p> 变量描述如下:
     * @Description:
     *     list0 - list13 共14个小仓库，用来缓冲从数据库中得到的新闻，共前端用户告诉访问；
     *     内容每天凌晨 00:01 更新一次
     */
    public static final int SIZE = 400;//每个小仓库中的缓存量
    public static List<NewsAndroid> list0 = new ArrayList<>();
    public static List<NewsAndroid> list1 = new ArrayList<>();
    public static List<NewsAndroid> list2 = new ArrayList<>();
    public static List<NewsAndroid> list3 = new ArrayList<>();
    public static List<NewsAndroid> list4 = new ArrayList<>();
    public static  List<NewsAndroid> list5 = new ArrayList<>();
    public static  List<NewsAndroid> list6 = new ArrayList<>();
    public static  List<NewsAndroid> list7 = new ArrayList<>();
    public static  List<NewsAndroid> list8 = new ArrayList<>();
    public static  List<NewsAndroid> list9 = new ArrayList<>();
    public static  List<NewsAndroid> list10 = new ArrayList<>();
    public static  List<NewsAndroid> list11= new ArrayList<>();
    public static  List<NewsAndroid> list12 = new ArrayList<>();
    public static  List<NewsAndroid> list13= new ArrayList<>();
    public static  List<NewsAndroid> list14= new ArrayList<>();
    public static List<NewsAndroid>getLittleRepertory(int module_id){
        switch (module_id){
            case 1:
                return list0;
            case 2: return list1;
            case 3: return list2;
            case 4: return list3;
            case 5: return list4;
            case 6: return list5;
            case 7: return list6;
            case 8: return list7;
            case 9: return list8;
            case 10: return list9;
            case 11: return list10;
            case 12: return list11;
            case 13: return list12;
            case 14: return list13;
            case 15: return list14;
            default:return null;
        }
    }
    public static void clearRepertory() {
        list0 = new ArrayList<>();
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();
        list4 = new ArrayList<>();
        list5 = new ArrayList<>();
        list6 = new ArrayList<>();
        list7 = new ArrayList<>();
        list8 = new ArrayList<>();
        list9 = new ArrayList<>();
        list10 = new ArrayList<>();
        list11 = new ArrayList<>();
        list12 = new ArrayList<>();
        list13 = new ArrayList<>();
        list14 = new ArrayList<>();
    }

}