package com.tamakara.litebooth.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileVO {
    private String avatar;
    private String username;
    private String email;
}
