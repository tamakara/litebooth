package com.tamakara.litebooth.domain.vo.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenVO {
    private String accessToken;
    private String refreshToken;
    private Instant expires;
}
