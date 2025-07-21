package com.artesanalbeer.artesanalbeerstore.service.filesystem;

import org.springframework.web.multipart.MultipartFile;

public interface FileSystemService {
    boolean storeFile(MultipartFile file);

}
