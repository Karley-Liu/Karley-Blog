package com.lin.karley.Utils;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.stereotype.Component;

/**
 * @Description Description
 * @Author Karley LIn
 * @Date Created in 2020/5/20
 */
@Component
public class PasswordUtil extends SimpleCredentialsMatcher {

//    @Override
    public boolean doCrendentialsMatch(AuthenticationToken authToken, AuthenticationInfo info){
        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        Object tokenCredentials = encrypt(String.valueOf(token.getPassword()));
        Object accountCredentials = getCredentials(info);
        //将密码加密与系统加密后的密码校验，内容一致返回true，不一致返回false
        return equals(tokenCredentials,accountCredentials);
    }

    //将传进来的密码加密
    public String encrypt(String password) {
        String sha256Hash = new Sha256Hash(password).toHex();
        return sha256Hash;
    }
}
