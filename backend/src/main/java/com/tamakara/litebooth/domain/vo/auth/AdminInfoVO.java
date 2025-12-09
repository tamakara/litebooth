package com.tamakara.litebooth.domain.vo.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminInfoVO {
    private String avatar;
    private String username;
    private String nickname;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime expires;
}
