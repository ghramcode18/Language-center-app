package Geeks.languagecenterapp.Tools;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FilesManagement {

    // Define the upload directory path relative to the project's root directory
    public static final String UPLOAD_DIR = "language-center-app/uploads";  // This will create an 'uploads' directory in the project root

    public static String uploadSingleFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        try {
            // Get the absolute path of the project directory
            Path projectDir = Paths.get("").toAbsolutePath();

            // Combine project directory path with the upload directory
            Path uploadPath = projectDir.resolve(UPLOAD_DIR);

            // Create the uploads directory if it doesn't exist
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Save the file to the uploads directory
            Path filePath = uploadPath.resolve(Objects.requireNonNull(file.getOriginalFilename()));
            file.transferTo(filePath.toFile());
            return filePath.toAbsolutePath().toString();

        } catch (IOException e) {
            System.err.println("Error uploading file: " + e.getMessage());
            return null;
        }
    }

    public static List<String> uploadMultipleFile(List<MultipartFile> files) {
        List<String> filePaths = new ArrayList<>();

        if (files == null || files.isEmpty()) {
            return null;
        }
        try {
            // Get the absolute path of the project directory
            Path projectDir = Paths.get("").toAbsolutePath();

            // Combine project directory path with the upload directory
            Path uploadPath = projectDir.resolve(UPLOAD_DIR);

            // Create the uploads directory if it doesn't exist
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Save each file in the list to the uploads directory
            for (MultipartFile element : files) {
                if (element != null && !element.isEmpty()) {
                    Path filePath = uploadPath.resolve(Objects.requireNonNull(element.getOriginalFilename()));
                    element.transferTo(filePath.toFile());
                    filePaths.add(filePath.toAbsolutePath().toString());
                }
            }

        } catch (IOException e) {
            System.err.println("Error uploading files: " + e.getMessage());
            return null;
        }
        return filePaths;
    }
}
