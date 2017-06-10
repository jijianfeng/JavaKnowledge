package com.jjf.lambda;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by jijianfeng on 2017/6/10.
 */
public class EventTest {
    /**
     * 例2、使用Java 8 lambda表达式进行事件处理
     如果你用过Swing API编程，你就会记得怎样写事件监听代码。
     这又是一个旧版本简单匿名类的经典用例，但现在可以不这样了。
     你可以用lambda表达式写出更好的事件监听代码，如下所示：
     * @param args
     */
    public static void main(String[] args) {

    }

    public void swing(){
        // Java 8之前：
        JButton show = new JButton("Show");
        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Event handling without lambda expression is boring");
            }
        });

        // Java 8方式：
        show.addActionListener((e) -> {
            System.out.println("Light, Camera, Action !! Lambda expressions Rocks");
        });
    }

    /**
     * Java开发者经常使用匿名类的另一个地方是为 Collections.sort() 定制 Comparator。
     * 在Java 8中，你可以用更可读的lambda表达式换掉丑陋的匿名类。
     * 我把这个留做练习，应该不难，可以按照我在使用lambda表达式实现 Runnable 和 ActionListener 的过程中的套路来做
     */
    public void collenctionSort(List<String> list){
        //jdk8 前
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1+"123");
            }
        });

        //jdk8 后
        Collections.sort(list,
                (o1,o2)-> {
                     return o2.compareTo(o1+"123");
                }
        );
    }
}
