package com.example.demo.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @Auther: GengXuelong
 * <p> 类 功能描述如下:
 * @Description:
 *     自定义Exception拦截并处理
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    /**
     * @Auther: GengXuelong
     * <p> 变量描述如下:
     * @Description:
     *     日志打印者
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     错误处理函数
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request,Exception e){
        logger.error("耿雪龙:===> 进入错误处理页面");
        ModelAndView mv = new ModelAndView();
        /**
         * 这里的占位符{} 只可以占string的位置。如果传入e.tostring.则占位符发挥作用，只显示错误类型
         * 如果传入e,则占位符失效，以{} 本身显示。并且会将整个e的信息打印出来，包括stack信息和类型信息
         */
        logger.error("耿雪龙：===> Request URL : {},  Exception : {}", request.getRequestURL(),e);
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exceptionMessage",e.getMessage());
        mv.addObject("exceptionStack", Arrays.toString(e.getStackTrace()));
        mv.setViewName("error/error");
        return mv;
    }
}
