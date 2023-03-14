package com.example.demo.algorithm.ztl.main;

import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import com.example.demo.algorithm.ztl.algorithms.PropGetKit;
import com.example.demo.algorithm.ztl.algorithms.RecommendKit;
import com.example.demo.algorithm.ztl.contentbasedrecommend.ContentBasedRecommender;
import com.example.demo.algorithm.ztl.contentbasedrecommend.quartz.CBCronTriggerRunner;
import com.example.demo.algorithm.ztl.dbconnection.DBKit;
import com.example.demo.algorithm.ztl.hotrecommend.HotRecommender;

import java.util.Date;
import java.util.List;

public class JobSetter
{
	
	public static final Logger logger=Logger.getLogger(JobSetter.class);
	


	public JobSetter() {
		//加载系统配置文件
		PropGetKit.loadProperties("paraConfig");
		//初始化操作：主要是数据库的连接
		DBKit.initalize();

	}
	
	
	/**
	 * 使用Quartz的表达式进行时间设定（默认为每天0点开始工作），详情请参照：http://www.quartz-scheduler.org/api/2.2.1/index.html(CronExpression)
	 * 当启用该方法时，推荐系统可以保持运行，直到被强制关闭。
	 * @param userList
	 */
	private void executeQuartzJob(List<Long> userList) {
		//设定推荐任务每天的执行时间
		String cronExpression=PropGetKit.getString("startAt");
		
		try
		{
			new CBCronTriggerRunner().task(userList,cronExpression);
		}
		catch (SchedulerException e)
		{
			e.printStackTrace();
		}
		System.out.println("本次推荐结束于"+new Date());
	}
	
	
	/**
	 * 为指定用户执行定时新闻推荐
	 * @param goalUserList 目标用户的id列表
	 */
	public void executeQuartzJobForCertainUsers(List<Long> goalUserList) {
		executeQuartzJob(goalUserList);
	}
	
	/**
	 * 为所有用户执行定时新闻推荐
	 */
	public void executeQuartzJobForAllUsers() {
		executeQuartzJob(RecommendKit.getAllUsers());
	}
	
	/**
	 * 为活跃用户进行定时新闻推荐。
	 * @param goalUserList
	 */
	public void executeQuartzJobForActiveUsers() {
		executeQuartzJob(RecommendKit.getActiveUsers());
	}
	
	
	/**
	 * 执行一次新闻推荐
	 */
	private void executeInstantJob(List<Long> userIDList) {
		//让热点新闻推荐器预先生成今日的热点新闻
		HotRecommender.formTodayTopHotNewsList();
		new ContentBasedRecommender().recommend(userIDList);
		System.out.println("本次推荐结束于"+new Date());
	}
	
	/**
	 * 执行一次新闻推荐

	 */
	/*public void executeInstantJobForCertainUsers(List<Long> goalUserList) {
		executeInstantJob(goalUserList);
	}*/
	public void executeInstantJobForCertainUsers(List<Long> goalUserList) {
		executeInstantJob(goalUserList);
	}
	/**
	 * 为所用有用户执行一次新闻推荐
	 */
	public void executeInstantJobForAllUsers() {
		executeInstantJob(RecommendKit.getAllUsers());
	}
	
	/**
	 * 为活跃用户进行一次推荐。
	 * @param goalUserList
	 */
	public void executeInstantJobForActiveUsers() {
		executeInstantJob(RecommendKit.getActiveUsers());
	}
}

