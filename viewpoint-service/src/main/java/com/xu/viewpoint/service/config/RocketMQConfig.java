package com.xu.viewpoint.service.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.util.StringUtils;
import com.xu.viewpoint.dao.domain.Danmu;
import com.xu.viewpoint.dao.domain.UserFollowing;
import com.xu.viewpoint.dao.domain.UserMoment;
import com.xu.viewpoint.dao.domain.constant.UserMomentsConstant;
//import com.xu.viewpoint.service.DanmuService;
//import com.xu.viewpoint.service.UserFollowingService;
//import com.xu.viewpoint.service.websocket.WebSocketService;
import com.xu.viewpoint.service.v2.impl.UserFollowingService;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;



/**
 * @author: xuJing
 * @date: 2024/11/24 17:47
 */
@Configuration
public class RocketMQConfig {

    @Value("${rocketmq.name.server.address}")
    private String nameServerAddr;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserFollowingService userFollowingService;

//    @Autowired
//    private DanmuService danmuService;

    /**
     * 用户动态生产者
     */
    @Bean("momentsProducer")
    public DefaultMQProducer momentsProducer() throws MQClientException {

        // 创建默认生产者，设置生产者组名和RocketMQ服务地址
        DefaultMQProducer producer = new DefaultMQProducer(UserMomentsConstant.GROUP_MOMENTS);
        producer.setNamesrvAddr(nameServerAddr);

        // 启动生产者
        producer.start();

        // 返回生产者对象
        return producer;
    }


    /**
     * 用户动态消费者
     * 整个生产消费：生产者将消息发送到RocketMQ中，然后消费者从RocketMQ拿到消息，消费
     * 消费者过程：将消息转换成UserMoment实体
     * 并获得当前消息生产者的用户id
     * 根据id查询当前用户的所有粉丝用户
     * 依次获取粉丝用户的id，根据id在redis中查询存储粉丝用户消息存放的数据的集合
     * 取出历史消息，再将当前消息放入到消息集合中
     * 再将集合存储到redis中，供用户获取
     */
    @Bean("momentsConsumer")
    public DefaultMQPushConsumer momentsConsumer() throws MQClientException {

        // 创建消费者，并设置消费者组名（消费者组名要和生产者组名相同，才能正确接收生产者消费）和 RocketMQ服务地址
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(UserMomentsConstant.GROUP_MOMENTS);
        consumer.setNamesrvAddr(nameServerAddr);
        // 订阅消息主题和标签消息
        consumer.subscribe(UserMomentsConstant.TOPIC_MOMENTS, "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

                // 1. 获取消息
                MessageExt msg = list.get(0);
                if(msg == null){
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }

                // 2. 将消息转换成UserMoment对象
                String msgStr = new String(msg.getBody());
                UserMoment userMoment = JSONObject.toJavaObject(JSONObject.parseObject(msgStr), UserMoment.class);

                // 3. 获取当前用户的所有粉丝
                Long userId = userMoment.getUserId();
                List<UserFollowing> userFans = userFollowingService.getUserFans(userId);

                // 4. 给每个粉丝都推送消息
                for (UserFollowing fan : userFans) {

                    // 4.1 从redis中获取当前粉丝的订阅
                    // 用户订阅的信息都存储在redis中，存储的key是固定值：前缀+当前用户id  值：是List集合，集合的元素是UserMoment类型，为了方便存储到redis中，将list集合转成了字符串在上传到redis中
                    // 因此每个用户在redis中 key = 前缀+当前用户id 其value存储了当前用户关注的各个用户的所有消息
                    String key = UserMomentsConstant.REDIS_SUBSCRIBED + fan.getUserId();
                    String subscribedListStr = redisTemplate.opsForValue().get(key);
                    List<UserMoment> subscribedList;

                    // 4.2 将订阅的消息转换成列表，若订阅没有消息，则申明一个空列表
                    if(StringUtils.isNullOrEmpty(subscribedListStr)){
                        subscribedList = new ArrayList<>();
                    }else {
                        subscribedList = JSONArray.parseArray(subscribedListStr, UserMoment.class);
                    }

                    // 4.3 将消息添加到列表中，并把列表转成字符串存储到redis中
                    subscribedList.add(userMoment);
                    redisTemplate.opsForValue().set(key, JSONObject.toJSONString(subscribedList));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        return consumer;
    }




    /**
     * 用户弹幕生产者
     */
//    @Bean("danmusProducer")
//    public DefaultMQProducer danmusProducer() throws MQClientException {
//        DefaultMQProducer producer = new DefaultMQProducer(UserMomentsConstant.GROUP_MOMENTS);
//        producer.setNamesrvAddr(nameServerAddr);
//        producer.start();
//        return producer;
//    }


    /**
     * 用户弹幕消费者
     */
//    @Bean("danmusConsumer")
//    public DefaultMQPushConsumer danmusConsumer() throws MQClientException {
//        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(UserMomentsConstant.GROUP_MOMENTS);
//        consumer.setNamesrvAddr(nameServerAddr);
//        consumer.subscribe(UserMomentsConstant.TOPIC_MOMENTS, "*");
//        consumer.registerMessageListener(new MessageListenerConcurrently() {
//            @Override
//            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
//
//                // 1. 获取消息
//                MessageExt msg = list.get(0);
//
//                // 2. 将消息转换成JSONObject对象
//                String msgStr = new String(msg.getBody());
//                JSONObject jsonObject = JSONObject.parseObject(msgStr);
//                // 3. 获取sessionId和弹幕信息
//                String sessionId = jsonObject.getString("sessionId");
//                String message = jsonObject.getString("message");
//                // 4. 根据sessionId获取当前发送弹幕的连接
//                WebSocketService webSocketService = WebSocketService.WEBSOCKET_MAP.get(sessionId);
//
//                // 5. 开始消费，发送消息到客户端
//                if(webSocketService.getSession().isOpen()){
//                    try {
//                        webSocketService.sendMessage(message);
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//
//                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//            }
//        });
//        consumer.start();
//        return consumer;
//    }




//    /**
//     * 用户弹幕生产者
//     */
//    @Bean("danmusSaveProducer")
//    public DefaultMQProducer danmusSaveProducer() throws MQClientException {
//        DefaultMQProducer producer = new DefaultMQProducer(UserMomentsConstant.GROUP_MOMENTS);
//        producer.setNamesrvAddr(nameServerAddr);
//        producer.start();
//        return producer;
//    }


    /**
     * 用户弹幕消费者
     */
//    @Bean("danmusSaveConsumer")
//    public DefaultMQPushConsumer danmusSaveConsumer() throws MQClientException {
//        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(UserMomentsConstant.GROUP_MOMENTS);
//        consumer.setNamesrvAddr(nameServerAddr);
//        consumer.subscribe(UserMomentsConstant.TOPIC_MOMENTS, "*");
//        consumer.registerMessageListener(new MessageListenerConcurrently() {
//            @Override
//            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
//
//                // 1. 获取消息
//                MessageExt msg = list.get(0);
//
//                // 2. 将消息转换成Danmu对象
//                String msgStr = new String(msg.getBody());
//                Danmu danmu = JSONObject.parseObject(msgStr, Danmu.class);
//                danmuService.asyncAddDanmu(danmu);
//                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//            }
//        });
//        consumer.start();
//        return consumer;
//    }
}
