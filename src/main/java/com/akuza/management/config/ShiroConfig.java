package com.akuza.management.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    // 3.ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("manager") DefaultWebSecurityManager manager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        // 关联DefaultWebSecurityManager,同样需要传参引入,此处的bean使用了别名
        bean.setSecurityManager(manager);
        Map<String,String> map = new LinkedHashMap<>();
        // 员工管理 所需权限
        map.put("/emp/list","perms[emp:list]");
        map.put("/emp/toAdd","perms[emp:toAdd]");
        map.put("/emp/add","perms[emp:add]");
        map.put("/emp/toUpdate/**","perms[emp:toUpdate]");
        map.put("/emp/update","perms[emp:update]");
        map.put("/emp/delete/**","perms[emp:delete]");
        map.put("/emp/get","perms[emp:get]");
        // 部门管理 所需权限
        map.put("/dep/list","perms[dep:list]");
        map.put("/dep/toAdd","perms[dep:toAdd]");
        map.put("/dep/add","perms[dep:add]");
        map.put("/dep/toUpdate/**","perms[dep:toUpdate]");
        map.put("/dep/update","perms[dep:update]");
        map.put("/dep/delete/**","perms[dep:delete]");
        // 角色管理 所需权限
        map.put("/role/list","perms[role:list]");
        map.put("/role/toAdd","perms[role:toAdd]");
        map.put("/role/add","perms[role:add]");
        map.put("/role/toUpdate/**","perms[role:toUpdate]");
        map.put("/role/update","perms[role:update]");
        map.put("/role/delete/**","perms[role:delete]");
        bean.setFilterChainDefinitionMap(map);
        bean.setUnauthorizedUrl("/unauthorized");
        return bean;
    }

    //2.DefaultWebSecurityManager
    @Bean(name = "manager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("myRealm") MyRealm myRealm)	{
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 关联Realm 因为MyRealm已经被Spring接管,所以需要传参引入,传参的时候需要用到Qualifier指定bean的名字
        manager.setRealm(myRealm);
        return manager;
    }

    //1.创建Realm
    @Autowired
    public MyRealm myRealm;

    // shiro整合thymeleaf
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

}
