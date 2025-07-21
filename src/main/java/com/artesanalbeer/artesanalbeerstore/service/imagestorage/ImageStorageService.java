package com.artesanalbeer.artesanalbeerstore.service.imagestorage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageStorageService {

    String uploadImage(MultipartFile file, String filename) throws IOException;
}
