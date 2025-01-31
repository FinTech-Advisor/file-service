package org.advisor.file.services;

import org.advisor.file.controllers.RequestUpload;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ActiveProfiles({"default", "test"})
public class FileDownloadServiceTest {
    @Autowired
    private FileDownloadService service;

    @Test
    void DownloadSuccessTest(){
        assertDoesNotThrow(()->{
            RequestUpload form = new RequestUpload();

            MockMultipartFile image = new MockMultipartFile(
                    "feed1.jpg",
                    "feed1.jpg",
                    "image/jpg",
                    new FileInputStream(new File("C:/Users/admin/Downloads/feed1.jpg")));

            MockMultipartFile[] files = {image};

            form.setGid("asdf");
            form.setLocation("asdf");
            form.setDone(false);
            form.setContentType("image/jpg");
            form.setFiles(files);

        });
    }
}