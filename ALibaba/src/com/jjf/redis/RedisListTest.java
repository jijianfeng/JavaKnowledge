package com.jjf.redis;

import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Created by jjf_lenovo on 2017/3/28.
 */
public class RedisListTest {
    Jedis jedis = null;
    static final String DATASOURCE_URL = "182.254.213.106";
    static final int DATASOURCE_SORT = 6379;
    static final String DATASOURCE_PASS = "123456";
    static final int DATASOURCE_SELECT = 1;
    public RedisListTest(){
        //基本配置
        jedis = new Jedis(DATASOURCE_URL,DATASOURCE_SORT);
        jedis.auth(DATASOURCE_PASS);
        jedis.select(DATASOURCE_SELECT);
    }

    @Test
    public void testLPush(){
        /**
         * 将一个或多个值 value 插入到列表 key 的表头

         如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表头： 比如说，对空列表 mylist 执行命令 LPUSH mylist a b c ，列表的值将是 c b a ，这等同于原子性地执行 LPUSH mylist a 、 LPUSH mylist b 和 LPUSH mylist c 三个命令。

         如果 key 不存在，一个空列表会被创建并执行 LPUSH 操作。

         当 key 存在但不是列表类型时，返回一个错误。

         在Redis 2.4版本以前的 LPUSH 命令，都只接
         */
        //jedis.lpushx("list","a","b","c") 会报错，因为lpushx必须key存在
        Long lpush = jedis.lpush("list","a","b","c"); //返回的是列表的长度!!不是操作数
        Long lpushx = jedis.lpushx("list","a","b","c");
//        System.out.println(lpush);
        Assert.assertTrue(lpush==3&&lpushx==6);
        Assert.assertTrue(jedis.del("list")==1);
    }


}
