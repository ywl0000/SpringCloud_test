package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService{


    @Override
    public String getPaymentIfo_Ok(Integer id) {
        return "调用客户端服务失败..................";
    }

    @Override
    public String getPaymentInfo_TimeOut(Integer id) {
        return "调用服务超时..........";
    }
}
