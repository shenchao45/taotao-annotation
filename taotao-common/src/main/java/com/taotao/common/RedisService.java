package com.taotao.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * Created by shenchao on 2017/2/16.
 */
@Service
public class RedisService {
    @Autowired
    private ShardedJedisPool shardedJedisPool;

    /**
     * 设置值到redis
     *
     * @param key
     * @param value
     * @return
     */
    public String set(String key, String value) {
        return this.execute(shardedJedis -> shardedJedis.set(key, value));
    }

    /**
     * 设置值，并且设置生产时间，单位为秒
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    public String set(String key, String value, int time) {
        return this.execute(shardedJedis -> {
            String str = shardedJedis.set(key, value);
            shardedJedis.expire(key, time);
            return str;
        });
    }

    /**
     * 从redis中获取值
     *
     * @param key
     * @return
     */
    public String get(String key) {
        return this.execute(shardedJedis -> shardedJedis.get(key));
    }

    public Long expire(String key, int seconds) {
        return this.execute(shardedJedis -> shardedJedis.expire(key, seconds));
    }

    private <T> T execute(Function<T, ShardedJedis> function) {
        ShardedJedis resource = null;
        try {
            resource = shardedJedisPool.getResource();
            return function.callback(resource);
        } finally {
            if (null == resource) {
                resource.close();
            }
        }
    }

    /**
     * 根据key删除缓存
     * @param key
     */
    public void delete(String key) {
        this.execute(shardedJedis -> shardedJedis.del(key));
    }
}
