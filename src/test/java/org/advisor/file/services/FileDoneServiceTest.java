package org.advisor.file.services;

import org.advisor.file.controllers.RequestUpload;
import org.advisor.file.entities.FileInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
@AutoConfigureMockMvc
@ActiveProfiles({"default", "test", "dev"})
public class FileDoneServiceTest {
    @Autowired
    private FileDoneService service;

    @Autowired
    private FileUploadService uploadService;

    private MockMultipartFile image;
    private MockMultipartFile image2;

    @BeforeEach
    void init() throws IOException {
        image = new MockMultipartFile("file", "test.png", MediaType.IMAGE_PNG_VALUE, new FileInputStream(new File("C:/test1.png")));
        image2 = new MockMultipartFile("file2", "test2.png", MediaType.IMAGE_PNG_VALUE, new FileInputStream(new File("C:/test2.png")));
    }

    @Test
    void doneTest(){
        assertDoesNotThrow(()->{
            RequestUpload form = new RequestUpload();
            MockMultipartFile[] mockFiles = new MockMultipartFile[] {image, image2};

            List<FileInfo> fileInfos = uploadService.upload(form);

            service.process(fileInfos.get(0).getGid(), fileInfos.get(0).getLocation());
        });
    }
}