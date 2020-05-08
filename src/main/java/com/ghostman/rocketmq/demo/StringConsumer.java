package com.ghostman.rocketmq.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQListener;

/**
 * @author fengaihua
 * @project rocketmq
 * @package com.ghostman.rocketmq.demo
 * @date 2020/4/28 17:53
 * @description
 */
@Slf4j
//@Service
//@RocketMQMessageListener(topic = "test-topic-1", consumerGroup = "sync-consumer-group1")
public class StringConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        log.info("received message: {}", message);
    }
}
