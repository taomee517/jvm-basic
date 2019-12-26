package com.demo.jvm.juc.distributedlock.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class JedisBuilder {

    private static class SingletonHolder {
        public final static JedisBuilder instance = new JedisBuilder();
    }

    public static JedisBuilder instance() {
        return JedisBuilder.SingletonHolder.instance;
    }

    private JedisBuilder(){

    }

    public Jedis getJedis(){
        Jedis jedis = new Jedis("127.0.0.1",6379);
        return jedis;
    }
}
