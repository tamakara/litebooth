package com.tamakara.litebooth.domain.vo;

import com.tamakara.litebooth.domain.pojo.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginVO {
    private String token;
    private Profile profile;
}
