package com.atguigu.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    public String paymentInfo_ok(Integer id){
        return "线程池:"+Thread.currentThread().getName()+"paymentIfo_ok:"+id;
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",
        commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds"
                    ,value = "5000")
        }
    )
    public String paymentIfo_TimeOut(Integer id){
        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程:"+Thread.currentThread().getName()+"paymentIfo_ok_TimeOut:"+id+"服务处理成功";
    }

    public String paymentInfo_TimeOutHandler(Integer id){
        return  "服务处理失败或者超时,请稍后再试!"+id;
    }
}
