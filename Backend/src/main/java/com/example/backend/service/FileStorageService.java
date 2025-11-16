package com.example.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {
    private final String ROOT_DIR = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;
    private final String IMAGE_DIR = ROOT_DIR + "images" + File.separator;
    private final String VIDEO_DIR = ROOT_DIR + "videos" + File.separator;

    private final String IMAGE_FILE_PATTERN = ".*\\.(jpg|jpeg|png|gif|webp)$";
    private final String VIDEO_FILE_PATTERN = ".*\\.(mp4|mov|avi|mkv)$";

    public String saveFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        System.out.println("Saving file: " + originalFilename + ", size=" + file.getSize());

        if (originalFilename == null || originalFilename.isBlank()) {
            throw new IOException("File không hợp lệ");
        }

        // Xác định thư mục lưu
        String folder;
        if (originalFilename.toLowerCase().matches(IMAGE_FILE_PATTERN)) {
            folder = IMAGE_DIR;
        } else if (originalFilename.toLowerCase().matches(VIDEO_FILE_PATTERN)) {
            folder = VIDEO_DIR;
        } else {
            throw new IOException("Định dạng file không được hỗ trợ: " + originalFilename);
        }

        // Tạo thư mục nếu chưa có
        File dir = new File(folder);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Không thể tạo thư mục lưu trữ: " + folder);
        }

        // 👉 Sử dụng cùng 1 timestamp cho tên file và lưu file
        long timestamp = System.currentTimeMillis();
        String fileName = timestamp + "_" + originalFilename;
        String filePath = folder + fileName;

        File destination = new File(filePath);
        file.transferTo(destination);

        return fileName; // trả về đúng tên file đã lưu
    }

    public boolean deleteFile(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            return true; // Không có tên file để xóa
        }

        try {
            // 1. Xác định loại file (ảnh hay video) để tìm đúng thư mục
            String folder;
            if (fileName.toLowerCase().matches(IMAGE_FILE_PATTERN)) {
                folder = IMAGE_DIR;
            } else if (fileName.toLowerCase().matches(VIDEO_FILE_PATTERN)) {
                folder = VIDEO_DIR;
            } else {
                // Nếu không xác định được loại file, không thể xóa
                System.err.println("Không thể xác định loại file để xóa: " + fileName);
                return false;
            }

            // 2. Tạo đường dẫn tuyệt đối đến file
            Path filePath = Paths.get(folder, fileName);

            // 3. Thực hiện xóa vật lý
            if (Files.exists(filePath)) {
                Files.delete(filePath);
                return true;
            } else {
                // File không tồn tại vẫn được coi là thành công (đã xóa)
                return true;
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi xóa file " + fileName + ": " + e.getMessage());
            return false;
        }
    }

}
