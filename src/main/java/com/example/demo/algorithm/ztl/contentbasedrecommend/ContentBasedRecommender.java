/**
 * 
 */
package com.example.demo.algorithm.ztl.contentbasedrecommend;

import org.ansj.app.keyword.Keyword;
import org.apache.log4j.Logger;
import com.example.demo.algorithm.ztl.algorithms.PropGetKit;
import com.example.demo.algorithm.ztl.algorithms.RecommendAlgorithm;
import com.example.demo.algorithm.ztl.algorithms.RecommendKit;
import com.example.demo.algorithm.ztl.model.News;

import java.util.*;

/**
 *       思路：提取抓取进来的新闻的关键词列表（tf-idf），与每个用户的喜好关键词列表，做关键词相似度计算，取最相似的N个新闻推荐给用户。
 */
public class ContentBasedRecommender implements RecommendAlgorithm
{
	public static final Logger logger = Logger.getLogger(ContentBasedRecommender.class);

	// TFIDF提取关键词数
	private static final int KEY_WORDS_NUM = PropGetKit.getInt("TFIDFKeywordsNum");

	// 推荐新闻数
	private static final int N = PropGetKit.getInt("CBRecNum");

	@Override
	public void recommend(List<Long> users)
	{
		try
		{
			int count=0;
			System.out.println("CB start at "+ new Date());
			// 首先进行用户喜好关键词列表的衰减更新+用户当日历史浏览记录的更新
			new UserPrefRefresher().refresh(users);
			// 新闻及对应关键词列表的Map
			HashMap<Long, List<Keyword>> newsKeyWordsMap = new HashMap<Long, List<Keyword>>();
			HashMap<Long, Integer> newsModuleMap = new HashMap<Long, Integer>();
			// 用户喜好关键词列表
			HashMap<Long, CustomizedHashMap<Integer, CustomizedHashMap<String, Double>>> userPrefListMap = RecommendKit
					.getUserPrefListMap(users);

			List<News> newsList=News.dao.find("select id,title,content,module_id from news where news_time>"
							+ RecommendKit.getInRecDate());
			for (News news:newsList)
			{
				newsKeyWordsMap.put(news.getId(), TFIDF.getTFIDE(news.getTitle(), news.getContent(), KEY_WORDS_NUM));
				newsModuleMap.put(news.getId(), news.getModuleId());
			}

			int c=0;
			for (Long userId : users)
			{
				Map<Long, Double> tempMatchMap = new HashMap<Long, Double>();
				Iterator<Long> ite = newsKeyWordsMap.keySet().iterator();
				while (ite.hasNext())
				{
					Long newsId = ite.next();
					int moduleId = newsModuleMap.get(newsId);
					if (null != userPrefListMap.get(userId).get(moduleId)) {
						tempMatchMap.put(newsId,
								getMatchValue(userPrefListMap.get(userId).get(moduleId), newsKeyWordsMap.get(newsId)));
					/*	c++;
						if(c<10){
							System.out.println(newsId);
							System.out.println(getMatchValue(userPrefListMap.get(userId).get(moduleId), newsKeyWordsMap.get(newsId)));
						}*/
					}
					else
						continue;
				}
				// 去除匹配值为0的项目
				//removeZeroItem(tempMatchMap);
				//


				//System.out.println((tempMatchMap.toString().equals("{}")));
				if (!(tempMatchMap.toString().equals("{}")))
				{
					//System.out.println("tempMatchMap not null");
					tempMatchMap = sortMapByValue(tempMatchMap);
					Set<Long> toBeRecommended=tempMatchMap.keySet();
					//过滤掉已经推荐过的新闻
					RecommendKit.filterReccedNews(toBeRecommended,userId);
					//过滤掉用户已经看过的新闻
					RecommendKit.filterBrowsedNews(toBeRecommended, userId);
					//如果可推荐新闻数目超过了系统默认为CB算法设置的单日推荐上限数（N），则去掉一部分多余的可推荐新闻，剩下的N个新闻才进行推荐
					if(toBeRecommended.size()>N){
						RecommendKit.removeOverNews(toBeRecommended,N);
					}
					RecommendKit.insertRecommend(userId, toBeRecommended.iterator(),RecommendAlgorithm.CB);
					count+=toBeRecommended.size();
				}
			}
			System.out.println("CB has contributed " + (count/users.size()) + " recommending news on average");
			System.out.println("CB finished at "+new Date());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return;
	}

	/**
	 * 获得用户的关键词列表和新闻关键词列表的匹配程度
	 * 
	 * @return
	 */
	private double getMatchValue(CustomizedHashMap<String, Double> map, List<Keyword> list)
	{
		Set<String> keywordsSet = map.keySet();
		double matchValue = 0;
		for (Keyword keyword : list)
		{
			if (keywordsSet.contains(keyword.getName()))
			{
				matchValue += keyword.getScore() * map.get(keyword.getName());
			}
		}
		return matchValue;
	}

	private void removeZeroItem(Map<Long, Double> map)
	{
		HashSet<Long> toBeDeleteItemSet = new HashSet<Long>();
		Iterator<Long> ite = map.keySet().iterator();
		while (ite.hasNext())
		{
			Long newsId = ite.next();
			if (map.get(newsId) <= 0)
			{
				toBeDeleteItemSet.add(newsId);
			}
		}
		for (Long item : toBeDeleteItemSet)
		{
			map.remove(item);
		}
	}
	

	public static Map<Long, Double> sortMapByValue(Map<Long, Double> oriMap) {
		if (oriMap == null || oriMap.isEmpty()) {
			return null;
		}
		Map<Long, Double> sortedMap = new LinkedHashMap<Long, Double>();
		List<Map.Entry<Long, Double>> entryList = new ArrayList<Map.Entry<Long, Double>>(
				oriMap.entrySet());
		Collections.sort(entryList, new MapValueComparator());

		Iterator<Map.Entry<Long, Double>> iter = entryList.iterator();
		Map.Entry<Long, Double> tmpEntry = null;
		while (iter.hasNext()) {
			tmpEntry = iter.next();
			sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
		}
		return sortedMap;
	}
}
