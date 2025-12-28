package com.tamakara.litebooth.domain.dto.auth;

import lombok.Data;

@Data
public class ChangePasswordDTO {
    private String oldPassword;
    private String newPassword;
}

