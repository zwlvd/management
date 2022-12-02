package com.akuza.management.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;       // 员工编号
    private String name;      // 员工姓名
    private String password;  // 账号密码
    private Integer age;       // 员工年龄
    private Integer sex;      // 员工性别  1 男 0 女
    private String phone;     // 联系方式
    private java.util.Date birth;       // 员工生日
    private Integer role;         // 角色编号
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private Roles roles;      //  角色
    @TableField(fill = FieldFill.INSERT)
    private java.util.Date createtime;  // 创建时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatetime;  // 修改时间
    @Version
    private Integer version;  // 乐观锁
    @TableLogic
    private Integer deleted;  // 逻辑删除
    private Integer departid; // 员工部门编号
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private Depart depart;    // 员工部门
}
