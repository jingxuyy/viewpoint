package com.xu.viewpoint.service.impl;

//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.xu.viewpoint.dao.UserMomentsDao;
//import com.xu.viewpoint.dao.domain.UserMoment;
//import com.xu.viewpoint.dao.domain.constant.UserMomentsConstant;
//import com.xu.viewpoint.service.UserMomentsService;
//import com.xu.viewpoint.service.util.RocketMQUtil;
//import org.apache.rocketmq.client.exception.MQBrokerException;
//import org.apache.rocketmq.client.exception.MQClientException;
//import org.apache.rocketmq.client.producer.DefaultMQProducer;
//import org.apache.rocketmq.common.message.Message;
//import org.apache.rocketmq.remoting.exception.RemotingException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.List;
//
///**
// * @author: xuJing
// * @date: 2024/11/25 9:24
// */
//@Service
//public class UserMomentsServiceImpl implements UserMomentsService {
//
//    @Autowired
//    private UserMomentsDao userMomentsDao;
//
//    @Autowired
//    private ApplicationContext applicationContext;
//
//    @Autowired
//    private DefaultMQProducer momentsProducer;
//
//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;
//
//
//    /**
//     * 用户发布动态
//     *
//     * @param userMoment
//     */
//    @Override
//    public void addUserMoments(UserMoment userMoment) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
//
//        // 1. 插入动态到数据库
//        userMoment.setCreateTime(new Date());
//        userMomentsDao.addUserMoments(userMoment);
//
//        // 2. 创建消息，将消息发送给rocketMQ的生产者发布消息
//        Message msg = new Message(UserMomentsConstant.TOPIC_MOMENTS, JSONObject.toJSONBytes(userMoment));
//        RocketMQUtil.syncSendMsg(momentsProducer, msg);
//    }
//
//    /**
//     * 查询当前用户订阅的所有动态
//     *
//     * @param userId
//     */
//    @Override
//    public List<UserMoment> getUserSubscribedMoments(Long userId) {
//        String key = "subscribed-" + userId;
//        String msg = redisTemplate.opsForValue().get(key);
//        List<UserMoment> userMoments = JSONArray.parseArray(msg, UserMoment.class);
//
//        return userMoments;
//    }
//}
