package com.jjf.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Created by jjf_lenovo on 2017/5/17.
 */
public class RedisPubSubTest {
    Jedis jedis = null;
    static final String DATASOURCE_URL = "182.254.213.106";
    static final int DATASOURCE_SORT = 6379;
    static final String DATASOURCE_PASS = "123456";
    static final int DATASOURCE_SELECT = 1;
    public RedisPubSubTest(){
        //ª˘±æ≈‰÷√
        jedis = new Jedis(DATASOURCE_URL,DATASOURCE_SORT);
        jedis.auth(DATASOURCE_PASS);
        jedis.select(DATASOURCE_SELECT);
    }

    @Test
    public void testSubScribe() throws InterruptedException {
        jedis.subscribe(new PubSubTest(),"key");
        Thread.sleep(100000 );
    }
}
