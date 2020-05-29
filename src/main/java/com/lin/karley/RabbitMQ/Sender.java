package com.lin.karley.RabbitMQ;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description Description
 * @Author Karley LIn
 * @Date Created in 2020/5/26
 */
@Component
public class Sender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(){
        String text = "queue"+ new Date();
        System.out.println("Sender:"+text);
        amqpTemplate.convertAndSend("queue",text);
    }
}
