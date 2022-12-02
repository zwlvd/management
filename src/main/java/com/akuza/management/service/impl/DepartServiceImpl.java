package com.akuza.management.service.impl;

import com.akuza.management.entity.Depart;
import com.akuza.management.mapper.DepartMapper;
import com.akuza.management.service.DepartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartServiceImpl implements DepartService {

    @Autowired
    DepartMapper departMapper;  // 调用dao层

    /**
     * selectById()、selectList()、deleteById()、updateById()、insert()
     * 这些都是继承BaseMapper之后 就直接可以使用的
     * */
    @Override
    public Depart getDepartByID(int id) {
        return departMapper.selectById(id);
    }

    @Override
    public List<Depart> getDepartList() {
        return departMapper.selectList(null);
    }

    @Override
    public int delete(int id) {
        return departMapper.deleteById(id);
    }

    @Override
    public int update(Depart depart) { // 需要用到乐观锁,需要先获取depart对象,再对其进行修改
        Depart departByID = this.getDepartByID(depart.getId());
        departByID.setName(depart.getName());
        return departMapper.updateById(departByID);
    }

    @Override
    public int add(Depart depart) {
        return departMapper.insert(depart);
    }
}

