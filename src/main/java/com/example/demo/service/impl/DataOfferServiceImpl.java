package com.example.demo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.dataCash.DataCash;
import com.example.demo.mapper.NewsMapper;
import com.example.demo.pojo.AndroidText;
import com.example.demo.pojo.NewsAndroid;
import com.example.demo.pojo.NewsEntity;
import com.example.demo.service.DataOfferService;
import com.example.demo.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Auther: GengXuelong
 * @Date: 2022/7/31 15:06
 * @Description:
 */
@Service
public class DataOfferServiceImpl implements DataOfferService {

    /**
     * @Auther: GengXuelong
     * <p> 变量描述如下:
     * @Description:
     *     提供和redis 数据交互的接口
     */
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     和数据库交互
     */
    @Autowired
    NewsMapper newsMapper;
    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     见上层接口
     */
    @Override
    public List<?> offerTodayNewsSegment() {



        /**
         * 双层缓存，先从内存缓存中取数据，如果同时访问用户过多导致加载超时或连接中断，则启用redis缓存机制，
         * 从redis缓存的数据中取出数据呈递给用户
         */
        try{

            int ceiling = DataCash.myContainer.getNewsLinkedList().size();
            int number = 30;
            if(ceiling<number){
                number = ceiling;
            }
            int[] ints = MyUtils.getNumberIntsLimitedWithNoRepeat(number,0,ceiling);
            List<NewsAndroid> newsList = new ArrayList<>();
            System.out.println(Arrays.toString(ints));
            assert ints != null;
            for (int anInt : ints) {
                newsList.add(DataCash.myContainer.getNewsLinkedList().get(anInt));
            }
            return newsList;
        }catch (Exception e){
            List<NewsAndroid> todayNewsList = (List<NewsAndroid>) redisTemplate.opsForValue().get("todayNews");
            System.out.println("the number of list in redis is : "+todayNewsList.size() );
            for (NewsAndroid newsAndroid : todayNewsList) {
                System.out.println(newsAndroid);
            }
            int ceiling = todayNewsList.size();
            int number = 30;
            if(ceiling<number){
                number = ceiling;
            }
            int[] ints = MyUtils.getNumberIntsLimitedWithNoRepeat(number,0,ceiling);
            List<NewsAndroid> newsList = new ArrayList<>();
            System.out.println(Arrays.toString(ints));
            for (int anInt : ints) {
                assert todayNewsList != null;
                newsList.add(todayNewsList.get(anInt));
            }
            System.out.println("mycontainer size:"+DataCash.myContainer.getNewsLinkedList().size());
            return newsList;
        }
    }

    @Override
    public List<NewsAndroid> offerAllNewsSegment() {
        int newsNum = newsMapper.getNum();
        Random random = new Random();
        int limit = newsNum/100;
        if(limit >=10){
            int yejiao = random.nextInt(limit)+1;
            List<NewsEntity> newsByLimit = newsMapper.getNewsByLimit((yejiao - 1) * 100, 100);
            return MyUtils.convertEntityToAndroidInNews(newsByLimit);
        }
        else if(limit>=1){
            int start = 0;
            if(newsNum != 100){
                start = random.nextInt(newsNum-100);
            }
            return MyUtils.convertEntityToAndroidInNews(newsMapper.getNewsByLimit(start,100));
        }else {
            int start = 0;
            return MyUtils.convertEntityToAndroidInNews(newsMapper.getNewsByLimit(start,newsNum));
        }
    }

    @Override
    public List<NewsAndroid> offerNewsByPrecise(Long user_id) {
        List<NewsEntity> newsByPrecise = newsMapper.getNewsByPrecise(user_id);
        if(newsByPrecise==null||newsByPrecise.size()==0){
            int start = (int) (Math.random()*8000);
            newsByPrecise = newsMapper.getNewsByTitleByLimit("%%",start,20);
        }
        return MyUtils.convertEntityToAndroidInNews(newsByPrecise);
    }



    @Override
    public List<NewsAndroid> offerIndividuationNews(int[] intarray) {
        int num = intarray.length;
        int[] ints = MyUtils.handleIntArrayFromAndroid(intarray);
        System.out.println("ints:"+ Arrays.toString(ints));
        Random random = new Random(System.currentTimeMillis());
        List<NewsAndroid> res = new LinkedList<>();
        for(int i = 0;i<num;i++){
            List<NewsAndroid> littleRepertory = DataCash.getLittleRepertory(i + 1);
            if(littleRepertory!=null){
                int ceiling = littleRepertory.size()-ints[i];
                if(ceiling>0){
                    int start = random.nextInt(ceiling);
                    res.addAll((littleRepertory.subList(start, start+ints[i])));
                }else{
                    res.addAll((littleRepertory));
                }
            }
        }
        Collections.shuffle(res);
        return res;
    }

    @Override
    public List<NewsAndroid> offerIndividuationNews() {
        int[] ints = {1,2,3,4,5,6,7,8,9,10,11,12,13,14};
        return offerIndividuationNews(ints);
    }

    @Override
    public List<NewsAndroid> offerNewsByTitle(String title) {

        System.out.println("进入title ---get");
        Random random = new Random(System.currentTimeMillis());
        title = "%"+title+"%";
        int ceiling = newsMapper.getNumByTitleLike(title)-100;
        if(ceiling>0){
            int start = random.nextInt(ceiling);
            List<NewsEntity> newsEntityList = newsMapper.getNewsByTitleByLimit(title,start,100);
            return MyUtils.convertEntityToAndroidInNews(newsEntityList);
        }else{
            List<NewsEntity> newsEntityList = newsMapper.getNewsByTitleByLimit(title,0,ceiling+100);
            return MyUtils.convertEntityToAndroidInNews(newsEntityList);
        }

    }

    @Override
    public List<NewsAndroid> offerNewsByTag(String tag) {
        int module = MyUtils.getModuleIdByTag(tag);
        Random random = new Random(System.currentTimeMillis());
        int ceiling = newsMapper.getNumByModule_id(module)-100;
        if(ceiling>0){
            int start = random.nextInt(ceiling);
            return MyUtils.convertEntityToAndroidInNews(newsMapper.getNewsByModule_idByLimit(module,start,100));
        }else{
            return MyUtils.convertEntityToAndroidInNews(newsMapper.getNewsByModule_idByLimit(module,0,ceiling+100));
        }

    }

    @Override
    public List<AndroidText> offerAPIServer(String base64) {
        base64 = base64.replaceAll("\"","");
        System.out.println(base64);
        JSONObject json = MyUtils.usrAPI(base64);
        assert json != null;
        JSONArray jsonArray = json.getJSONArray("items");
        StringBuffer stringBuffer = new StringBuffer();
        for (Object o : jsonArray) {
            String temp = "";
            temp =(String) ((JSONObject)o).get("itemstring");
            stringBuffer.append(temp);
        }
        System.out.println(stringBuffer);
        AndroidText testMessageForAndroid = new AndroidText(stringBuffer.toString());
        List<AndroidText> list = new LinkedList<>();
        list.add(testMessageForAndroid);
        return list ;
    }

    public List<?> getLittleRepertory(int module_id){
        switch (module_id){
            case 1:try{ return (List<?>) redisTemplate.opsForValue().get("list0"); }catch (Exception e){ return DataCash.list0; }
            case 2:try{ return (List<?>) redisTemplate.opsForValue().get("list1"); }catch (Exception e){ return DataCash.list1; }
            case 3:try{ return (List<?>) redisTemplate.opsForValue().get("list2"); }catch (Exception e){ return DataCash.list2; }
            case 4:try{ return (List<?>) redisTemplate.opsForValue().get("list3"); }catch (Exception e){ return DataCash.list3; }
            case 5:try{ return (List<?>) redisTemplate.opsForValue().get("list4"); }catch (Exception e){ return DataCash.list4; }
            case 6:try{ return (List<?>) redisTemplate.opsForValue().get("list5"); }catch (Exception e){ return DataCash.list5; }
            case 7:try{ return (List<?>) redisTemplate.opsForValue().get("list6"); }catch (Exception e){ return DataCash.list6; }
            case 8:try{ return (List<?>) redisTemplate.opsForValue().get("list7"); }catch (Exception e){ return DataCash.list7; }
            case 9:try{ return (List<?>) redisTemplate.opsForValue().get("list8"); }catch (Exception e){ return DataCash.list8; }
            case 10:try{ return (List<?>) redisTemplate.opsForValue().get("list9"); }catch (Exception e){ return DataCash.list9; }
            case 11:try{ return (List<?>) redisTemplate.opsForValue().get("list10"); }catch (Exception e){ return DataCash.list10; }
            case 12:try{ return (List<?>) redisTemplate.opsForValue().get("list11"); }catch (Exception e){ return DataCash.list11; }
            case 13:try{ return (List<?>) redisTemplate.opsForValue().get("list12"); }catch (Exception e){ return DataCash.list12; }
            case 14:try{ return (List<?>) redisTemplate.opsForValue().get("list13"); }catch (Exception e){ return DataCash.list13; }
            case 15:try{ return (List<?>) redisTemplate.opsForValue().get("list14"); }catch (Exception e){ return DataCash.list14; }

            default:return null;
        }
    }


}
