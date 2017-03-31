package com.jjf.redis;

import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;

import java.util.List;

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
    public void testLPushAndLPushX(){
        /**
         * 将一个或多个值 value 插入到列表 key 的表头

         如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表头： 比如说，对空列表 mylist 执行命令 LPUSH mylist a b c ，列表的值将是 c b a ，这等同于原子性地执行 LPUSH mylist a 、 LPUSH mylist b 和 LPUSH mylist c 三个命令。

         如果 key 不存在，一个空列表会被创建并执行 LPUSH 操作。

         当 key 存在但不是列表类型时，返回一个错误。

         在Redis 2.4版本以前的 LPUSH 命令，都只接
         */
        //jedis.lpushx("list","a","b","c") 会报错，因为lpushx必须key存在
        Long lpush = jedis.lpush("list","a","b","c"); //返回的是列表的长度!!不是操作数
        Assert.assertTrue(lpush==3);
        /**
         * 多个值lpushx会报错 redis.clients.jedis.exceptions.JedisDataException: ERR wrong number of arguments for 'lpushx' command
         * lpushx不支持多个参数，但是jedis里有方法，rpushx也是，反人类。。。
        */
        Long lpushx = jedis.lpushx("list","a");
        Assert.assertTrue(lpushx==4);
        Assert.assertTrue(jedis.del("list")==1);
    }

    @Test
    public void testLLen(){
        //返回列表 key 的长度。
        Assert.assertTrue(jedis.llen("list")==0);
        Assert.assertTrue(jedis.lpush("list","a","b","c")==3);
        Assert.assertTrue(jedis.llen("list")==3);
        Assert.assertTrue(jedis.del("list")==1);
    }

    @Test
    public void testLIndex(){
        //返回列表 key 中，下标为 index 的元素。
        Assert.assertTrue(jedis.lpush("list","a","b","c")==3);
        Assert.assertTrue(jedis.lpush("list","d")==4);
//        System.out.println(jedis.lindex("list",0));
        Assert.assertTrue(jedis.lindex("list",0).equals("d"));
        Assert.assertTrue(jedis.lindex("list",-1).equals("a"));
        Assert.assertTrue(jedis.del("list")==1);
    }

    @Test
    public void testLInsert(){
        //将值 value 插入到列表 key 当中，位于值 pivot 之前或之后。
        Assert.assertTrue(jedis.lpush("list","a","b","c")==3);
        Assert.assertTrue(jedis.lpush("list","d")==4);
        //将"insert"插入到"list"值"d"的后面,"c"的前面，下面两句效果一样
        Assert.assertTrue(jedis.linsert("list", BinaryClient.LIST_POSITION.BEFORE,"c","insert")==5);
//        Assert.assertTrue(jedis.linsert("list", BinaryClient.LIST_POSITION.AFTER,"d","insert")==5);
        Assert.assertTrue(jedis.del("list")==1);
    }

    @Test
    public void testLRange(){
        //lrange 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。
        Assert.assertTrue(jedis.lpush("list","a","b","c")==3);
        Assert.assertTrue(jedis.lpush("list","d")==4);
        List<String> list = jedis.lrange("list",0,-1);
        Assert.assertTrue(list.size()==4);
        Assert.assertTrue(jedis.del("list")==1);
    }

    @Test
    public void testLrem(){
        /**lrem
         * 根据参数 count 的值，移除列表中与参数 value 相等的元素。

         count 的值可以是以下几种：

         count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。
         count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。
         count = 0 : 移除表中所有与 value 相等的值。
         */
        Assert.assertTrue(jedis.lpush("list","rem")==1);
        Assert.assertTrue(jedis.llen("list")==1);
        jedis.lrem("list",0,"rem");
        Assert.assertTrue(jedis.llen("list")==0);
    }

    @Test
    public void testLSet(){
        //lset 将列表 key 下标为 index 的元素的值设置为 value
        Assert.assertTrue(jedis.lpush("list","a","b","c")==3);
        Assert.assertTrue(jedis.lpush("list","d")==4);
        Assert.assertTrue(jedis.lindex("list",1).equals("c"));
        Assert.assertTrue(jedis.lset("list",1,"e").equals("OK"));
        Assert.assertTrue(jedis.lindex("list",1).equals("e"));
        Assert.assertTrue(jedis.del("list")==1);
    }

    @Test
    public void testLTrim(){
        //ltrim 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
        Assert.assertTrue(jedis.lpush("list","a","b","c","d","e")==5);
        Assert.assertTrue(jedis.ltrim("list",0,1).equals("OK"));
        Assert.assertTrue(jedis.lrange("list",0,1).size()==2);
        Assert.assertTrue(jedis.del("list")==1);
    }

    @Test
    public void testLPopAndRPop(){
        //lpop 移除并返回列表 key 的头元素。
        //rpop 移除并返回列表 key 的尾元素。
        Assert.assertTrue(jedis.lpush("list","a","b","c","d","e")==5);
        Assert.assertTrue(jedis.lpop("list").equals("e"));
        Assert.assertTrue(jedis.rpop("list").equals("a"));
        Assert.assertTrue(jedis.llen("list")==3);
        Assert.assertTrue(jedis.del("list")==1);
    }

    @Test
    public void testRPopLPush(){
        /**
         * 原子操作
         * 将列表 source 中的最后一个元素(尾元素)弹出，并返回给客户端。
         * 将 source 弹出的元素插入到列表 destination ，作为 destination 列表的的头元素。
         */
        Assert.assertTrue(jedis.lpush("pop","a","b","c","d","e")==5);
        Assert.assertTrue(jedis.lpush("push","A","B","C","D","E")==5);
        jedis.rpoplpush("pop","push");
        jedis.rpoplpush("pop","push");
        jedis.rpoplpush("pop","push");
        Assert.assertTrue(jedis.rpoplpush("pop","push").equals("d"));
        Assert.assertTrue(jedis.llen("push")==9);
        Assert.assertTrue(jedis.del("pop","push")==2);
    }

    @Test
    public void testRPushAndRPushX(){
        Assert.assertTrue(jedis.rpush("list","a","b","c","d","e")==5);//跟lpush顺序相反
        Assert.assertTrue(jedis.rpushx("list","12333")==6);
        Assert.assertTrue(jedis.lindex("list",0).equals("a"));//最先插入的最前面
        Assert.assertTrue(jedis.del("list")==1);
    }

    @Test
    public void testBLPopAndBRPop(){
        //它是 LPOP 命令的阻塞版本，当给定列表内没有任何元素可供弹出的时候，连接将被 BLPOP 命令阻塞，直到等待超时或发现可弹出元素为止。
        /**
         * 相同的key被多个客户端同时阻塞

         相同的 key 可以被多个客户端同时阻塞。

         不同的客户端被放进一个队列中，按『先阻塞先服务』(first-BLPOP，first-served)的顺序为 key 执行 BLPOP 命令。

         在MULTI/EXEC事务中的BLPOP

         BLPOP 可以用于流水线(pipline,批量地发送多个命令并读入多个回复)，但把它用在 MULTI / EXEC 块当中没有意义。因为这要求整个服务器被阻塞以保证块执行时的原子性，该行为阻止了其他客户端执行 LPUSH 或 RPUSH 命令。

         因此，一个被包裹在 MULTI / EXEC 块内的 BLPOP 命令，行为表现得就像 LPOP 一样，对空列表返回 nil ，对非空列表弹出列表元素，不进行任何阻塞操作。
         */
//        jedis.blpop(100,"list");//其实就是lpop的阻塞版本，可以手动添加试试，单位是秒
//
//        jedis.brpop(100,"list");//其实就是rpop的阻塞版本，可以手动添加试试，单位是秒
//
//        jedis.brpoplpush("pop","push",100);//rpoplpush，可以手动添加试试，单位是秒
    }
}
