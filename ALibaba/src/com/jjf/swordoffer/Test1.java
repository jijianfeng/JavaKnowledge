package com.jjf.swordoffer;
/**
 * 在一个二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。
 * 请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 * @author jjf_lenovo
 * 2017年2月24日18:24:06
 * 时间限制：1秒 空间限制：32768K
 * 运行时间：143ms 占用内存：5147k
 */
public class Test1 {
	public static void main(String args[]){
		 int A[][] = { { 1, 2, 8, 9 }, { 2, 4, 9, 12 }, { 4, 7, 10, 13 },  
	                { 6, 8, 11, 15 } };  
	        System.out.println(Find(7,A));  
	}
	public static boolean Find(int target, int [][] array) {
			boolean flag = false;  
	//        int rows = array.length;// 行数  
	//        int columns = array[0].length;// 列数  
	        int row = 0;  
	        int column = array[0].length - 1;  
	        while (row < array.length && column >= 0) {  
	            // 比较二维数组中的元素与number的关系  target
	            if (array[row][column] == target) {  
	                flag = true;  
	                break;// 跳出循环  
	            } else if (array[row][column] > target) {  
	                // 列变小  
	                column--;  
	            } else {  
	                // 行变大  
	                row++;  
	            }  
	        }  
	        return flag;  
    }
}
