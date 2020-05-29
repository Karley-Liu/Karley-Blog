package com.lin.karley;

import com.lin.karley.RabbitMQ.Sender;
import com.lin.karley.Service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KarleyApplicationTests {

    @Autowired
    UserService userService;

    @Autowired
    private Sender sender;

    @Test
    void contextLoads() {
        System.out.println(userService.selectByUsername("karley"));
    }

    @Test
    public void hello() throws Exception{
        sender.send();
    }
}
