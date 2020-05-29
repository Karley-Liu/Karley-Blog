package com.lin.karley.Service.impl;

/**
 * @Description Description
 * @Author Karley LIn
 * @Date Created in 2020/5/19
 */


import com.lin.karley.Common.ResponseCode;
import com.lin.karley.Mapper.UserRoleMapper;
import com.lin.karley.Pojo.User;

import com.lin.karley.Mapper.UserMapper;
import com.lin.karley.Pojo.UserRole;
import com.lin.karley.Service.UserService;
import com.lin.karley.Utils.PasswordUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.*;

//@Component
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    PasswordUtil passwordUtil;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Transactional
    public String register(User user) {
        if (user.getUsername().equals("karley")) {
            user.setIsactive(1);
        } else {
            user.setIsactive(0);
        }
        String password = passwordUtil.encrypt(user.getPassword());
        user.setPassword(password);
        user.setCreatetime(new Date());
        int res = userMapper.insert(user);

        int id = userRoleMapper.selectByUsername(user.getUsername());
        UserRole userRole = new UserRole();
        userRole.setUserId(id);
        userRole.setRole(0);
        userRoleMapper.insert(userRole);

        return ResponseCode.getMsgByStatus(res);
    }

    public String login(String username, String password, boolean remember,HttpSession session) {
        //获取当前的用户
        Subject subject = SecurityUtils.getSubject();

        //封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(username,password,remember);

        try {
            subject.login(token);   //登录，如果没有异常说明ok
//            Subject currentSubject = SecurityUtils.getSubject();
            User user = (User) subject.getPrincipal();
//            Session session = currentSubject.getSession();
            session.setAttribute("user",user);
            System.out.println(user);
//            执行成功操作
            System.out.println(ResponseCode.getMsgByStatus(3));
            return ResponseCode.getMsgByStatus(3);
        } catch (UnknownAccountException e) {   //用户名不存在
//            e.printStackTrace();    //执行操作
            System.out.println(ResponseCode.getMsgByStatus(4));
            return ResponseCode.getMsgByStatus(4);
        }catch (IncorrectCredentialsException e){   //密码不存在
//            e.printStackTrace();
            System.out.println(ResponseCode.getMsgByStatus(2));
            return ResponseCode.getMsgByStatus(2);
        }
    }

    @Override
    public User selectByUsername(String username) {
        return this.userMapper.selectByUsername(username);
    }

}

