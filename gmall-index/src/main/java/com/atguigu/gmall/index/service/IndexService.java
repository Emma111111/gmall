package com.atguigu.gmall.index.service;

import com.alibaba.fastjson.JSON;
import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.index.VO.CategoryVO;
import com.atguigu.gmall.index.feign.GmallPmsClient;
import com.atguigu.gmall.pms.entity.CategoryEntity;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class IndexService {
    @Autowired
    private GmallPmsClient gmallPmsClient;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redissonClient;
    private static final String KEY_PREFIX="index:cates:";
    public List<CategoryEntity> queryCategories() {
        Resp<List<CategoryEntity>> listResp = this.gmallPmsClient.queryCategoriesByLevelOrPid(1, null);
        List<CategoryEntity> categoryEntities = listResp.getData();
        return categoryEntities;
    }

    public List<CategoryVO> queryCategoryWithSub(Long pid) {
        //获取缓存中的数据
        String cateJson = this.redisTemplate.opsForValue().get(KEY_PREFIX + pid);
        //有，直接返回
        if(StringUtils.isNoneBlank(cateJson)){
            return JSON.parseArray(cateJson, CategoryVO.class);
        }
        //加分布式锁
        RLock lock = this.redissonClient.getLock("lock"+pid);
        lock.lock();
        String cateJson2 = this.redisTemplate.opsForValue().get(KEY_PREFIX + pid);
        //有，直接返回
        if(StringUtils.isNoneBlank(cateJson2)){
            return JSON.parseArray(cateJson2, CategoryVO.class);
        }
        //没用，远程调用查询缓存
        Resp<List<CategoryVO>> listResp = this.gmallPmsClient.queryCategoriesWithSub(pid);
        List<CategoryVO>  vos= listResp.getData();
        //查询完成之后放入缓存
        this.redisTemplate.opsForValue().set(KEY_PREFIX+pid,JSON.toJSONString(vos),5+ new Random().nextInt(5), TimeUnit.DAYS);
        lock.unlock();
        return vos;
    }
}
