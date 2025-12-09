package com.tamakara.litebooth.service.impl;

import com.tamakara.litebooth.domain.dto.auth.LoginFormDTO;
import com.tamakara.litebooth.domain.entity.AdminInfo;
import com.tamakara.litebooth.domain.vo.auth.AdminInfoVO;
import com.tamakara.litebooth.domain.vo.auth.LoginVO;
import com.tamakara.litebooth.mapper.AdminInfoMapper;
import com.tamakara.litebooth.service.AuthService;
import com.tamakara.litebooth.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final FileService fileService;
    private final AdminInfoMapper adminInfoMapper;

    @Override
    public LoginVO login(LoginFormDTO loginFormDTO) {
        AdminInfo adminInfo = adminInfoMapper.selectById(1L);

        String avatarUrl = fileService.getFileUrl(adminInfo.getAvatar(), 86400);

        AdminInfoVO adminInfoVO = new AdminInfoVO();
        adminInfoVO.setAvatar(avatarUrl);
        adminInfoVO.setUsername(adminInfo.getUsername());
        adminInfoVO.setNickname(adminInfo.getNickname());
        adminInfoVO.setAccessToken("eyJhbGciOiJIUzUxMiJ9.admin");
        adminInfoVO.setRefreshToken("eyJhbGciOiJIUzUxMiJ9.adminRefresh");
        adminInfoVO.setExpires(LocalDateTime.now().plusSeconds(86400));

        return new LoginVO(true, adminInfoVO);
    }
}
