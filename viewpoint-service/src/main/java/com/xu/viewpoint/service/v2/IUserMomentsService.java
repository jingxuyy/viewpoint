package com.xu.viewpoint.service.v2;

import com.xu.viewpoint.dao.domain.PageResult;
import com.xu.viewpoint.dao.domain.UserMoment;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;

public interface IUserMomentsService {

    /**
     * 用户发布动态
     * @param userMoment
     */
    void addUserMoments(UserMoment userMoment) throws MQBrokerException, RemotingException, InterruptedException, MQClientException;


    /**
     * 根据userId获取当前用户所关注的用户发布的动态
     * @param userId
     */
    List<UserMoment> getUserSubscribedMoments(Long userId);

    /**
     * 分页查询用户的动态，可以根据动态类型type进行查询，若type为空，则默认查询所有类型动态
     * @param size 每页条目数
     * @param no  要查询的页数
     * @param userId 用户id
     * @param type  动态的类型  可以为空
     */
    PageResult<UserMoment> pageListMoments(Integer size, Integer no, Long userId, String type);
}
