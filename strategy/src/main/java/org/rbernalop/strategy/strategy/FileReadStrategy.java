package org.rbernalop.strategy.strategy;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileReadStrategy {
    List<String> readFile(MultipartFile multipartFile);
    boolean supports(String fileType);
}
