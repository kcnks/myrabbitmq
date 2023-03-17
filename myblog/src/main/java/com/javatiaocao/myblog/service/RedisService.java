package com.javatiaocao.myblog.service;

import com.javatiaocao.myblog.utils.DataMap;

public interface RedisService {
    /**
     * 判断key是否存在
     */
    Boolean hasKey(String key);


}
