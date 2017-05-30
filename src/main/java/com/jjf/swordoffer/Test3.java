package com.jjf.swordoffer;

import java.util.ArrayList;
import java.util.Stack;

/**
 * 输入一个链表，从尾到头打印链表每个节点的值。
 * 
 * @author jjf_lenovo 时间限制：1秒 空间限制：32768K 
 * 2017年2月24日19:18:04 运行时间：36ms 占用内存：636k
 * 运行时间：36ms 占用内存：629k
 */
public class Test3 {

	public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
		Stack<Integer> stack = new Stack<>();
		while(listNode!=null){
			stack.push(listNode.val);
			listNode = listNode.next;
		}
//		Collections.reverse(list);  //util类来反转
		ArrayList<Integer> result = new ArrayList<Integer>();
		while(!stack.isEmpty()){
			result.add(stack.pop());
		}
		return result ;
	}
}

class ListNode {
	int val;
	ListNode next = null;
	ListNode(int val) {
		this.val = val;
	}
}
