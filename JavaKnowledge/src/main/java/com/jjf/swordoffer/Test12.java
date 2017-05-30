package com.jjf.swordoffer;

/**
 * Created by jjf_lenovo on 2017/4/25.
 */
//输入一个链表，反转链表后，输出链表的所有元素。
public class Test12 {
    public static void main(String[] args){
//        System.out.println("dada");
        ListNode a = new ListNode(1);
        ListNode b = new ListNode(2);
        ListNode c = new ListNode(3);
        ListNode d = new ListNode(4);
        ListNode e = new ListNode(5);
        a.next=b;
        b.next=c;
        c.next=d;
        d.next=e;
        ReverseList(a);
    }

    /**
     * 交换版本的，其实最简单就是再新建一个链表
     * @param head
     * @return
     */
    public static ListNode ReverseList(ListNode head) {
        if(head==null){
            return null;
        }
        if(head.next==null){
            return head;
        }
        ListNode next = head.next;
        head.next=null;
        while(next.next!=null){
            ListNode temp =next.next;
            next.next=head;
            head = next ;
            next = temp;
        };
        next.next=head;
        return next;
    }
}
