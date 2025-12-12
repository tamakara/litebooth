package com.tamakara.litebooth.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.Instant;

@Data
@TableName("shop_info")
public class ShopInfo {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "logo")
    private Long logo;

    @TableField(value = "logo_title")
    private String logoTitle;

    @TableField(value = "home_title")
    private String homeTitle;

    @TableField(value = "home_subtitle")
    private String homeSubtitle;

    @TableField(value = "home_announcement")
    private String homeAnnouncement;

    @TableField(value = "created_at")
    private Instant createdAt;

    @TableField(value = "updated_at")
    private Instant updatedAt;
}
