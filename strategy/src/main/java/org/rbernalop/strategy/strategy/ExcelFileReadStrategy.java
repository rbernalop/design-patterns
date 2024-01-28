package org.rbernalop.strategy.strategy;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class ExcelFileReadStrategy implements FileReadStrategy {
    @Override
    public List<String> readFile(MultipartFile multipartFile) {
        List<String> rows = new ArrayList<>();

        if (multipartFile.isEmpty()) {
            return rows;
        }

        try (Workbook workbook = WorkbookFactory.create(multipartFile.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                StringBuilder rowString = new StringBuilder();
                for (Cell cell : row) {
                    String cellValue = getCellValueAsString(cell);
                    rowString.append(cellValue).append("; ");
                }
                rows.add(rowString.toString());
            }
        } catch (IOException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }

        return rows;
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return Double.toString(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    @Override
    public boolean supports(String fileType) {
        return fileType.equals("xlsx");
    }
}
