package org.advisor.file.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"default", "test", "dev"})
public class FileDoneServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FileInfoService infoService;

    private MockMultipartFile testFile;
    private final String TEST_GID = "test-gid";
    private final String TEST_LOCATION = "test-location";

    @BeforeEach
    void setUp() {
        testFile = new MockMultipartFile("file", "test1.png", MediaType.IMAGE_PNG_VALUE, new byte[] {1, 2, 3, 4});
    }

    @Test
    void whenUploadWithDoneTrue_shouldMarkFilesAsDone() throws Exception {
        // 파일 업로드 요청 (done=true 설정)
        mockMvc.perform(multipart("/upload")
                        .file(testFile)
                        .param("gid", TEST_GID)
                        .param("location", TEST_LOCATION)
                        .param("done", "false"))
                .andExpect(status().isCreated());
    }
}