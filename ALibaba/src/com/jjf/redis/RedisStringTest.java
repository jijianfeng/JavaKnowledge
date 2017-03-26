package com.jjf.redis;

import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Created by jjf_lenovo on 2017/3/22.
 */
public class RedisStringTest {
    Jedis jedis = null;
    static final String DATASOURCE_URL = "182.254.213.106";
    static final int DATASOURCE_SORT = 6379;
    static final String DATASOURCE_PASS = "123456";
    static final int DATASOURCE_SELECT = 1;
    public RedisStringTest(){
        //基本配置
        jedis = new Jedis(DATASOURCE_URL,DATASOURCE_SORT);
        jedis.auth(DATASOURCE_PASS);
        jedis.select(DATASOURCE_SELECT);
    }

    @Test
    public void testSetGet(){
        //String set get
        jedis.set("hello","world");
        Assert.assertTrue(jedis.get("hello").equals("world"));
        Assert.assertTrue(jedis.del("hello")==1);
    }

    @Test
    public void testAppend(){
        //如果 key 已经存在并且是一个字符串， APPEND 命令将 value 追加到 key 原来的值的末尾。
        //如果 key 不存在， APPEND 就简单地将给定 key 设为 value ，就像执行 SET key value 一样。
        jedis.append("append","append");
        jedis.append("append","append");
        Assert.assertTrue(jedis.get("append").equals("appendappend"));
        Assert.assertTrue(jedis.del("append")==1);
    }

    @Test
    public void testDecrAndDecrBy(){
        //decr 将 key 中储存的数字值减一。 不存在初始化为0 ，非数值异常
        //decrby 将 key 所储存的值减去减量 decrement 。
        jedis.set("decr","10");
        jedis.set("decrerror","decrerror");
        Assert.assertTrue(jedis.decr("decr")==9);
        Assert.assertTrue(jedis.decrBy("decr",9)==0);
        jedis.decr("new");
        Assert.assertTrue(jedis.decr("new")==-2);
        Assert.assertTrue(jedis.decrBy("new",-2)==0);
//        try {
//            jedis.decr("decrerror");
//        }
//        catch(Exception exception){
//            System.out.println("decr初始化非数值值为报异常");
//        }
        Assert.assertTrue(jedis.del("decr","decrerror","new")==3);
    }

    @Test
    public void testBitSetGet(){
        //对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)。
        //字符串会进行伸展(grown)以确保它可以将 value 保存在指定的偏移量上。当字符串值进行伸展时，空白位置以 0 填充。
        //offset 参数必须大于或等于 0 ，小于 2^32 (bit 映射被限制在 512 MB 之内)。
        jedis.setbit("bit",1,true);// ==jedis.setbit("bit",1,"1");
        jedis.setbit("bit",2,false);
        jedis.setbit("bit",123123,false);
        System.out.println(jedis.get("bit"));
        System.out.println(jedis.getbit("bit",1));
        System.out.println(jedis.getbit("bit",2));
        System.out.println(jedis.getbit("bit",123)); //空白位置以 0 (false)填充。
        Assert.assertTrue(!jedis.getbit("bit",123));
        Assert.assertTrue(jedis.getbit("bit",1));
        Assert.assertTrue(!jedis.getbit("bit",2));
        Assert.assertTrue(jedis.del("bit")==1);
    }

    @Test
    public void testBitcount(){
        //bitcount 计算给定字符串中，被设置为 1 的比特位的数量。
        for(int i=0;i<10;i++){
            if(i%2==0){
                jedis.setbit("bit",i*8,true); //是位
            }
            else{
                jedis.setbit("bit",i*8,false);
            }
        }
        Assert.assertTrue(jedis.bitcount("bit")==5);
        Assert.assertTrue(jedis.bitcount("bit",0,-1)==5);
//        System.out.println(jedis.bitcount("bit"));
//        System.out.println(jedis.bitcount("bit",0,4));
        Assert.assertTrue(jedis.bitcount("bit",0,4)==3);
        Assert.assertTrue(jedis.del("bit")==1);
    }
}
