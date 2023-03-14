/**
 * 
 */
package com.example.demo.algorithm.ztl.hotrecommend.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.example.demo.algorithm.ztl.hotrecommend.HotRecommender;

public class HRJob implements Job
{
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		HotRecommender.getTopHotNewsList().clear();
		HotRecommender.formTodayTopHotNewsList();
	}

}

