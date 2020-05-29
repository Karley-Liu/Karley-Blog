package com.lin.karley.Config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description Description
 * @Author Karley LIn
 * @Date Created in 2020/5/20
 */
@Configuration
public class ShiroConfig {

    //ShrioFilterFactoryBean：3
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        //设置登录url
        shiroFilterFactoryBean.setLoginUrl("/login");
        //设置成功登陆打开的url
        shiroFilterFactoryBean.setSuccessUrl("/back_notes");
        //添加shiro的内置过滤器
//        anon:无需认证可以访问
//        auth:必须认真才能访问
//        user:必须拥有记住我功能才能用
//        perms:拥有对某个资源的权限
//        role:拥有某个角色权限才能访问
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/logout","logout");
        filterMap.put("/releasenote","roles[1]");
        filterMap.put("/contactMe","roles[0]");
//        filterMap.put("/contactCus","roles[1]");
//        filterMap.put("/user/*","authc");     //认证才能访问的链接
//        filterMap.put("/user/add","perms[user:add]");     //正常情况下未授权页面401
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
//        bean.setUnauthorizedUrl("/noauth"); //未授权请求
//        bean.setLoginUrl("/toLogin");   //设置登录页面
        return shiroFilterFactoryBean;

    }

    //DetaultWebSecurityManager：2
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm, @Qualifier("rememberMeManager")RememberMeManager rememberMeManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联UserRealm
        securityManager.setRealm(userRealm);
        securityManager.setRememberMeManager(rememberMeManager);
        return securityManager;
    }

    //创建realm对象，需要自定义：1
    @Bean
    public UserRealm userRealm(){
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return userRealm;
    }

    //整合shiroDialect
    @Bean(name = "shiroDialect")
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("sha-256");
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }

//    记住我cookie
    @Bean
    public SimpleCookie rememberMeCookie(){
        SimpleCookie remember = new SimpleCookie("remember");

        remember.setHttpOnly(true);
        remember.setPath("/");
        remember.setMaxAge(2592000);
        return remember;
    }

//    记住我管理器
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));

        return cookieRememberMeManager;
    }

    @Bean
//    记住我filter
    public FormAuthenticationFilter formAuthenticationFilter(){
        FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter();
        formAuthenticationFilter.setRememberMeParam("remember");
        return formAuthenticationFilter;
    }

}
