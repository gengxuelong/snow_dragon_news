package com.example.demo.timer;

/**
 * @Auther: GengXuelong
 * <p> 类 功能描述如下:
 * @Description:
 *     用来提供定时的数据刷新
 */
public interface MyTimer {
    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     定时运行函数，每24小时刷新一遍缓存和 redis 中的数据
     */
    void startRun();
}
