package com.akuza.management.mapper;

import com.akuza.management.entity.Depart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper  // 配置扫描该包
public interface DepartMapper extends BaseMapper<Depart> {  // 继承BaseMapper 获取基本的CRUD操作

}

