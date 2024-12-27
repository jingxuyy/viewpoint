package com.xu.viewpoint.service.config;

//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.mysql.cj.util.StringUtils;
//import com.xu.viewpoint.dao.domain.Danmu;
//import com.xu.viewpoint.dao.domain.UserFollowing;
//import com.xu.viewpoint.dao.domain.UserMoment;
//import com.xu.viewpoint.dao.domain.constant.UserMomentsConstant;
//import com.xu.viewpoint.service.DanmuService;
//import com.xu.viewpoint.service.UserFollowingService;
//import com.xu.viewpoint.service.websocket.WebSocketService;
//import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
//import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
//import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
//import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
//import org.apache.rocketmq.client.exception.MQClientException;
//import org.apache.rocketmq.client.producer.DefaultMQProducer;
//import org.apache.rocketmq.common.message.MessageExt;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.core.RedisTemplate;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author: xuJing
// * @date: 2024/11/24 17:47
// */
//@Configuration
//public class RocketMQConfig {
//
//    @Value("${rocketmq.name.server.address}")
//    private String nameServerAddr;
//
//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;
//
//    @Autowired
//    private UserFollowingService userFollowingService;
//
//    @Autowired
//    private DanmuService danmuService;
//
//    /**
//     * 用户动态生产者
//     */
//    @Bean("momentsProducer")
//    public DefaultMQProducer momentsProducer() throws MQClientException {
//        DefaultMQProducer producer = new DefaultMQProducer(UserMomentsConstant.GROUP_MOMENTS);
//        producer.setNamesrvAddr(nameServerAddr);
//        producer.start();
//        return producer;
//    }
//
//
//    /**
//     * 用户动态消费者
//     */
//    @Bean("momentsConsumer")
//    public DefaultMQPushConsumer momentsConsumer() throws MQClientException {
//        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(UserMomentsConstant.GROUP_MOMENTS);
//        consumer.setNamesrvAddr(nameServerAddr);
//        consumer.subscribe(UserMomentsConstant.TOPIC_MOMENTS, "*");
//        consumer.registerMessageListener(new MessageListenerConcurrently() {
//            @Override
//            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
//
//                // 1. 获取消息
//                MessageExt msg = list.get(0);
//                if(msg == null){
//                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//                }
//
//                // 2. 将消息转换成UserMoment对象
//                String msgStr = new String(msg.getBody());
//                UserMoment userMoment = JSONObject.toJavaObject(JSONObject.parseObject(msgStr), UserMoment.class);
//
//                // 3. 获取当前用户的所有粉丝
//                Long userId = userMoment.getUserId();
//                List<UserFollowing> userFans = userFollowingService.getUserFans(userId);
//
//                // 4. 给每个粉丝都推送消息
//                for (UserFollowing fan : userFans) {
//
//                    // 4.1 从redis中获取当前粉丝的订阅
//                    String key = "subscribed-" + fan.getUserId();
//                    String subscribedListStr = redisTemplate.opsForValue().get(key);
//                    List<UserMoment> subscribedList;
//
//                    // 4.2 将订阅的消息转换成列表，若订阅没有消息，则申明一个空列表
//                    if(StringUtils.isNullOrEmpty(subscribedListStr)){
//                        subscribedList = new ArrayList<>();
//                    }else {
//                        subscribedList = JSONArray.parseArray(subscribedListStr, UserMoment.class);
//                    }
//
//                    // 4.3 将消息添加到列表中，并把列表转成字符串存储到redis中
//                    subscribedList.add(userMoment);
//                    redisTemplate.opsForValue().set(key, JSONObject.toJSONString(subscribedList));
//                }
//                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//            }
//        });
//        consumer.start();
//        return consumer;
//    }
//
//
//
//
//    /**
//     * 用户弹幕生产者
//     */
//    @Bean("danmusProducer")
//    public DefaultMQProducer danmusProducer() throws MQClientException {
//        DefaultMQProducer producer = new DefaultMQProducer(UserMomentsConstant.GROUP_MOMENTS);
//        producer.setNamesrvAddr(nameServerAddr);
//        producer.start();
//        return producer;
//    }
//
//
//    /**
//     * 用户弹幕消费者
//     */
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
//
//
//
//
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
//
//
//    /**
//     * 用户弹幕消费者
//     */
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
//}
