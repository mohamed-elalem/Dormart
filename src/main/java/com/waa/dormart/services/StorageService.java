package com.waa.dormart.services;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {
    void store(MultipartFile file, String name);

    Path load(String filename);
}
