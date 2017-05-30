package com.jjf.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * Created by jjf_lenovo on 2017/4/4.
 */
public class Client implements Watcher {
    ZooKeeper zk;
    String hostPort;

    Client(String hostPort){
        this.hostPort = hostPort;
    }

    void startZK() throws IOException {
        zk = new ZooKeeper(hostPort,15000,this);
    }

    String queueCommand(String command) throws KeeperException, InterruptedException {
        while(true){
            try{
                String name = zk.create("/tasks/task-",command.getBytes(),
                        ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.EPHEMERAL_SEQUENTIAL);
                return name;
//                break;
            }
            catch (KeeperException.NodeExistsException e){
                throw new KeeperException.NodeExistsException("already appears to be running");
            }
            catch (KeeperException.ConnectionLossException e){

            }
        }
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println(event);
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        Client c = new Client("118.190.92.176:2181");
        c.startZK();
        String name = c.queueCommand("1031397017");
        System.out.println("¥¥Ω®¡À"+name);
    }
}
