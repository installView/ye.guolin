package com.guozi.service;

import com.guozi.annotation.GZService;

/**
 * 作者：ye.guolin
 * 日期：2019/08/12
 * 描述：
 */
@GZService("myService")
public class MyService {

    public String query(){
        return "index";
    }
}
