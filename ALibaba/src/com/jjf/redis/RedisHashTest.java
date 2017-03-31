package com.jjf.redis;

import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.ScanResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by jjf_lenovo on 2017/3/27.
 */
public class RedisHashTest {
    Jedis jedis = null;
    static final String DATASOURCE_URL = "182.254.213.106";
    static final int DATASOURCE_SORT = 6379;
    static final String DATASOURCE_PASS = "123456";
    static final int DATASOURCE_SELECT = 1;
    public RedisHashTest(){
        //基本配置
        jedis = new Jedis(DATASOURCE_URL,DATASOURCE_SORT);
        jedis.auth(DATASOURCE_PASS);
        jedis.select(DATASOURCE_SELECT);
    }

    @Test
    public void testHSetAndHGetAndHDel(){
        //hset 将哈希表 key 中的域 field 的值设为 value 。
        //hget 返回哈希表 key 中给定域 field 的值。
        //hdel 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
        Assert.assertTrue(jedis.hset("hash","key","value")==1);
        Assert.assertTrue(jedis.hget("hash","key").equals("value"));
        Assert.assertTrue(jedis.hdel("hash","key")==1);
        Assert.assertTrue(jedis.hget("hash","key")==null);
    }

    @Test
    public void testHExists(){
        //hexists 查看哈希表 key 中，给定域 field 是否存在。
        jedis.hset("hash","key","value");
        Assert.assertTrue(jedis.hexists("hash","key"));
        jedis.hdel("hash","key");
        Assert.assertTrue(!jedis.hexists("hash","key"));
    }

    @Test
    public void testHGetAll(){
        //返回哈希表 key 中，所有的域和值。
        Pipeline pipeline = jedis.pipelined();//流水线一次性提交
        for(int i=0;i<10;i++){
            pipeline.hset("hash","key"+i,String.valueOf(i));
        }
        pipeline.sync();
        Map<String, String> map =  jedis.hgetAll("hash");
        System.out.println(map.toString());
        Assert.assertTrue(map.size()==10);  //还有一个住的
        Assert.assertTrue(jedis.del("hash")==1);
    }

    @Test
    public void testHIncrbyAndHincrFloat(){
        //hincrBy 为哈希表 key 中的域 field 的值加上增量 increment。如果域 field 不存在，域的值先被初始化为 0 。
        //hincrByFloat 同上，支持浮点数
        jedis.hincrBy("hash","key",5);
        Assert.assertTrue(jedis.hincrBy("hash","key",5)==10);
        Assert.assertTrue(jedis.hincrByFloat("hash","key",2.5)==12.5);
        Assert.assertTrue(jedis.del("hash")==1);
    }

    @Test
    public void testHKeys(){
        //hkeys 返回哈希表 key 中的所有域。
        //hlen 返回哈希表 key 中域的数量。
        Pipeline pipeline = jedis.pipelined();//流水线一次性提交
        for(int i=0;i<10;i++){
            pipeline.hset("hash","key"+i,String.valueOf(i));
        }
        pipeline.sync();
        Set<String> keys = jedis.hkeys("hash");
        for(String s:keys){
            System.out.println(s);//乱序的
        }
        Assert.assertTrue(jedis.hlen("hash")==10);
        Assert.assertTrue(keys.size()==10);
        Assert.assertTrue(jedis.del("hash")==1);
    }

    @Test
    public void testHMSetAndHMGet(){
        Map<String,String> map = new HashMap<>();
        for(int i=0;i<10;i++){
            map.put("key"+i,String.valueOf(i));
        }
        jedis.hmset("hash",map);
        Assert.assertTrue(jedis.hlen("hash")==10);
        List<String> list = jedis.hmget("hash","key1","key2","key0");
        System.out.println(list.toString());
        Assert.assertTrue(list.size()==3);
        Assert.assertTrue(jedis.del("hash")==1);
    }

    @Test
    public void testHSetNX(){
        //hsetnx 将哈希表 key 中的域 field 的值设置为 value ，当且仅当域 field 不存在。
        Assert.assertTrue(jedis.hsetnx("hash","key","value")==1);
        Assert.assertTrue(jedis.hsetnx("hash","key","value")==0);
        Assert.assertTrue(jedis.del("hash")==1);
    }

    @Test
    public void testHVals(){
        //hvals 返回哈希表 key 中所有域的值。
        Map<String,String> map = new HashMap<>();
        for(int i=0;i<10;i++){
            map.put("key"+i,String.valueOf(i));
        }
        jedis.hmset("hash",map);
        List<String> list = jedis.hvals("hash");
        System.out.println(list.toString());
        Assert.assertTrue(list.size()==10);
        Assert.assertTrue(jedis.del("hash")==1);
    }

    @Test
    public void testHScan(){
        //HSCAN 命令用于迭代哈希键中的键值对。
        Map<String,String> data = new HashMap<>();
        for(int i=0;i<1000;i++){
            data.put("key"+i,String.valueOf(i));
        }
        jedis.hmset("hash",data);
        ScanResult<Map.Entry<String, String>> result;// =  jedis.hscan("hash",DATASOURCE_SELECT);
        int count = 0;
        int cursor = 0;
        do {
            result = jedis.hscan("hash",cursor);
            cursor = Integer.valueOf(result.getStringCursor());
            for (Map.Entry<String, String> map : result.getResult()) {
                System.out.println(map.getKey() + ":" + map.getValue());
                count++;
            }
        }
        while(cursor!=0);
        Assert.assertTrue(count==1000);
        Assert.assertTrue(jedis.del("hash")==1);
    }

    @Test
    public void testHStrLen(){
        //返回哈希表 key 中， 与给定域 field 相关联的值的字符串长度（string length）。
        System.out.println("jedis没有HSTRLEN命令！");
    }
}
