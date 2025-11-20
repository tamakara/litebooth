package com.tamakara.litebooth.domain.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginVO {
    private String token;
    private ProfileVO profile;
}
