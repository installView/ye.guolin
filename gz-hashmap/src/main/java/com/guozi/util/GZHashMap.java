package com.guozi.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：ye.guolin
 * 日期：2019/08/16
 * 描述：
 */
public class GZHashMap<K, V> implements GZMap<K, V> {

    // 默认初始化集合大小
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

    // HashMap默认负载因子
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private int defaultInitSize;// 用于使用时用户指定集合长度

    private float defaultLoadFactor;// 用于使用时用户指定负载因子

    // map中key-value的对数
    private int size;

    // 盛装数据的数组容器
    private Entry<K, V>[] table;

    // 无参构造
    public GZHashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    // 带参构造
    public GZHashMap(int defaultInitialCapacity, float defaultLoadFactor) {
        if (defaultInitialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
                    defaultInitialCapacity);
        if (defaultLoadFactor <= 0 || Float.isNaN(defaultLoadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +
                    defaultLoadFactor);
        // 传入参数合法,数组初始长度，负载因子赋值
        this.defaultInitSize = defaultInitialCapacity;
        this.defaultLoadFactor = defaultLoadFactor;
        table = new Entry[defaultInitSize];
    }

    @Override
    public V put(K key, V value) {
        // 先判断是否需要扩容且扩容后重新散列
        if (size >= defaultInitSize * defaultLoadFactor) {
            // 此处扩容并散列
            resize(2*defaultInitSize);
        }
        // 计算新添加数据在数组中的位置
        int index = hash(key) & (defaultInitSize - 1);
        // 若该位置为空，则直接添加进去
        if (table[index] == null) {
            table[index] = new Entry<K, V>(key, value, null);
            ++size;
        }
        // 若该位置不为空，则遍历链表判断是否有key值相同的元素，若有，替换，若无，链表头部添加此元素
        else {
            // 拿到该位置链表的第一个元素，逐一向下遍历,知道next为null
            Entry<K, V> entry = table[index];
            Entry<K, V> e = entry;
            while (e.next != null) {
                // 若有相同key值，替换value结束遍历
                if (key == e.getKey() || key.equals(e.getKey()))
                    return e.setValue(value);

                e = e.next;
            }
            // 若遍历完皆无相同的key，则将改元素插入表头
            table[index] = new Entry<>(key, value, entry);
            ++size;
        }
        return null;
    }

    // 扩容
    private void resize(int i) {
        // 创建新的数组
        Entry[] newTable = new Entry[i];
        defaultInitSize = i;
        size = 0;
        rehash(newTable);
    }

    // 重新散列
    private void rehash(Entry[] newTable) {
        // 将老数组中的元素一一添加到新数组中
        // 先将老数组中的元素全取出来放入集合中
        List<Entry<K, V>> entryList = new ArrayList<>();
        for (Entry<K, V> entry : table) {
            while (entry != null) {
                entryList.add(entry);
                entry = entry.next;
            }
        }
        // 将新数组替换老数组
        if (newTable.length > 0) {
            table = newTable;
        }
        // 将老集合的元素放入新集合中
        for (Entry<K, V> entry : entryList) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public V get(K key) {
        // 计算新添加数据在数组中的位置
        int index = hash(key) & (defaultInitSize - 1);
        Entry<K,V> entry = table[index];
        while (entry != null){
            if(key == entry.getKey() || key.equals(entry.getKey())){
                return entry.getValue();
            }
            entry = entry.next;
        }
        return null;
    }

    static class Entry<K, V> implements GZMap.Entry<K, V> {
        // key值
        final K key;

        // value值
        V value;

        // 如果出现hash冲突，指向下一节点
        Entry<K, V> next;

        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            // 设置新的value值，返回老的value值
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }


    // 先获取到key的hashCode，然后进行移位再进行异或运算，为什么这么复杂，不用想肯定是为了减少hash冲突
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    // 返回map集合中key-value对数
    public int size() {
        return size;
    }

}
