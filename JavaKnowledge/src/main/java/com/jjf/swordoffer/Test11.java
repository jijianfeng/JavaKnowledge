package com.jjf.swordoffer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jjf_lenovo on 2017/4/25.
 */
//输入一个链表，输出该链表中倒数第k个结点。
public class Test11 {
    public ListNode FindKthToTail(ListNode head,int k) {
        if(head==null||k==0){
            return null;
        }
        List<ListNode> list = new ArrayList<>();
        list.add(head);
        while(head.next!=null){
            head = head.next;
            list.add(head);
        }
        if(k>list.size()){
            return null;
        }
        return list.get(list.size()-k);
    }
}

