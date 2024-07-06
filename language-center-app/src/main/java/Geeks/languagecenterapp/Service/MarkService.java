package Geeks.languagecenterapp.Service;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Service
public class MarkService {

    public static List<String[]> handleExcelFile(MultipartFile file) {
        List<String[]> data = new ArrayList<>();

        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) { // 1 Sami 09446346423 68 89 78

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                Iterator<Cell> cells = row.iterator();

                List<String> cellData = new ArrayList<>();
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    switch (cell.getCellType()) {
                        case STRING:
                            cellData.add(cell.getStringCellValue());
                            break;
                        case NUMERIC:
                            cellData.add(String.valueOf(cell.getNumericCellValue()));
                            break;
                        default:
                            cellData.add("Invalid Type");
                    }
                }
                data.add(cellData.toArray(new String[0]));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
        return data;
    }

    public static List<String[]> searchInExcelFile(List<String[]> data, String keyword) {
        List<String[]> searchResults = new ArrayList<>();
        for (String[] row : data) {
            for (String cell : row) {
                if (cell != null && cell.contains(keyword)) {
                    searchResults.add(row);
                    break;
                }
            }
        }
        return searchResults;
    }

    public static MultipartFile convertFileToMultipartFile(String filePath) {
        File file = new File(filePath);
        MultipartFile multipartFile;
        try {
            FileInputStream input = new FileInputStream(file);
            multipartFile = new MockMultipartFile("file", file.getName(),
                    Files.probeContentType(file.toPath()), input);
            input.close();
            return multipartFile;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Error When Converting File loaded from DataBase");
        return null;
    }

}
