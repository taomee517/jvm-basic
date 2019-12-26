package com.demo.jvm.juc.distributedlock.service;

import com.demo.jvm.juc.distributedlock.util.JedisBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.concurrent.ScheduledThreadPoolExecutor;

@Slf4j
public class RedisDao {
    private static ThreadLocal<String> UNIQUE_SIGN = new ThreadLocal<>();
    private static ScheduledThreadPoolExecutor schedulePool = new ScheduledThreadPoolExecutor(1);

    public boolean setNxEx(String key, String value){
        Jedis jedis = JedisBuilder.instance().getJedis();
        boolean flag = false;
        try {
            SetParams setParams = new SetParams();
            setParams.nx();
            setParams.ex(10);
            flag = StringUtils.isNotEmpty(jedis.set(key,value,setParams));
            UNIQUE_SIGN.set(value);
            if (flag) {
                //开启定时刷新过期时间
                scheduleExpirationRenewal(key,value);
            }
        } finally {
            jedis.close();
            return flag;
        }
    }

    public boolean delete(String key){
        Jedis jedis = JedisBuilder.instance().getJedis();
        boolean flag = false;
//        try {
//            //get后,再比较,再delete 不能保证操作的原子性
//            //建议采用lua脚本
//            String srcValue = jedis.get(key);
//            if(UNIQUE_SIGN.get().equals(srcValue)){
//                flag = jedis.del(key)>0;
//            }
//        } finally {
//            jedis.close();
//            return flag;
//        }

        try {
            String expectedValue = UNIQUE_SIGN.get();
            // 使用lua脚本进行原子删除操作
            String checkAndDelScript = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                    "return redis.call('del', KEYS[1]) " +
                    "else " +
                    "return 0 " +
                    "end";
            Long delResult = ((Long) jedis.eval(checkAndDelScript, 1, key, expectedValue));
            flag = delResult != 0;
        } finally {
            jedis.close();
            return flag;
        }

    }


    private void scheduleExpirationRenewal(String key, String value){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Jedis jedis = JedisBuilder.instance().getJedis();
                try {
                    while (true) {
                        try {
                            Thread.sleep(8000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        log.info("se:{}",value);
                        String checkAndExpireScript = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                                "return redis.call('expire',KEYS[1],ARGV[2]) " +
                                "else " +
                                "return 0 end";
                        long result = ((long) jedis.eval(checkAndExpireScript, 1, key, value, "10"));
                        if(result>0){
                            log.info("执行延迟失效时间中...");
                        }else {
                            break;
                        }
                    }
                } finally {
                    jedis.close();
                }
            }
        }).start();
    }
}
