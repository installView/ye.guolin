package com.guozi.gzspringboot4redis.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 作者：ye.guolin
 * 日期：2020/01/04
 * 描述：
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    //===============================String======================
    /**
     * 作者: ye.guolin
     * 日期: 2020/01/04 14:00
     * 描述: 设置过期时间
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 作者: ye.guolin
     * 日期: 2020/01/04 14:01
     * 描述: 根据key值获取缓存过期时间
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }
    
    /**
     * 作者: ye.guolin
     * 日期: 2020/01/04 14:02
     * 描述: 判断key值是否存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 作者: ye.guolin
     * 日期: 2020/01/04 14:04
     * 描述: 根据key删除缓存
     */
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }
    
    /**
     * 作者: ye.guolin
     * 日期: 2020/01/04 14:05
     * 描述: 获取key的value
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 作者: ye.guolin
     * 日期: 2020/01/04 14:05
     * 描述: 缓存放入
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 作者: ye.guolin
     * 日期: 2020/01/04 14:10
     * 描述: 存入缓存并设置失效时间
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 作者: ye.guolin
     * 日期: 2020/01/04 14:11
     * 描述: 获取递增数
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 作者: ye.guolin
     * 日期: 2020/01/04 14:12
     * 描述: 递减
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }
    //======================================Hash===================================
    /**
     * 作者: ye.guolin
     * 日期: 2020/01/04 14:13
     * 描述: HashGet
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 作者: ye.guolin
     * 日期: 2020/01/04 14:13
     * 描述: 获取hashGet所有值
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 作者: ye.guolin
     * 日期: 2020/01/04 14:17
     * 描述: HashSet
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 作者: ye.guolin
     * 日期: 2020/01/04 14:18
     * 描述: 存入hashSet并设置缓存时间
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 作者: ye.guolin
     * 日期: 2020/01/04 14:19
     * 描述: 向一张hash表中放入数据,如果不存在将创建
     */
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 作者: ye.guolin
     * 日期: 2020/01/04 14:20
     * 描述: 删除hash表中的值
     */
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }
    
    /**
     * 作者: ye.guolin
     * 日期: 2020/01/04 14:21
     * 描述: 判断hash表中是否有该项的值
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

}
