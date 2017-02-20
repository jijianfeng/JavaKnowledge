package com.jjf.sort;
//冒泡排序 稳定 
//时间复杂度在O(n) ~ O(n^2),数据序列初始排列越接近有序，直接插入排序的时间效率越高
//空间复杂度O(1),因为temp
//20W条数据：50689 50940 50474
//2W条数据：489 485 477
//2k条数据：7 8 7
public class BubbleSort {
	public static void main(String[] args){
		int lengtg = 2000;
		int[] a = new int[lengtg];
		for(int i = 0;i<lengtg;i++){
			a[i] = (int) (Math.random()*1000);
		}
		//20W条数据：10996 12530 10961
		long start = System.currentTimeMillis();
		bubbleSort(a);
		long end = System.currentTimeMillis();
		System.out.println("花费时间："+(end-start));
		//20W条数据：10996 12530 10961
//		print(a);
	}
	
	public static void bubbleSort(int[] table){ // 冒泡排序{
		System.out.println("冒泡排序");
		boolean exchange = true; // 是否交换的标记
		for (int i = 1; i < table.length && exchange; i++){ // 有交换时再进行下一趟，最多n-1趟
			exchange = false; // 假定元素未交换
			for (int j = 0; j < table.length - i; j++){
				// 一趟比较、交换
				if (table[j] > table[j + 1]){ // 反序时，交换
					int temp = table[j];
					table[j] = table[j + 1];
					table[j + 1] = temp;
					exchange = true; // 有交换
				}
			}
//			System.out.print("第" + i + "趟: ");
//			print(table);
		}
	}

	public static void print(int a[]){
		for(int i =0;i<a.length;i++){
			System.out.print(" "+a[i]);
		}
		System.out.println();
	}
}
