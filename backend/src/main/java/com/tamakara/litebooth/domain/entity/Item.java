package com.tamakara.litebooth.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.Instant;

@Data
@TableName("item")
public class Item {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "is_active")
    private Boolean isActive;

    @TableField(value = "name")
    private String name;

    @TableField(value = "price")
    private Double price;

    @TableField(value = "stock")
    private Long stock;

    @TableField(value = "`group`")
    private String group;

    @TableField(value = "description")
    private String description;

    @TableField(value = "created_at")
    private Instant createdAt;

    @TableField(value = "updated_at")
    private Instant updatedAt;
}
