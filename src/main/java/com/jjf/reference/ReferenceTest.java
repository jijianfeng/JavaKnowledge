package com.jjf.reference;

import org.junit.Test;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Created by jijianfeng on 2017/7/30.
 */
public class ReferenceTest {
  @Test
  public void strongReference() {
    Object referent = new Object();

    /**
     * 通过赋值创建 StrongReference
     */
    Object strongReference = referent;

    assertSame(referent, strongReference);

    referent = null;
    System.gc();

    /**
     * StrongReference 在 GC 后不会被回收
     */
    assertNotNull(strongReference);
  }

  @Test
  public void weakReference() {
    Object referent = new Object();
    WeakReference<Object> weakRerference = new WeakReference<Object>(referent);

    assertSame(referent, weakRerference.get());

    referent = null;
    System.gc();

    /**
     * 一旦没有指向 referent 的强引用, weak reference 在 GC 后会被自动回收
     */
    assertNull(weakRerference.get());
  }

  @Test
  public void weakHashMap() throws InterruptedException {
    Map<Object, Object> weakHashMap = new WeakHashMap<Object, Object>();
    Object key = new Object();
    Object value = new Object();
    weakHashMap.put(key, value);

    assertTrue(weakHashMap.containsValue(value));

    key = null;
    System.gc();

    /**
     * 等待无效 entries 进入 ReferenceQueue 以便下一次调用 getTable 时被清理
     */
    Thread.sleep(1000);

    /**
     * 一旦没有指向 key 的强引用, WeakHashMap 在 GC 后将自动删除相关的 entry
     */
    assertFalse(weakHashMap.containsValue(value));
  }

  @Test
  public void softReference() {
    Object referent = new Object();
    SoftReference<Object> softRerference = new SoftReference<Object>(referent);

    assertNotNull(softRerference.get());

    referent = null;
    System.gc();

    /**
     *  soft references 只有在 jvm OutOfMemory 之前才会被回收, 所以它非常适合缓存应用
     */
    assertNotNull(softRerference.get());
  }
}
