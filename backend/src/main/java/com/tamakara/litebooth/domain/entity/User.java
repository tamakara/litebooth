package com.tamakara.litebooth.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.tamakara.litebooth.domain.pojo.Profile;
import lombok.Data;

import java.time.Instant;

@Data
@TableName("user")
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "avatar_file_id")
    private Long avatarFileId;

    @TableField(value = "username")
    private String username;

    @TableField(value = "password")
    private String password;

    @TableField(value = "email")
    private String email;

    @TableField(value = "created_at")
    private Instant createdAt;

    @TableField(value = "updated_at")
    private Instant updatedAt;

}
