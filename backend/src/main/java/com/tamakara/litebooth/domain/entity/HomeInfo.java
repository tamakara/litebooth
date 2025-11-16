package com.tamakara.litebooth.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("home_info")
public class HomeInfo {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "announcement")
    private String announcement;

    @TableField(value = "title")
    private String title;

    @TableField(value = "subtitle")
    private String subtitle;
}
