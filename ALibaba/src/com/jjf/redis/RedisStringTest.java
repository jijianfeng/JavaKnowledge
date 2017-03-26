package com.jjf.redis;

import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.BitOP;
import redis.clients.jedis.Jedis;

import java.util.List;

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
    public void testSetAndGet(){
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
    public void testIncrAndIncrByAndIncrbyByFloat(){
        //incr 将 key 中储存的数字值加一。 不存在初始化为0 ，非数值异常
        //incrby 将 key 所储存的值加上 decrement 。
        jedis.set("incr","10");
        Assert.assertTrue(jedis.incr("incr")==11);
        Assert.assertTrue(jedis.incrBy("incr",9)==20);
        jedis.incr("new");
        Assert.assertTrue(jedis.incr("new")==2);
        Assert.assertTrue(jedis.incrBy("new",-2)==0);
        jedis.incrByFloat("new",1.5);
        Assert.assertTrue(jedis.get("new").equals("1.5"));
        Assert.assertTrue(jedis.del("incr","new")==2);
    }

    @Test
    public void testGetRange(){
        //返回 key 中字符串值的子字符串，字符串的截取范围由 start 和 end 两个偏移量决定(包括 start 和 end 在内)。
        jedis.set("range","123456789");
        Assert.assertTrue(jedis.getrange("range",0,2).equals("123"));
        Assert.assertTrue(jedis.getrange("range",0,-1).equals("123456789"));
        Assert.assertTrue(jedis.del("range")==1);
    }

    @Test
    public void testGetset(){
        //将给定 key 的值设为 value ，并返回 key 的旧值(old value)。
        jedis.set("key","old");
        Assert.assertTrue(jedis.getSet("key","new").equals("old"));
        Assert.assertTrue(jedis.get("key").equals("new"));
        Assert.assertTrue(jedis.del("key")==1);
    }

    @Test
    public void testMget(){
        //MGET key [key ...] 返回所有(一个或多个)给定 key 的值。
        jedis.set("a","b");
        jedis.set("b","c");
        jedis.set("c","d");
        List<String> list = jedis.mget("a","b","c","d");
        System.out.println(list.toString());
        Assert.assertTrue(jedis.del("a","b","c")==3);
    }

    @Test
    public void testMSet(){
        //MSET key value [key value ...] 同时设置一个或多个 key-value 对。
        jedis.mset("a","b","b","c");
        List<String> list = jedis.mget("a","b","c");
        System.out.println(list.toString());
        Assert.assertTrue(jedis.del("a","b")==2);
    }

    @Test
    public void testMsetNX(){
        //MSETNX key value [key value ...] 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在。
//        System.out.println(jedis.msetnx("a","b","b","c"));
        Assert.assertTrue(jedis.msetnx("a","b","b","c")==1);
        Assert.assertTrue(jedis.msetnx("a","b2","b","c2")==0);
        Assert.assertTrue(jedis.del("a","b")==2);
    }

    @Test
    public void testPsetEX() throws InterruptedException {
        //PSETEX key milliseconds value 这个命令和 SETEX 命令相似，但它以毫秒为单位设置 key 的生存时间，而不是像 SETEX 命令那样，以秒为单位。
        jedis.psetex("psetex",1000,"123");
        Thread.sleep(1005);
        Assert.assertTrue(jedis.get("pseten")==null);
    }

    @Test
    public void testSetEXAndSetNX() throws InterruptedException {
        //SETEX key seconds value  将值 value 关联到 key ，并将 key 的生存时间设为 seconds (以秒为单位)。
        //SETNX key value  将 key 的值设为 value ，当且仅当 key 不存在。
        jedis.setnx("setex","234");
        jedis.setex("setex",1,"123");
        Thread.sleep(1005);
        Assert.assertTrue(jedis.get("sexex")==null);
    }

    @Test
    public void testSetRange(){
        //SETRANGE key offset value  用 value 参数覆写(overwrite)给定 key 所储存的字符串值，从偏移量 offset 开始。
        jedis.set("setrange","hello world");
        jedis.setrange("setrange",6,"redis");
        Assert.assertTrue(jedis.get("setrange").equals("hello redis"));
        Assert.assertTrue(jedis.del("setrange")==1);
//        System.out.println(jedis.get("a"));
    }

    @Test
    public void testStrLen(){
        //STRLEN key  返回 key 所储存的字符串值的长度。
        jedis.psetex("len",1000,"123456");
        Assert.assertTrue(jedis.strlen("len")==6);
    }

    //--------------------------------以下都是关于bit--------------------------------
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

    @Test
    public void testBitOp(){
        /*
            BITOP AND destkey key [key ...] ，对一个或多个 key 求逻辑并，并将结果保存到 destkey 。
            BITOP OR destkey key [key ...] ，对一个或多个 key 求逻辑或，并将结果保存到 destkey 。
            BITOP XOR destkey key [key ...] ，对一个或多个 key 求逻辑异或，并将结果保存到 destkey 。
            BITOP NOT destkey key ，对给定 key 求逻辑非，并将结果保存到 destkey 。
         */
        jedis.setbit("bitop1",0,true);
        jedis.setbit("bitop1",1,true);
        jedis.setbit("bitop0",0,false);
        jedis.setbit("bitop0",1,false);
        jedis.bitop(BitOP.AND,"AND","bitop1","bitop0");
//        System.out.println(jedis.getbit("AND",0));
//        System.out.println(jedis.getbit("AND",1));
        Assert.assertTrue(!jedis.getbit("AND",0));
        Assert.assertTrue(!jedis.getbit("AND",1));
        jedis.bitop(BitOP.NOT,"NOT","bitop1");
//        System.out.println(jedis.getbit("NOT",0));
//        System.out.println(jedis.getbit("NOT",1));
        Assert.assertTrue(!jedis.getbit("NOT",0));
        Assert.assertTrue(!jedis.getbit("NOT",1));
        jedis.bitop(BitOP.OR,"OR","bitop1","bitop0");
//        System.out.println(jedis.getbit("OR",0));
//        System.out.println(jedis.getbit("OR",1));
        Assert.assertTrue(jedis.getbit("OR",0));
        Assert.assertTrue(jedis.getbit("OR",1));
        jedis.bitop(BitOP.XOR,"XOR","bitop1","bitop0");
//        System.out.println(jedis.getbit("XOR",0));
//        System.out.println(jedis.getbit("XOR",1));.Assert.assertTrue(!jedis.getbit("NOT",0));
        Assert.assertTrue(jedis.getbit("XOR",0));
        Assert.assertTrue(jedis.getbit("XOR",1));
        Assert.assertTrue(jedis.del("bitop1","bitop0","AND","NOT","OR","XOR")==6);

    }

    @Test
    public void testBitField(){
        /**
         * BITFIELD 命令可以将一个 Redis 字符串看作是一个由二进制位组成的数组，
         * 并对这个数组中储存的长度不同的整数进行访问 （被储存的整数无需进行对齐）。
         * 换句话说， 通过这个命令， 用户可以执行诸如 “对偏移量 1234 上的 5 位长有符号整数进行设置”、
         * “获取偏移量 4567 上的 31 位长无符号整数”等操作。
         * 此外， BITFIELD 命令还可以对指定的整数执行加法操作和减法操作，
         * 并且这些操作可以通过设置妥善地处理计算时出现的溢出情况。
        */
        System.out.print("厉害了，我的redis！");
    }
}
