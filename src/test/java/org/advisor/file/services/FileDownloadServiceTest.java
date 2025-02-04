package org.advisor.file.services;

import org.advisor.file.controllers.RequestUpload;
import org.advisor.file.entities.FileInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ActiveProfiles({"default", "test"})
public class FileDownloadServiceTest {
    @Autowired
    private FileUploadService uploadService;

    @Autowired
    private FileDownloadService service;

    private MockMultipartFile image;
    private MockMultipartFile image2;

    @BeforeEach
    void init() throws IOException {
        image = new MockMultipartFile("file", "test.png", MediaType.IMAGE_PNG_VALUE, new FileInputStream(new File("C:/test1.png")));

    }

    @Test
    void downloadTest(){
        assertDoesNotThrow(()->{
            RequestUpload form = new RequestUpload();

            MockMultipartFile[] files = {image};

            form.setGid("");
            form.setLocation("");
            form.setDone(false);
            form.setContentType("image/png");
            form.setFiles(files);

            List<FileInfo> fileInfos = uploadService.upload(form);
            service.process(fileInfos.get(0).getSeq());
        });
    }
}