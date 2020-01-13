package com.demo.jvm.juc.distributedlock.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import redis.clients.jedis.Jedis;

import java.io.IOException;

@Slf4j
public class ZkClientBuilder {
    public static final String ZK_ADDR = "127.0.0.1:2181";

    private static class SingletonHolder {
        public final static ZkClientBuilder instance = new ZkClientBuilder();
    }

    public static ZkClientBuilder instance() {
        return ZkClientBuilder.SingletonHolder.instance;
    }

    private ZkClientBuilder(){

    }

    public ZooKeeper buildClient(Watcher watcher){
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(ZK_ADDR,5000, watcher);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return zk;
    }
}
