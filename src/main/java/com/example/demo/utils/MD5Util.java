package com.example.demo.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Auther: GengXuelong
 * <p> 类 功能描述如下:
 * @Description:
 *     md5加密功能的实现工具类。实现双次加盐加密
 */
public class MD5Util {

    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     一般md5加密
     */
    private static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    /**
     * @Auther: GengXuelong
     * <p> 变量描述如下:
     * @Description:
     *     盐
     */
    private static final String salt = "t1w2s3x4x5n6";


    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     单层加密
     */
    private static String zeroToOnePass(String inputPass){
        String str =  salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(7);
        return md5(str);
    }
    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     过渡 加密
     */
    private static String oneToTwoPass(String inputPass){
        String str =  salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(7);
        return md5(str);
    }

    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     双层加密
     */
    public static String zeroToTwoPass(String zeroPass){
        String onePass = zeroToOnePass(zeroPass);
        String dbPass = oneToTwoPass(onePass);
        return dbPass;
    }

}
