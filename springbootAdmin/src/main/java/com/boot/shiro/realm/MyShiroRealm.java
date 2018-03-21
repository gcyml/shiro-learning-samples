package com.boot.shiro.realm;

import com.boot.domain.UserInfo;
import com.boot.service.UserInfoService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @author wgc
 * @date 2018/02/09
 */
public class MyShiroRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);
    @Resource
    private UserInfoService userInfoService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return new SimpleAuthorizationInfo();
    }

    //主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        //获取用户的输入的账号.
        String username = (String)token.getPrincipal();
        logger.info("对用户[{}]进行登录验证..验证开始",username);
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        UserInfo userInfo =  userInfoService.findByUsername(username);
        if(userInfo == null){
        	// 抛出 帐号找不到异常  
            throw new UnknownAccountException();  
        }
        return new SimpleAuthenticationInfo(userInfo, userInfo.getPassword(), getName());
    }
}
