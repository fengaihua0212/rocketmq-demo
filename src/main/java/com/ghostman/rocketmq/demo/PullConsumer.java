package com.ghostman.rocketmq.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * @author fengaihua
 * @project rocketmq
 * @package com.ghostman.rocketmq.demo
 * @date 2020/4/29 15:07
 * @description
 */
@Component
@Slf4j
public class PullConsumer implements ApplicationRunner {

    private static long start;

    @Autowired
    private DefaultLitePullConsumer consumer;

    @Autowired
    private ValueOperations<String, String> valueOperations;

    public void consumer(String message) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        valueOperations.decrement("nb-msg-counter", 1);
        log.info("consumer message: {}", message);
        if (Integer.valueOf(valueOperations.get("nb-msg-counter")) == 0) {
            log.info("total time: {}", System.currentTimeMillis() - start);
        }
    }

    @Bean
    public DefaultLitePullConsumer createConsumer() throws MQClientException {
        DefaultLitePullConsumer consumer = new DefaultLitePullConsumer("nb-consumer-group");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.start();
        return consumer;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        while (true) {
            String counter = valueOperations.get("nb-msg-counter");
            if (null == counter) {
                valueOperations.set("nb-msg-counter", "0");
            } else if (Integer.valueOf(counter) >= 64) {
                while (true) {
                    if (Integer.valueOf(valueOperations.get("nb-msg-counter")) < 64) {
                        break;
                    }
                }
            }
            if (Integer.valueOf(valueOperations.get("nb-msg-counter")) == 0) {
                start = System.currentTimeMillis();
            }
            Collection<MessageQueue> messageQueues = consumer.fetchMessageQueues("nb-topic");
            consumer.assign(messageQueues);
            consumer.setPullBatchSize(64 - Integer.valueOf(valueOperations.get("nb-msg-counter")));
            List<MessageExt> messageExtList = consumer.poll();
            valueOperations.increment("nb-consumer-group", messageExtList.size());
            log.info("messageExtListSize: {}",messageExtList.size());
            for (MessageExt messageExt : messageExtList) {
                new Thread(() -> {consumer(new String(messageExt.getBody()));}).start();
            }
        }
    }
}
