package com.jjf.thread;

import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;  
public class ThreadPoolTest {  
    public static void main(String[] args) {  
//        ExecutorService threadPool = Executors.newFixedThreadPool(3); 
//        ExecutorService threadPool = Executors.newCachedThreadPool();  
        ExecutorService threadPool = Executors.newSingleThreadExecutor(); 
//        ExecutorService threadPool = Executors.newCachedThreadPool(); 
        for(int i = 1; i < 5; i++) {  
            final int taskID = i;  
            threadPool.execute(new Runnable() {  
                public void run() {  
                    for(int i = 1; i < 500; i++) {  
//                        try {  
//                            Thread.sleep(20);// 为了测试出效果，让每次任务执行都需要一定时间  
//                        } catch (InterruptedException e) {  
//                            e.printStackTrace();  
//                        }  
                        System.out.println("第" + taskID + "次任务的第" + i + "次执行");  
                    }  
                }  
            });  
        }  
        threadPool.shutdown();// 任务执行完毕，关闭线程池  
    }  
}  