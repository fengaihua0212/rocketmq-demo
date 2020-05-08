package com.ghostman.rocketmq.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @author fengaihua
 * @project rocketmq
 * @package com.ghostman.rocketmq.demo
 * @date 2020/4/28 17:53
 * @description
 */
@Slf4j
//@Service
//@RocketMQMessageListener(topic = "nb-topic", consumerGroup = "nb-consumer-group", consumeMode = ConsumeMode.ORDERLY)
public class PushOrderlyConsumer implements RocketMQListener<OrderPaidEvent> {
    private static int count = 0;

    private static long start;

    @PostConstruct
    public void init() {
        valueOperations.set("nb-msg-counter", "0");
    }


    @Autowired
    private ValueOperations<String, String> valueOperations;

    @Override
    public void onMessage(OrderPaidEvent message) {
        String counter = valueOperations.get("nb-msg-counter");
        if (null == counter || Integer.valueOf(counter) < 0) {
            valueOperations.set("nb-msg-counter", "0", 60, TimeUnit.SECONDS);
        } else if (Integer.valueOf(counter) >= 64) {
            while (true) {
                log.info("wating ");
                if (Integer.valueOf(valueOperations.get("nb-msg-counter")) < 64) {
                    break;
                }
            }
        }

        if (Integer.valueOf(valueOperations.get("nb-msg-counter")) == 0) {
            start = System.currentTimeMillis();
        }
        valueOperations.increment("nb-msg-counter", 1);
        count++;
        log.info("message {}", count);
        new Thread(() -> {consumer(message);}).start();
    }

    public void consumer(OrderPaidEvent message) {
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
}
