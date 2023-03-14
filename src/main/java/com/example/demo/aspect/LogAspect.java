package com.example.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: GengXuelong
 * <p> 类 功能描述如下:
 * @Description:
 *     日志切片类，切入所有controller包中的类，记录其中每个方法的调用
 */
@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     第一个星星 是 无视私有类还是共有类
     *     第二个星星是 包里的所有类
     *     第三个*(..) 代表所有参数的所有方法
     *
     *     切入点，作用于整个controller 包
     */
    @Pointcut("execution(* com.example.demo.controller.*.*(..) )")
    public void log(){}


    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     前执行日志切片
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = String.valueOf(request.getRequestURL());
        String ip=  request.getRemoteAddr();
        String clasMethod = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url,ip,clasMethod,args);
        logger.info("耿雪龙==>request:{}",requestLog.toString());
        String method = requestLog.classMethod.toString();
        String[] strings = method.split("\\.");
        String lei =  strings[strings.length-2];
        String fangfa = strings[strings.length-1];
        logger.info("-----进入"+lei+"类的"+fangfa+"方法-------------------------------------");
    }

    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     后执行日志切片
     */
    @After("log()")
    public void doAfter(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = String.valueOf(request.getRequestURL());
        String ip=  request.getRemoteAddr();
        String clasMethod = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url,ip,clasMethod,args);
        String method = requestLog.classMethod.toString();
        String[] strings = method.split("\\.");
        String lei =  strings[strings.length-2];
        String fangfa = strings[strings.length-1];
        logger.info("-----完全出"+lei+"类的"+fangfa+"方法-------------------------------------------");

    }



    /**
     * @Auther: GengXuelong
     * <p> 类 功能描述如下:
     * @Description:
     *     私有类 ，用来描述访问者的信息
     */
    private class RequestLog{
         String url;
         String ip;
         String classMethod;
         Object args;

        public RequestLog(String url, String ip, String classMethod, Object args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + args +
                    '}';
        }
    }
}
