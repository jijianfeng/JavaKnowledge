package com.jjf.sort;
//插入排序 稳定   场景:物品的报价总体总体呈上升趋势
//时间复杂度在O(n) ~ O(n^2)
//空间复杂度O(1),因为temp
//20W条数据：10996 12530 10961
//2W条数据：129 111 128
//2k条数据 ：4 4 6
public class InsertSort {
	public static void main(String args[]){
		int lengtg = 20000000;
		int[] a = new int[lengtg];
		for(int i = 0;i<lengtg;i++){
			a[i] = (int) (Math.random()*1000);
		}
		long start = System.currentTimeMillis();
		sort(a);
		long end = System.currentTimeMillis();
		System.out.println("花费时间："+(end-start));
		print(a);
	}
	
	public static void sort(int table[]){
		for(int i=1;i<table.length;i++){
			int temp = table[i];
			int j;
			for(j=i-1;j>=0&&table[j]>temp;j--){
				table[j+1] = table[j];
			}
			table[j+1] = temp;
		}
	}
	
	public static void print(int a[]){
		for(int i =0;i<a.length;i++){
			System.out.print(" "+a[i]);
		}
	}
}
