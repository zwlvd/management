package com.akuza.management.service;

import com.akuza.management.entity.Employee;

import java.util.List;

public interface EmployeeService {
    // 增加员工
    int add(Employee employee);
    // 查询所有员工
    List<Employee> getEmployeeList();
    // 根据id查询员工
    Employee getEmployeeByID(int id);
    // 根据姓名查询员工
    Employee getEmployeeByName(String name);
    // 根据id删除员工
    int delete(int id);
    // 根据id更新员工
    int update(Employee employee);
    // 姓名和密码进行登录
    Employee login(String username,String password);
    // 名字或部门进行查询
    List<Employee> get(String name,int departid,int roleid);
    // 根据姓名查询员工
    List<Employee> getEmployee(String name);
    // 根据部门id查询员工
    List<Employee> getEmployeeByDepart(int departid);

}
