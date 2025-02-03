package org.advisor.file.controllers;


import org.advisor.file.entities.FileInfo;
import org.advisor.file.services.FileUploadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles({"default", "test", "dev"})
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FileUploadService uploadService;

    private MockMultipartFile file1;
    private MockMultipartFile file2;
    private MockMultipartFile[] files;

    @BeforeEach
    void init() throws Exception{
        file1 = new MockMultipartFile("file", "test1.png", MediaType.IMAGE_PNG_VALUE, new FileInputStream(new File ("C:/Users/admin/Downloads/feed1.jpg")));
        file2 = new MockMultipartFile("file2", "test2.png", MediaType.IMAGE_PNG_VALUE, new FileInputStream(new File ("C:/Users/admin/Downloads/feed2.jpg")));
        files = new MockMultipartFile[]{file1, file2};
    }

    @Test
    void fileUploadTest() throws Exception{
        assertDoesNotThrow(()->{
            RequestUpload form = new RequestUpload();
            form.setGid("");
            form.setLocation("");
            form.setDone(false);
            form.setContentType("image/jpg");
            form.setFiles(files);

            List<FileInfo> fileInfos = uploadService.upload(form);
        });
    }
}