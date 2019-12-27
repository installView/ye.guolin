package com.guozi.util;

/**
 * 作者：ye.guolin
 * 日期：2019/08/15
 * 描述：
 */
public class GZLinkedList<E> {

    // 集合的大小,刚创建的集合容量为0
    private int size;

    // 首个节点
    private Node<E> first;

    // 最后一个节点
    private Node<E> last;


    // 给链表添加元素，从尾部添加
    public boolean add(E e) {
        linkLast(e);
        return true;
    }

    // 从头部添加元素
    public void addFirst(E e) {
        linkFirst(e);
    }

    // 从尾部添加元素，没有返回值
    public void addLast(E e) {
        linkLast(e);
    }

    // 获取链表的值
    public E get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    // 替换指定位置的元素
    public E set(int index, E e) {
        // 检查下边是否越界
        checkElementIndex(index);
        // 查得当前index位置的节点
        Node<E> x = node(index);
        E oldValue = x.item;
        // 替换当前节点内容
        x.item = e;
        return oldValue;
    }

    // 指定位置添加元素
    public void add(int index, E e) {
        checkPositionIndex(index);
        if (index == size)
            linkLast(e);
        else {
            linkBefore(e,node(index));
        }
    }

    // 查找元素在链表中的位置
    public int indexOf(Object o) {
        int index = 0;
        if (o == null) {
            for (Node node = first; node != null; node = node.next) {

                if (node.item == null)
                    return index;
                index++;
            }
        } else {
            for (Node node = first; node != null; node = node.next) {
                if (o.equals(node.item)) {
                    return index;
                }
                index++;
            }

        }
        return -1;
    }


    // 查询index位置的节点
    private Node<E> node(int index) {
        Node<E> x = first;
        // 二分法判断从头部还是尾部开始查找
        if (index < (size >> 1)) {
            // 从first开始查找
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
        } else {
            x = last;
            for (int i = size - index - 1; i > 0; i--) {
                x = x.prev;
            }
        }

        return x;
    }

    // 从链表头部添加元素
    private void linkFirst(E e) {
        // 获取first
        final Node<E> f = first;
        // 当前添加的节点
        final Node<E> newNode = new Node<>(null, e, f);
        // 当添加的为第一个节点
        if (f == null)
            last = newNode;
            // 当添加的不为第一个节点
        else
            // 将当前新的first指向原来的first
            newNode.next = f;
        first = newNode;
        size++;
    }

    // 从链表尾部添加元素
    private void linkLast(E e) {
        // 获取last
        final Node<E> l = last;
        // 当前添加的节点
        final Node<E> newNode = new Node<>(l, e, null);
        // 当添加的为第一个节点
        if (l == null)
            first = newNode;
            // 当添加的不是第一个节点
        else
            // 将当前的last节点指向新的last节点
            l.next = newNode;
        last = newNode;
        size++;
    }

    // 在指定节点前插入元素
    void linkBefore(E e, Node<E> succ) {
        // assert succ != null;
        final Node<E> pred = succ.prev;
        final Node<E> newNode = new Node<>(pred, e, succ);
        succ.prev = newNode;
        if (pred == null)
            first = newNode;
        else
            pred.next = newNode;
        size++;
    }

    // 获得链表大小
    public int size() {
        return size;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("下标越界");
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("下标越界");
    }

    // 节点的对象
    private static class Node<E> {
        // 本节点内容
        E item;
        // 上一节点
        Node<E> prev;
        // 下一节点
        Node<E> next;

        Node(Node<E> prev, E item, Node<E> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
