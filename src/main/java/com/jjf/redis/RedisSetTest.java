package com.jjf.redis;

import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.ScanResult;

import java.util.List;
import java.util.Set;

/**
 * Created by jjf_lenovo on 2017/3/31.
 */
public class RedisSetTest {
    Jedis jedis = null;
    static final String DATASOURCE_URL = "182.254.213.106";
    static final int DATASOURCE_SORT = 6379;
    static final String DATASOURCE_PASS = "123456";
    static final int DATASOURCE_SELECT = 1;

    public RedisSetTest(){
        //基本配置
        jedis = new Jedis(DATASOURCE_URL,DATASOURCE_SORT);
        jedis.auth(DATASOURCE_PASS);
        jedis.select(DATASOURCE_SELECT);
    }

    @Test
    public void testSAdd(){
        //sadd 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。
        Assert.assertTrue(jedis.sadd("set","a")==1);
        Assert.assertTrue(jedis.sadd("set","a","b","c")==2);
        Assert.assertTrue(jedis.del("set")==1);
    }

    @Test
    public void testSCard(){
        //scard 返回集合 key 的基数(集合中元素的数量)。
        Assert.assertTrue(jedis.sadd("set","a","b","c")==3);
        Assert.assertTrue(jedis.scard("set".getBytes())==3);
        Assert.assertTrue(jedis.del("set")==1);
    }

    @Test
    public void testSDiff(){
        //sdiff 返回一个集合的全部成员，该集合是所有给定集合之间的差集。
        Assert.assertTrue(jedis.sadd("set","a","b","c")==3);
        Assert.assertTrue(jedis.sadd("SET","A","B","C","a","b","c")==6);
        Set<String> a = jedis.sdiff("set","SET");//是找出set中，SET没有的元素，多级的话，一个个比
        Set<String> b = jedis.sdiff("SET","set");//是找出SET中，set没有的元素，多级的话，一个个比
        System.out.println(a.toString()+":::"+b.toString());
        Assert.assertTrue(jedis.del("set","SET")==2);
    }

    @Test
    public void testSDiffStore(){
        //sdiffstore 这个命令的作用和 SDIFF 类似，但它将结果保存到 destination 集合，而不是简单地返回结果集。
        Assert.assertTrue(jedis.sadd("set","a","b","c")==3);
        Assert.assertTrue(jedis.sadd("SET","A","B","C","a","b","c")==6);
        Assert.assertTrue(jedis.sdiffstore("store","SET","set")==3);
        Assert.assertTrue(jedis.sdiffstore("store","set","SET")==0);
        Assert.assertTrue(jedis.del("set","SET")==2);
    }

    @Test
    public void testSInter(){
        //sinter 返回一个集合的全部成员，该集合是所有给定集合的交集。
        Assert.assertTrue(jedis.sadd("set","a","b","c")==3);
        Assert.assertTrue(jedis.sadd("SET","A","B","C","a","b","c")==6);
        Set<String> set = jedis.sinter("set","SET");
        System.out.println(set.toString());
        Assert.assertTrue(set.size()==3);
        Assert.assertTrue(jedis.del("set","SET")==2);
    }

    @Test
    public void testSInterStore(){
        //sinterstore 这个命令类似于 SINTER 命令，但它将结果保存到 destination 集合，而不是简单地返回结果集。
        Assert.assertTrue(jedis.sadd("set","a","b","c")==3);
        Assert.assertTrue(jedis.sadd("SET","A","B","C","a","b","c")==6);
        Assert.assertTrue(jedis.sinterstore("store","set","SET")==3);
        Assert.assertTrue(jedis.del("set","SET","store")==3);
    }

    @Test
    public void testSIsMember(){
        //sismember 判断 member 元素是否集合 key 的成员。
        Assert.assertTrue(jedis.sadd("set","a","b","c")==3);
        Assert.assertTrue(jedis.sismember("set","b"));
        Assert.assertTrue(jedis.del("set")==1);
    }

    @Test
    public void testSMembers(){
        //smembers 返回集合 key 中的所有成员。 不存在的 key 被视为空集合。
        Assert.assertTrue(jedis.sadd("set","a","b","c")==3);
        Set<String> set = jedis.smembers("set");
        System.out.println(set.toString());
        Assert.assertTrue(set.size()==3);
        Assert.assertTrue(jedis.del("set")==1);
    }

    @Test
    public void testSMove(){
        //将 member 元素从 source 集合移动到 destination 集合。 (原子操作)
        Assert.assertTrue(jedis.sadd("set","a","b","c")==3);
        Assert.assertTrue(jedis.sadd("SET","A","B","C","a","b","c")==6);
        Assert.assertTrue(jedis.smove("set","SET","a")==1);
        Set<String> a = jedis.smembers("set");
        Set<String> b = jedis.smembers("SET");
        System.out.println(a.toString());
        System.out.println(b.toString());
        Assert.assertTrue(a.size()==2);
        Assert.assertTrue(b.size()==6);//因为原本就存在a ，所以还是6个
        Assert.assertTrue(jedis.del("set","SET")==2);
    }

    @Test
    public void testSPop(){
        //spop 移除并返回集合中的一个随机元素。
        Assert.assertTrue(jedis.sadd("set","a","b","c")==3);
        System.out.println(jedis.spop("set"));
        Set<String> a = jedis.smembers("set");
        Assert.assertTrue(a.size()==2);
        Assert.assertTrue(jedis.del("set")==1);

    }

    @Test
    public void testSRandMember(){
        /**
         * 如果命令执行时，只提供了 key 参数，那么返回集合中的一个随机元素。

         从 Redis 2.6 版本开始， SRANDMEMBER 命令接受可选的 count 参数：

         如果 count 为正数，且小于集合基数，那么命令返回一个包含 count 个元素的数组，数组中的元素各不相同。如果 count 大于等于集合基数，那么返回整个集合。
         如果 count 为负数，那么命令返回一个数组，数组中的元素可能会重复出现多次，而数组的长度为 count 的绝对值。
         */
        Assert.assertTrue(jedis.sadd("set","a","b","c")==3);
        System.out.println(jedis.srandmember("set"));//不同于spop，srandmember不移除
        Set<String> a = jedis.smembers("set");
        Assert.assertTrue(a.size()==3);

        //测试多个随机数
        List<String> lista = jedis.srandmember("set",2);//返回两个不重复的随机数
        System.out.println("lista:"+lista.toString());
        List<String> listb = jedis.srandmember("set",5);//5>3 返回全部数据
        System.out.println("listb:"+listb.toString());
        List<String> listc = jedis.srandmember("set",-2); //返回|-2|个可以重复的数据
        System.out.println("listc:"+listc.toString());
        List<String> listd = jedis.srandmember("set",-6); //返回|-6|个可以重复的数据
        System.out.println("listd:"+listd.toString());
        Assert.assertTrue(jedis.del("set")==1);
    }

    @Test
    public void testSRem(){
        //srem 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。
        Assert.assertTrue(jedis.sadd("set","a","b","c")==3);
        Assert.assertTrue(jedis.srem("set","a","d")==1);
        Assert.assertTrue(jedis.del("set")==1);
    }

    @Test
    public void testSunion(){
        //sunion 返回一个集合的全部成员，该集合是所有给定集合的并集。
        Assert.assertTrue(jedis.sadd("set","a","b","c")==3);
        Assert.assertTrue(jedis.sadd("SET","A","B","C","a","b","c")==6);
        Set<String> set = jedis.sunion("set","SET");
        System.out.println(set.toString());
        Assert.assertTrue(set.size()==6);
        Assert.assertTrue(jedis.del("set","SET")==2);
    }

    @Test
    public void testSunionStore(){
        //sunion 返回一个集合的全部成员，该集合是所有给定集合的并集。
        Assert.assertTrue(jedis.sadd("set","a","b","c")==3);
        Assert.assertTrue(jedis.sadd("SET","A","B","C","a","b","c")==6);
        Assert.assertTrue(jedis.sunionstore("store","set","SET")==6);
        Assert.assertTrue(jedis.del("set","SET","store")==3);
    }

    @Test
    public void testSScan(){
        Pipeline pipeline = jedis.pipelined();
        for(int i=0;i<1000;i++){
            pipeline.sadd("set",String.valueOf(i));
        }
        pipeline.sync();
        ScanResult<String> result ;//=  jedis.sscan("set",0);
//        System.out.println(result.getResult().size()+"::"+result.getStringCursor());
//        System.out.println(result.getResult().toString());
        int count = 0;
        int cursor = 0;
        do{
//            System.out.println(result.getCursor());
            result = jedis.sscan("set",cursor);
            cursor = Integer.valueOf(result.getStringCursor());
            for (String ss : result.getResult()) {
                count++;
                System.out.print("<"+count+":" + ss + ">");
            }
            System.out.println();
        }
        while(cursor!=0);
        System.out.println(count);
        Assert.assertTrue(count==1000);
        Assert.assertTrue(jedis.del("set","SET","store")==1);
    }
}
