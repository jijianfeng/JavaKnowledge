package com.jjf.singleton;

/**
 * Created by jijianfeng on 2017/6/22.
 */
public class Singleton {
    //ตฅภý --ถ๖บบ
    public static final Singleton singleton = new Singleton(); //2.private

    private Singleton(){
        System.out.println("i init");
    };

    public  void doSomething(){
        //
    }

    //2ถ๖บบ
//    public Singleton getInstance(){
//        return singleton;
//    }
}

