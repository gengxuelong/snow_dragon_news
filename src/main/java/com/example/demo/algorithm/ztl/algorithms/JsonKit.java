/**
 * 
 */
package com.example.demo.algorithm.ztl.algorithms;

import com.example.demo.algorithm.ztl.contentbasedrecommend.CustomizedHashMap;
import org.apache.htrace.fasterxml.jackson.core.JsonParseException;
import org.apache.htrace.fasterxml.jackson.core.type.TypeReference;
import org.apache.htrace.fasterxml.jackson.databind.JsonMappingException;
import org.apache.htrace.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class JsonKit
{
	public static String test(){
		String json=null;
		try {	        
	        Map<Integer, Object> moduleidMap = new HashMap<Integer, Object>();
	        Map<String, Double> keywordRateMap = new HashMap<String, Double>();
	        keywordRateMap.put("政治", 123.1);
	        keywordRateMap.put("金融", 35.2);
	        moduleidMap.put(1,keywordRateMap);
	        keywordRateMap.put("电影", 351.1);
	        moduleidMap.put(2,keywordRateMap);
	        ObjectMapper objectMapper=new ObjectMapper();

	        json=objectMapper.writeValueAsString(moduleidMap);
	        
	        String test="{\"1\":{},\"2\":{},\"3\":{},\"4\":{}}";
	        return test;
	    } catch (IOException e) {

	        e.printStackTrace();

	    }
        return json;
	}

	
	/**
	 * 将用户的喜好关键词列表字符串转换为map
	 * @param srcJson
	 * @return
	 */
	public static CustomizedHashMap<Integer,CustomizedHashMap<String,Double>> jsonPrefListtoMap(String srcJson){
		ObjectMapper objectMapper=new ObjectMapper();
		CustomizedHashMap<Integer,CustomizedHashMap<String,Double>> map=null;
		try
		{			
			map=objectMapper.readValue(srcJson, new TypeReference<CustomizedHashMap<Integer,CustomizedHashMap<String,Double>>>(){});
		}
		catch (JsonParseException e)
		{
			e.printStackTrace();
		}
		catch (JsonMappingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
    
}
