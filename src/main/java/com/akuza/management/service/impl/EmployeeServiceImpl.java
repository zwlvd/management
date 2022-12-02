package com.akuza.management.service.impl;

import com.akuza.management.entity.Employee;
import com.akuza.management.mapper.EmployeeMapper;
import com.akuza.management.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper; // 调用dao层

    @Override
    public int add(Employee employee) {
        return employeeMapper.insert(employee);
    }

    @Override
    public List<Employee> getEmployeeList() {
        return employeeMapper.getEmpList(); // 调用自己的方法
    }

    @Override
    public Employee getEmployeeByID(int id) {
        return employeeMapper.getEmployeeByID(id);// 调用自己的方法
    }

    @Override
    public Employee getEmployeeByName(String name) {
        return employeeMapper.getEmployeeByName(name);
    } // 调用自己的方法

    @Override
    public int delete(int id) {
        return employeeMapper.deleteById(id);
    }

    @Override
    public int update(Employee employee) {
        Employee employeeByID = employeeMapper.getEmployeeByID(employee.getId());
        employeeByID.setName(employee.getName());
        employeeByID.setPassword(employee.getPassword());
        employeeByID.setAge(employee.getAge());
        employeeByID.setSex(employee.getSex());
        employeeByID.setPhone(employee.getPhone());
        employeeByID.setBirth(employee.getBirth());
        employeeByID.setRole(employee.getRole());
        employeeByID.setDepartid(employee.getDepartid());
        /**
         * 注意此处的employee属性的部门对象在数据库中并没有存储,所以不需要进行更新,
         * 只更新其另加的部门id字段即可,但是mybatis-plus默认的更新策略时更新所有不为空的字段,
         * 在更新的时候sql语句中会有部门对象的字段,此时就需要修改employee实体类的该字段的
         * 更新策略,改为never,这样sql语句中就不会再出现该字段
         * */
        return employeeMapper.updateById(employeeByID);
    }

    @Override
    public Employee login(String username, String password) {
        return employeeMapper.login(username,password);
    }

    @Override
    public List<Employee> get(String name, int departid,int roleid) {
        if (StringUtils.isEmpty(name)){  // 此处前端可能会传来null或者"",此处统一变为null,方便sql语句判断
            name=null;
        }
        return employeeMapper.get(name,departid,roleid);
    }

    @Override
    public List<Employee> getEmployee(String name) {
        return employeeMapper.getEmployee(name);
    }

    @Override
    public List<Employee> getEmployeeByDepart(int departid) {
        return employeeMapper.getEmployeeByDepartId(departid);
    }

}

