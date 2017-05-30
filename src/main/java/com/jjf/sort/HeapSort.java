package com.jjf.sort;
//堆排序
//时间复杂度在
//空间复杂度
//2亿条数据：46419 44916 45233
//2000W条数据：4073 4072 4107
//200W条数据：270 259 270
//20W条数据：24 25 25
//2W条数据：5 4 4
public class HeapSort {
	public static void main(String args[]){
		int lengtg = 200000000;
		int[] a = new int[lengtg];
		for(int i = 0;i<lengtg;i++){
			a[i] = (int) (Math.random()*1000);
		}
		long start = System.currentTimeMillis();
		heapSort_max(a);
		long end = System.currentTimeMillis();
		System.out.println("花费时间："+(end-start));
//		print(a);
	}
	
	public static void heapSort_max(int[] table) // 堆排序（升序），最大堆
	{
//		System.out.println("最大堆？ " + isMaxHeap(table));
//		System.out.println("建立最大堆序列");
		int n = table.length;
		for (int j = n / 2 - 1; j >= 0; j--)
			// 创建最大堆
			sift_max(table, j, n - 1);
//		System.out.println("最大堆？ " + isMaxHeap(table));
//		System.out.println("堆排序（升序）");
		for (int j = n - 1; j > 0; j--) // 每趟将最大值交换到后面，再调整成堆
		{
			int temp = table[0];
			table[0] = table[j];
			table[j] = temp;
			sift_max(table, 0, j - 1);
		}
	}

	// 将以begin为根的子树调整成最大堆，begin、end是序列下界和上界
	private static void sift_max(int[] table, int begin, int end) {
		int i = begin, j = 2 * i + 1; // i为子树的根，j为i结点的左孩子
		int temp = table[i]; // 获得第i个元素的值
		while (j <= end) // 沿较大值孩子结点向下筛选
		{
			if (j < end && table[j] < table[j + 1]) // 数组元素比较
				j++; // j为左右孩子的较大者
			if (temp < table[j]) // 若父母结点值较小
			{
				table[i] = table[j]; // 孩子结点中的较大值上移
				i = j; // i、j向下一层
				j = 2 * i + 1;
			} else
				break;
		}
		table[i] = temp; // 当前子树的原根值调整后的位置
//		System.out.print("sift  " + begin + ".." + end + "  ");
//		print(table);
	}
	
//	public static boolean isMinHeap(int[] value) // 判断一个数据序列是否为最小堆
//	{
//		if (value.length == 0) // 空序列不是堆。若无此句，则空序列是堆，定义不同
//			return false;
//		for (int i = value.length / 2 - 1; i >= 0; i--) // i从最深一棵子树的根结点开始
//		{
//			int j = 2 * i + 1; // j是i的左孩子，肯定存在
//			if (value[i] > value[j] || j + 1 < value.length
//					&& value[i] > value[j + 1])
//				return false;
//		}
//		return true;
//	}
	
//	public static boolean isMaxHeap(int[] value) // 判断一个数据序列是否为最大堆
//	{
//		if (value.length == 0) // 空序列不是堆
//			return false;
//		for (int i = value.length / 2 - 1; i >= 0; i--) // i从最深一棵子树的根结点开始
//		{
//			int j = 2 * i + 1; // j是i的左孩子，肯定存在
//			if (value[i] < value[j] || j + 1 < value.length
//					&& value[i] < value[j + 1])
//				return false;
//		}
//		return true;
//	}
	
	public static void print(int a[]){
		for(int i =0;i<a.length;i++){
			System.out.print(" "+a[i]);
		}
	}

}
