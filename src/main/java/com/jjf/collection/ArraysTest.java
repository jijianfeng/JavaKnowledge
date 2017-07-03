package com.jjf.collection;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jijianfeng on 2017/6/30.
 */
public class ArraysTest {
    @Test
    public void test(){
        String s[]={"aa","bb","cc"};
        List<String> sList= Arrays.asList(s);
        for(String str:sList){//能遍历出各个元素
            System.out.println(str);
        }
        System.out.println(sList.size());//为3

        System.out.println("- - - - - - - - - - -");

        int i[]={11,22,33};
        List intList=Arrays.asList(i);  //intList中就有一个Integer数组类型的对象，整个数组作为一个元素存进去的
        for(Object o:intList){//就一个元素
            System.out.println(o.toString());
        }

        System.out.println("- - - - - - - - - - -");

        List intObjectList=Arrays.asList(ArrayUtils.toObject(i));  //intList中就有一个Integer数组类型的对象，整个数组作为一个元素存进去的
        for(Object o:intObjectList){//就一个元素
            System.out.println(o.toString());
        }

        System.out.println("- - - - - - - - - - -");

        Integer ob[]={11,22,33};
        List<Integer> objList=Arrays.asList(ob);  //数组里的每一个元素都是作为list中的一个元素
        for(int a:objList){
            System.out.println(a);
        }

        System.out.println("- - - - - - - - - - -");

        //objList.remove(0);//asList()返回的是arrays中私有的终极ArrayList类型，它有set,get，contains方法，但没有增加和删除元素的方法，所以大小固定,会报错
        //objList.add(0);//由于asList返回的list的实现类中无add方法，所以会报错
    }
}
