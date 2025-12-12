package com.tamakara.litebooth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tamakara.litebooth.domain.entity.File;
import com.tamakara.litebooth.mapper.FileMapper;
import com.tamakara.litebooth.service.FileService;
import com.tamakara.litebooth.util.HashUtil;
import com.tamakara.litebooth.util.MinIOUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {
    private final MinIOUtil minIOUtil;
    private final FileMapper fileMapper;

    @Transactional
    public Long uploadFile(MultipartFile multipartFile) {
        File file = new File();
        file.setName(multipartFile.getOriginalFilename());
        file.setHash(HashUtil.calculateHash(multipartFile));
        file.setSize(multipartFile.getSize());

        if (!fileMapper.existsByHash(file.getHash()))
            minIOUtil.upload(file.getHash(), multipartFile);

        fileMapper.insert(file);
        return file.getId();
    }

    public String getFileUrl(Long fileId, Integer time) {
        File file = fileMapper.selectById(fileId);
        if (file == null) throw new RuntimeException("文件不存在");
        return minIOUtil.generateFileUrl(file.getHash(), time);
    }
}
