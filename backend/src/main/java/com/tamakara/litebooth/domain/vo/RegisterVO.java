package com.tamakara.litebooth.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class RegisterVO {
    private String token;
    private ProfileVO profile;
}
