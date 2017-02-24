package com.jjf.swordoffer;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 输入一个链表，从尾到头打印链表每个节点的值。
 * 
 * @author jjf_lenovo 时间限制：1秒 空间限制：32768K 
 * 2017年2月24日19:18:04 运行时间：36ms 占用内存：636k
 * 运行时间：36ms 占用内存：629k
 */
public class Test3 {

	public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		while(listNode!=null){
			list.add(listNode.val);
			listNode = listNode.next;
		}
		Collections.reverse(list);  
		return list ;
	}
}

class ListNode {
	int val;
	ListNode next = null;
	ListNode(int val) {
		this.val = val;
	}
}
