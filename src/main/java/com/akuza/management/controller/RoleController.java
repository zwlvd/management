package com.akuza.management.controller;


import com.akuza.management.entity.Roles;
import com.akuza.management.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class RoleController {
    @Autowired
    RoleService roleService;

    @RequestMapping("/role/list")
    public String list(Model model){
        List<Roles> rolesList = roleService.getRolesList();
        if(rolesList!=null){
            for (Roles roles : rolesList) {
                StringBuilder builder = new StringBuilder();
                if(!StringUtils.isEmpty(roles.getPerms())){
                    String[] split = roles.getPerms().split(",");
                    for (String s : split) {
                        if(s.equals("emp:*")){
                            builder.append("操作员工的所有权限--");
                        }
                        if(s.equals("dep:*")){
                            builder.append("操作部门的所有权限--");
                        }
                        if(s.equals("role:*")){
                            builder.append("操作角色的所有权限--");
                        }
                        if(s.equals("emp:list")){
                            builder.append("查看员工列表--");
                        }
                        if(s.equals("emp:toAdd")){
                            builder.append("跳转增加员工--");
                        }
                        if(s.equals("emp:add")){
                            builder.append("增加员工--");
                        }
                        if(s.equals("emp:toUpdate")){
                            builder.append("跳转修改员工--");
                        }
                        if(s.equals("emp:update")){
                            builder.append("修改员工--");
                        }
                        if(s.equals("emp:delete")){
                            builder.append("删除员工--");
                        }
                        if(s.equals("emp:get")){
                            builder.append("查询员工--");
                        }

                        if(s.equals("dep:list")){
                            builder.append("查看部门列表--");
                        }
                        if(s.equals("dep:toAdd")){
                            builder.append("跳转增加部门--");
                        }
                        if(s.equals("dep:add")){
                            builder.append("增加部门--");
                        }
                        if(s.equals("dep:toUpdate")){
                            builder.append("跳转修改部门--");
                        }
                        if(s.equals("dep:update")){
                            builder.append("修改部门--");
                        }
                        if(s.equals("dep:delete")){
                            builder.append("删除部门--");
                        }


                        if(s.equals("role:list")){
                            builder.append("查看角色列表--");
                        }
                        if(s.equals("role:toAdd")){
                            builder.append("跳转增加角色--");
                        }
                        if(s.equals("role:add")){
                            builder.append("增加角色--");
                        }
                        if(s.equals("role:toUpdate")){
                            builder.append("跳转修改角色--");
                        }
                        if(s.equals("role:update")){
                            builder.append("修改角色--");
                        }
                        if(s.equals("role:delete")){
                            builder.append("删除角色--");
                        }
                        if(s.equals("无")){
                            builder.append("无权限");
                        }
                    }
                    roles.setPerms(builder.toString());
                }
            }
        }
        model.addAttribute("rolesList",rolesList);
        return "role/list";
    }
    @RequestMapping("/role/toAdd")
    public String toAdd(){
        return "role/add";
    }
    @RequestMapping("/role/add")
    public String add(Roles roles,String[] perms){
        if (roles.getName()==null || roles.getName()==""){
            roles.setName("默认");   // 用户不输入,角色名字就是默认
        }
        String perm = null;
        if(!StringUtils.isEmpty(perms)&&perms!=null){
            List<String> list = Arrays.asList(perms); // 将用户在多选框中选择的权限组成一个list列表
            perm = String.join(",", list);   // 各个权限之间用,分割
        }else {
            perm = "无"; // 用户没有选择权限
        }
        roles.setPerms(perm);   // 设置权限
        roleService.add(roles); // 增加角色
        return "redirect:/role/list";
    }
    @RequestMapping("/role/toUpdate/{id}")
    public String toUpdate(@PathVariable("id") int id, Model model){
        Roles roleByID = roleService.getRoleByID(id);
        model.addAttribute("role",roleByID);
//        String perms = roleByID.getPerms();
//        String[] split = perms.split(",");
//        List<String> list = Arrays.asList(split);
//        System.out.println(list);
//        model.addAttribute("lists",list);
        return "role/update";
    }
    @RequestMapping("/role/update")
    public String update(Roles roles,String [] perms){
        if(roles.getName()==""||roles.getName()==null){
            roles.setName("默认");
        }
        String join = null;
        if(perms!=null&&!StringUtils.isEmpty(perms)){
            List<String> list = Arrays.asList(perms);
            join = String.join(",", list);
        }
        roles.setPerms(join);
        roleService.update(roles);
        return "redirect:/role/list";
    }
    @RequestMapping("/role/delete/{id}")
    public String delete(@PathVariable("id") int id){
        roleService.delete(id);
        return "redirect:/role/list";
    }

}
