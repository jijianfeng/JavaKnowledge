package com.jjf.thread;
public class JoinTest {  
    public static void main(String[] args) throws InterruptedException {  
    	MainThread t1 = new MainThread(); 
    	t1.start();
        System.out.println("主线程开始执行！");  
    }  
}  
class JoinThread extends Thread {  
    public JoinThread(String name) {  
        super(name);  
    }  
    public void run() {  
        for(int i = 1; i <= 10; i++)  
            System.out.println(getName() + "执行了" + i + "次");  
    }  
}  

class MainThread extends Thread {
	@Override
    public void run() {  
		JoinThread t1 = new JoinThread("t1");  
        JoinThread t2 = new JoinThread("t2");  
        t1.start();  
        t2.start();  
        try {
			t1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        try {
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        System.out.println("副线程开始执行！");  
    }  
}