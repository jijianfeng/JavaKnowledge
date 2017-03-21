package com.jjf.redis;

import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanResult;

import java.util.List;

/**
 * 屌丝腾讯云装了个redis
 * @author jjf_lenovo
 * 2017年3月12日22:13:47
 */
public class RedisTest {
    Jedis jedis = null;
    public RedisTest(){
        //基本配置
        jedis = new Jedis("182.254.213.106",6379);
        jedis.auth("123456");
        jedis.select(1);
    }
    @Test
	public void testSetDel()
    {
		//set del  删除给定的一个或多个key 。
		jedis.set("a","123");
        jedis.set("b","123");
        jedis.set("c","123");
        Assert.assertTrue(jedis.del("aaa")==0);
        Assert.assertTrue(jedis.del("a")==1);
        Assert.assertTrue(jedis.del("b","c")==2);
    }

    @Test
    public void testDumpRestore(){
        //dump restore   序列化给定 key ，并返回被序列化的值，使用 RESTORE 命令可以将这个值反序列化为 Redis 键。
		jedis.set("dump","dump","NX","EX",10);//  NX/XX-覆盖set/不覆盖set   EX/PX-seconds/milliseconds 10秒清除
		jedis.hset("hashdump","hash","dump");
		byte[] ss = jedis.dump("dump");
		byte[] hashss = jedis.dump("hashdump");
		System.out.println(ss.toString()+":::"+hashss);
        //参数 ttl 以毫秒为单位为 key 设置生存时间；如果 ttl 为 0 ，那么不设置生存时间。
        System.out.println(jedis.restore("dump-code",0,ss)+":::"+jedis.restore(hashss,10000,hashss));
        System.out.println(ss.toString()+":::"+hashss);
        Assert.assertTrue(jedis.get("dump-code").equals("dump"));
        Assert.assertTrue(jedis.hget("hashdump","hash").equals("dump"));
        Assert.assertTrue(jedis.del("dump-code")==1);
        Assert.assertTrue(jedis.del("hashdump")==1);
    }

    @Test
    public void testExistsExpireExpireat(){
        //Exists  Expire ExpiteAt  生存时间
        jedis.set("exist","exist");
        jedis.set("existAt","existAt");
        Assert.assertTrue(jedis.exists("exist"));
        Assert.assertTrue(jedis.expire("exist",1)==1);
        // 1.这里要考虑到服务器的时间和request的时间，2.而且是秒Unix timestamp，不是毫秒
        Assert.assertTrue(jedis.expireAt("existAt",System.currentTimeMillis()/1000+5)==1);
        System.out.println(System.currentTimeMillis()/1000+5);
        try {
            Thread.sleep(5050);
        } catch (InterruptedException e) {
            Assert.fail("线程休眠异常");
        }
        System.out.println(System.currentTimeMillis()/1000);
        Assert.assertTrue(!jedis.exists("exist"));
        Assert.assertTrue(!jedis.exists("existAt"));
    }

    @Test
    public void testKeys(){
        //Keys   查找所有符合给定模式 pattern 的 key 。
        jedis.set("ont","one","NX","EX",10);
        jedis.set("two","one","NX","EX",10);
        jedis.set("three","one","NX","EX",10);
        jedis.set("four","one","NX","EX",10);
        Assert.assertTrue(jedis.keys("*").size()==4);
        Assert.assertTrue(jedis.keys("t*").size()==2);
        Assert.assertTrue(jedis.keys("t??").size()==1);
        Assert.assertTrue(jedis.keys("f[ab]ur").size()==0);
        Assert.assertTrue(jedis.keys("f[ou]ur").size()==1);
    }

    @Test
    public void testMigrate(){
        //MIGRATE 将 key 原子性地从当前实例传送到目标实例的指定数据库上
        // ，一旦传送成功，key 保证会出现在目标实例上，而当前实例上的 key 会被删除。
        //云服务器内存有限，就不开两个实例了
    }

    @Test
    public void testMove(){
        //MOVE 同一个redis不同库之间的“剪切”
        jedis.select(1);
        jedis.set("move","123");
        jedis.expire("move",5);//还是会生效
        Assert.assertTrue(jedis.move("move",2)==1);
    }

    @Test
    public void testObject(){
        //OBJECT 命令允许从内部察看给定 key 的 Redis 对象。
        jedis.set("object","object111111111111111111111111111111111111111111111111111111111","NX","EX",10);
        String ss = jedis.get("object");
        System.out.println(jedis.objectEncoding("object"));//内部表示
        System.out.println(jedis.objectIdletime("object"));//自储存以来的空闲时间
        System.out.println(jedis.objectRefcount("object"));//引用次数
    }

    @Test
    public void testPersist(){
        //Expire 指定时间后删除  PERSIST  移除给定 key 的生存时间
        jedis.set("PERSIST","PERSIST","NX","EX",2);
        jedis.persist("PERSIST");
        try {
            Thread.sleep(2200);
        } catch (InterruptedException e) {
            Assert.fail("主线程休眠异常");
        }
        Assert.assertTrue(jedis.get("PERSIST")!=null&&jedis.get("PERSIST").equals("PERSIST"));
        Assert.assertTrue(jedis.del("PERSIST")==1);
    }

    @Test
    public void testPexpirePexpireAt(){
        //Pexpire  PexpireAt  毫秒级别的expire
        jedis.set("exist","exist");
        jedis.set("existAt","existAt");
        Assert.assertTrue(jedis.pexpire("exist",1000)==1);
        // 1.这里要考虑到服务器的时间和request的时间，2.而且是秒Unix timestamp，不是毫秒
        Assert.assertTrue(jedis.pexpireAt("existAt",System.currentTimeMillis()+5000)==1);
        System.out.println(System.currentTimeMillis()+5000);
        try {
            Thread.sleep(5050);
        } catch (InterruptedException e) {
            Assert.fail("线程休眠异常");
        }
        System.out.println(System.currentTimeMillis());
        Assert.assertTrue(!jedis.exists("exist"));
        Assert.assertTrue(!jedis.exists("existAt"));
    }

    @Test
    public void testPttlTtl(){
        //ttl 查看生存剩余时间  pttl 毫秒级
        jedis.set("ttl","ttl","NX","EX",10);
        System.out.println(jedis.ttl("ttl"));
        System.out.println(jedis.pttl("ttl"));
    }

    @Test
    public void testRandomKey(){
        //RANDOMKEY  从当前数据库中随机返回(不删除)一个 key 。
//        System.setProperty("http.proxySet", "true");
//        System.setProperty("http.proxyHost", "127.0.0.1");
//        System.setProperty("http.proxyPort", "8888");
        //TODO 有没有可能抓个包，判断下速度
        for(int i=0;i<100;i++){
            jedis.set("random"+i,String.valueOf(Math.random()),"NX","EX",10);
        }
        for(int i=0;i<10;i++){
            System.out.println(jedis.get(jedis.randomKey()));
        }
    }

    @Test
    public void testRename(){
        //rename 将 key 改名为 newkey
        //renamenx 当且仅当 newkey 不存在时，将 key 改名为 newkey 。
        jedis.set("one","1","NX","EX",10);
        jedis.set("two","2","NX","EX",10);
        Assert.assertTrue(jedis.exists("one"));
        jedis.renamenx("one","three");
        Assert.assertTrue(!jedis.exists("one"));
        Assert.assertTrue(jedis.exists("three"));
        jedis.rename("two","three"); //如果rename的新旧key都存在，那就保留旧key的值
        Assert.assertTrue(!jedis.exists("two"));
        Assert.assertTrue(jedis.get("three").equals("2"));
    }

    @Test
    public void testSort(){
        //sort 返回或保存给定列表、集合、有序集合 key 中经过排序的元素。
        for(int i=0;i<100;i++){
            jedis.lpush("sort",String.valueOf((int)(Math.random()*100)));
        }
        List list = jedis.sort("sort");
        System.out.println("排序后:"+list.toString());
        Assert.assertTrue(jedis.sort("sort","dstkey")==100);
        jedis.del("sort","dstkey");
    }

    @Test
    public void testType(){
        //type 数据类型
        jedis.set("a","b");
        Assert.assertTrue(jedis.type("a").equals("string"));
        jedis.del("a");
    }

    @Test
    public void testScan(){
        /**
         SCAN 命令用于迭代当前数据库中的数据库键。
         SSCAN 命令用于迭代集合键中的元素。
         HSCAN 命令用于迭代哈希键中的键值对。
         ZSCAN 命令用于迭代有序集合中的元素（包括元素成员和元素分值）。
         */
        //TODO 明天搞
        ScanResult<String> result =  jedis.scan(0);
        for(String key :result.getResult()){
            System.out.println(key);
        }
    }
}
