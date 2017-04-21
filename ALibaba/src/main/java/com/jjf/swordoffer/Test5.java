package com.jjf.swordoffer;

import java.util.Stack;
/**
 * 用两个栈来实现一个队列，完成队列的Push和Pop操作。 队列中的元素为int类型。
 */

/**
 * Created by jjf_lenovo on 2017/4/19.
 */
public class Test5 {
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        stack1.push(node);
    }

    public int pop() {
        if(stack2.isEmpty()){
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        return stack2.pop();
    }

//    public static void main(String args[]){
//        //1,2,3,4,5
//        push(1);
//        push(2);
//        push(3);
//        pop();
//        pop();
//        push(4);
//        pop();
//        push(5);
//        pop();
//        pop();
//    }
}
