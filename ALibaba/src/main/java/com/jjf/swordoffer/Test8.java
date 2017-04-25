package com.jjf.swordoffer;

/**
 * Created by jjf_lenovo on 2017/4/21.
 */
//输入一个整数，输出该数二进制表示中1的个数。其中负数用补码表示。
//考察位运算
public class Test8 {

    //垃圾版
    public int NumberOf1(int n) {
        char[] chs = Integer.toBinaryString(n).toCharArray();
        int count = 0;
        for(char ch: chs){
            if(ch=='1'){
                count++;
            }
        }
        return count;
    }

    //简单位运算版
    public static int NumberOf2(int n) {
        int flag = 1;
        int count = 0;
        while(flag!=0){
            if((n&flag)!=0){
                count++;
            }
            flag = flag << 1;
        }
        return count;
    }

    //更高级位运算版
    public static int NumberOf3(int n) {
        int count = 0;
        System.out.println(Integer.toBinaryString(n));
        while(n!=0){
            count++;
            n = (n-1) & n;
            System.out.println(Integer.toBinaryString(n));
        }
        return count;
    }

    public static void main(String args[]){
        System.out.println(NumberOf3(10));
//        int a = 9;
//        int b = -9;
//        int da = 2147483647;
//        char ch = 'd';
//        double c = Math.pow(2,32);
//        System.out.println(Integer.toBinaryString(a));
//        System.out.println(Integer.toBinaryString(b));
//        System.out.println(c);
//        String start = "\\u4e00";
//        String end = "\\u9fa5";
//        int s = Integer.parseInt(start.substring(2, start.length()), 16);
//        int e = Integer.parseInt(end.substring(2, end.length()), 16);
//        System.out.println(s+":"+e);
//        for (int i = 19968; i <= 40869; i++) {
//            System.out.println((char) i);
//        }
    }
}
