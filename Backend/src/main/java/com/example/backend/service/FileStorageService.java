package com.example.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileStorageService {
    private final String VIDEO_DIR = "uploads/videos";
    private final String IMAGE_DIR = "uploads/images";

    private final String IMAGE_FILE_PATTERN = ".*\\\\.(jpg|jpeg|png|gif)$";
    private final String VIDEO_FILE_PATTERN = ".*\\.(mp4|mov|avi|mkv)$";

    public String saveFile(MultipartFile file) throws IOException {
        String folder;
        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null) {
            throw new IOException("File không hợp lệ");
        }

        if (originalFilename.matches(IMAGE_FILE_PATTERN)) {
            folder = VIDEO_DIR;
        } else if (originalFilename.matches(IMAGE_DIR)) {
            folder = IMAGE_DIR;
        } else {
            throw new IOException("Định dạng file không được hỗ trợ: " + originalFilename);
        }

        File dir = new File(folder);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String filePath = folder + originalFilename;
        file.transferTo(new File(filePath));

        return filePath;
    }
}
