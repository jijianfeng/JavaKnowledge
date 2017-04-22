package com.jjf.swordoffer;

/**
 * Created by jjf_lenovo on 2017/4/21.
 */
//输入一个整数，输出该数二进制表示中1的个数。其中负数用补码表示。
//考察位运算
public class Test8 {
    public static void main(String args[]){
        int a = 9;
        int b = -9;
        int da = 2147483647;
        char ch = 'd';
        double c = Math.pow(2,32);
        System.out.println(Integer.toBinaryString(a));
        System.out.println(Integer.toBinaryString(b));
        System.out.println(c);
        String start = "\\u4e00";
        String end = "\\u9fa5";
        int s = Integer.parseInt(start.substring(2, start.length()), 16);
        int e = Integer.parseInt(end.substring(2, end.length()), 16);
        System.out.println(s+":"+e);
        for (int i = 19968; i <= 40869; i++) {
            System.out.println((char) i);
        }
    }
}
