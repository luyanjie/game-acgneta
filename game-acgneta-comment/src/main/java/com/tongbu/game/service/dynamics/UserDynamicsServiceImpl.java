package com.tongbu.game.service.dynamics;

import com.alibaba.fastjson.JSON;
import com.tongbu.game.common.enums.UserActionEnum;
import com.tongbu.game.dao.AccountMapper;
import com.tongbu.game.dao.dynamics.UserDynamicsNoShowDao;
import com.tongbu.game.dao.dynamics.UserDynamicsShowDao;
import com.tongbu.game.entity.dynamics.DynamicsNoShow;
import com.tongbu.game.entity.dynamics.DynamicsShow;
import com.tongbu.game.entity.response.PageResponse;
import com.tongbu.game.service.AnimationsOtherService;
import com.tongbu.game.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@EnableScheduling
@Slf4j
public class UserDynamicsServiceImpl implements IUserDynamicsService {

    // private static final AtomicInteger a = new AtomicInteger();

    @Autowired
    UserDynamicsShowDao userDynamicsDao;

    @Autowired
    UserDynamicsNoShowDao userDynamicsNoShowDao;

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    AnimationsOtherService otherService;

    @Autowired
    RedisService redisService;

    /**
     * 根据uid获取所有动态
     *
     * @param uid uid
     * @return List
     */
    @Override
    public List<DynamicsShow> findByUid(int uid) {
        return userDynamicsDao.findByUid(uid);
    }

    @Override
    public PageResponse<DynamicsShow> findByUid(int uid, int page, int size) {
        // 使用timestamp分页，每页指定两条数据
        Sort sort = new Sort(Sort.Direction.DESC, sortFiled);
        Pageable pageable = PageRequest.of(page - 1, size <= 0 ? defaultSize : size, sort);
        Page<DynamicsShow> pageList = userDynamicsDao.findByUid(uid, pageable);
        return new PageResponse<>(pageList.getTotalPages(), pageList.getTotalElements(), pageList.getContent());
    }

    /**
     * 单条插入
     */
    @Override
    public DynamicsShow insert(DynamicsShow dynamicsA) {
        UserActionEnum actionEnum = dynamicsA.getUserActionEnum();
        // 判断动态写入mongo
        if (StringUtils.isEmpty(dynamicsA.getDescription())) {
            // 如果未传描述信息，使用默认的描述信息
            dynamicsA.setDescription(actionEnum.getDescription());
        }
        // 需要展示的动态
        if (actionEnum.isShow()) {
            userDynamicsDao.insert(dynamicsA);
            // 更新数据库
            UpdateAssociateId(actionEnum,dynamicsA.getTypeId(),dynamicsA.getCommentId(),dynamicsA.get_id());
        }
        // 记录所有动态 等1.4.0.1 版本发布后开放
        addScore(new DynamicsNoShow(dynamicsA.get_id(), dynamicsA.getUserActionEnum(),
                dynamicsA.getUid(), dynamicsA.getTypeId(), dynamicsA.getCommentId(), dynamicsA.getDescription(), 1, dynamicsA.getTimestamp()));
        return dynamicsA;
    }

    @Override
    public DynamicsNoShow addScore(DynamicsNoShow dynamicsNoShow) {
        UserActionEnum actionEnum = dynamicsNoShow.getUserActionEnum();
        if (actionEnum.getAction().equals(UserActionEnum.STARTUP.getAction())) {

            // 如果是启动 先判断是不是今天首次启动
            String key = StringUtils.join(CACHE_LOGIN_TODAY, CACHE_SCORE_LIST_SPLIT, DateFormatUtils.format(new Date(), "yyyyMMdd"));
            if (redisService.getBit(key, dynamicsNoShow.getUid())) {
                return dynamicsNoShow;
            }
            redisService.setbit(key, dynamicsNoShow.getUid(), true);
        }
        // 单条动态用户所能得到的分数
        int integral = dynamicsNoShow.getUserActionEnum().getIntegral();
        redisService.rpush(CACHE_SCORE_LIST, StringUtils.join(dynamicsNoShow.getUid(), CACHE_SCORE_LIST_SPLIT, integral));
        // 判断动态写入mongo
        if (StringUtils.isEmpty(dynamicsNoShow.getDescription())) {
            // 如果未传描述信息，使用默认的描述信息
            dynamicsNoShow.setDescription(actionEnum.getDescription());
        }
        // 记录所有动态
        return userDynamicsNoShowDao.insert(dynamicsNoShow);
    }

    @Override
    public void deleteById(String id) {
        userDynamicsDao.deleteById(id);
    }

    @Override
    public void deleteByUidAndTypeIdAndCommentId(int uid, int typeId, int commentId) {
        userDynamicsDao.deleteByUidAndTypeIdAndCommentId(uid,typeId,commentId);
    }

    /**
     * 批量插入
     */
    @Override
    public List<?> insert(ArrayList<DynamicsShow> list) {
        return userDynamicsDao.insert(list);
    }

    private void UpdateAssociateId(UserActionEnum actionEnum, int typeId,int commentId, String associateId)
    {
        switch (actionEnum.getAction()){
            case "love": // 追番 commentId 为追番表（gameAnimationsLove）的主键id
                otherService.updateLoveAssociateId(commentId,associateId);
                break;
            case "answer": // 问答回答
            case "comment": // 动画评论
                otherService.updateCommentAssociateId(commentId,associateId);
                break;
            case "question": // 提问 typeId 为问答id（questionId）
                otherService.updateQuestionAssociateId(typeId,associateId);
                break;

        }
    }

    /**
     * 更新用户积分
     *
     * 使用redis的方式会存在集群处理时没法锁定数据导致重复处理的问题
     * 可以使用消息队列的方式处理（已写kafka demo）
     */
    // @Scheduled(cron = "0/10 * * * * *")
    public void updateScore() {
        // System.out.println(DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));

        // @Scheduled 会等待上次完成在执行，所以不用这么写
        /*if (a.get() == 1) {
            return;
        }
        a.set(1);*/

        List<String> content = redisService.lrange(CACHE_SCORE_LIST, 0, 50);
        if (content.size() > 0) {
            try {
                accountMapper.updateScore(content);
                redisService.ltrim(CACHE_SCORE_LIST, content.size(), -1);
            } catch (Exception e) {
                log.error(JSON.toJSONString(content), e);
            }
        }
        // 清掉上两天的缓存
        /*if(DateUtils.getFragmentInHours(new Date(), Calendar.DATE) == 0L
        && DateUtils.getFragmentInMinutes(new Date(), Calendar.HOUR_OF_DAY) == 1L){
            String  key = StringUtils.join(CACHE_LOGIN_TODAY, CACHE_SCORE_LIST_SPLIT, DateFormatUtils.format(DateUtils.addDays(new Date(), -2), "yyyyMMdd"));
            IStringRedisClient client = Caches.String.STANDALONE.getClient();
            client.del(key);
        }*/
        // a.getAndSet(0);
    }
}
