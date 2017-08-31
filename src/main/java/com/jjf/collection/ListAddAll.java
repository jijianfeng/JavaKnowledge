package com.jjf.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jijianfeng on 2017/8/28.
 */
public class ListAddAll {
  public static void main(String[] args){
    /*测试合并两个类型相同的list*/
    List<String> list1 = new ArrayList<String>();
    List<String> list2 = new ArrayList<String>();
    //给list1赋值
    list1.add("1");
    list1.add("2");
    list1.add("3");
    list1.add("4");
    //给list2赋值
    list2.add("5");
    list2.add("6");
    list2.add("7");
    list2.add("8");
    //将list1.list2合并
    list1.addAll(list2);
    //循环输出list1 看看结果
    for (String s : list1) {
      System.out.print(s);
    }
  }
}
