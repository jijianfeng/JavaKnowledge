package com.jjf.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTest {  
    public static void main(String[] args) {  
//        ExecutorService threadPool = Executors.newFixedThreadPool(3);//ȷ���������̳߳�
        ExecutorService threadPool = Executors.newCachedThreadPool();//�̳߳صĴ�С�����ִ�е���������̬����
//        ExecutorService threadPool = Executors.newSingleThreadExecutor();  //���̳߳�5
//        ExecutorService threadPool = Executors.newCachedThreadPool(); 
        for(int i = 1; i < 5; i++) {  
            final int taskID = i;  
            threadPool.execute(new Runnable() {  
                public void run() {  
                    for(int i = 1; i < 50; i++) {
                        try {
                            Thread.sleep(20);// Ϊ�˲��Գ�Ч������ÿ������ִ�ж���Ҫһ��ʱ��
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("��" + taskID + "������ĵ�" + i + "��ִ��");  
                    }  
                }  
            });  
        }  
        threadPool.shutdown();// ����ִ����ϣ��ر��̳߳�
        System.out.println("Main gg");
    }
}  