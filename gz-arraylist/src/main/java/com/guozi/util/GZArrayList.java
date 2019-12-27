package com.guozi.util;

import java.util.Arrays;

/**
 * 作者：ye.guolin
 * 日期：2019/08/13
 * 描述：手写ArrayList
 */
public class GZArrayList<E> {

    // 默认初始容量
    private static final int DEFAULT_CAPACITY = 10;

    // 空数组
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    // 存储元素的数组
    private Object[] elementData;

    // 集合的大小,刚创建的集合容量为0
    private int size;

    public GZArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            elementData = new Object[DEFAULT_CAPACITY];
        } else {
            throw new IllegalArgumentException("数组长度不合法: " + initialCapacity);
        }
    }

    public GZArrayList() {
        elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    // 获取集合元素
    public E get(int index) {
        rangeCheck(index);
        return elementData(index);
    }

    // 添加元素
    public boolean add(E e) {
        // 判断是否需要扩容，并操作
        ensureCapacityInternal(size + 1);

        elementData[size++] = e;
        return true;
    }

    // 给指定位置添加指定元素
    public void add(int index, E e) {
        // 插入的位置不得小于0，不得与原有元素不连续
        rangeCheckForAdd(index);
        // 判断并扩容
        ensureCapacityInternal(size + 1);
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = e;
        size++;
    }

    // 替换集合中某一元素
    public E set(int index, E e) {
        E oldValue = get(index);
        elementData[index] = e;
        return oldValue;
    }

    // 删除某一元素
    public E remove(int index) {
        E oldValue = get(index);
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        elementData[--size] = null;
        return oldValue;
    }

    // 判断数组初始长度是否为0，若为0，则将最小容量设置为默认容量
    public void ensureCapacityInternal(int minCapacity) {
        // 如果数组长度为0
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            // 取最小容量为默认容量和最小容量中大的数
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        // 根据最小容量扩容数组,如果数组长度小于最小容量则扩容
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }

    // 数组扩容
    public void grow(int minCapacity) {
        // 原有数组容量
        int oldCapacity = elementData.length;
        // 扩容后的容量
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        // 如果扩容后的容量小于最小容量,则扩容为最小容量
        if (newCapacity - minCapacity < 0) {
            newCapacity = minCapacity;
        }
        // 给数组扩容
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    // 检查数组下标越界
    private void rangeCheck(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException("数组下标越界");
    }

    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException("数组下标越界");
    }

    // 获得集合长度
    public int size() {
        return size;
    }

    // 获得数组的长度
    public int length() {
        return elementData.length;
    }

    @SuppressWarnings("unchecked")
    E elementData(int index) {
        return (E) elementData[index];
    }


}
