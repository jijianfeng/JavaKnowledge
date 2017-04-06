package com.jjf.redis;

import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
        //基本配置
        jedis = new Jedis(DATASOURCE_URL, DATASOURCE_SORT);
        jedis.auth(DATASOURCE_PASS);
        jedis.select(DATASOURCE_SELECT);
    }

    @Test
    public void testZAddAndZCardAndZScore(){
        //zadd 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。
        //zcard 返回有序集 key 的基数。
        //zscore 返回有序集 key 中，成员 member 的 score 值。
        jedis.zadd("sortedSet",1111,"value:10");
        Map<String,Double> map = new HashMap<String,Double>();
        for(int i=0;i<=10;i++){
            map.put("value:"+i,Double.valueOf(i)); //覆盖了上面的赋值
        }
        jedis.zadd("sortedSet",map);
        Assert.assertTrue(jedis.zscore("sortedSet","value:10")==10);
        Assert.assertTrue(jedis.zcard("sortedSet")==11);
        Assert.assertTrue(jedis.del("sortedSet")==1);
    }

    @Test
    public void testZCount(){
        //zcount 返回有序集 key 中， score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max )的成员的数量。
        Map<String,Double> map = new HashMap<String,Double>();
        for(int i=0;i<10;i++){
            map.put("value:"+i,Double.valueOf(i));
        }
        jedis.zadd("sortedSet",map);
        Long count = jedis.zcount("sortedSet",0,9);
        System.out.println(count);
        Assert.assertTrue(count==10);
        Assert.assertTrue(jedis.del("sortedSet")==1);
    }

    @Test
    public void testZincrby(){
        //zincrby 为有序集 key 的成员 member 的 score 值加上增量 increment 。
        jedis.zadd("sortedSet",1111,"value:10");
        jedis.zincrby("sortedSet",-111.1,"value:10");
        Assert.assertTrue(jedis.zscore("sortedSet","value:10")==999.9);
        Assert.assertTrue(jedis.del("sortedSet")==1);
    }

    @Test
    public void testZRangeAndZRevRange(){
        /** zrange
         * 返回有序集 key 中，指定区间内的成员。

         其中成员的位置按 score 值递增(从小到大)来排序。 rev则相反

         具有相同 score 值的成员按字典序(lexicographical order )来排列。
         */
        Map<String,Double> map = new HashMap<String,Double>();
        for(int i=0;i<10;i++){
            map.put("value:"+i,Math.random()*100);
        }
        jedis.zadd("sortedSet",map);
        Set<String> set = jedis.zrange("sortedSet",0,-1); //递增
        Set<String> revSet = jedis.zrevrange("sortedSet",0,-1); //递减
        for(String ss:set){
            System.out.println(ss+":"+jedis.zscore("sortedSet",ss));
        }
        System.out.println("rev----------");
        for(String ss:revSet){
            System.out.println(ss+":"+jedis.zscore("sortedSet",ss));
        }
        Assert.assertTrue(jedis.del("sortedSet")==1);
    }

    @Test
    public void testZRangeByScoreAndZRevRangeByScore(){
        //返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。
        //有序集成员按 score 值递增(从小到大)次序排列。  rev递减
        Map<String,Double> map = new HashMap<String,Double>();
        for(int i=0;i<10;i++){
            map.put("value:"+i,Math.random()*100);
        }
        jedis.zadd("sortedSet",map);
        Set<String> set = jedis.zrangeByScore("sortedSet",25,75);
        Set<String> revSet = jedis.zrevrangeByScore("sortedSet",75,25);
        for(String ss:set){
            System.out.println(ss+":"+jedis.zscore("sortedSet",ss));
        }
        System.out.println("rev----------");
        for(String ss:revSet){
            System.out.println(ss+":"+jedis.zscore("sortedSet",ss));
        }
        Assert.assertTrue(jedis.del("sortedSet")==1);
    }

    @Test
    public void testZRankAndZRevRank(){
        //zrank 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递增(从小到大)顺序排列。从0开始，rev表示从大到小
        Map<String,Double> map = new HashMap<String,Double>();
        for(int i=0;i<10;i++){
            map.put("value:"+i,Double.valueOf(i));
        }
        jedis.zadd("sortedSet",map);
        Assert.assertTrue(jedis.zrank("sortedSet","value:5")==5);
        Assert.assertTrue(jedis.zrevrank("sortedSet","value:5")==4);
        Assert.assertTrue(jedis.del("sortedSet")==1);
    }

    @Test
    public void testZRem(){
        //zrem 移除有序集 key 中的一个或多个成员，不存在的成员将被忽略。
        Map<String,Double> map = new HashMap<String,Double>();
        for(int i=0;i<10;i++){
            map.put("value:"+i,Double.valueOf(i));
        }
        jedis.zadd("sortedSet",map);
        Assert.assertTrue(jedis.zrem("sortedSet","value:0","value:1","dertcfghvjbk")==2);
        Assert.assertTrue(jedis.zcard("sortedSet")==8);
        Assert.assertTrue(jedis.del("sortedSet")==1);
    }

    @Test
    public void testZRemRangeByRank(){
        //zremrangeByRank 移除有序集 key 中，指定排名(rank)区间内的所有成员。
        Map<String,Double> map = new HashMap<String,Double>();
        for(int i=0;i<10;i++){
            map.put("value:"+i,Double.valueOf(i));
        }
        jedis.zadd("sortedSet",map);
        jedis.zremrangeByRank("sortedSet",1,3);
        Assert.assertTrue(jedis.zcard("sortedSet")==7);
        Assert.assertTrue(jedis.del("sortedSet")==1);
    }

    @Test
    public void testZRemRangeByScore(){
        //zremrangeByScore 移除有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。
        Map<String,Double> map = new HashMap<String,Double>();
        for(int i=0;i<10;i++){
            map.put("value:"+i,Double.valueOf(i));
        }
        jedis.zadd("sortedSet",map);
        jedis.zremrangeByScore("sortedSet",1,3);
        Assert.assertTrue(jedis.zcard("sortedSet")==7);
        Assert.assertTrue(jedis.del("sortedSet")==1);
    }

    @Test
    public void testZUnionStore(){
        //计算给定的一个或多个有序集的并集，其中给定 key 的数量必须以 numkeys 参数指定，并将该并集(结果集)储存到 destination 。
        Map<String,Double> map = new HashMap<String,Double>();
        for(int i=0;i<10;i++){
            map.put("value:"+i,Double.valueOf(i));
        }
        jedis.zadd("keya",map);
        for(int i=0;i<10;i++){
            map.put("value:"+i,Double.valueOf(i+3));
        }
        map.put("adsad",123d);
        jedis.zadd("keyb",map);
        jedis.zunionstore("destination","keya","keyb");
        Assert.assertTrue(jedis.zcard("destination")==11);
        Assert.assertTrue(jedis.del("keya","keyb","destination")==3);
    }

    @Test
    public void testZInterStore(){
        //计算给定的一个或多个有序集的交集，其中给定 key 的数量必须以 numkeys 参数指定，并将该交集(结果集)储存到 destination
        Map<String,Double> map = new HashMap<String,Double>();
        for(int i=0;i<10;i++){
            map.put("value:"+i,Double.valueOf(i)); //覆盖了上面的赋值
        }
        jedis.zadd("keya",map);
        for(int i=0;i<10;i++){
            map.put("value:"+i,Double.valueOf(i+3)); //覆盖了上面的赋值
        }
        map.put("adsad",123d);
        jedis.zadd("keyb",map);
        jedis.zinterstore("destination","keya","keyb");
        Assert.assertTrue(jedis.zcard("destination")==10);
        Assert.assertTrue(jedis.del("keya","keyb","destination")==3);
        Assert.assertTrue(jedis.del("keya","keyb","destination")==3);
    }

    @Test
    public void testZRangeByLex(){
        //当有序集合的所有成员都具有相同的分值时， 有序集合的元素会根据成员的字典序（lexicographical ordering）来进行排序，
        // 而这个命令则可以返回给定的有序集合键 key 中， 值介于 min 和 max 之间的成员。
        jedis.zadd("中文",123d,"a");
        jedis.zadd("中文",123d,"b");
        jedis.zadd("中文",123d,"c");
        jedis.zadd("中文",123d,"d");
        jedis.zadd("中文",123d,"e");
        Set<String> set = jedis.zrangeByLex("中文","(b","[e");
//        Set<String> set = jedis.zrangeByLex("中文","-","+");
//        System.out.println(set.toString());
        Assert.assertTrue(set.size()==3);
        Assert.assertTrue(jedis.del("中文")==1);
    }

    @Test
    public void testZLexCount(){
        //对于一个所有成员的分值都相同的有序集合键 key 来说， 这个命令会返回该集合中， 成员介于 min 和 max 范围内的元素数量。
        jedis.zadd("key",123d,"a");
        jedis.zadd("key",123d,"b");
        jedis.zadd("key",123d,"c");
        jedis.zadd("key",123d,"d");
        jedis.zadd("key",123d,"e");
        Assert.assertTrue(jedis.zlexcount("key","-","+")==5);
        Assert.assertTrue(jedis.zlexcount("key","(b","[e")==3);
        Assert.assertTrue(jedis.del("key")==1);
    }

    @Test
    public void testZRemRangeByLex(){
        //对于一个所有成员的分值都相同的有序集合键 key 来说， 这个命令会移除该集合中， 成员介于 min 和 max 范围内的所有元素。
        jedis.zadd("key",123d,"a");
        jedis.zadd("key",123d,"b");
        jedis.zadd("key",123d,"c");
        jedis.zadd("key",123d,"d");
        jedis.zadd("key",123d,"e");
        Assert.assertTrue(jedis.zremrangeByLex("key","(b","[e")==3);
        Assert.assertTrue(jedis.zlexcount("key","-","+")==2);
    }

    @Test
    public void testZScan(){
        Pipeline pipeline = jedis.pipelined();
        for(int i=0;i<1000;i++){
            pipeline.zadd("key",Math.random()*100,"value:"+i);
        }
        pipeline.sync();
        int cursor = 0;
        int count = 0;
        do{
            ScanResult<Tuple> result =  jedis.zscan("key",cursor);
            cursor = Integer.valueOf(result.getStringCursor());
            for(Tuple tuple :result.getResult()){
//                System.out.print(new String(tuple.getBinaryElement())+":"+tuple.getScore());
                count++;
            }
//            System.out.println();
        }
        while (cursor!=0);
        System.out.println(count);
        Assert.assertTrue(count==1000);
        Assert.assertTrue(jedis.del("key")==1);
    }
}
