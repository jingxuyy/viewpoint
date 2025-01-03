package com.xu.viewpoint.service.util;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.CountDownLatch2;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/**
 * @author: xuJing
 * @date: 2024/11/24 19:41
 */

public class RocketMQUtil {


    /**
     * 同步无返回发送消息
     * @param producer
     * @param msg
     */
    public static void syncSendMsg(DefaultMQProducer producer, Message msg) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        SendResult result = producer.send(msg);
        System.out.println(result);
    }


    /**
     * 异步发送消息
     * @param producer
     * @param msg
     */
    public static void asyncSendMsg(DefaultMQProducer producer, Message msg) throws RemotingException, InterruptedException, MQClientException {
        int messageCount = 2;
        CountDownLatch2 countDownLatch = new CountDownLatch2(messageCount);
        for (int i = 0; i < messageCount; i++) {
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    countDownLatch.countDown();
                    System.out.println(sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable throwable) {
                    countDownLatch.countDown();
                    System.out.println("生产者发送消息异常！");
                }
            });
        }
        countDownLatch.await(5, TimeUnit.SECONDS);
    }
}
