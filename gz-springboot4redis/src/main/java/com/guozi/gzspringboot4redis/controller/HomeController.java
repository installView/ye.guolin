package com.guozi.gzspringboot4redis.controller;

import com.guozi.gzspringboot4redis.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 作者：ye.guolin
 * 日期：2020/01/04
 * 描述：
 */
@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/setValue")
    public String setValue() {
        String redisKey = "name";
        String redisValue = "叶果林";
        if (redisUtil.set(redisKey, redisValue))
            return "success";
        else
            return "failure";
    }
}
