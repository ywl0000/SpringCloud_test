package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {


    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;


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

    @GetMapping(value = "/payment/discovery")
    public Object getService(){
        List<String> services = discoveryClient.getServices();
        for (String element : services
             ) {
            log.info("*********element:{}"+element);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance service :instances) {
            log.info(service.getInstanceId()+"\t"+service.getHost()+"\t"
                    +service.getPort()+"\t"+service.getUri());
        }
        return this.discoveryClient;
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentLB() {
        return serverPort;
    }


    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout() {
        try {
            // 暂停3秒钟
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }

    /**
     * 链路跟踪
     *
     * @return
     */
    @GetMapping(value = "/payment/zipkin")
    public String paymentZipkin() {
        return "hi,i'am paymentZipkin server fall back,welcome to atguigu,O(∩_∩)O哈哈~";
    }

}
