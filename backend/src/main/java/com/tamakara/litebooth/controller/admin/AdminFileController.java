package com.tamakara.litebooth.controller.admin;

import com.tamakara.litebooth.common.result.Result;
import com.tamakara.litebooth.service.FileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "文件模块接口")
@RestController
@RequestMapping("/admin/files")
@RequiredArgsConstructor
public class AdminFileController {
    private final FileService fileService;

    @PostMapping
    public Result<Long> uploadFile(
            @RequestParam("file") MultipartFile file
    ) {
        Long fileId = fileService.uploadFile(file);
        return Result.success(fileId);
    }
}
