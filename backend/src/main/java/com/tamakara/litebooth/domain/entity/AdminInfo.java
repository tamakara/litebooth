package com.tamakara.litebooth.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.Instant;

@Data
@TableName("admin_info")
public class AdminInfo {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "avatar")
    private Long avatar;

    @TableField(value = "username")
    private String username;

    @TableField(value = "password")
    private String password;

    @TableField(value = "nickname")
    private String nickname;

    @TableField(value = "created_at")
    private Instant created_at;

    @TableField(value = "updated_at")
    private Instant updated_at;
}
