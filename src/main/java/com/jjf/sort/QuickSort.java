package com.jjf.sort;
//快速排序
//时间复杂度在
//空间复杂度
//2000W条数据：Exception in thread "main" java.lang.StackOverflowError
//200W条数据：1147 1140 1143
//20W条数据：26 29 39
//2W条数据：4 5 16
public class QuickSort {
	public static void main(String[] args){
//		int lengtg = 200000;
		int[] a = {4,7,0,1,3};
//		int[] a = new int[lengtg];
		for(int i = 0;i<a.length;i++){
			a[i] = (int) (Math.random()*10);
		}
		long start = System.currentTimeMillis();
//		print(a);
		System.out.println("初始↑↑↑");
		quickSort(a,0,a.length-1);
		long end = System.currentTimeMillis();
		System.out.println("花费时间："+(end-start));
//		print(a);
	}
	// 一趟快速排序，begin、high指定序列的下界和上界，递归算法
	private static void quickSort(int[] table, int begin, int end) {
		if (begin < end) // 序列有效
		{
			int i = begin, j = end;
//			System.out.println(i+":"+j);
			int vot = table[i]; // 第一个值作为基准值
			while (i != j) // 一趟排序
			{
				while (i < j && vot <= table[j]){
					// 从后向前寻找较小值
					j--;
				}
				if (i < j){
					table[i++] = table[j];// 较小元素向前移动
				}
				while (i < j && table[i] <= vot){
					// 从前向后寻找较大值
					i++;
				}
				if (i < j){
					table[j--] = table[i]; // 较大元素向后移动
				}
//				print(table);
			}
			table[i] = vot; // 基准值到达最终位置
//			print(table);
			quickSort(table, begin, j - 1); // 前端子序列再排序，递归调用
			quickSort(table, i + 1, end); // 后端子序列再排序，递归调用
		}
	}

	public static void print(int a[]) {
		for (int i = 0; i < a.length; i++) {
			System.out.print(" " + a[i]);
		}
		System.out.println();
	}
}
