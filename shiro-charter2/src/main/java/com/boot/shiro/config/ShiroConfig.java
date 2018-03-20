package com.boot.shiro.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.boot.shiro.filter.CaptchaValidateFilter;
import com.boot.shiro.filter.MyFormAuthenticationFilter;
import com.boot.shiro.realm.MyShiroRealm;

/**
 * @author wgc
 * @date 2018/02/09
 */
@Configuration
public class ShiroConfig {
    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     *
     Filter Chain定义说明
     1、一个URL可以配置多个Filter，使用逗号分隔
     2、当设置多个过滤器时，全部验证通过，才视为通过
     3、部分过滤器可指定参数，如perms，roles
     *
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //拦截器.
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 配置不会被拦截的链接 顺序判断

        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/layui/**", "anon");
        filterChainDefinitionMap.put("/captcha/**", "anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");

        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问; user”表示访问该地址的用户是身份验证通过或RememberMe登录的都可以-->
        filterChainDefinitionMap.put("/add", "perms[add]");
        filterChainDefinitionMap.put("/delete", "roles[admin]");
        filterChainDefinitionMap.put("/login", "captchaVaildate,authc");

        filterChainDefinitionMap.put("/**", "user");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");


        //自定义拦截器
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put("captchaVaildate", new CaptchaValidateFilter());
        filters.put("authc", new MyFormAuthenticationFilter());
        return shiroFilterFactoryBean;
    }

    /**
	 * 身份认证realm; (这个需要自己写，账号密码校验；权限等)
	 * 
	 * @return myShiroRealm
	 */
    @Bean
    public MyShiroRealm myShiroRealm(){
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        return myShiroRealm;
    }

    /**
     * 安全管理器
     * @return securityManager
     */
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        
        //注入记住我管理器
        securityManager.setRememberMeManager(rememberMeManager());
        //注入ehCache管理器
        securityManager.setCacheManager(ehCacheManager());
        // 注入session管理器, 不注入session管理器, 默认30分钟
        securityManager.setSessionManager(configWebSessionManager());
        return securityManager;
    }
    
    ///////////////////////////////////////////////////////////////////////
    
    /**
     * cookie对象;
     * rememberMeCookie()方法是设置Cookie的生成模版，比如cookie的name，cookie的有效时间等等。
     * @return rememberMeCookie
     */
    @Bean
    public SimpleCookie rememberMeCookie(){
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(30*24*60*60);
        return simpleCookie;
    }
    
    /**
     * cookie管理对象;
     * rememberMeManager()方法是生成rememberMe管理器，而且要将这个rememberMe管理器设置到securityManager中
     * @return rememberMeManager
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }
    
    /**
     * 缓存管理器
     * @return cacheManager
     */
    @Bean
    public EhCacheManager ehCacheManager(){
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
        return cacheManager;
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////
    // session相关配置
    //////////////////////////////////////////////////////////////////////////////////////
    /**
     * 会话管理器
     * @return sessionManager
     */
    @Bean
    public DefaultWebSessionManager configWebSessionManager(){
        DefaultWebSessionManager manager = new DefaultWebSessionManager();
        // 加入缓存管理器
        manager.setCacheManager(ehCacheManager());
        // 删除过期的session
        manager.setDeleteInvalidSessions(true);
        
        // 设置全局session超时时间
        manager.setGlobalSessionTimeout(1 * 60 *1000);
        
        // 是否定时检查session
        manager.setSessionValidationSchedulerEnabled(true);
        manager.setSessionValidationScheduler(configSessionValidationScheduler());
        manager.setSessionIdUrlRewritingEnabled(false);
        manager.setSessionIdCookieEnabled(true);
        manager.setSessionIdCookie(sessionIdCookie());
        return manager;
    }
    
    /**
     * session会话验证调度器
     * @return session会话验证调度器
     */
    @Bean 
    public ExecutorServiceSessionValidationScheduler configSessionValidationScheduler() {
    	ExecutorServiceSessionValidationScheduler sessionValidationScheduler = new ExecutorServiceSessionValidationScheduler();
    	//设置session的失效扫描间隔，单位为毫秒
    	sessionValidationScheduler.setInterval(300*1000);
    	return sessionValidationScheduler;
    }
    
    /**
     * 会话Cookie模板
     * @return cookie
     */
    @Bean
    public SimpleCookie sessionIdCookie(){
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("sid");
        simpleCookie.setHttpOnly(true);
        // 无限期
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }

}
