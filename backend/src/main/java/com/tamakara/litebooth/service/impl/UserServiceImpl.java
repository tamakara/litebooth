package com.tamakara.litebooth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tamakara.litebooth.domain.dto.LoginFormDTO;
import com.tamakara.litebooth.domain.dto.RegisterFormDTO;
import com.tamakara.litebooth.domain.entity.User;
import com.tamakara.litebooth.domain.pojo.Profile;
import com.tamakara.litebooth.domain.vo.LoginVO;
import com.tamakara.litebooth.domain.vo.RegisterVO;
import com.tamakara.litebooth.mapper.UserMapper;
import com.tamakara.litebooth.service.FileService;
import com.tamakara.litebooth.service.UserService;
import com.tamakara.litebooth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final UserMapper userMapper;
    private final FileService fileService;

    @Value("${token.secret}")
    private String TOKEN_SECRET;

    @Value("${token.expiration}")
    private Integer TOKEN_EXPIRATION;

    @Override
    @Transactional
    public LoginVO login(LoginFormDTO loginFormDTO) {
        User user = userMapper.selectByUsernameAndPassword(loginFormDTO.getUsername(), loginFormDTO.getPassword());
        if (user == null) throw new RuntimeException("用户不存在");

        String token = JwtUtil.generateJWT(user.getId(), TOKEN_SECRET, TOKEN_EXPIRATION);
        String avatarUrl = fileService.getFileUrl(user.getAvatarFileId(), 3600 * 24);
        Profile profile = new Profile(avatarUrl, user.getUsername(), user.getEmail());

        return new LoginVO(token, profile);
    }

    @Override
    public RegisterVO register(RegisterFormDTO registerFormDTO) {
        User user = new User();

        user.setAvatarFileId(0L);
        user.setUsername(registerFormDTO.getUsername());
        user.setPassword(registerFormDTO.getPassword());
        user.setEmail(registerFormDTO.getEmail());

        userMapper.insert(user);

        String token = JwtUtil.generateJWT(user.getId(), TOKEN_SECRET, TOKEN_EXPIRATION);
        String avatarUrl = fileService.getFileUrl(user.getAvatarFileId(), 3600 * 24);
        Profile profile = new Profile(avatarUrl, user.getUsername(), user.getEmail());

        return new RegisterVO(token, profile);
    }

//    @Override
//    @Transactional(readOnly = true)
//    public UserCenterVO getUserCenterVO(Long userId) {
//        User user = userMapper.selectById(userId);
//        if (user == null) throw new RuntimeException("用户不存在");
//
//        String avatarUrl = fileClient.getFileUrl(user.getAvatarFileId()).getBody();
//
//        UserInfo userInfo = new UserInfo();
//        userInfo.setAvatarUrl(avatarUrl);
//        userInfo.setPhoneNumber(user.getPhoneNumber());
//        userInfo.setNickName(user.getNickName());
//        userInfo.setGender(user.getGender());
//
//        return new UserCenterVO(userInfo);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public PersonVO getPersonVO(Long userId) {
//        User user = userMapper.selectById(userId);
//        if (user == null) throw new RuntimeException("用户不存在");
//
//        String avatarUrl = fileClient.getFileUrl(user.getAvatarFileId()).getBody();
//
//        return new PersonVO(avatarUrl, user.getNickName(), user.getPhoneNumber(), user.getGender());
//    }
//
//    @Override
//    public String updateAvatar(Long userId, MultipartFile file) {
//        User user = userMapper.selectById(userId);
//        if (user == null) throw new RuntimeException("用户不存在");
//
//        System.out.println(file.getName());
//        Long fileId = fileClient.uploadFile(file).getBody();
//        user.setAvatarFileId(fileId);
//        userMapper.updateById(user);
//
//        String avatarUrl = fileClient.getFileUrl(user.getAvatarFileId()).getBody();
//
//        return avatarUrl;
//    }
//
//    @Override
//    public void updateGender(Long userId, Integer gender) {
//        User user = userMapper.selectById(userId);
//        if (user == null) throw new RuntimeException("用户不存在");
//
//        user.setGender(gender);
//        userMapper.updateById(user);
//    }

}
