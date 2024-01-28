package org.rbernalop.strategy.service;

import lombok.AllArgsConstructor;
import org.rbernalop.strategy.strategy.FileReadStrategy;
import org.rbernalop.strategy.strategy.TextFileReadStrategy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@AllArgsConstructor
public class FileReader {
    private final List<FileReadStrategy> fileReadStrategies;

    public List<String> readFile(MultipartFile multipartFile) {
        String fileExtension = getFileExtension(multipartFile);
        FileReadStrategy fileReadStrategy = getFileReadStrategyByExtension(fileExtension);
        return fileReadStrategy.readFile(multipartFile);
    }

    private FileReadStrategy getFileReadStrategyByExtension(String extension) {
        return fileReadStrategies.stream()
                .filter(fileReadStrategy -> fileReadStrategy.supports(extension))
                .findFirst()
                .orElse(new TextFileReadStrategy()); // default strategy
    }

    private String getFileExtension(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }

        String fileName = multipartFile.getOriginalFilename();
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return null;
        }

        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
