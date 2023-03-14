package com.example.demo.utils;

import com.alibaba.fastjson.JSONObject;
import com.chinamobile.cmss.sdk.ocr.ECloudDefaultClient;
import com.chinamobile.cmss.sdk.ocr.IECloudClient;
import com.chinamobile.cmss.sdk.ocr.http.constant.Region;
import com.chinamobile.cmss.sdk.ocr.http.signature.Credential;
import com.chinamobile.cmss.sdk.ocr.request.IECloudRequest;
import com.chinamobile.cmss.sdk.ocr.request.ocr.OcrRequestFactory;
import com.example.demo.dataCash.DataCash;
import com.example.demo.mapper.NewsMapper;
import com.example.demo.mapper.UserPhoneMapper;
import com.example.demo.pojo.ModuleEntity;
import com.example.demo.pojo.NewsAndroid;
import com.example.demo.pojo.NewsEntity;
import com.example.demo.pojo.UserPhoneEntity;

import java.io.*;
import java.util.*;

/**
 * @Auther: GengXuelong
 * <p> 类 功能描述如下:
 * @Description:
 *     提供本项目编写过程中常用的工具
 */
public class MyUtils {
    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     得到一个 大于等于min小于max的一个随机数
     */
    public  static int myIntRandom(int min,int max){

        Random random =new Random();
        int res = 0;
        if(min>=max){
            return -1;
        }
        while(true){
            res = random.nextInt(max);
            if(res>=min)break;
            else{
                res = random.nextInt(max);
            }
        }
        return res;
    }

    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     得到 number个大于等于 floorLimit 且小于 ceilingLimit 的数 ，以数组形式返回
     */
    public static int[] getNumberIntsLimitedWithNoRepeat(int number,
                                                         int floorLimit,
                                                         int ceilingLimit){

        if(number <=0||floorLimit>=ceilingLimit||number>ceilingLimit-floorLimit){
            return null;
        }
        int[] ints = new int[number];
        if(ceilingLimit-floorLimit < 1.5*number){
            int start = (int) (Math.random()*(ceilingLimit-floorLimit-number));
            for (int i = 0;i < number;i++){
                ints[i] = floorLimit+i+start;
            }
            return ints;
        }
        int index = 0;
        while(index<number){
            int temp = myIntRandom(floorLimit,ceilingLimit);
            boolean isOK = true;
            for(int i = 0;i<index;i++){
                if(ints[i]==temp){
                    isOK = false;
                    break;
                }
            }
            if(isOK){
                ints[index] = temp;
                index ++;
            }
        }
        return ints;
    }

    public static NewsEntity convertAndroidToEntityInNews(NewsAndroid newsAndroid){
        String content = newsAndroid.getArticle();
        String title = newsAndroid.getTitle();
        int module_id = getModuleIdByTag(newsAndroid.getTag());
        String url = "https://www.baidu.com";
        return new NewsEntity(content,new Date(),title,module_id,url);
    }
    public static List<NewsEntity> convertAndroidToEntityInNews(List<NewsAndroid> newsAndroidList){
        List<NewsEntity> newsEntityList = new ArrayList<>();
        for (NewsAndroid newsAndroid : newsAndroidList) {
            String content = newsAndroid.getArticle();
            String title = newsAndroid.getTitle();
            int module_id = getModuleIdByTag(newsAndroid.getTag());
            String url = "https://www.baidu.com";
            newsEntityList.add(new NewsEntity(content,new Date(),title,module_id,url));
        }
        return newsEntityList;
    }

    public static NewsEntity convertAndroidToEntityInNewsWithId(NewsAndroid newsAndroid){
        long id;
        try{
            id = Long.parseLong(newsAndroid.getId().trim());
        }catch (Exception e){
            id = -1L;
        }
        String content = newsAndroid.getArticle();
        String title = newsAndroid.getTitle();
        int module_id = getModuleIdByTag(newsAndroid.getTag());
        String url = "https://www.baidu.com";
        return new NewsEntity(content,new Date(),title,module_id,url);
    }


    public static List<NewsAndroid> convertEntityToAndroidInNews(List<NewsEntity> newsEntityList){
        List<NewsAndroid> res = new ArrayList<>();
        for (NewsEntity newsEntity : newsEntityList) {
            NewsAndroid android = new NewsAndroid(newsEntity.getId()+"",newsEntity.getTitle(), getTagByModuleId(newsEntity.getModule_id()),newsEntity.getContent());
            res.add(android);
        }
        return res;
    }
    public static NewsAndroid convertEntityToAndroidInNews(NewsEntity newsEntity){
        return new NewsAndroid(newsEntity.getId()+"",newsEntity.getTitle(), getTagByModuleId(newsEntity.getModule_id()),newsEntity.getContent());
    }


    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     从官网出题方所给的数据文件提取数据并存入数据库
     */
    public static void readTxt(String file_path, NewsMapper newsMapper)  {

        File file = new File(file_path);//file 只能是TXT文件的上一级目录的上一级目录
        List<File> files1 = new LinkedList<>();
        List<File> files2 = new LinkedList<>();
        if(!file.isFile()){
            files1 = Arrays.asList(Objects.requireNonNull(file.listFiles()).clone());
        }
        List<NewsEntity> allNews = new ArrayList<>();
        for (File file1 : files1) {
            if(!file1.isFile()){
                String path  = file1.getPath();
                System.out.println(path);
                //得到tag
                List<String> strings = new LinkedList<>();
                StringTokenizer stringTokenizer=new StringTokenizer(path,"\\",true);
                while(stringTokenizer.hasMoreElements()){
                    strings.add(stringTokenizer.nextToken());
                }
                String tag = strings.get(strings.size()-1);
                System.out.println(tag);
                files2 = Arrays.asList(Objects.requireNonNull(file1.listFiles()));
                int count = 0;
                List<NewsEntity> newsEntityList = new ArrayList<>();
                for (File file2 : files2) {
                    String path2 = file2.getPath();
                    System.out.print(path2);
                    if(path2.endsWith(".txt")){
                        /*if(count<101){
                            count++;
                            continue;
                        }*/
                        BufferedReader bufferedReader = null;
                        try {
                            bufferedReader = new BufferedReader(new FileReader(path2));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        String first = "";
                        StringBuilder text = new StringBuilder();
                        String temp = null;
                        try {
                            assert bufferedReader != null;
                            first = bufferedReader.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        while(true){
                            try {
                                if ((temp = bufferedReader.readLine()) == null) break;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            text.append(temp);
                        }
                        int module_id = getModuleIdByTag(tag);
                        NewsEntity news_elem = new NewsEntity(text.toString(),new Date(),first.toString(),module_id,"https://www.baidu.com");
                        newsEntityList.add(news_elem);
                        count++;
                        if(count>=1000){
                            break;
                        }
                    }
                }
                allNews.addAll(newsEntityList);

            }
        }
        Collections.shuffle(allNews);
        newsMapper.addAll(allNews);
        System.out.println();
        System.out.println("存入14000条");
    }
    public static int getModuleIdByTag(String tag){
        for (ModuleEntity moduleEntity : DataCash.moduleEntities) {
            if(moduleEntity.getName().equals(tag)){
                return moduleEntity.getId();
            }
        }
        return myIntRandom(1,16);
    }

    public static String getTagByModuleId(int number){
        for (ModuleEntity moduleEntity : DataCash.moduleEntities) {
            if(moduleEntity.getId()==(number)){
                return moduleEntity.getName();
            }
        }
        return getTagByModuleId( myIntRandom(1,16));
    }
    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     实现了调用移动云图像识别API的功能
     */
    public static JSONObject usrAPI(String base){//base是base64码

        /**
         * @Auther: GengXuelong
         * <p> 变量描述如下:
         * @Description:
         *     个人移动云账号的访问密钥
         */
        String user_ak = "79c87da1c098466fbb6dc17f21d9e517";
        String user_sk = "e14f713edb7042178ff3f059d5d311f1";

        Credential credential = new Credential(user_ak,   user_sk);
        IECloudClient client = new ECloudDefaultClient(credential, Region.POOL_SZ);
        HashMap<String, Object> bankCardParam = new HashMap<>();
        JSONObject bankCardOptions = new JSONObject();
        bankCardOptions.put("ret_warncode_flag", false);
        bankCardOptions.put("ret_border_cut_image", false);
        bankCardOptions.put("enable_copy_check", false);
        bankCardOptions.put("enable_reshoot_check", false);
        bankCardOptions.put("enable_border_check", false);
        bankCardOptions.put("enable_recognize_warn_code", false);
        bankCardOptions.put("enable_quality_value", false);
        bankCardParam.put("options", bankCardOptions);
        IECloudRequest bankCardRequest = OcrRequestFactory.getOcrBase64Request("/api/ocr/v1/general",base, bankCardParam);
        try {
            JSONObject response = (JSONObject) client.call(bankCardRequest);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     自定义的数学表达式，用来更好的拟合用户的偏好习惯
     */
    public static double my_similar_exponent_function(int x, int num, int min, int max){

        double y = (double)1/num;
        if(min<100){
            y  =  ( -Math.exp(-Math.log(2)-(double)2*x/1000))+0.5+1.0/num;
        }else if(min < 500){
            y  =  ( -Math.exp(-Math.log(2)-(double)x/1000))+0.5+1.0/num;
        }else if(min<1000){
            y  =  (( -Math.exp(-Math.log(2)-(double)x/1000/15))  +  0.5)  +  1.0/num;
        }else if(min < 2000){
            y  =  (( -Math.exp(-Math.log(2)-(double)x/1000/30))  +  0.5)  +  1.0/num;
        }else if(min < 4000){
            y  =  (( -Math.exp(-Math.log(2)-(double)x/1000/60))  +  0.5)  +  1.0/num;
        }else{
            int a = min/30;
            y  =  (( -Math.exp(-Math.log(2)-(double)x/1000/a))  +  0.5)  +  1.0/num;
        }
        return y;
    }

    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     得到数据最小值
     */
    public static int getMin(int[] ints){

        int min = ints[0];
        for(int a : ints){
            if(a<min) min =a;
        }
        return min;
    }

    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     得到数据最大值
     */
    public static int getMax(int[] ints){

        int m = ints[0];
        for(int a : ints){
            if(a>m) m =a;
        }
        return m;
    }

    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     处理安卓端传回的用户对各个类型新闻的观看习惯，得到本次推荐新闻中各个类型的占比
     */
    public static int[] handleIntArrayFromAndroid(int[] intarray){

        int min = getMin(intarray);
        int max = getMax(intarray);
        int num = intarray.length;
        double[] doubles =new double[num];
        for (int i= 0;i<num;i++) {
            intarray[i] = (int)((my_similar_exponent_function(intarray[i],num,min,max))*100000);
        }
        int total = 0;
        for (int anInt : intarray) {
            total += anInt;
        }
        for(int i = 0;i<num;i++){
            doubles[i] = (double)Math.round((double)intarray[i]/total * 100)/100;//四舍五入
        }
        int[] ints = new int[num];
        for(int i = 0;i<num;i++){
            if(i<num-1)
                ints[i] = (int)(doubles[i]*100);
            else{
                int sum = 0;
                for(int j=0;j<num-1;j++){
                    sum += ints[j];
                }
                ints[i]= 100-sum;
            }
        }
        return ints;
    }


    /**
     * @Auther: GengXuelong
     * <p> 函数功能描述如下:
     * @Description:
     *     消除List<NewsEntity>中的重复项
     */
    public static void killRepeat(List<NewsEntity> newsEntityList) {
        int num = newsEntityList.size();
        List<NewsEntity> remove = new ArrayList<>();
        for(int i = 0;i<num;i++){
            for(int j=i+1;j<num;j++){
                if((newsEntityList.get(i).getTitle().trim()).equals(newsEntityList.get(j).getTitle().trim())){
                    remove.add(newsEntityList.get(i));
                    break;
                }else if((newsEntityList.get(i).getContent().trim()).equals(newsEntityList.get(j).getContent().trim())){
                    remove.add(newsEntityList.get(i));
                    break;
                }else if ((newsEntityList.get(i).getUrl().trim()).equals(newsEntityList.get(j).getUrl().trim()));
            }
        }
        newsEntityList.removeAll(remove);
        newsEntityList.addAll(remove);
    }
}
