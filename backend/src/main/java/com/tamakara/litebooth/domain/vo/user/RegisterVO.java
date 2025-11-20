package com.tamakara.litebooth.domain.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class RegisterVO {
    private String token;
    private ProfileVO profile;
}
