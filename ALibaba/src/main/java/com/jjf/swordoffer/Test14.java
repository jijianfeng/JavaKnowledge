package com.jjf.swordoffer;

/**
 * Created by jjf_lenovo on 2017/4/26.
 */
//输入两个单调递增的链表，输出两个链表合成后的链表，当然我们需要合成后的链表满足单调不减规则。
public class Test14 {
    public static void main(String args[]){
        ///list1
        ListNode a = new ListNode(0);
        ListNode b = new ListNode(2);
        ListNode c = new ListNode(4);
        ListNode d = new ListNode(6);
        a.next=b;b.next=c;c.next=d;
        //list2
        ListNode e = new ListNode(1);
        ListNode f = new ListNode(3);
        ListNode g = new ListNode(5);
        e.next=f;f.next=g;
        ListNode result = Merge(a,e);
//        System.out.println(result.val);
        do{
            System.out.println(result.val);
            result = result.next;
        }
        while (result.next!=null);
    }

    //递归一直用不好，烦
    public static ListNode Merge(ListNode list1,ListNode list2) {
        if(list1 == null){
            return list2;
        }
        if(list2 == null){
            return list1;
        }
        if(list1.val <= list2.val){
            list1.next = Merge(list1.next, list2);
            return list1;
        }else{
            list2.next = Merge(list1, list2.next);
            return list2;
        }
    }
}
