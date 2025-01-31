package org.advisor.file.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RequestUpload {
    @NotBlank
    private String gid;
    private String location;
    private boolean done; // 업로드 하자마자 완료 처리
    public MultipartFile[] files;
    public boolean single;
    private String contentType;
}