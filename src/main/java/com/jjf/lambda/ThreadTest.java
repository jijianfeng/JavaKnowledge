package com.jjf.lambda;

/**
 * Created by jijianfeng on 2017/6/10.
 */
public class ThreadTest {
    /**
     * 例1、用lambda表达式实现Runnable
     我开始使用Java 8时，首先做的就是使用lambda表达式替换匿名类，
     而实现Runnable接口是匿名类的最好示例。看一下Java 8之前的runnable实现方法，
     需要4行代码，而使用lambda表达式只需要一行代码。我们在这里做了什么呢？那就是用() -> {}代码块替代了整个匿名类。
     * @param args
     */
    public static void main(String[] args){
        // Java 8之前：
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Before Java8, too much code for too little to do");
            }
        }).start();

        //Java 8方式：
        new Thread( () -> System.out.println("In Java8, Lambda expression rocks !!") ).start();
    }

}
