package com.artesanalbeer.artesanalbeerstore.service.imagestorage;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ImgBBImageStorageTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private ImageStorageService imageStorageService;

    @Test
    void uploadAndDeleteImage() throws Exception {

        String TESTING_IMAGE_FIXTURE = "/fixtures/images/testing.png";
        InputStream is = getClass().getResourceAsStream(TESTING_IMAGE_FIXTURE);
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "testing.png",
                "image/png",
                is
        );

        String responseBody = imageStorageService.uploadImage(mockFile, "test-image");


        JsonNode json = objectMapper.readTree(responseBody);
        assertTrue(json.get("success").asBoolean());

        JsonNode data = json.get("data");
        String imageUrl = data.get("url").asText();
        String deleteUrl = data.get("delete_url").asText();

        assertNotNull(imageUrl);
        assertNotNull(deleteUrl);

    }
}
