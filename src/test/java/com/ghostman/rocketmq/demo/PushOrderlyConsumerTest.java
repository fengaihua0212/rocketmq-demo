package com.ghostman.rocketmq.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ValueOperations;


/**
 * @author fengaihua
 * @project rocketmq-demo
 * @package com.ghostman.rocketmq.demo
 * @date 2020/5/8 11:06
 * @description
 */
@SpringBootTest
class PushOrderlyConsumerTest {
    @Autowired
    private ValueOperations<String, String> valueOperations;

    @Test
    public void test() {
        System.out.println(valueOperations.get("nb-msg-counter12"));
        valueOperations.increment("nb-msg-counter12", 1);
        System.out.println(valueOperations.get("nb-msg-counter12"));
    }
}