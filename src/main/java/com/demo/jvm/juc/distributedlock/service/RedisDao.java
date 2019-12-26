package com.demo.jvm.juc.distributedlock.service;

import com.demo.jvm.juc.distributedlock.util.JedisBuilder;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

public class RedisDao {
    private static ThreadLocal<String> UNIQUE_SIGN = new ThreadLocal<>();

    public static boolean setNxEx(String key, String value){
        Jedis jedis = JedisBuilder.instance().getJedis();
        boolean flag = false;
        try {
            SetParams setParams = new SetParams();
            setParams.nx();
            setParams.ex(10);
            flag = StringUtils.isNotEmpty(jedis.set(key,value,setParams));
            UNIQUE_SIGN.set(value);
        } finally {
            jedis.close();
            return flag;
        }
    }

    public static boolean delete(String key){
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
}
