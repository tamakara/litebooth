package com.tamakara.litebooth.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@TableName("stock")
public class Stock {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "status")
    private String status;

    @TableField(value = "item_id")
    private Long itemId;

    @TableField(value = "order_id")
    private Long orderId;

    @TableField(value = "content")
    private String content;

    @TableField(value = "created_at")
    private Instant createdAt;

    @TableField(value = "updated_at")
    private Instant updatedAt;
}
