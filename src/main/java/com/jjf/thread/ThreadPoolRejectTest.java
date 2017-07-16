package com.jjf.thread;

import org.junit.*;
import org.junit.Test;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Random;
import java.util.concurrent.RejectedExecutionException;

/**
 * Created by jijianfeng on 2017/6/22.
 */
public class ThreadPoolRejectTest {

    private static ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();

    @Before
    public void init(){
        pool.setCorePoolSize(3);
        pool.setMaxPoolSize(5);
        pool.setQueueCapacity(10);
        pool.initialize();
        long start = System.currentTimeMillis();
        System.out.println(System.currentTimeMillis()-start);
    }

    /**
     * 不处理TaskRejectedException，会丢失线程任务
     */
    @Test
    public void normal(){
        //  一个个比较 CorePoolSize -> CorePoolSize+QueueCapacity -> MaxPoolSize+QueueCapacity
        //情况1：当线程数为3的时候，小于等于CorePoolSize，直接运行。
        //结果1：连续输出3个"end"

        //情况2：当线程数为5的时候，小于CorePoolSize+QueueCapacity，3个线程直接运行、2个放到队列中、等有空余线程后再运行。
        //结果2：先输出3个"end"，过一会再输出2个"end"

        //情况3：当线程数为14的时候，小于MaxPoolSize+QueueCapacity，3个线程直接运行、10个放到队列，并且额外开启1个线程，直到队列为空。
        //结果3：先输出4个"end"，过一会再输出4个"end"，过一会再输出4个"end"，过一会再输出2个"end"

        //情况4：当线程数为16的时候，大于于MaxPoolSize+QueueCapacity，3个线程直接运行、10个放到队列，并且额外开启2个线程，还有1个线程的执行被拒绝
        //结果4：reject一个线程（不会再被执行），先输出5个"end"，过一会再输出5个"end"，过一会再输出5个"end"，
        for(int i=0;i<16;i++){  //分别设为3 5 14
            try{
                pool.execute(new LowThread());
            }
            catch(RejectedExecutionException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 额外花费一个线程处理TaskRejectedException，任务不会丢失
     */
    @Test
    public void sacrificeOneThread(){
        for(int i=0;i<16;i++){  //分别设为3 5 14
            new Thread(() ->{
                for(;;){//比while true 性能高
                    try{
                        pool.execute(new LowThread());
                        break;
                    }
                    catch (TaskRejectedException exception){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    continue;
                }
            }).start();
        }
    }

    @After
    public void show() throws InterruptedException {
        Thread.sleep(1000000000);
    }
}

/**
 * 慢线程
 */
class LowThread implements Runnable{
    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end");
    }
}