package com.jjf.swordoffer;

/**
 * Created by jjf_lenovo on 2017/4/24.
 */
public class Test9 {
    public static void main(String args[]){
        int i = 0x7FFFFFFF;//涨姿势了
        int j = 0xFFFFFFFF;
        System.out.println(Integer.toBinaryString(i).length());
        System.out.println(Integer.toBinaryString(j).length());
        System.out.println(powWithOffer(2,300));
        System.out.println(Math.pow(2,300));
    }

    //offer版改进求次方
    public static double powWithOffer(double base,int exp){
        if(exp==0){
            return 1;
        }
        if(exp==1){
            return base;
        }
        double result = powWithOffer(base,exp>>1);
        result = result * result;
        if((exp & 0x1)==1){ //与运算判断是否为奇数,是奇数表明少乘了一个base
            result = result *base;
        }
        return result;
    }

    //疯狂if循环版，大众思想
    public double Power2(double base, int exponent) {
        if(base ==0){
            return 0;
        }
        double result = base;
        if(exponent>0){
            for(int i=1;i<exponent;i++){
                result = result * base;
            }
            return result;
        }
        if(exponent<0){
            exponent = Math.abs(exponent);
            for(int i=1;i<exponent;i++){
                result = result * base;
            }
            return 1/result;
        }
        return 1;
    }

    //这他妈都能过
    public double Power1(double base, int exponent) {
        return Math.pow(base,Double.valueOf(exponent));
    }
}
