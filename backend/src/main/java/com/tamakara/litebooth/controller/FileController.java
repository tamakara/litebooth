package com.tamakara.litebooth.controller;

import com.tamakara.litebooth.service.FileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "文件模块接口")
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/uploadFile")
    public ResponseEntity<String> updateFile(
            @RequestParam("file") MultipartFile file
    ) {
        Long fileId = fileService.uploadFile(file);
        return ResponseEntity.ok(fileId.toString());
    }
}
