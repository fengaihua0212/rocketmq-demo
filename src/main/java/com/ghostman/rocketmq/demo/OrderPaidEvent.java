package com.ghostman.rocketmq.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author fengaihua
 * @project rocketmq
 * @package com.ghostman.rocketmq.demo
 * @date 2020/4/29 10:58
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPaidEvent implements Serializable {
    private String orderId;

    private BigDecimal paidMoney;
}
