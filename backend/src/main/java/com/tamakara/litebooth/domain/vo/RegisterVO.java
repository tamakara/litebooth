package com.tamakara.litebooth.domain.vo;

import com.tamakara.litebooth.domain.pojo.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class RegisterVO {
    private String token;
    private Profile profile;
}
