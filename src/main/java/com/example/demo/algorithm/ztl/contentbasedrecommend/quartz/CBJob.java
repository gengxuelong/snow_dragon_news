/**
 * 
 */
package com.example.demo.algorithm.ztl.contentbasedrecommend.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.example.demo.algorithm.ztl.contentbasedrecommend.ContentBasedRecommender;

import java.util.List;


public class CBJob implements Job
{
	@SuppressWarnings("unchecked")
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		List<Long> users=(List<Long>) arg0.getJobDetail().getJobDataMap().get("users");
		new ContentBasedRecommender().recommend(users);
	}

}

