package com.tamakara.litebooth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tamakara.litebooth.domain.entity.File;
import org.springframework.web.multipart.MultipartFile;

public interface FileService extends IService<File> {
    Long uploadFile(MultipartFile multipartFile);

    String getFileUrl(Long fileId, Integer time);

}
