//package com.ghostman.rocketmq.demo;
//
//import org.apache.rocketmq.client.producer.SendCallback;
//import org.apache.rocketmq.client.producer.SendResult;
//import org.apache.rocketmq.spring.core.RocketMQTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.math.BigDecimal;
//
///**
// * @author fengaihua
// * @project rocketmq
// * @package com.ghostman.rocketmq.demo
// * @date 2020/4/28 17:09
// * @description
// */
//@Component
//public class Producer {
//
//    @Autowired
//    private RocketMQTemplate rocketMQTemplate;
//
//    @Scheduled(cron = "0 */1 * * * ?")
//    public void producer() {
//
////        for (int i = 0; i < 10; i++) {
////            rocketMQTemplate.convertAndSend("sync-topic", "I'm sync msg " + i);
////        }
//
//        //send message synchronously
//        rocketMQTemplate.convertAndSend("test-topic-1", "Hello, World!");
//
//        //send spring message
//        rocketMQTemplate.send("test-topic-1", MessageBuilder.withPayload("Hello, World! I'm from spring message").build());
//
//        //send messgae asynchronously
//        rocketMQTemplate.asyncSend("test-topic-2", new OrderPaidEvent("T_001", new BigDecimal("88.00")),
//                new SendCallback() {
//            @Override
//            public void onSuccess(SendResult sendResult) {
//                System.out.printf("async onSucess SendResult=%s %n", sendResult);
//            }
//
//            @Override
//            public void onException(Throwable e) {
//                System.out.printf("async onException Throwable=%s %n", e);
//            }
//        });
//
//        //Send messages orderly
//        rocketMQTemplate.syncSendOrderly("orderly_topic", MessageBuilder.withPayload("Hello, World").build(),"hashkey");
//    }
//}
