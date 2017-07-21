package com.ymg.framework.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
/**
 * Created by yumg on 2017/7/17.
 */
public class RedisUtil {

    //redis 服务器IP地址
    private static final String IP_ADDRESS = "120.26.202.235";

    //redis 端口号
    private static final int PORT = 6379;

    //访问密码
    private static final String AUTH = "yumg10";

    //可连接实例的最大数目，默认值为8
    ///如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static final int MAX_ACTIVE = 1024;

    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static final int MAX_IDLE = 200;

    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static final int MAX_WAIT = 10000;

    private static final int TIMEOUT = 10000;

    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static final boolean TEST_ON_BORROW = true;

    private static JedisPool jedisPool = null;

    /**
     * 初始化連接池
     */
    static {
        try{
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxActive(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWait(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config,IP_ADDRESS,PORT,TIMEOUT,AUTH);
            System.out.println("初始化成功");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取redis实例
     */
    public synchronized static Jedis getJedis(){
        try {
            if (jedisPool != null){
                Jedis resource = jedisPool.getResource();
                System.out.println("获得连接");
                return resource;
            }else{
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }

    public static void returnResource(final Jedis jedis){
        if (jedis != null){
            jedisPool.returnResource(jedis);
        }
    }



}
