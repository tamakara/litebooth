package com.tamakara.litebooth.service;

import com.tamakara.litebooth.domain.dto.LoginFormDTO;
import com.tamakara.litebooth.domain.dto.RegisterFormDTO;
import com.tamakara.litebooth.domain.entity.User;
import com.tamakara.litebooth.domain.vo.LoginVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tamakara.litebooth.domain.vo.ProfileVO;
import com.tamakara.litebooth.domain.vo.RegisterVO;
import org.springframework.web.multipart.MultipartFile;

public interface UserService extends IService<User> {
    LoginVO login(LoginFormDTO loginFormDTO);

    RegisterVO register(RegisterFormDTO registerFormDTO);

    ProfileVO getProfileVO(Long userId);

    String updateAvatar(Long userId, MultipartFile file);

//    void updateGender(Long userId, Integer gender);
}
