package com.ghostman.rocketmq.demo;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fengaihua
 * @project rocketmq-demo
 * @package com.ghostman.rocketmq.demo
 * @date 2020/5/7 9:58
 * @description
 */
@RestController
public class MQController {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @PostMapping("/order")
    public String order(@RequestBody OrderPaidEvent event) {
        rocketMQTemplate.syncSendOrderly("nb-topic", event, "ordermsg");
        return "ok";
    }
}
