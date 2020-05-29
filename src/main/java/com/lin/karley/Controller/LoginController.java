package com.lin.karley.Controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.lin.karley.Pojo.User;
import com.lin.karley.Service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.util.HashMap;


@Controller
public class LoginController {
    @Autowired(required = true)
    private UserService userService;

    @GetMapping(value = "/login")
    public String login(){return "module/login";}

    @ResponseBody
    @PostMapping(value = "/shirologin")
    public String loginCheck(String username, String password, boolean remember, HttpSession session){
        String login = userService.login(username,password,remember,session);
        HashMap<String, String> map = new HashMap<>();
        map.put("res",login);
        String jsonString = JSONUtils.toJSONString(map);
        System.out.println(jsonString);
        return jsonString;
    }

    @GetMapping("/register")
    public String register(){return "module/register";}

    @ResponseBody
    @PostMapping(value = "/register")
    public String registerCheck(User user)
    {
        String register = userService.register(user);
        HashMap<String, String> map = new HashMap<>();
        map.put("res",register);
        System.out.println(JSONUtils.toJSONString(map));
        return JSONUtils.toJSONString(map);
    }

    @GetMapping("/noauth")
    @ResponseBody
    public String uUnauthorized(){
        return "未经授权无法访问此页面";
    }

//    @ResponseBody
    @RequiresUser
    @GetMapping("/logout")
    public String logout(){
//        ModelAndView mv = new ModelAndView();
        Subject user = SecurityUtils.getSubject();
        user.logout();
//        mv.setViewName("redirect:/back_notes");
        return "redirect:/back_notes";
    }
}
