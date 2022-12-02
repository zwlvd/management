package com.akuza.management.controller;

import com.akuza.management.entity.Depart;
import com.akuza.management.entity.Employee;
import com.akuza.management.entity.Roles;
import com.akuza.management.service.DepartService;
import com.akuza.management.service.EmployeeService;
import com.akuza.management.service.RoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @Autowired
    DepartService departService;

    @Autowired
    RoleService roleService;
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "index";
    }

    @RequestMapping("/login")
    public String login(String username, String password,Model model){
        Subject subject = SecurityUtils.getSubject();  // 使用shiro 实现安全管理, 获取当前的对象
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);  // 登录
            return "redirect:/main.html"; // 重定向到首页
        }catch (UnknownAccountException e){
            model.addAttribute("msg","没有该用户");
            return "index";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
            return "index";
        }
    }
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        Object employee = session.getAttribute("employee");
        if(employee==null){ // 未登录 直接跳转登录页
        }else {
            session.removeAttribute("employee");  // 已登录 清除session 跳转登录页
        }
        return "index";
    }
    @RequestMapping("/emp/list")
    public String list(Model model,HttpSession session){
        Employee employee = (Employee)session.getAttribute("employee");
        Employee employeeByID = employeeService.getEmployeeByID(employee.getId()); // 根据id重新查一遍,以防信息不准确
        String name = employeeByID.getRoles().getName();  // 得到当前角色名字
        List<Employee> employeeList;
        if(name.equals("管理员")){
            employeeList = employeeService.getEmployeeList(); // 当前登录的是管理员,查询全部的员工
        }else if (name.equals("普通员工")){
            employeeList = employeeService.getEmployee(employeeByID.getName()); // 当前登录的是普通员工,只能查询自己的信息
        }else{
            employeeList = employeeService.getEmployeeByDepart(employee.getDepartid()); // 当前登录的是部门经理,查询自己部门的员工
        }
        return getString(model, employeeList);
    }

    private String getString(Model model, List<Employee> employeeList) {
        model.addAttribute("employeeList",employeeList);  // 统一将查询的员工信息返回前端
        List<Depart> departList = departService.getDepartList();
        model.addAttribute("departList",departList);  // 将所有的部门信息返回前端
        List<Roles> rolesList = roleService.getRolesList();
        model.addAttribute("rolesList",rolesList);    // 将所有的角色信息返回前端
        return "emp/list";
    }

    @RequestMapping("/emp/toAdd")
    public String toAdd(Model model){
        List<Depart> departList = departService.getDepartList();
        model.addAttribute("departList",departList); // 将所有的部门信息返回前端
        List<Roles> rolesList = roleService.getRolesList();
        model.addAttribute("rolesList",rolesList);   // 将所有的角色信息返回前端
        return "emp/add";
    }
    @RequestMapping("/emp/add")
    public String add(Employee employee){
        employeeService.add(employee); // 添加员工操作
        return "redirect:/emp/list";
    }
    @RequestMapping("/emp/toUpdate/{id}")
    public String toUpdate(@PathVariable("id") int id,Model model){
        Employee employeeByID = employeeService.getEmployeeByID(id); // 更新员工先查到该员工,将信息返回更新页面
        model.addAttribute("employee",employeeByID);
        List<Depart> departList = departService.getDepartList();
        model.addAttribute("departList",departList);
        List<Roles> rolesList = roleService.getRolesList();
        model.addAttribute("rolesList",rolesList);
        return "emp/update";
    }
    @RequestMapping("/emp/update")
    public String update(Employee employee){
        employeeService.update(employee); // 更新员工操作
        return "redirect:/emp/list";
    }
    @RequestMapping("/emp/delete/{id}")
    public String delete(@PathVariable("id") int id){
        employeeService.delete(id);      // 删除员工操作
        return "redirect:/emp/list";
    }

    @RequestMapping("/emp/get")
    public String get(String name,String departid,String roleid,Model model){
        List<Employee> employeeList = employeeService.get(name, Integer.parseInt(departid),Integer.parseInt(roleid));
        return getString(model, employeeList);
    }
    @RequestMapping("/unauthorized")
    public String unauthorized(){
        return "unauthorized";
    }  // 此处需要设置一个未授权页面,之后当有没有权限的用户不合法访问页面时会跳转到该页面
}





