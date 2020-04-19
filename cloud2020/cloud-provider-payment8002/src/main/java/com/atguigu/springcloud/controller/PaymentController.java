package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {


    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;


    @PostMapping(value = "/payment/create")
    public CommonResult createPayment(@RequestBody Payment payment){
        int result = paymentService.create(payment);

        if(result>0){
            return new CommonResult(200,"插入成功,serverPort:"+serverPort,result);
        }else{
            return new CommonResult(500,"插入数库失败");
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        try {
            Thread.sleep(6000);
        }catch (Exception e){
            log.info("请求失败了");
        }

        log.info("id:{}",id);
        Payment payment = paymentService.getPaymentById(id);

        if(null!=payment){
            return new CommonResult(200,"查询成功,serverPort:"+serverPort,payment);
        }else {
            return new CommonResult(500,"没有对应的纪录");
        }
    }

}
