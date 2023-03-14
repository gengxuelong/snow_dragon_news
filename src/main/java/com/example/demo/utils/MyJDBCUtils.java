package com.example.demo.utils;

import com.example.demo.pojo.NewsEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @Auther: GengXuelong
 * <p> 类 功能描述如下:
 * @Description:
 *     自定义JDBC连接数据库的工具类，用来在非spring组件类的环境中和数据库的交互
 *     用于前期开发阶段，后来逐步有springboot接管数据库连接
 */
public class MyJDBCUtils {

    /**
     * @Auther: GengXuelong
     * <p> 变量描述如下:
     * @Description:
     *     链接数据库必须的参数
     */
    public static String name = "news";
    public static String password = "news";
    public static String url = "jdbc:mysql://121.4.33.213:3306/news?useUnicode = true&characterEncoding = utf-8&serverTimezone = UTC";
    public static String Driver = "com.mysql.cj.jdbc.Driver";

    /**
     * @Auther: GengXuelong
     * <p> 变量描述如下:
     * @Description:
     *     一些工具对象
     */
    public static Connection connection ;
    public static PreparedStatement preparedStatement;
    public static ResultSet resultSet;
    public static int executeUpdate;

    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     得到和数据库的连接
     */
    public static Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName(Driver);
            connection = DriverManager.getConnection(url,name,password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     查询语句执行函数
     */
    public static ResultSet executeQuery(String sql,Object...args){
        try {
            if(connection == null || connection.isClosed()) {
                connection = getConnection();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            preparedStatement = connection.prepareStatement(sql);
            if(args != null && args.length != 0){
                for(int i = 0;i<args.length;i++){
                    preparedStatement.setObject(i+1,args[i]);
                }
            }
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     delete update insert 语句的执行函数
     */
    public static int executeUpdate(String sql,Object...args){
        try {
            if(connection == null || connection.isClosed()) {

                    connection = getConnection();

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            preparedStatement = connection.prepareStatement(sql);
            if(args != null&& args.length != 0){
                for(int i = 0;i<args.length;i++){
                    preparedStatement.setObject(i+1,args[i]);
                }
            }
            executeUpdate = preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return executeUpdate;
    }

    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     关闭连接
     */
    public static void getClose(){
        if(resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(preparedStatement!=null){
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(connection!= null){
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     向数据库中添加 News 数据
     */
    public static void addNews(NewsEntity news){
        String sql = "insert into news  value (null,?,?,?,?,?)";
        executeUpdate(sql, news.getContent(),news.getNews_time(),news.getTitle(),news.getModule_id(),news.getUrl());
        getClose();
    }
    public static void addNewsLog(Long userId,Long newsId){

        String sql = "insert into newslogs value (null,?,?,?,1)";
        Date date = new Date();
        int a = executeUpdate(sql,userId,newsId,date);
        System.out.println(a);
        getClose();
    }
    public static List<Long> getAllUserId(){
        String sql = "select id from users";
        ResultSet resultSet = executeQuery(sql);
        List<Long> longs = new ArrayList<>();
        while (true){
            try {
                if (!resultSet.next()) break;
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
            try {
                longs.add(resultSet.getLong("id"));
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return longs;
    }

}
