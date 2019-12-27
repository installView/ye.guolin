package com.guozi.main;


import com.guozi.util.GZHashMap;
import com.guozi.util.GZMap;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：ye.guolin
 * 日期：2019/08/14
 * 描述：
 */
public class GZTest {

    public static void main(String[] args) {
        GZMap<String,String > gzMap = new GZHashMap<>(4,0.75F);
        gzMap.put("first","第一个");
        gzMap.put("second","第二个");
        gzMap.put("third","第三个");
        gzMap.put("fourth","第四个");
        System.out.println(gzMap.get("first"));
        System.out.println(gzMap.get("second"));
//        System.out.println(((GZHashMap<String, String>) gzMap).size());
//        gzMap.put("first","第san个");
//        System.out.println(gzMap.get("first"));

    }
}
