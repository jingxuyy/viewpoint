package com.xu.viewpoint.service;

import com.xu.viewpoint.dao.domain.UserMoment;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;

public interface UserMomentsService {

    /**
     * 用户发布动态
     * @param userMoment
     */
    void addUserMoments(UserMoment userMoment) throws MQBrokerException, RemotingException, InterruptedException, MQClientException;

    /**
     * 查询当前用户订阅的所有动态
     * @param userId
     */
    List<UserMoment> getUserSubscribedMoments(Long userId);
}
