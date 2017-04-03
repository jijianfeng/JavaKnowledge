package com.jjf.redis;

import redis.clients.jedis.Jedis;

/**
 * Created by jjf_lenovo on 2017/3/31.
 */
public class RedisSortedSet {
    Jedis jedis = null;
    static final String DATASOURCE_URL = "182.254.213.106";
    static final int DATASOURCE_SORT = 6379;
    static final String DATASOURCE_PASS = "123456";
    static final int DATASOURCE_SELECT = 1;
    public RedisSortedSet() {
        //ª˘±æ≈‰÷√
        jedis = new Jedis(DATASOURCE_URL, DATASOURCE_SORT);
        jedis.auth(DATASOURCE_PASS);
        jedis.select(DATASOURCE_SELECT);
    }
}
