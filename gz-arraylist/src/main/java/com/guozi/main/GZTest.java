package com.guozi.main;

import com.guozi.util.GZArrayList;

import java.util.ArrayList;

/**
 * 作者：ye.guolin
 * 日期：2019/08/14
 * 描述：
 */
public class GZTest {

    public static void main(String[] args) {
        GZArrayList<String> list = new GZArrayList<>(4);
        list.add("0000");
        list.add("2222");
        list.add("3333");
        list.add(1,"1111");
        list.remove(1);
        System.out.println(list.length()+""+list.size());
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i)+",");
        }
    }
}
