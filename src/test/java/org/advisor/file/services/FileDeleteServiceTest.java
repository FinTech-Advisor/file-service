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
public class FileDeleteServiceTest {
    @Autowired
    private FileDeleteService service;

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
    void deletesTest(){
        assertDoesNotThrow(()->{
            RequestUpload form = new RequestUpload();
            MockMultipartFile[] mockFiles = new MockMultipartFile[] {image, image2};

            form.setGid("");
            form.setLocation("");
            form.setDone(false);
            form.setContentType("image/jpg");
            form.setFiles(mockFiles);

            List<FileInfo> fileInfos = uploadService.upload(form);

            service.deletes(fileInfos.get(0).getGid(), fileInfos.get(0).getLocation());
        });
    }

    @Test
    void deleteTest(){
        assertDoesNotThrow(()->{
            RequestUpload form = new RequestUpload();
            MockMultipartFile[] mockFiles = new MockMultipartFile[] {image};

            form.setGid("");
            form.setLocation("");
            form.setDone(false);
            form.setContentType("image/jpg");
            form.setFiles(mockFiles);

            List<FileInfo> fileInfos = uploadService.upload(form);
            service.delete(fileInfos.get(0).getSeq());
        });
    }
}