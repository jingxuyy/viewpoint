package com.xu.viewpoint.service.websocket;

import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.util.StringUtils;
import com.xu.viewpoint.dao.domain.Danmu;
import com.xu.viewpoint.dao.domain.constant.UserMomentsConstant;
import com.xu.viewpoint.service.DanmuService;
import com.xu.viewpoint.service.util.RocketMQUtil;
import com.xu.viewpoint.service.util.TokenUtil;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: xuJing
 * @date: 2024/12/6 14:36
 */
@Component
@ServerEndpoint("/imserver/{token}")
public class WebSocketService {

    // log
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 在线人数（客户端） 统计
    private static final AtomicInteger ONLINE_COUNT = new AtomicInteger(0);

    // 存放不同客户端的webSocket连接
    public static final ConcurrentHashMap<String, WebSocketService> WEBSOCKET_MAP = new ConcurrentHashMap<>();

    // 用于通信
    private Session session;

    private String sessionId;

    private Long userId;


    /**
     * Websocket是多例，因此不能直接使用@Autowired注入，必须先声明成静态，
     * 然后使用set方法注入
     */
//    private static RedisTemplate<String, String> redisTemplate;
//    @Autowired
//    public  void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
//        WebSocketService.redisTemplate = redisTemplate;
//    }

    private static DanmuService danmuService;

    @Autowired
    public void setDanmuService(DanmuService danmuService) {
        WebSocketService.danmuService = danmuService;
    }


    private static DefaultMQProducer danmusProducer;

    @Resource(name = "danmusProducer")
    public void setDanmusProducer(DefaultMQProducer danmusProducer) {
        WebSocketService.danmusProducer = danmusProducer;
    }


    private static DefaultMQProducer danmusSaveProducer;

    @Resource(name = "danmusSaveProducer")
    public void setDanmusSaveProducer(DefaultMQProducer danmusSaveProducer) {
        WebSocketService.danmusSaveProducer = danmusSaveProducer;
    }

    /**
     * 客户端发起连接，调用
     * @param session
     */
    @OnOpen
    public void openConnection(Session session, @PathParam("token") String token){
        try {
            this.userId = TokenUtil.verifyToken(token);
        }catch (Exception e){

        }

        this.session = session;
        this.sessionId = session.getId();

        if(WEBSOCKET_MAP.containsKey(sessionId)){
            // 判断是否是已经存在的连接，是，则更新一下（删除旧的+存储新的）
            WEBSOCKET_MAP.remove(sessionId);
            WEBSOCKET_MAP.put(sessionId, this);
        }else {
            // 是新连接
            WEBSOCKET_MAP.put(sessionId, this);
            // 新连接，增加连接次数
            ONLINE_COUNT.getAndIncrement();
        }

        logger.info("用户连接成功：" + sessionId + "，当前在线人数为：" + ONLINE_COUNT.get());

        try {
            // 连接成功
            this.sendMessage("0");
        }catch (Exception e){
            // 连接失败
            logger.error("连接异常");
        }
    }

    /**
     * 客户端或者服务端关闭连接调用
     */
    @OnClose
    public void closeConnection(){
        if(WEBSOCKET_MAP.containsKey(sessionId)){
            WEBSOCKET_MAP.remove(sessionId);
            ONLINE_COUNT.getAndDecrement();
        }
        logger.info("用户退出：" + sessionId + "，当前在线人数为：" + ONLINE_COUNT.get());
    }

    /**
     * 客户端向服务端发送消息调用
     * @param message
     */
    @OnMessage
    public void onMessage(String message){
        // 客户端向服务端发送了弹幕，服务端需要将弹幕推送到所有在线的用户
        logger.info("用户信息：" + sessionId + "，报文：" + message);
        // 1. 判断消息是否为空
        if(!StringUtils.isNullOrEmpty(message)){
            try {

                // 2. 不为空，将消息群发到所有在线用户
                for (Map.Entry<String, WebSocketService> entry : WEBSOCKET_MAP.entrySet()) {
                    // 2.1 遍历WEBSOCKET_MAP获取连接对象
                    WebSocketService webSocketService = entry.getValue();

                    // **** 高并发优化 ****
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("message", message);
                    jsonObject.put("sessionId", webSocketService.getSessionId());
                    Message msg = new Message(UserMomentsConstant.TOPIC_MOMENTS, jsonObject.toString().getBytes());
                    RocketMQUtil.asyncSendMsg(danmusProducer, msg);
                    // ********

                    // 2.2 若连接是打开状态，则发送消息
//                    if (webSocketService.session.isOpen()) {
//                        webSocketService.sendMessage(message);
//                    }
                }

                if (this.userId != null){
                    // 3. 将弹幕存入数据库，前端不会传递userId
                    Danmu danmu = JSONObject.parseObject(message, Danmu.class);
                    danmu.setUserId(userId);
                    danmu.setCreateTime(new Date());

                    // **** 高并发优化 ****
                    danmuService.asyncAddDanmu(danmu);
                    Message saveMsg = new Message(UserMomentsConstant.TOPIC_MOMENTS, JSONObject.toJSONBytes(danmu));
                    RocketMQUtil.asyncSendMsg(danmusSaveProducer, saveMsg);
                    // ********



                    // danmuService.addDanmu(danmu);

                    // 4. 保存弹幕到redis
                    danmuService.addDanmuToRedis(danmu);
                }
            }catch (Exception e){
                logger.error("弹幕解析出现问题！");
                e.printStackTrace();
            }
        }

    }

    /**
     * 出错调用
     * @param error
     */
    @OnError
    public void onError(Throwable error){

    }


    /**
     * 向客户端发送消息
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 定时任务，每过5s钟，给每个连接发送在线人数
     * @throws IOException
     */
    @Scheduled(fixedRate = 5000)
    private void noticeOnlineCount() throws IOException {
        for (Map.Entry<String, WebSocketService> entry : WEBSOCKET_MAP.entrySet()) {
            WebSocketService webSocketService = entry.getValue();
            if(webSocketService.session.isOpen()){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("onlineCount", ONLINE_COUNT.get());
                jsonObject.put("msg", "当前在线人数为" + ONLINE_COUNT.get());
                webSocketService.sendMessage(jsonObject.toJSONString());
            }
        }
    }


    public Session getSession() {
        return session;
    }

    public String getSessionId() {
        return sessionId;
    }
}
