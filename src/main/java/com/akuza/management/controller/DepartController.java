package com.akuza.management.controller;


import com.akuza.management.entity.Depart;
import com.akuza.management.entity.Roles;
import com.akuza.management.service.DepartService;
import com.akuza.management.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class DepartController {

    @Autowired
    DepartService departService;

    @Autowired
    RoleService roleService;

    @RequestMapping("/dep/list")
    public String list(Model model){
        List<Depart> departList = departService.getDepartList();
        model.addAttribute("departList",departList);
        return "dep/list";
    }
    @RequestMapping("/dep/toAdd")
    public String toAdd(){
        return "dep/add";
    } // 跳转到增加页面
    @RequestMapping("/dep/add")
    public String add(Depart depart){
        departService.add(depart);    // 增加操作
        String name = depart.getName();
        Roles roles = new Roles();
        roles.setName(name+"经理");   // 增加一个部门的同时增加一个角色
        roles.setPerms("无");         // 默认没有权限
        roleService.add(roles);
        return "redirect:/dep/list";
    }
    @RequestMapping("/dep/toUpdate/{id}")   // 跳转到修改页面 需要携带该部门的信息
    public String toUpdate(@PathVariable("id") int id,Model model){
        Depart departByID = departService.getDepartByID(id);
        model.addAttribute("depart",departByID);
        return "dep/update";
    }
    @RequestMapping("/dep/update")
    public String update(Depart depart,String old){
        Roles roleByName1 = roleService.getRoleByName(old+"经理"); // 原来名称
        roleByName1.setName(depart.getName()+"经理");
        roleService.update(roleByName1); // 修改角色名称
        departService.update(depart);    // 修改部门名称
        return "redirect:/dep/list";
    }
    @RequestMapping("/dep/delete/{id}")
    public String delete(@PathVariable("id") int id){
        Depart departByID = departService.getDepartByID(id);
        String name = departByID.getName();
        roleService.deleteByName(name+"经理");
        departService.delete(id);
        return "redirect:/dep/list";
    }
}