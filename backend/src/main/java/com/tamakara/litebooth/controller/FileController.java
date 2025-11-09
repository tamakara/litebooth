package com.tamakara.litebooth.controller;

import com.tamakara.litebooth.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "文件接口")
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
//    private final FileService fileService;

//    @Operation(summary = "上传文件")
//    @PostMapping("/uploadFile")
//    public ResponseEntity<Long> uploadFile(
//            @RequestParam("file") MultipartFile file
//    ) {
//        Long fileId = fileService.uploadFile(file);
//        return ResponseEntity.ok(fileId);
//    }
//
//    @Operation(summary = "获取文件URL")
//    @GetMapping("/getFileUrl")
//    public ResponseEntity<String> getFileUrl(
//            @RequestParam("fileId") Long fileId,
//            @RequestParam("time") Integer time
//    ) {
//        String url = fileService.getFileUrl(fileId, time);
//        return ResponseEntity.ok(url);
//    }

}
