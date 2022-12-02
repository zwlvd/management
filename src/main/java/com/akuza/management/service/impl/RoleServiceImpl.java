package com.akuza.management.service.impl;

import com.akuza.management.entity.Roles;
import com.akuza.management.mapper.RoleMapper;
import com.akuza.management.service.RoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper; // 调用dao层

    @Override
    public List<Roles> getRolesList() {
        return roleMapper.selectList(null);
    }

    @Override
    public int add(Roles roles) {
        return roleMapper.insert(roles);
    }

    @Override
    public int delete(int id) {
        return roleMapper.deleteById(id);
    }

    @Override
    public int update(Roles roles) {
        return roleMapper.updateById(roles);
    }

    @Override
    public Roles getRoleByID(int id) {
        return roleMapper.selectById(id);
    }

    @Override
    public int deleteByName(String name) {
        QueryWrapper<Roles> rolesQueryWrapper = new QueryWrapper<>();
        rolesQueryWrapper.eq("name",name); // 使用条件构造器
        return roleMapper.delete(rolesQueryWrapper);
    }

    @Override
    public Roles getRoleByName(String name) {
        QueryWrapper<Roles> rolesQueryWrapper = new QueryWrapper<>();
        rolesQueryWrapper.eq("name",name);
        return roleMapper.selectOne(rolesQueryWrapper);
    }
}
