package com.jjf.test;

import org.junit.Assert;

import java.util.Scanner;

/**
 * 模拟Ca多线程下的解密方式
 * 
 * @author jjf_lenovo
 * 
 */
public class Test{

  @org.junit.Test
  public void testParse(){
    System.out.println(Long.parseLong(null));
  }

  @org.junit.Test
  public void testEquals(){
//    Assert.assertTrue("123".equals(123));
    Assert.assertTrue(Long.valueOf(123L).equals("123"));
  }

  static int cut(int[] parts) {
    int cost=0;
    int sum=0;
    for(int part:parts){
      sum=sum+part;
    }
    int temp=parts[0];
    if(parts.length==1){
      return parts[0];
    }
    if(parts.length==2){
      return parts[0]+parts[1];
    }
    for(int i=1;i<parts.length-1;i++){
      temp=temp+parts[i]+parts[parts.length-1];
      if((sum-temp>temp)&&(sum-temp-parts[i+1]-parts[parts.length-i-1]<temp+parts[i+1]+parts[parts.length-i-1])){
        cost=cost+sum;
        cost=cost+cut(cutParts(parts,0,i));
        cost=cost+cut(cutParts(parts,i,parts.length-1));
        return cost;
      }
    }
    return 0;
  }

  private static int[] cutParts(int[] parts,int start,int end){
    int[] result=new int[end-start];
    int j=0;
    for(int i=start;i<end;i++){
      result[j]=parts[i];
      j++;
    }
    return result;
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
  public static void main(String[] args){
    Scanner in = new Scanner(System.in);
    int pieces = Integer.parseInt(in.nextLine().trim());
    int[] parts = new int[pieces];
    for (int i = 0; i < pieces; i++) {
      parts[i] = Integer.parseInt(in.nextLine().trim());
    }
    quickSort(parts,0,parts.length-1);
    System.out.println(cut(parts));
  }
}
