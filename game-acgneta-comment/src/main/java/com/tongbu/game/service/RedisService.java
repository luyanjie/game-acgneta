package com.tongbu.game.service;

import org.springframework.stereotype.Service;
import vip.xinba.core.redis.Caches;
import vip.xinba.core.redis.IStringRedisClient;
import vip.xinba.core.redis.stand.alone.StringRedisClient;

import java.util.List;

@Service
public class RedisService {
    public <T> T get(String key, Class<T> clazz) {
        IStringRedisClient client = new StringRedisClient("online");
        return client.get(key, clazz);
        //return Caches.String.STANDALONE.getClient().get(key, clazz);
    }

    public String  get(String key) {

        return Caches.String.STANDALONE.getClient().get(key);
    }

    public Long del(final String key) {

        return Caches.String.STANDALONE.getClient().del(key);
    }

    public boolean exists(final String key) {
        return Caches.String.STANDALONE.getClient().exists(key);
    }

    public boolean rpush(String key, String value) {
        return this.rpush(key, value, -1);
    }

    public boolean rpush(final String key, final String value, final int seconds) {
        return Caches.List.STANDALONE.getClient().rpush(key, value, seconds);
    }

    public List<String> lrange(final String key, final int start, final int end) {
        return Caches.List.STANDALONE.getClient().lrange(key, start, end);
    }

    public boolean ltrim(final String key, final int start, final int stop) {
        return Caches.List.STANDALONE.getClient().ltrim(key,start,stop);
    }

    public Boolean getBit(String key, long offset) {
        return Caches.String.STANDALONE.getClient().getBit(key,offset);
    }

    public Boolean setbit(String key, long offset, boolean value) {
        return Caches.String.STANDALONE.getClient().setbit(key,offset,value);
    }
}
