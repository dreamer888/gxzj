package com.dlc.modules.sys.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro的配置文件
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017/9/27 22:02
 */
@Configuration
public class ShiroConfig {

	@Bean("sessionIdCookie")
	public SimpleCookie simpleCookie(){
		SimpleCookie simpleCookie = new SimpleCookie("xprs.session.id");
		return simpleCookie;
	}
	@Bean("sessionManager")
	public SessionManager sessionManager(SimpleCookie sessionIdCookie,RedisShiroSessionDAO redisShiroSessionDAO, @Value("${renren.redis.open}") boolean redisOpen, @Value("${renren.shiro.redis}") boolean shiroRedis) {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		// 设置session过期时间为1小时(单位：毫秒)，默认为30分钟
		sessionManager.setGlobalSessionTimeout(60 * 60 * 1000);
		sessionManager.setSessionValidationSchedulerEnabled(true);
		sessionManager.setSessionIdUrlRewritingEnabled(false);
		sessionManager.setSessionIdCookie(sessionIdCookie);
		sessionManager.setSessionIdCookieEnabled(true);
		// 如果开启redis缓存且renren.shiro.redis=true，则shiro session存到redis里
		if (redisOpen && shiroRedis) {
			sessionManager.setSessionDAO(redisShiroSessionDAO);
		}
		return sessionManager;
	}

	@Bean("securityManager")
	public SecurityManager securityManager(UserRealm userRealm, SessionManager sessionManager) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(userRealm);
		securityManager.setSessionManager(sessionManager);

		return securityManager;
	}

	@Bean("shiroFilter")
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setSecurityManager(securityManager);
		shiroFilter.setLoginUrl("/login.html");
		shiroFilter.setUnauthorizedUrl("/");

		Map<String, String> filterMap = new LinkedHashMap<>();
		filterMap.put("/statics/**", "anon");
        filterMap.put("/h5/**", "anon");
		filterMap.put("/ueditor/**", "anon");
		filterMap.put("/login.html", "anon");
		filterMap.put("/images/*", "anon");
		filterMap.put("/sys/login", "anon");
		filterMap.put("/favicon.ico", "anon");
		filterMap.put("/captcha.jpg", "anon");
		filterMap.put("/druid/**", "anon");
		filterMap.put("/qh/**", "anon");
		filterMap.put("/wx/**", "anon");
		filterMap.put("/api/**", "anon");
		filterMap.put("/pay/**", "anon");
		filterMap.put("/fileUpload/**", "anon");
		filterMap.put("/account/**", "anon");
		filterMap.put("/**", "authc");
		shiroFilter.setFilterChainDefinitionMap(filterMap);

		return shiroFilter;
	}

	@Bean("lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
		proxyCreator.setProxyTargetClass(true);
		return proxyCreator;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}
}
