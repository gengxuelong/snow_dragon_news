package com.example.demo.utils;



import com.example.demo.algorithm.ztl.model.News;
import com.example.demo.pojo.NewsAndroid;

import java.util.LinkedList;
import java.util.List;

/**
 * @Auther: GengXuelong
 * <p> 类 功能描述如下:
 * @Description:
 *     自定义容器，用来盛放News对象，初始化时必须指定其容量大小。
 *     具有队列性质，当数量超出最大容量后，最早进入的元素将被删除
 *     具有集合的唯一性，添加元素时，标题已经在容器中存在的News将不可以被添加
 */
public class MyContainer {

    /**
     * @Auther: GengXuelong
     * <p> 变量描述如下:
     * @Description:
     *     最大容量
     */
    private final int max_size;

    /**
     * @Auther: GengXuelong
     * <p> 变量描述如下:
     * @Description:
     *     容器的本质
     */
    private  final LinkedList<NewsAndroid> newsLinkedList = new LinkedList<>();

    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     getter函数
     */
    public  LinkedList<NewsAndroid> getNewsLinkedList(){
        return newsLinkedList;
    }


    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     addAll 方法， 实现了title 唯一性 和 队列先进先出的性质
     */
    public  void addAll(List<NewsAndroid> list){

        for (NewsAndroid news : list) {
            String title = news.getTitle();
            if(title!=null&&!"".equals(title)){
                boolean isContain = false;
                for (NewsAndroid news1 : newsLinkedList) {
                    if(title.equals(news1.getTitle())){
                        isContain = true;
                        break;
                    }
                }
                if(!isContain){
                    newsLinkedList.addFirst(news);
                }
            }
        }
        int over = newsLinkedList.size()- max_size;
        if(over>0){
            while (over>0){
                newsLinkedList.removeLast();
                over--;
            }
        }
    }

    public void addOne(NewsAndroid news){
        String title = news.getTitle();
        if(title!=null&&!"".equals(title)){
            boolean isContain = false;
            for (NewsAndroid news1 : newsLinkedList) {
                if(title.equals(news1.getTitle())){
                    isContain = true;
                    break;
                }
            }
            if(!isContain){
                newsLinkedList.addFirst(news);
            }
        }
        int over = newsLinkedList.size()- max_size;
        if(over>0){
            while (over>0){
                newsLinkedList.removeLast();
                over--;
            }
        }
    }

    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     构造函数
     */
    public MyContainer(int max_size) {
        this.max_size = max_size;
    }
}
