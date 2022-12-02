package com.akuza.management.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Depart {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    @TableField(fill = FieldFill.INSERT)
    private Date createtime;  // 创建时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatetime;  // 修改时间
    @Version
    private Integer version;  // 乐观锁
    @TableLogic
    private Integer deleted;  // 逻辑删除
}
