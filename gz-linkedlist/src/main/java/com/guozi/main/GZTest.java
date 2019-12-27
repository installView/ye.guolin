package com.guozi.main;


import com.guozi.util.GZLinkedList;

/**
 * 作者：ye.guolin
 * 日期：2019/08/14
 * 描述：
 */
public class GZTest {

    public static void main(String[] args) {
        GZLinkedList<Integer> linkedList = new GZLinkedList<>();
        linkedList.add(0);
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);
        linkedList.add(4);
        linkedList.add(5);
        linkedList.add(6);
        linkedList.add(7);
        linkedList.add(4,44);
        System.out.println(linkedList.get(4));
        System.out.println(linkedList.indexOf(4));
    }
}
