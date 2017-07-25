package com.jjf.test;

import com.jjf.annotation.Apple;

import org.junit.*;
import org.junit.Test;

import lombok.Data;

/**
 * Created by jijianfeng on 2017/7/25.
 */
public class TTT {
  @Test
  public void testSwitch(){
    Bean bean = Bean.selectById("ban1231ana");
    switch (bean){
      case Apple:{
        System.out.println(Bean.Apple.getId());
      }
      case Banana:{
        System.out.println(Bean.Banana.getId());
        break;
      }
    }
  }
}

enum Bean {
  Apple("apple","Æ»¹û"),
  Banana("banana","Ïã½¶");

  private String id;
  private String name;

  Bean(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public static Bean selectById(String id){
    for (Bean bean : Bean.values()){
      if (bean.getId().equals(id)){
        return bean;
      }
    }
    return  null;
  }
}