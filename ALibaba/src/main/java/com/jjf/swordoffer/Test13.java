package com.jjf.swordoffer;

/**
 * Created by jjf_lenovo on 2017/4/25.
 */
//写一个函数，求两个整数之和，不能使用 + - * / 四个运算符
public class Test13 {
    public static void main(String[] args){
        System.out.println(Integer.toBinaryString(-9));
//        System.out.println(add(123,-122));
    }

    public static int add(int number1,int number2){
        do{
            int a = number1 ^ number2; //异或可以看出不进位的加法  1+1=0 0+0=0 1+0=0+1=1
            number2 = (number1 & number2 )<<1; //如果进位，则往前一位，没进位0，左移也没关系
            number1 = a;
        }
        while(number2!=0);
        return number1 ;
    }
}
