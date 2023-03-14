package com.example.demo.algorithm.ztl.dbconnection;

import com.example.demo.algorithm.ztl.model.*;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLBooleanPrefJDBCDataModel;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.*;
import java.util.HashMap;
import java.util.Properties;

public class DBKit{
	
	public static final Logger logger=Logger.getLogger(DBKit.class);
	
	//偏好表表名
	public static final String PREF_TABLE="newslogs";  
	//用户id列名
	public static final String PREF_TABLE_USERID="user_id";
	//新闻id列名
	public static final String PREF_TABLE_NEWSID="news_id";
	//偏好值列名
	public static final String PREF_TABLE_PREFVALUE="prefer_degree";
	//用户浏览时间列名
	public static final String PREF_TABLE_TIME="view_time";
	
	private static C3p0Plugin cp;

	private static ComboPooledDataSource ds;
	static{
		HashMap<String, String> info = getDBInfo();
		ds = new ComboPooledDataSource();
		try {
			ds.setDriverClass(info.get("driver"));
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ds.setJdbcUrl(info.get("url"));
		ds.setUser("root");
		ds.setPassword("root");
		ds.setMaxPoolSize(30);
		ds.setMinPoolSize(2);
		ds.setInitialPoolSize(10);
		ds.setMaxStatements(180);
	}
	
	public static void initalize()
	{
		try
		{
			HashMap<String, String> info = getDBInfo();
			System.out.println(info.get("url"));
			System.out.println(info.get("user"));
			System.out.println(info.get("password"));
			System.out.println(info.get("driver"));
			cp = new C3p0Plugin(info.get("url"), info.get("user"), info.get("password"),info.get("driver"));
			//System.out.println((cp == null));
			ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
			arp.addMapping("users", Users.class);
			arp.addMapping("news", News.class);
			arp.addMapping("newsmodules", Newsmodules.class);
			arp.addMapping("newslogs", Newslogs.class);
			arp.addMapping("recommendations", Recommendations.class);
			
			
			if(cp.start() && arp.start())
				logger.info("数据库连接池插件启动成功......");
			else
				logger.info("c3p0插件启动失败！");
			logger.info("数据库初始化工作完毕！");
		}
		catch (Exception e) {
			logger.error("数据库连接初始化错误！");
		}
		return;
	}
	
	public static HashMap<String, String> getDBInfo()
	{
		HashMap<String, String> info = null;
		try
		{
			Properties p = new Properties();
			//D:\work\java\recommend\NewsRecommendSystem-master\res\dbconfig.properties
			//p.load(new FileInputStream(new File("src/main/resources/res/dbconfig.properties")));
			//p.load(new FileInputStream(new File(DBKit.class.getResource("").getPath()+"dbconfig.properties")));
			ClassPathResource classPathResource = new ClassPathResource("res/dbconfig.properties");
			InputStream inputStream = classPathResource.getInputStream();
			p.load(inputStream);
			info = new HashMap<String, String>();
			info.put("url",p.getProperty("url"));
					info.put("user", p.getProperty("user"));
			info.put("password", p.getProperty("password"));
			info.put("driver",p.getProperty("driver-class-name"));
			//Db.query("select * from news");
		}
		catch (FileNotFoundException e)
		{
			logger.error("读取属性文件--->失败！- 原因：文件路径错误或者文件不存在");
		}
		catch (IOException e)
		{
			logger.error("装载文件--->失败!");
		}
		return info;
	}
	
	public static DataSource getDataSource() {
		if(cp==null)
			initalize();

		return cp.getDataSource();
	}



	
	public static MySQLBooleanPrefJDBCDataModel getMySQLJDBCDataModel(){
	return new MySQLBooleanPrefJDBCDataModel(DBKit.getDataSource(), PREF_TABLE, PREF_TABLE_USERID,
		PREF_TABLE_NEWSID,PREF_TABLE_TIME);
	}

}
