package com.guozi.controller;

import com.guozi.annotation.GZController;
import com.guozi.annotation.GZQualifier;
import com.guozi.annotation.GZRequestMapping;
import com.guozi.service.MyService;

/**
 * 作者：ye.guolin
 * 日期：2019/08/12
 * 描述：
 */
@GZController
@GZRequestMapping("/gz")
public class MyController {

    @GZQualifier("myService")
    private MyService myservice;

    @GZRequestMapping("/query")
    public String query(){
        return myservice.query();
    }

}
