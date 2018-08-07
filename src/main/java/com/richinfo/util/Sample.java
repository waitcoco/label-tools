package com.richinfo.util;

import com.baidu.aip.imageclassify.AipImageClassify;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class Sample {
    //设置APPID/AK/SK
    public static final String APP_ID = "11416276";
    public static final String API_KEY = "uPYrmZGWcZRkxXIvdO1VHeVC";
    public static final String SECRET_KEY = "MbhAIugEaB8hVHZu6kR2P1Nn65jZATQm";

    public static void main(String[] args) {
        // 初始化一个AipImageClassifyClient
        AipImageClassify client = new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);


        // 调用接口
        String path = "/Users/robinjie/Downloads/frames_002405.jpg";
        JSONObject res = client.carDetect(path, new HashMap<String, String>());
        System.out.println(res.toString(2));

        if(res.opt("result") !=null) {
            JSONArray result = (JSONArray) res.get("result");
            JSONObject car = (JSONObject) result.get(0);
            double score = car.optDouble("score");
            double per = (double)Math.round(score*10000)/100;
            System.out.println("这张图片是"+car.get("name")+"的概率为："+per+"%");
        }

    }
}