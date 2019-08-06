package org.springboot.distribute.bitmap;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 位图
 * 优势：
 * 1.基于最小的单位bit进行存储，所以非常省空间；
 * 2.设置时候时间复杂度O（1），读取时候时间复杂度O（n），操作是非常快；
 * 3.二进制数据的存储，进行相关计算的时候非常快；
 * 4.方便扩容。
 *
 * 使用场景：
 * 1.用户签到；
 * 2.用户在线状态；
 * 3.视频的各种属性状态；
 * 4.统计活跃用户。
 *
 * @link https://coolcoder.cc/?p=342
 */
@Component
@Slf4j
public class RedisBitMap {

    /**
     * 注意此处要使用，StringRedisTemplate，使用RedisTemplate 无法进行 setBit 操作
     */
    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String key = "online";

    private static final int SPLIT = 10000;

    /**
     * 用户签到
     * @param userId
     */
    public void signIn(Long userId){
        String logKey = genKey(new DateTime().toString("yyyy-MM-dd"));
        Integer offset = Integer.valueOf(new DateTime().toString("yyyyMMdd"));
        log.debug("{}\t{} 签到成功",logKey,offset);
        redisTemplate.opsForValue().setBit(logKey,userId,true);
    }

    public boolean isSignIn(Long userId){
        String logKey = genKey(new DateTime().toString("yyyy-MM-dd"));
        Integer offset = Integer.valueOf(new DateTime().toString("yyyyMMdd"));
        return redisTemplate.opsForValue().getBit(logKey,userId);
    }

    /**
     * 统计活跃用户
     * @param userId
     * @return
     */
    public Long countsActive(Long userId){
        String logKey = genKey(new DateTime().toString("yyyy-MM-dd"));
        Long counts = bitCount(logKey);
        log.debug("{}\t{}",logKey,userId);
        return counts;
    }

    private Long bitCount(final String key){
        return (Long)redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.bitCount(key.getBytes());
            }
        });
    }



    /**
     * 生成名称
     * 格式：
     *  loginLog:2019-06-29:userId:19950701
     *
     * @return
     */
    private String genKey(String date){
        Joiner joiner =  Joiner.on(":");
        List<Object> strs = Arrays.asList("loginLog",date);
        return joiner.join(strs);
    }

    /**
     * 设置用户在线
     * @param userId
     */
    public void setOnlineStatus(Long userId){
        redisTemplate.opsForValue().setBit(key,userId,true);
    }

    /**
     * 设置用户离线
     * @param userId
     */
    public void setOfflineStatus(Long userId){
        redisTemplate.opsForValue().setBit(key,userId,false);
    }

    /**
     * 存储视频的一个属性值
     * @param businessId
     * @param mediaId
     */
    public void setVideoProperty(String businessId,Long mediaId){
        String key = genKey(businessId,mediaId);
        long offset = getOffset(mediaId);
        redisTemplate.opsForValue().setBit(key,offset,true);
    }

    /**
     * 获取视频的属性值
     * @param businessId
     * @param mediaId
     * @return
     */
    public boolean getVideoProperty(String businessId,Long mediaId){
        String key = genKey(businessId,mediaId);
        long offset = getOffset(mediaId);
        return redisTemplate.opsForValue().getBit(key,offset);
    }

    private long getOffset(long mediaId){
        return mediaId % 10000;
    }

    private String genKey(String businessId,Long mediaId){
        return new StringBuffer(businessId).append(":").append(mediaId/SPLIT).toString();
    }


}
