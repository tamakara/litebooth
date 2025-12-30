package com.tamakara.litebooth.domain.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenDTO {
    @NotBlank(message = "Refresh Token不能为空")
    private String refreshToken;
}
