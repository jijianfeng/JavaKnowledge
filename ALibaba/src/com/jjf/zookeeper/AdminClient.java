package com.jjf.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.Date;

/**
 * Created by jjf_lenovo on 2017/4/5.
 */
public class AdminClient implements Watcher {
    ZooKeeper zk;
    String hostPort;

    AdminClient(String hostPort){
        this.hostPort = hostPort;
    }

    void Start() throws Exception{
        zk = new ZooKeeper(hostPort,15000,this);
    }

    void listState(){
        //master
        try{
            Stat stat = new Stat();
            byte[] masterData = zk.getData("/master",false,stat);
            Date startDate = new Date(stat.getCtime());
            System.out.println("Master: "+new String(masterData)+"since"+startDate);
        } catch (KeeperException.NoNodeException e) {
            System.out.println("主节点不存在");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }

        //workers
        try {
            for(String w:zk.getChildren("/workers",false)){
                byte data[] = zk.getData("/workers"+w,false,null);
                String state = new String(data);
                System.out.println("\t"+w+":"+state);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //tasks
        try {
            for(String t:zk.getChildren("/assign",false)){
                System.out.println("\t"+t);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println(event);
    }

    public static void main(String[] args){

    }
}
