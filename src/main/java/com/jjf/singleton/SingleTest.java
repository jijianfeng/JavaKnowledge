package com.jjf.singleton;

import org.junit.Test;

/**
 * Created by jijianfeng on 2017/6/22.
 */
public class SingleTest {
    @Test
    public void test() throws InterruptedException {
        while(true){
            new Thread(()->{
                Singleton.singleton.doSomething();
            }).start();
            new Thread(()->{
                Singleton.singleton.doSomething();
            }).start();
        }
    }
}
