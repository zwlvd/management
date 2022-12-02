package com.akuza.management.mapper;

import com.akuza.management.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper    // 配置扫描该包
public interface EmployeeMapper extends BaseMapper<Employee> { // 继承BaseMapper 获取基本的CRUD方法
    // 查询所有员工
    List<Employee> getEmpList();   // 扩展自己的CRUD方法
    // 根据姓名查询员工
    Employee getEmployeeByName(@Param("name") String name);
    // 根据id查询员工
    Employee getEmployeeByID(@Param("id") int id);
    // 用户名和密码进行登录
    Employee login(@Param("username") String username,@Param("password") String password);
    // 用户名或部门或角色查询
    List<Employee> get(@Param("name") String name,@Param("departid") int departid,@Param("roleid") int roleid);
    // 根据姓名查询员工  之后设计员工登录的时候只显示自己的信息
    List<Employee> getEmployee(@Param("name") String name);
    // 根据部门查询员工  之后设计各个部门经理登录会会显示各自部门的员工
    List<Employee> getEmployeeByDepartId(@Param("departid") int departid);

}

