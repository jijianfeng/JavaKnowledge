package com.jjf.redis;

import redis.clients.jedis.JedisPubSub;

/**
 * Created by jjf_lenovo on 2017/5/17.
 */
public class PubSubTest  extends JedisPubSub {

    // 取得订阅的消息后的处理
    public void onMessage(String channel, String message) {
        System.out.println("onMessage");
        System.out.println(channel+"::"+message);
    }

    // 取得按表达式的方式订阅的消息后的处理
    public void onPMessage(String pattern, String channel, String message) {
        System.out.println("onPMessage");
        System.out.println(pattern+"::"+channel+"::"+message);
    }

    // 初始化订阅时候的处理
    public void onSubscribe(String channel, int subscribedChannels) {
        System.out.println("onSubscribe");
        System.out.println(channel+"::"+subscribedChannels);
    }

    // 取消订阅时候的处理
    public void onUnsubscribe(String channel, int subscribedChannels) {
        System.out.println("onUnsubscribe");
        System.out.println(channel+"::"+subscribedChannels);
    }

    // 取消按表达式的方式订阅时候的处理
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        System.out.println("onPUnsubscribe");
        System.out.println(pattern+"::"+subscribedChannels);
    }

    // 初始化按表达式的方式订阅时候的处理
    public void onPSubscribe(String pattern, int subscribedChannels) {
        System.out.println("onPSubscribe");
        System.out.println(pattern+"::"+subscribedChannels);
    }

    public void onPong(String pattern) {
        System.out.println("onPong");
        System.out.println(pattern);
    }
}
