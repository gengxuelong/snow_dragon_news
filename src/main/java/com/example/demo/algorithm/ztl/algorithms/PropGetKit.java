/**
 * 
 */
package com.example.demo.algorithm.ztl.algorithms;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropGetKit
{
	private static final Logger logger = Logger.getLogger(PropGetKit.class);

	public static Properties propGetKit = new Properties();;

	public static void loadProperties(String configFileName)
	{
		try
		{
			//propGetKit.load(new FileInputStream("src/main/resources/res/"+configFileName+".properties"));
			ClassPathResource classPathResource = new ClassPathResource("res/" + configFileName + ".properties");
			InputStream inputStream = classPathResource.getInputStream();
			propGetKit.load(inputStream);
		}
		catch (FileNotFoundException e)
		{
			logger.error("读取属性文件--->失败！- 原因：文件路径错误或者文件不存在");
		}
		catch (IOException e)
		{
			logger.error("装载文件--->失败!");
		}
	}

	public static String getString(String key)
	{
		return propGetKit.getProperty(key);
	}
	
	public static int getInt(String key)
	{
		return Integer.valueOf(propGetKit.getProperty(key));
	}
	
}
