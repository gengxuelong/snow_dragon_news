package com.example.demo.algorithm.ztl.main;

import com.example.demo.utils.MyJDBCUtils;
import com.jfinal.plugin.activerecord.Db;
import com.example.demo.algorithm.ztl.algorithms.RecommendKit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AIRunner
{
	public  void runData() {
		System.out.println("开始测试数据的运行！");
		//默认基于新闻内容推荐
		JobSetter jobSetter=new JobSetter();
		
		//更新测试数据的时间
		databaseReady();

		//为指定用户执行一次推荐
		//jobSetter.executeInstantJobForCertainUsers(userList);
		jobSetter.executeInstantJobForAllUsers();
		System.out.println("智能推荐算法运行结束！");
	}
	
	//对测试数据的相关日期数据进行更新，以保证能够在测试运行中获得理想的推荐结果。
	public static void databaseReady() {
		//Db.update("update news set news_time=?",new Date());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月和小时的格式为两个大写字母
		Date date = new Date();//获得当前时间
		String day = df.format(date);//将当前时间转换成特定格式的时间字符串，这样便可以插入到数据库中
		//System.out.println(day);
		Db.update("update news set news_time=?",day);
		for(int id=1;id<8;id++) {
			Db.update("update users set latest_log_time=? where id=?", RecommendKit.getInRecTimestamp(25+id),id);
		}
		Db.update("update newslogs set view_time=?",df.format(new Date()));

		
	}
}

