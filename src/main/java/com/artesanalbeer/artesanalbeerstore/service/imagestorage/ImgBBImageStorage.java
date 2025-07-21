package com.artesanalbeer.artesanalbeerstore.service.imagestorage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;


@Service
public class ImgBBImageStorage implements ImageStorageService {

    @Value("${imgbb.api-key}")
    private String API_KEY;

    @Value("${imgbb.url}")
    private String API_URL;


    @Override
    public String uploadImage(MultipartFile file, String fileName) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String base64Image = Base64.getEncoder().encodeToString(file.getBytes());
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("key", API_KEY);
        body.add("image", base64Image);
        body.add("name", fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(API_URL, request, String.class);
        return response.getBody();
    }

}
