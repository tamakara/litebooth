package com.tamakara.litebooth.domain.dto;

import lombok.Data;

@Data
public class RegisterFormDTO {
    private String username;
    private String email;
    private String code;
    private String password;
}
