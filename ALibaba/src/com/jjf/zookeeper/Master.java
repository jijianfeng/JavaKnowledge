package com.jjf.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Random;

/**
 * Created by jjf_lenovo on 2017/4/4.
 */
public class Master implements Watcher{
    ZooKeeper zk;
    String hostPort;
    String serverId = String.valueOf(new Random().nextLong());
    static boolean isLeader = false; //是否主群首节点

    Master(String hostPort){
        this.hostPort = hostPort;
    }

    void startZK() throws IOException {
        zk = new ZooKeeper(hostPort,15000,this);//当你关闭程序起15秒
    }

    void stopZK() throws IOException, InterruptedException {
        zk.close();
    }

    /**
     * 检查节点
     * @return 是否成为主节点
     */
    boolean checkMaster(){
        while (true){
            Stat stat = new Stat();
            try {
                byte data[] = zk.getData("/master",false,stat);
                isLeader = new String(data).equals(serverId); //如果已经存在"/master"节点，则使用"/master"的数据确定谁是群首
                return true;
            } catch (KeeperException.ConnectionLossException e) {
                System.out.println("重试");
//                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (KeeperException.NoNodeException e){
                return false;
            } catch (KeeperException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建一个所有权限的临时节点临时节点
     */
    void runForMaster(){
       while(true){
           try {
               //ZooDefs.Ids.OPEN_ACL_UNSAFE 为所有人提供了所有权限
               //CreateMode.EPHEMERAL 临时节点
               zk.create("/master",serverId.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
               //创建成功那就是主节点
               isLeader = true;
               break;
           } catch (KeeperException.NodeExistsException e) {
               //已经存在主节点了
               isLeader = false;
               System.out.println("该节点已经存在");
               break;
           } catch (KeeperException e) {
               //这里很有一般都是ConnectionLossException（KeeperException的子类），这里发生错误一般要先判断请求是否完成，来决定是否再次发送请求
               e.printStackTrace();
           } catch (InterruptedException e) {
               //源于客户端线程调用了Thread.interrupt,通常这是因为应用程序部分关闭
               e.printStackTrace();
           }
           if(checkMaster()){
               break;
           }
       }
    }

    //---------------------异步版本start----------------

    /**
     * 异步create节点，但是只有一个单独的回调函数，所以如果单个回调函数被阻塞，会阻塞后续全部
     */
    AsyncCallback.StringCallback masterCreateCallback = new AsyncCallback.StringCallback() {
        /**
         *
         * @param rc 返回调用的结构
         * @param path 我们传给create的path参数值
         * @param ctx 我们传给create的上下文参数
         * @param name 节点名
         */
        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            switch(KeeperException.Code.get(rc)) { //根据rc来判断create的请求结果
                case CONNECTIONLOSS:
                    checkMasterWithCallback();//这里需要判断有没有执行完成，不断重试
                    return;
                case NODEEXISTS:
                    System.out.println("该节点以及存在");
                    return;
                case OK:
                    isLeader = true;
                    System.out.println("ok");
                    break;
                default:
                    System.out.println("default");
                    isLeader = false;
            }
            System.out.println("I'm "+(isLeader?"":"not") +"the leader");
        }
    };

    void checkMasterWithCallback(){
        zk.getData("/master",false,masterCheckCallback,null);
    }

    AsyncCallback.DataCallback masterCheckCallback = new AsyncCallback.DataCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
            switch (KeeperException.Code.get(rc)){
                case CONNECTIONLOSS:
                    checkMasterWithCallback();
                    return;
                case NONODE:
                    runForMasterWithCallBack(); //继续发生请求
                    return;
            }
        }
    };

    void runForMasterWithCallBack(){
        zk.create("/master",
                serverId.getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL,
                masterCreateCallback,"ctx");
    }

    //---------------------end----------------

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Master m = new Master("118.190.92.176:2182");
        m.startZK();
        //同步
//        m.runForMaster();
//        if(isLeader){
//            System.out.println("I'm the leader");
//            //链接后，会创建一个守护线程维护此次会话，因为是守护线程，不影响程序退出，我们要保证程序运行一会儿
//        }
//        else{
//            System.out.println("I'm not the leader");
//        }
//        Thread.sleep(60000);
        //异步
        m.runForMasterWithCallBack();
        Thread.sleep(60000);

        //结束
        m.stopZK();
    }
}
