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

@SpringBootTest
@ActiveProfiles({"default", "test"})
public class ImageServiceTest {

    @Autowired
    private ImageService service;

    @Autowired
    private FileUploadService uploadService;

    private MockMultipartFile image;

    @BeforeEach
    void init() throws IOException {
        image = new MockMultipartFile("file", "test.png", MediaType.IMAGE_PNG_VALUE, new FileInputStream(new File("C:/test1.png")));
    }
    @Test
    void imageTest(){
        RequestUpload uploadForm = new RequestUpload();
        MockMultipartFile[] mockFiles = new MockMultipartFile[] {image};

        uploadForm.setGid("");
        uploadForm.setLocation("");
        uploadForm.setDone(false);
        uploadForm.setContentType("image/jpg");
        uploadForm.setFiles(mockFiles);

        List<FileInfo> fileInfos = uploadService.upload(uploadForm);

        service.select(fileInfos.get(0).getSeq());
    }
}
