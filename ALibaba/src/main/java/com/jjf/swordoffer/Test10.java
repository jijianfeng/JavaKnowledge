package com.jjf.swordoffer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jjf_lenovo on 2017/4/24.
 */
//输入一个整数数组，实现一个函数来调整该数组中数字的顺序，
//使得所有的奇数位于数组的前半部分，所有的偶数位于位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。
public class Test10 {
    public static void main(String[] args){
        int[] array = {2,4,1,3,5,7,6};
        reOrderArrayBySwap(array);
        for(int a:array){
            System.out.print(a);
        }
    }

    //交换版本
    public static void reOrderArrayBySwap(int [] array) {
        if(array==null){
            throw new NullPointerException();
        }
        for(int i=0;i<array.length-1;i++){
            if((array[i]&0x1)==0&&(array[i+1]&0x1)==1){//偶数且下一位为奇数,则交换
                int temp = array[i];
                array[i] = array[i+1];
                array[i+1] = temp;
                if(i>=1&&(array[i-1]&0x1)==0){ //如果上一个数也是偶数，则i退到前一位
                    i=i-2;
                }
            }

        }
    }

    //简单版,维护两个数组
    public static void reOrderArray(int [] array) {
        List<Integer> i = new ArrayList<>(array.length);
        List<Integer> j = new ArrayList<>(array.length);
        for(int a :array){
            if((a & 0x1)==1){//奇数
                i.add(a);
            }
            else{
                j.add(a);
            }
        }
        int count = 0;
        for(int a :i){
            array[count] = a;
            count++;
        }
        for(int a :j){
            array[count] = a;
            count++;
        }
    }

}
