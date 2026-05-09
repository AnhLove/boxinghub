package com.boxinghub.utils;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

public class FileUploadUtil {
    public static String saveFile(String uploadDir, MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        System.out.println("Path thực tế: " + uploadPath.toAbsolutePath().toString());
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Đổi tên file thành UUID để tránh trùng lặp
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        try (var inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + fileName, ioe);
        }
    }
}