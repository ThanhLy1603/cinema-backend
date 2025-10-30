package com.example.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileStorageService {
    private final String ROOT_DIR = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;
    private final String IMAGE_DIR = ROOT_DIR + "images" + File.separator;
    private final String VIDEO_DIR = ROOT_DIR + "videos" + File.separator;

    private final String IMAGE_FILE_PATTERN = ".*\\.(jpg|jpeg|png|gif)$";
    private final String VIDEO_FILE_PATTERN = ".*\\.(mp4|mov|avi|mkv)$";

    public String saveFile(MultipartFile file) throws IOException {
        String fileName;
        String originalFilename = file.getOriginalFilename();
        System.out.println("Saving file: " + originalFilename + ", size=" + file.getSize());

        if (originalFilename == null || originalFilename.isBlank()) {
            throw new IOException("File không hợp lệ");
        }

        String folder;
        if (originalFilename.toLowerCase().matches(IMAGE_FILE_PATTERN)) {
            folder = IMAGE_DIR;
        } else if (originalFilename.toLowerCase().matches(VIDEO_FILE_PATTERN)) {
            folder = VIDEO_DIR;
        } else {
            throw new IOException("Định dạng file không được hỗ trợ: " + originalFilename);
        }

        File dir = new File(folder);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Không thể tạo thư mục lưu trữ: " + folder);
        }

        String filePath = folder + System.currentTimeMillis() + "_" + originalFilename;
        File destination = new File(filePath);
        file.transferTo(destination);

        fileName = System.currentTimeMillis() + "_" + originalFilename;

        return fileName;
    }
}
