package com.artesanalbeer.artesanalbeerstore.service.filesystem;

import org.springframework.web.multipart.MultipartFile;

public class FileSystemServiceImp implements FileSystemService {
    @Override
    public boolean storeFile(MultipartFile file) {
        return false;
    }
}
