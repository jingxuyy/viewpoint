package com.xu.viewpoint.service.v2.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xu.viewpoint.dao.domain.*;
import com.xu.viewpoint.dao.domain.constant.UserConstant;
import com.xu.viewpoint.dao.domain.constant.UserMomentsConstant;
import com.xu.viewpoint.dao.v2.UserMomentsDao;
import com.xu.viewpoint.service.util.RocketMQUtil;
import com.xu.viewpoint.service.v2.IUserMomentsService;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: xuJing
 * @date: 2025/1/2 15:37
 */
@Service
public class UserMomentsService implements IUserMomentsService {


    @Autowired
    private UserMomentsDao userMomentsDao;


    @Autowired
    private DefaultMQProducer momentsProducer;


    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Autowired
    private UserService userService;


    /**
     * 用户发布动态
     *
     * @param userMoment
     */
    @Override
    public void addUserMoments(UserMoment userMoment) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {

        // 1. 将动态常规信息插入到数据库t_user_moments中
        userMoment.setCreateTime(new Date());
        userMomentsDao.addUserMoments(userMoment);

        // 2. 创建消息，将消息发送给rocketMQ的生产者，主要是通过生产者提示消费者当前用户发布了动态
        Message msg = new Message(UserMomentsConstant.TOPIC_MOMENTS, JSONObject.toJSONBytes(userMoment));
        RocketMQUtil.syncSendMsg(momentsProducer, msg);
    }


    /**
     * 根据userId获取当前用户所关注的用户发布的动态
     *
     * @param userId
     */
    @Override
    public List<UserMoment> getUserSubscribedMoments(Long userId) {

        // 构建key
        String key = UserMomentsConstant.REDIS_SUBSCRIBED + userId;
        // 从redis中查询消息集合
        String momentsStr = redisTemplate.opsForValue().get(key);
        // 解析返回
        return JSONArray.parseArray(momentsStr, UserMoment.class);
    }


    /**
     * 分页查询用户的动态，可以根据动态类型type进行查询，若type为空，则默认查询所有类型动态
     *
     * @param size   每页条目数
     * @param no     要查询的页数
     * @param userId 用户id
     * @param type   动态的类型  可以为空
     */
    @Override
    public PageResult<UserMoment> pageListMoments(Integer size, Integer no, Long userId, String type) {

        // 构建查询参数
        Map<String, Object> params = new HashMap<>();
        params.put("start", (no-1)*size);
        params.put("limit", size);
        params.put("userId", userId);
        params.put("type", type);

        // 查询符合条件的动态总数
        // 1. 首先需要根据userId从数据库t_user_following查询当前用户关注信息，获取关注的用户的id
        // 2. 再根据 上一步查询到的关注的用户的id的集合，从数据库t_user_moments中查询这些用户发布的动态信息
        // 3. 如果type不为空则，需要加条件查询
        Integer total = userMomentsDao.pageCountMoments(params);

        // 创建空的集合用来存储分页动态信息
        List<UserMoment> list = new ArrayList<>();

        if(total > 0){
            // 动态总数大于0，在数据库中查询动态信息，注意，需要查询动态的详细信息，也就是动态内容
            list = userMomentsDao.pageListMoments(params);

            if(!list.isEmpty()){
                //处理不同类型的动态
                this.processVideoMoment(list.stream()
                        .filter(item -> UserMomentsConstant.TYPE_VIDEO
                                .equals(item.getType())).collect(Collectors.toList()));
                this.processImgMoment(list.stream()
                        .filter(item -> UserMomentsConstant.TYPE_IMG
                                .equals(item.getType())).collect(Collectors.toList()));
                //匹配对应用户信息
                Set<Long> userIdSet = list.stream()
                        .map(UserMoment :: getUserId).collect(Collectors.toSet());
                List<UserInfo> userInfoList = userService.getUserInfoByUserIds(userIdSet);
                list.forEach(moment -> userInfoList.forEach(userInfo -> {
                    if(moment.getUserId().equals(userInfo.getUserId())){
                        moment.setUserInfo(userInfo);
                    }
                }));
            }
        }
        return new PageResult<>(total, list);
    }







  //    --------------------------------------private---------------------------------

    private void processImgMoment(List<UserMoment> list) {
        list.forEach(moment -> {
            Content content = moment.getContent();
            ImgContent contentDetail = content.getContentDetail().toJavaObject(ImgContent.class);
//            contentDetail.setImg(fastdfsUrl + contentDetail.getImg());
            content.setContentDetail(JSONObject.parseObject(JSONObject.toJSONString(contentDetail)));
            moment.setContent(content);
        });
    }

    private void processVideoMoment(List<UserMoment> list) {
        List<Video> videoList = list.stream()
                .map(UserMoment::getContent)
                .map(content -> content.getContentDetail().toJavaObject(Video.class))
                .collect(Collectors.toList());
//        List<Video> newVideoList = videoService.getVideoCount(videoList);
//        newVideoList.forEach(video -> video.setThumbnail(fastdfsUrl+video.getThumbnail()));
//        list.forEach(moment -> newVideoList.forEach(video ->{
//            if(video.getId().equals(moment.getContent().getContentDetail().getLong("id"))){
//                JSONObject contentDetail = JSONObject.parseObject(JSONObject.toJSONString(video));
//                moment.getContent().setContentDetail(contentDetail);
//            }
//        }));
    }
}
