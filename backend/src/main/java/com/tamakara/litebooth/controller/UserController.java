package com.tamakara.litebooth.controller;

import com.tamakara.litebooth.domain.dto.LoginFormDTO;
import com.tamakara.litebooth.domain.dto.RegisterFormDTO;
import com.tamakara.litebooth.domain.vo.user.LoginVO;
import com.tamakara.litebooth.domain.vo.user.ProfileVO;
import com.tamakara.litebooth.domain.vo.user.RegisterVO;
import com.tamakara.litebooth.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "用户接口")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginVO> login(
            @RequestBody LoginFormDTO loginFormDTO
    ) {
        LoginVO vo = userService.login(loginFormDTO);
        return ResponseEntity.ok(vo);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterVO> register(
            @RequestBody RegisterFormDTO registerFormDTO
    ) {
        RegisterVO vo = userService.register(registerFormDTO);
        return ResponseEntity.ok(vo);
    }

    @GetMapping("/getProfileVO")
    public ResponseEntity<ProfileVO> getProfileVO(
            @RequestAttribute("userId") Long userId
    ) {
        ProfileVO vo = userService.getProfileVO(userId);
        return ResponseEntity.ok(vo);
    }

    @PutMapping("/updateAvatar")
    public ResponseEntity<String> updateAvatar(
            @RequestAttribute("userId") Long userId,
            @RequestParam("file") MultipartFile file
    ) {
        String avatarUrl = userService.updateAvatar(userId, file);
        return ResponseEntity.ok(avatarUrl);
    }
}