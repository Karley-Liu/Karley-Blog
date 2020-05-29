package com.lin.karley.RabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Description Description
 * @Author Karley LIn
 * @Date Created in 2020/5/26
 */
@Component
@RabbitListener(queues = "queue")
public class Receiver {

    @RabbitHandler
    public void process(String hello){
        System.out.println("Receiver:"+hello);
    }
}
