package org.advisor.file.services;

import org.advisor.file.entities.FileInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ActiveProfiles({"default", "test"})
public class FileDeleteServiceTest {
    @Autowired
    private FileDeleteService service;

    private MockMultipartFile image;

    @BeforeEach
    void init() {
        image = new MockMultipartFile("file", "test1.png", MediaType.IMAGE_PNG_VALUE, new byte[] {1, 2, 3, 4});
    }

    @Test
    void deleteTest(){
        assertDoesNotThrow(()->{
            String url = image.getInputStream().toString();
            FileInfo item = new FileInfo();
            item.setSeq(0L);
            item.setGid("asdf");
            item.setFileName("file.jpg");
            item.setFileUrl(url);

            Long seq = item.getSeq();

            service.delete(seq);
        });
    }
}