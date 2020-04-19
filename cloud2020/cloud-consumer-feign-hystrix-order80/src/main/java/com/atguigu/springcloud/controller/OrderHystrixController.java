package com.atguigu.springcloud.controller;


import com.atguigu.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@DefaultProperties(defaultFallback = "payment_Global_Fallback")
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping(value = "/consumer/payment/hystrix/ok/{id}")

    public String getPaymentIfo(@PathVariable(value = "id") Integer id){
        return paymentHystrixService.getPaymentIfo_Ok(id);
    }

    @GetMapping(value = "/consumer/payment/hystrix/timeout/{id}")
   /* @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",
            commandProperties = {@HystrixProperty(
                    name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")
            }
    )*/
    @HystrixCommand
    public String getPaymentIfo_TimeOut(@PathVariable(value = "id") Integer id){
        return paymentHystrixService.getPaymentInfo_TimeOut(id);
    }

    public String paymentInfo_TimeOutHandler(Integer id){
        return  "调用远程服务失败,或者出错,请稍后再试.........";
    }

    public String payment_Global_Fallback(){
        return "服务出错了，请稍后再试..........";
    }
}
