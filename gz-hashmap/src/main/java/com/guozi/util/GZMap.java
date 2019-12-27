package com.guozi.util;

public interface GZMap<K, V> {

    // 向集合插入元素
    V put(K key, V value);

    // 根据k 从Map集合中查询元素
    V get(K key);

    interface Entry<K,V>{
        K getKey();

        V getValue();

        V setValue(V value);
    }
}
