package com.artesanalbeer.artesanalbeerstore.service.imagestorage;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {

    String uploadImage(MultipartFile file, String filename) throws IOException;
}
