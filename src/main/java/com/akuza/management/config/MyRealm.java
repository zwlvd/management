package com.akuza.management.config;


import com.akuza.management.entity.Employee;
import com.akuza.management.service.EmployeeService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
@Component
public class MyRealm extends AuthorizingRealm {

    @Autowired
    EmployeeService employeeService;

    @Override // 授权
    // 通过验证后,会对登录的用户进行授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        Employee employee = (Employee)subject.getPrincipal(); // 获取当前登录的用户
        String perms = employee.getRoles().getPerms();        // 将数据库中权限字段以,进行拆分
        String[] split = perms.split(",");              // 构成权限数组
        List<String> list = Arrays.asList(split);
        info.addStringPermissions(list);    // 授予权限
        return info;
    }
    @Override // 认证
    // 会从登录请求到此
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken token1 = (UsernamePasswordToken) token;
        String username = token1.getUsername();
        Employee employeeByName = employeeService.getEmployeeByName(username); // 拿到当前的登录的用户名
        if(employeeByName==null){ // 用户不存在,返回为空,此时登录请求会出现异常UnknownAccountException,提示前端用户名不存在
            return null;
        }
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute("employee",employeeByName); // 将当前的登录用户存入session
        return new SimpleAuthenticationInfo(employeeByName,employeeByName.getPassword(),""); // shiro会自己判断密码是否正确,不正确同样会出现异常,提示前端密码不正确
    }
}
