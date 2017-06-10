package com.jjf.lambda;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jijianfeng on 2017/6/10.
 */
public class StreamTest {
    public static void main(String[] args){
        test2();
    }

    /**
     * 例6、Java 8中使用lambda表达式的Map和Reduce示例
     本例介绍最广为人知的函数式编程概念map。
     它允许你将对象进行转换。例如在本例中，我们将 costBeforeTax 列表的每个元素转换成为税后的值。
     我们将 x -> x*x lambda表达式传到 map() 方法，后者将其应用到流中的每一个元素。
     然后用 forEach() 将列表元素打印出来。使用流API的收集器类，可以得到所有含税的开销。
     有 toList() 这样的方法将 map 或任何其他操作的结果合并起来。由于收集器在流上做终端操作，因此之后便不能重用流了。
     你甚至可以用流API的 reduce() 方法将所有数字合成一个，下一个例子将会讲到。
     */
    public static void test1(){
        // 不使用lambda表达式为每个订单加上12%的税
        List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
        for (Integer cost : costBeforeTax) {
            double price = cost + .12*cost;
            System.out.println(price);
        }

        // 使用lambda表达式
        costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
        costBeforeTax.stream().map((cost) -> cost + .12*cost).forEach(System.out::println);
    }

    /**
     *
     */
    public static void test2(){
        // 为每个订单加上12%的税
        // 老方法：
        List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
        double total = 0;
        for (Integer cost : costBeforeTax) {
            double price = cost + .12*cost;
            total = total + price;
        }
        System.out.println("Total : " + total);

        // 新方法：
        costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
        double bill = costBeforeTax.stream().map((cost) -> cost + .12*cost).reduce((sum, cost) -> sum + cost).get();
        System.out.println("Total : " + bill);
    }
}
