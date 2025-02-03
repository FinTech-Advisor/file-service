package org.advisor.file.services;

import org.advisor.file.controllers.RequestThumb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ActiveProfiles({"default", "test"})
public class ThumbnailServiceTest {

    @Autowired
    private ThumbnailService service;
    private MockMultipartFile image;

    @BeforeEach
    void init() throws IOException {
        image = new MockMultipartFile(
                "feed1.jpg",
                "feed1.jpg",
                "image/jpg",
                new FileInputStream(new File("C:/Users/admin/Downloads/feed1.jpg")));
    }

    @Test
    void ThumbnailTest(){
        assertDoesNotThrow(()->{
            RequestThumb form = new RequestThumb();
            String url = image.getInputStream().toString();
            form.setSeq(0L);
            form.setUrl(url);
            form.setWidth(50);
            form.setHeight(50);

            service.create(form);
        });
    }
}