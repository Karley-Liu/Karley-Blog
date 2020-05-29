package com.lin.karley.Config;

import com.lin.karley.Mapper.UserRoleMapper;
import com.lin.karley.Pojo.User;
import com.lin.karley.Pojo.UserRole;
import com.lin.karley.Service.UserService;
import com.lin.karley.Utils.PasswordUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description Description
 * @Author Karley LIn
 * @Date Created in 2020/5/20
 */
//自定义的UserRealm
public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    @Autowired
    PasswordUtil passwordUtil;

    @Autowired
    UserRoleMapper userRoleMapper;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        拿到当前登录用户的对象
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal();//取出user,取出object对象，强转成user
        int user_id = currentUser.getId();
        List<UserRole> userRoles = userRoleMapper.selectByUserId(user_id);
//        String role = null;
        Set<String> roles = new HashSet<>();
        for (int i=0;i<userRoles.size();i++){
            //        设置当前用户的权限
            String role = String.valueOf(userRoles.get(i).getRole());
            System.out.println(role);
            roles.add(role);
        }
        System.out.println(currentUser);

        info.setRoles(roles);
//        info.addStringPermission(String.valueOf(currentUser.getIsSuperuser()));
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行了认证");

        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;

        //连接真实数据库
        User user = userService.selectByUsername(userToken.getUsername());
        if (user == null){
            //没有这个人     抛出一个UnknownAccountException异常
            return null;
        }

        //密码认证shiro做
        //可以加密，盐值
//        将用户信息封装在AuthenticationInfo
        return new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
    }
}
