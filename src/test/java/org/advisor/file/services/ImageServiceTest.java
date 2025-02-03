package org.advisor.file.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({"default", "test"})
public class ImageServiceTest {

    @Autowired
    private ImageService service;

    @Test
    void imageTest(){
    }
}
