package org.advisor.file.services;

import org.advisor.file.entities.FileInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ActiveProfiles({"default", "test"})
public class FileDoneServiceTest {
    @Autowired
    private FileDoneService service;

    @Test
    void doneSuccessTest(){
        assertDoesNotThrow(()->{
            List<FileInfo> items = new ArrayList<>();
            FileInfo item = new FileInfo();
            item.setSeq(1L);
            item.setGid("asdf");
            item.setFileName("test.jpg");
            items.add(item);

            String gid = item.getGid();
            String location = null;

            service.process(gid, location);
        });
    }
}
