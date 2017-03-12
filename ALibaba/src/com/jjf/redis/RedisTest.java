package com.jjf.redis;
import redis.clients.jedis.Jedis;
/**
 * 屌丝腾讯云装了个redis 但连接方式不正确。使用密码应该能被暴力破解的
 * @author jjf_lenovo
 * 2017年3月12日22:13:47
 */
public class RedisTest {
	public static void main( String[] args )
    {
    	Jedis jedis = new Jedis("182.254.213.106",6379);
    	jedis.auth("123456");
    	String hello = jedis.get("hello");
    	jedis.append("hello", "123123");
    	//查看服务是否运行
    	System.out.println("Server is running: "+jedis.ping());
    	System.out.println(hello);
    }
}
