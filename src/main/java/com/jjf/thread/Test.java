package com.jjf.thread;


/**
 * @author IronMan 2020/07/23
 */
public class Test {
    public static void main(String[] args) {
//        System.out.println(hash(123));
//        System.out.println(hash("a"));
//        System.out.println(hash("b"));
    }

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
}
