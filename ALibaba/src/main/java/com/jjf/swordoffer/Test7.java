package com.jjf.swordoffer;

/**
 * Created by jjf_lenovo on 2017/4/21.
 */

/**
 * 大家都知道斐波那契数列，现在要求输入一个整数n，请你输出斐波那契数列的第n项。
 n<=39
 */
public class Test7 {

    /**
     * 垃圾递归算法，重复计算很严重,效率低到不行..
     * @param n
     * @return
     */
    public static int Fibonacci(int n) {
        if(n==1||n==2){
            return 1;
        }
        else{
            return Fibonacci(n-1)+Fibonacci(n-2);
        }
    }

    /**
     * 剑指offer的较优解，不是最好的
     * @param n
     * @return
     */
    public static int FibonacciByOffer(int n){
        if(n<2){
            return n==0?0:1;
        }
        int a=0,b=1,c=1;
        for(int i=2;i<=n;i++){
            c=a+b;
            a=b;
            b=c;
        }
        return c;
    }

    /**
     * 暴力解
     * @param n
     * @return
     */
    public static int FibonacciByBom(int n){
        if(n<2){
            return n==0?0:1;
        }
        else{
            int tem[] = new int [n];
            tem[0] = 1;
            tem[1] = 1;
            for(int i=2;i<n;i++){
                tem[i] = tem[i-1]+tem[i-2];
            }
            return tem[n-1];
        }
    }

    public static void main(String[] args){
        //递归效率低下
//        Long start = System.currentTimeMillis();
//        System.out.println(Fibonacci(50));
//        Long end = System.currentTimeMillis();//计算第五十个树需要40954ms，40s以上了！！！ i7-4710mq的cpu......
//        System.out.println(end-start);
        //暴力解费空间，效率还行
//        Long start = System.currentTimeMillis();
//        System.out.println(FibonacciByBom(10000000));
//        Long end = System.currentTimeMillis();//计算第1000W个数只需26ms
//        System.out.println(end-start);
        //动态规划，较优解
//        Long start = System.currentTimeMillis();
//        System.out.println(FibonacciByOffer(10000000));
//        Long end = System.currentTimeMillis();//计算第1000W个数只需7ms
//        System.out.println(end-start);
//        for(int i=1;i<=39;i++){
//            System.out.println(FibonacciByoffer(i));
//        }
//        for(int i=1;i<=39;i++){
//            System.out.println(Fibonacci(i));
//        }
    }
}
