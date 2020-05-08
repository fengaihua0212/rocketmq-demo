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
//@RocketMQMessageListener(topic = "test-topic-2", consumerGroup = "sync-consumer-group2")
public class SpringConsumer implements RocketMQListener<OrderPaidEvent> {

    @Override
    public void onMessage(OrderPaidEvent message) {
        log.info("received OrderPaidEvent message: {}", message);
    }
}
