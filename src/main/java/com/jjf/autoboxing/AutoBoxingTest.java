package com.jjf.autoboxing;

import org.junit.Test;

/**
 * Created by jijianfeng on 2017/6/22.
 */
public class AutoBoxingTest {

    @Test
    public void testAutoBox(){
        Long sum = 0L ;  //使用了自动装箱 慢
        //long sum = 0L ;//没使用，快
        long begin = System.currentTimeMillis();
        for(int i=0;i<Integer.MAX_VALUE;i++){
            sum+=i;
        }
        System.out.println("计算"+Integer.MAX_VALUE+"次"+sum);

        System.out.println(System.currentTimeMillis()-begin);
    }
}
