package com.ymg.redis;

import com.ymg.framework.redis.RedisUtil;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by yumg on 2017/7/17.
 */
public class RedisTest {
    private Jedis jedis;

    @Before
    public void setup(){
        jedis = new Jedis("120.26.202.235",6379);
        //权限认证
        jedis.auth("yumg10");
    }

    /**
     * redis存储字符串
     */
    @Test
    public void testString(){
        //-----添加数据----------
        jedis.set("name","于美光1");//向key-->name中放入了value-->于美光
        System.out.println(jedis.get("name"));//执行结果：于美光
        jedis.append("name", "创建的redis"); //拼接
        System.out.println(jedis.get("name"));
        jedis.del("name");  //删除某个键
        System.out.println(jedis.get("name"));
        //设置多个键值对
        jedis.mset("name","yumg","age","28","qq","470215591");
        jedis.incr("age"); //进行加1操作
        System.out.println(jedis.get("name") + "-" + jedis.get("age") + "-" + jedis.get("qq"));
    }

    /**
     * 操作map
     */
    @Test
    public void testMap(){
        //添加数据
        Map<String,String> map = new HashMap();
        map.put("name","于美光1");
        map.put("age","28");
        map.put("address","北京");
        jedis.hmset("user",map);
        //取出数据
        List<String> rsMap = jedis.hmget("user","name","age","address");
        System.out.println(rsMap);
        //删除map中的某个键值
        jedis.hdel("user","age");
        System.out.println(jedis.hget("user","age") );
        System.out.println(jedis.hlen("user"));//长度
        System.out.println(jedis.exists("user"));//是否存在key为user的记录 返回true
        System.out.println(jedis.hkeys("user"));//返回map对象中的所有key
        System.out.println(jedis.hvals("user"));//返回map对象中的所有value

        Iterator<String> iterator = jedis.hkeys("user").iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            System.out.println(key+":"+jedis.hget("user",key));
        }
    }

    @Test
    public void testList(){
        //开始前，先移除所有的内容
        jedis.del("framework1");
        //先打印所有
        System.out.println(jedis.lrange("framework",0,-1));
        //添加上 left 左边
        jedis.lpush("framework","mybatis");
        jedis.lpush("framework","struts");
        jedis.lpush("framework","hibernate");
        jedis.lpush("framework","jenkins");
        //取出所有并打印
        System.out.println(jedis.lrange("framework",0,-1));;
        //右边
        jedis.del("java framework");
        jedis.rpush("java framework","spring");
        jedis.rpush("java framework","struts");
        jedis.rpush("java framework","hibernate");
        System.out.println(jedis.lrange("java framework",0,-1));
    }

    //测试Set
    @Test
    public void testSet(){
        jedis.del("user1");
        jedis.sadd("user","yumg");
        jedis.sadd("user","ymg");
        jedis.sadd("user","yumeiguang");
        jedis.sadd("user","abc");
        //移除noname
        jedis.srem("user","abc");
        System.out.println(jedis.smembers("user"));
        System.out.println(jedis.sismember("user","yumeiguang"));
        System.out.println(jedis.srandmember("user"));
        System.out.println(jedis.scard("user"));//返回集合的元素个数
    }

    /**
     * jedis 排序测试
     */
    @Test
    public void testSort(){
        jedis.del("array1");
        jedis.rpush("array","3");
        jedis.lpush("array","2");
        jedis.lpush("array","15");
        jedis.lpush("array","6");
        jedis.lpush("array","9");
        jedis.lpush("array","0");
        System.out.println(jedis.lrange("array",0,-1));
        System.out.println(jedis.sort("array"));
        System.out.println(jedis.lrange("array",0,-1));
    }

    //测试线程池
    @Test
    public void testRedisPoll(){
        RedisUtil.getJedis().set("redisPool","redisPoolTest");
        System.out.println(RedisUtil.getJedis().get("redisPool"));
    }

}
