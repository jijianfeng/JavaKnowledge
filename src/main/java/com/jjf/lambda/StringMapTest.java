package com.jjf.lambda;

import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jijianfeng on 2017/9/5.
 */
public class StringMapTest {

  @Test
  public void test1() {
    String[] ids = {"1", "2", "3", "3"};
    List<Long> list = Arrays
        .stream(ids)
        .map(value -> Long.valueOf(value))
//        .distinct()
        .collect(Collectors.toList());
    list.forEach(System.out::print);
  }

  @Test
  public void test2(){
    int[] ids = {0,1,2,3,4};
    List list = new ArrayList();
    for(int i=0;i<4;i++){
      Integer temp = new Integer(i);
      Arrays.stream(ids)
          .filter(value->value==temp)
          .forEach(val->list.add(temp));
    }
    list.forEach(System.out::print);
  }
}
