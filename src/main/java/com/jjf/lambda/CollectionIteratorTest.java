package com.jjf.lambda;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jijianfeng on 2017/6/10.
 */
public class CollectionIteratorTest {
    public static void main(String[] args){
        // Java 8之前：
        List<String> oldFeatures = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
        for (String feature : oldFeatures) {
            System.out.println(feature);
        }

        // Java 8之后：
        List<String> features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
        features.forEach(n -> System.out.println(n));

        // 使用Java 8的方法引用更方便，方法引用由::双冒号操作符标示，
        // 看起来像C++的作用域解析运算符
        features.forEach(System.out::println);
    }
}
