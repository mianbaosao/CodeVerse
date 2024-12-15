package com.mianbao.subject.domain.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * 缓存工具类
 *
 * @author: bread
 * @date: 2024/9/22
 */
@Component
public class CacheUtil<K, V> {

    private Cache<String, String> localCache =
            CacheBuilder.newBuilder()
                    .maximumSize(5000)
                    .expireAfterWrite(10, TimeUnit.SECONDS)
                    .build();

    //Class<V> clazz: 泛型类型 V 的类对象，用于反序列化 JSON 字符串。
    //Function<String, List<V>> function: 一个函数式接口的实现，用于在缓存中没有数据时调用的方法，
    // 用于在缓存未命中时执行特定的获取数据并转换为List<V>的操作。
    public List<V> getResult(String cacheKey, Class<V> clazz,
                             Function<String, List<V>> function) {
        List<V> resultList = new ArrayList<>();
        String content = localCache.getIfPresent(cacheKey);
        if (StringUtils.isNotBlank(content)) {
            //用于将 JSON 数组字符串解析为指定类型的 List 对象。
            resultList = JSON.parseArray(content, clazz);
        } else {
            resultList = function.apply(cacheKey);
            if (!CollectionUtils.isEmpty(resultList)) {
                localCache.put(cacheKey, JSON.toJSONString(resultList));
            }
        }
        return resultList;
    }

    public Map<K, V> getMapResult(String cacheKey, Class<V> clazz,
                                  Function<String, Map<K, V>> function) {
        HashMap<K, V> map = new HashMap<>();
        String content = localCache.getIfPresent(cacheKey);
        if (StringUtils.isNotBlank(content)) {
            map = (HashMap<K, V>) JSON.parseObject(content, new TypeReference<Map<K, V>>() {
            });
        } else {
            map = (HashMap<K, V>) function.apply(cacheKey);
            if (!CollectionUtils.isEmpty(map)) {
                localCache.put(cacheKey, JSON.toJSONString(map));
            }
        }
        return map;
    }

}
