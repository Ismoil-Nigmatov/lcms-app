package com.example.lcmsapp.controller;

import com.example.lcmsapp.dto.ApiResponse;
import com.example.lcmsapp.entity.Attachment;
import com.example.lcmsapp.exception.ResourceNotFoundException;
import com.example.lcmsapp.repository.AttachmentRepository;
import com.example.lcmsapp.service.AttachmentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class AttachmentController {
    private final AttachmentRepository attachmentRepository;
    private final AttachmentService attachmentService;
    private final Path root = Paths.get("C:\\PDP Lesson\\Backend G7\\lcms-app\\src\\main\\resources\\upload");


//    @PreAuthorize(value = "hasAnyRole('MANAGER','ADMIN')")
    @PostMapping("/upload")
    public ResponseEntity<?> upload(MultipartHttpServletRequest request) {
        ApiResponse apiResponse = attachmentService.uploadFileSystem(request);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

//    @PreAuthorize(value = "hasAnyRole('ADMIN','MANAGER','USER')")
    @SneakyThrows
    @GetMapping("/{id}")
    public ResponseEntity<?> getFile(@PathVariable UUID id) {

        Attachment attachment = attachmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("file", "id", id));

//        File file = new File(attachment.getUrl());

        Path file = root.resolve(attachment.getFileName());
        Resource resource = new UrlResource(file.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(attachment.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getFileName() + "\"")
                .body(resource);
    }


    //filesave DB
//    @PreAuthorize(value = "hasAnyRole('ADMIN','MANAGER')")
    @PostMapping("/uploadDB")
    public ResponseEntity<?> saveToDB(MultipartHttpServletRequest request) {
        ApiResponse response = attachmentService.uploadDB(request);
        return ResponseEntity.ok(response);
    }

//    @PreAuthorize(value = "hasAnyRole('ADMIN','MANAGER','USER')")
    @GetMapping("/downloadDB/{attachmentId}")
    public ResponseEntity<?> downloadDB(@PathVariable(value = "attachmentId") UUID id) {
       return attachmentService.downloadDB(id);
    }


}
