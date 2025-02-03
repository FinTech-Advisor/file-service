package org.advisor.file.services;

import org.advisor.file.controllers.RequestThumb;
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
public class ThumbnailServiceTest {

    @Autowired
    private ThumbnailService service;

    @Autowired
    private FileUploadService uploadService;

    private MockMultipartFile image;

    @BeforeEach
    void init() throws IOException {
        image = new MockMultipartFile("file", "test.png", MediaType.IMAGE_PNG_VALUE, new FileInputStream(new File("C:/test1.png")));
    }

    @Test
    void ThumbnailTest(){
        assertDoesNotThrow(()->{

            RequestUpload uploadForm = new RequestUpload();
            MockMultipartFile[] mockFiles = new MockMultipartFile[] {image};

            uploadForm.setGid("");
            uploadForm.setLocation("");
            uploadForm.setDone(false);
            uploadForm.setContentType("image/jpg");
            uploadForm.setFiles(mockFiles);

            List<FileInfo> fileInfos = uploadService.upload(uploadForm);

            RequestThumb form = new RequestThumb();
            String url = fileInfos.get(0).getFileUrl();
            form.setSeq(0L);
            form.setUrl(url);
            form.setWidth(50);
            form.setHeight(50);

            service.create(form);
        });
    }
}