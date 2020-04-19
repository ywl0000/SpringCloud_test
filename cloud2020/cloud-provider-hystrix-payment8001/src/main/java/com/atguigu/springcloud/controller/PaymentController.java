package com.atguigu.springcloud.controller;


import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping(value = "/payment/hystrix/ok/{id}")
    public String getPaymentIfo_ok(@PathVariable(value = "id") Integer id){
       return paymentService.paymentInfo_ok(id);
    }

    @GetMapping(value = "/payment/hystrix/timeout/{id}")
    public String getPaymentInfo_TimeOut(@PathVariable(value = "id") Integer id){
        return paymentService.paymentIfo_TimeOut(id);
    }

}
