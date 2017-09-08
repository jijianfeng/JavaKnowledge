package com.jjf.cache;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

/**
 * 一个单机缓存类。cachesMap -> cacheList -> Cache
 * Created by jijianfeng on 2017/9/7.
 */
@Slf4j
//TODO 热点数据排序靠前
//TODO 容量的统计
//TODO 为各个缓存集设置单独的缓存长度
// 已知问题：
// 1.多机情况下，可能读到空数据、历史数据。这些应该被允许才能使用这个缓存。
// 2.目前key没有限制，使用对象当key可能会造成内存无法释放的情况
public class CacheUtil<K, V> {

  private Map<String, Vector<Cache<K, V>>> cachesMap = new ConcurrentHashMap();

  private int cacheLength = 10;//支持的缓存长度

  //得到缓存集合。
  public List<Cache<K, V>> getCacheList(String cacheName) {
    return cachesMap.get(cacheName);
  }

  //得到缓存值。
  public V getCache(String cacheName, K k) {
    List<Cache<K, V>> cacheList = cachesMap.get(cacheName);
    if (cacheList == null) {
      return null;
    }
    Optional<Cache<K, V>> optional = cacheList.parallelStream()
        .filter(cache -> (cache.getKey()).equals(k))
        .findFirst();
    if (optional.isPresent() && cacheExpired(optional.get())) {
      return optional.get().getValue();
    }
    return null;
  }

  //获取缓存信息
  public Cache getCacheInfo(String cacheName, K k) {
    List<Cache<K, V>> cacheList = cachesMap.get(cacheName);
    if (cacheList == null) {
      return null;
    }
    Optional<Cache<K, V>> optional = cacheList.parallelStream()
        .filter(cache -> (cache.getKey()).equals(k))
        .findFirst();
    if (optional.isPresent() && cacheExpired(optional.get())) {
      return optional.get();
    }
    return null;
  }

  //新增缓存
  public void putCache(String cacheName, Cache cache) {
    addCache(cacheName, cache);
  }

  //新增缓存
  public void putCache(String cacheName, K k, V v, long expired) {
    addCache(cacheName, new Cache(k, v, expired));
  }

  //新增缓存
  public void putCache(String cacheName, K k, V v) {
    addCache(cacheName, new Cache(k, v));
  }

  //判断是否存在一个缓存集合
  public boolean hasCacheList(String cacheName) {
    return cachesMap.containsKey(cacheName);
  }

  //清除所有缓存
  public void clearAll() {
    cachesMap.clear();
  }

  //清除指定的缓存
  public void clear(String cacheName) {
    cachesMap.remove(cacheName);
  }

  //判断缓存是否在有效期内
  public boolean cacheExpired(Cache cache) {
    if (cache == null) {
      return false;
    }
    return cache.getTimeOut() + cache.getExpired() >= System.currentTimeMillis();
  }

  //获取缓存对象中的所有键值名称
  public Vector getCacheAllkey() {
    Vector a = new Vector();
    try {
      Iterator i = cachesMap.entrySet().iterator();
      while (i.hasNext()) {
        Map.Entry entry = (Map.Entry) i.next();
        a.add(entry.getKey());
      }
    } catch (Exception ex) {
    } finally {
      return a;
    }
  }

  private void addCache(String cacheName, Cache cache) {
    List<Cache<K, V>> list = cachesMap.get(cacheName);
    if (list == null) {
      Vector<Cache<K, V>> cacheList = new Vector();
      cacheList.add(cache);
      cachesMap.put(cacheName, cacheList);
    } else {
      list.add(cache);
    }
  }
}

class Cache<K, V> {

  private K k;
  private V v;
  private long timeOut = 0L;//更新时间
  private long expired = 5000L; //持续时间 、默认10S

  public Cache(long expired) {
    updateTimeOut();
    this.expired = expired;
  }

  public Cache(K k, V v) {
    updateTimeOut();
    this.k = k;
    this.v = v;
  }

  public Cache(K k, V v, long expired) {
    updateTimeOut();
    this.k = k;
    this.v = v;
    this.expired = expired;
  }

  public void put(K k, V v, long expired) {
    updateTimeOut();
    this.k = k;
    this.v = v;
    this.expired = expired;
  }

  public void put(K k, V v) {
    updateTimeOut();
    this.k = k;
    this.v = v;
  }

  public K getKey() {
    return k;
  }

  public V getValue() {
    updateTimeOut();
    return v;
  }

  public long getTimeOut() {
    return timeOut;
  }

  public long getExpired() {
    return expired;
  }

  /**
   * 无论存、取都应该更新缓存的时间
   */
  private void updateTimeOut() {
    this.timeOut = System.currentTimeMillis();
  }
}

