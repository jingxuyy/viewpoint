package com.xu.viewpoint.dao.domain.constant;

/**
 * @author: xuJing
 * @date: 2024/11/24 17:54
 */

public class UserMomentsConstant {

    /**
     * RocketMQ的分组名
     */
//    public static final String GROUP_MOMENTS = "DynamicMomentsGroup";
    public static final String GROUP_MOMENTS = "MomentsGroup";
//    public static final String GROUP_MOMENTS = "DynamicMomentsGroup";
//    public static final String GROUP_MOMENTS = "DynamicMomentsGroup";

    /**
     * 订阅名称
     */
    public static final String TOPIC_MOMENTS = "Topic-Moments";

    /**
     * 消息存储到redis的key的前缀
     */
    public static final String REDIS_SUBSCRIBED = "subscribed-";

    /**
     * 动态类型：视频
     */
    public static final String TYPE_VIDEO = "0";

    /**
     * 动态类型：图片
     */
    public static final String TYPE_IMG = "1";
}
