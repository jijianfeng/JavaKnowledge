package com.jjf.lambda;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jijianfeng on 2017/6/30.
 */
public class LambdaT {
    //写一个lambda表达式破坏范型结构的例子
    public static void main(String[] args){
        List list;
        list = Factory.drive(ArrayList::new);
        System.out.println(list.toString());
        list = Factory.drive(new Worker<Integer>() {
            @Override
            public <String> List<String> doWork() {
                return new ArrayList<String>();
            }
        });
        System.out.println(list.toString());

        //??????????还有这种写法的啊啊啊啊啊啊啊啊
        List<String> drive = Factory.drive((Worker<String>) ArrayList::new);
    }
}

//简单的复制生产
interface Worker<T>{
     <T> List<T> doWork();
}

class Factory{
    public static <T> List<T>  drive(Worker<T> worker){
        return worker.doWork();
    }
}