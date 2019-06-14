package com.taotao.rest.dao;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.rest.dao
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/22 10:44
 *   *
 **/
public interface JedisClient {
    String get(String key);

    String set(String key, String value);

    String hget(String hkey, String key);

    Long hset(String hkey, String key, String value);

    long incr(String key);

    long decr(String key);

    long expire(String key, int second);

    long ttl(String key);

    long del(String key);

    long hdel(String hkey, String key);
}
