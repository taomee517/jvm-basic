package com.demo.jvm.juc.distributedlock.service;

import com.demo.jvm.juc.distributedlock.util.ZkClientBuilder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Data
@Slf4j
public class ZkLockService {
    private ZooKeeper zkClient;
    /**分布式锁的根节点路径*/
    private static final String LOCK_ROOT_PATH = "/Locks";
    /**分布式锁子节点前缀*/
    private static final String LOCK_NODE_NAME = "Lock_";
    private String lockPath;

    public ZkLockService() {
        this.zkClient = ZkClientBuilder.instance().buildClient(new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if(watchedEvent.getState()== Event.KeeperState.Disconnected){
                    System.out.println("失去连接");
                }
            }
        });
    }

    /**
     * 创建分布式锁子节点
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void creatLockNode() throws KeeperException, InterruptedException {
        //如果根节点不存在，则创建根节点
        Stat stat = zkClient.exists(LOCK_ROOT_PATH, false);
        if (stat == null) {
            zkClient.create(LOCK_ROOT_PATH, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }

        // 创建EPHEMERAL_SEQUENTIAL类型子节点
        // 临时有序节点特性： 访问时创建，断连时删除
        String lockPath = zkClient.create(LOCK_ROOT_PATH + "/" + LOCK_NODE_NAME,
                Thread.currentThread().getName().getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);
        log.info(Thread.currentThread().getName() + " 锁创建: " + lockPath);
        this.lockPath = lockPath;
    }

    public void tryAcquireLock() throws Exception{
        List<String> nodePaths = zkClient.getChildren(LOCK_ROOT_PATH,false);
        if(nodePaths!=null && nodePaths.size()>0){
            Collections.sort(nodePaths);
        }else {
            return;
        }
        // 如果同时创建了多个子节点（临时有序），子节点会排序，先创建的index小
        // 判断是否子节点锁排序的第1位
        int index = nodePaths.indexOf(StringUtils.substring(lockPath,LOCK_ROOT_PATH.length() + 1));
        if(index == 0){
            log.info("{}获得锁",Thread.currentThread().getName());
            return;
        }else {
            String preNodePath = nodePaths.get(index-1);
            String preNodeFullPath = LOCK_ROOT_PATH + "/" + preNodePath;
            Watcher watcher = new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    synchronized (this) {
                        if(watchedEvent.getType().equals(Event.EventType.NodeDeleted)) {
                            notifyAll();
                        }
                    }
                }
            };
            Stat stat = zkClient.exists(preNodeFullPath, watcher);
            if (Objects.isNull(stat)) {
                tryAcquireLock();
            }else {
                synchronized (watcher){
                    watcher.wait();
                }
                tryAcquireLock();
            }
        }
    }

    public void releaseLock(){
        try {
            Stat stat = zkClient.exists(lockPath, false);
            if (Objects.nonNull(stat)) {
                zkClient.delete(lockPath, -1);
            }
            zkClient.close();
            log.info("{}释放锁",Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
