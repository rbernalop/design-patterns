package org.rbernalop.strategy.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class TextFileReadStrategy implements FileReadStrategy {

    @Override
    public List<String> readFile(MultipartFile multipartFile) {
        List<String> lines = new ArrayList<>();

        if (multipartFile.isEmpty()) {
            return lines;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }

        return lines;
    }

    @Override
    public boolean supports(String fileType) {
        return fileType.equals("txt");
    }
}
