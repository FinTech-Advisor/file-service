package org.advisor.file.services;

import org.advisor.file.entities.FileInfo;
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
public class FileDeleteServiceTest {
    @Autowired
    private FileDeleteService service;

    @Test
    void doneSuccessTest(){
        assertDoesNotThrow(()->{
            MockMultipartFile image = new MockMultipartFile(
                    "feed1.jpg",
                    "feed1.jpg",
                    "image/jpg",
                    new FileInputStream(new File("C:/Users/admin/Downloads/feed1.jpg")));
            MockMultipartFile[] files = {image};


            FileInfo item = new FileInfo();
            item.setSeq(1L);
            item.setGid("asdf");
            item.setFileName("test.jpg");
            item.setLocation("asdf");

            Long seq = item.getSeq();
            String gid = item.getGid();

            service.delete(seq);
        });
    }
}