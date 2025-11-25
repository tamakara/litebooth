package com.tamakara.litebooth.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.Instant;

@Data
@TableName("`order`")
public class Order {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "status")
    private String status;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "user_mail")
    private String userMail;

    @TableField(value = "item_id")
    private Long itemId;

    @TableField(value = "item_name")
    private String itemName;

    @TableField(value = "item_price")
    private Double itemPrice;

    @TableField(value = "quantity")
    private Long quantity;

    @TableField(value = "total_price")
    private Double totalPrice;

    @TableField(value = "pay_method")
    private String payMethod;

    @TableField(value = "created_at")
    private Instant createdAt;

    @TableField(value = "updated_at")
    private Instant updatedAt;
}
