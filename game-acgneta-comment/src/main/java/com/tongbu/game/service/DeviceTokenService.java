package com.tongbu.game.service;

import com.tongbu.game.dao.UmengDeviceTokenMapper;
import com.tongbu.game.entity.UmengDeviceTokenEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jokin
 * @date 2018/9/29 15:55
 */
@Service
public class DeviceTokenService {

    @Autowired
    UmengDeviceTokenMapper mapper;

    private static final Object LOCK = new Object();

    public int insert(int uid, String deviceToken, int appSource) {
        synchronized (LOCK){
            UmengDeviceTokenEntity item = mapper.findByUidAndToken(uid, deviceToken);
            if (item != null && item.getId() > 0) {
                return mapper.update(item.getId());
            }
            item = new UmengDeviceTokenEntity(uid, deviceToken, appSource);
            int count = mapper.insert(item);
            return count > 0 ? item.getId() : 0;
        }
    }

    /**
     * 根据uid 和 deviceToken 返回信息
     * */
    public UmengDeviceTokenEntity findByUidAndToken(int uid,String deviceToken)
    {
        return mapper.findByUidAndToken(uid, deviceToken);
    }

    /**
     * 根据uid 获取信息
     * */
    public List<UmengDeviceTokenEntity> findByUid(int uid){
        return mapper.findByUid(uid);
    }

    /**
     * 获取平台所有的设备，这里数据量较小，直接group by 即可
     *
     * @param appSource 1:iOS  2:Android
     * */
    public List<String> findByAllDeviceToken(int appSource,int page,int pageSize){
        return mapper.findByAppSource(appSource,page,pageSize);
    }
}
