package com.lin.karley.Service;

import com.lin.karley.Pojo.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public interface UserService {
    String register(User user);

    String login(String username, String password, boolean remember, HttpSession session);

    User selectByUsername(String username);
}
