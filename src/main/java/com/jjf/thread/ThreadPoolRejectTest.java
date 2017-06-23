package com.jjf.thread;

import org.junit.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Random;
import java.util.concurrent.RejectedExecutionException;

/**
 * Created by jijianfeng on 2017/6/22.
 */
public class ThreadPoolRejectTest {
    public static void main(String[] args){
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(0);
        pool.setMaxPoolSize(2);
        pool.setQueueCapacity(3);
        pool.initialize();
        long start = System.currentTimeMillis();
        for(int i=0;i<10;i++){  //
            try{
                pool.execute(()->{
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("end");
                });
            }
            catch(RejectedExecutionException e){
                e.printStackTrace();
            }
        }
        System.out.println(System.currentTimeMillis()-start);
    }
}
