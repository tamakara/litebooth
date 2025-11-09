package com.tamakara.litebooth.controller;

import com.tamakara.litebooth.domain.dto.LoginFormDTO;
import com.tamakara.litebooth.domain.dto.RegisterFormDTO;
import com.tamakara.litebooth.domain.vo.LoginVO;
import com.tamakara.litebooth.domain.vo.RegisterVO;
import com.tamakara.litebooth.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

//    @GetMapping("/getUserCenterVO")
//    public ResponseEntity<UserCenterVO> getUserCenterVO(
//            @RequestHeader("X-USER-ID") Long userId
//    ) {
//        UserCenterVO vo = userService.getUserCenterVO(userId);
//        return ResponseEntity.ok(vo);
//    }
//
//    @GetMapping("/getPersonVO")
//    public ResponseEntity<PersonVO> getPersonVO(
//            @RequestHeader("X-USER-ID") Long userId
//    ) {
//        PersonVO vo = userService.getPersonVO(userId);
//        return ResponseEntity.ok(vo);
//    }
//
//    @PostMapping("/updateAvatar")
//    public ResponseEntity<String> updateAvatar(
//            @RequestHeader("X-USER-ID") Long userId,
//            @RequestPart("file") MultipartFile file
//    ) {
//        String avatarUrl = userService.updateAvatar(userId, file);
//        return ResponseEntity.ok(avatarUrl);
//    }
//
//    @PostMapping("/updateGender")
//    public ResponseEntity<Void> updateGender(
//            @RequestHeader("X-USER-ID") Long userId,
//            @RequestParam Integer gender
//    ) {
//        userService.updateGender(userId, gender);
//        return ResponseEntity.ok().build();
//    }

}