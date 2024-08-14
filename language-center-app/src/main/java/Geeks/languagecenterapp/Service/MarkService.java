package Geeks.languagecenterapp.Service;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.nio.file.Files;
import java.util.*;


@Service
public class MarkService {

    public static List<Map<String, String>> handleExcelFile(MultipartFile file) {
        List<Map<String, String>> data = new ArrayList<>();

        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() > 0) {
                    Iterator<Cell> cells = row.iterator();
                    Map<String, String> cellData = new HashMap<>();
                    int counter = 0;
                    while (cells.hasNext() && counter < 5) {
                        Cell cell = cells.next();
                        switch (counter) {
                            case 0: {
                                cellData.put("ID ", cell.toString());
                                break;
                            }
                            case 1: {
                                cellData.put("Name ", cell.toString());
                                break;
                            }
                            case 2: {
                                cellData.put("Phone ", cell.toString());
                                break;
                            }
                            case 3: {
                                cellData.put("Reading Mark ", cell.toString());
                                break;
                            }
                            case 4: {
                                cellData.put("Writing Mark ", cell.toString());
                                break;
                            }
                        }
                        counter++;
                    }
                    data.add(cellData);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
        return data;
    }

    public static Map<String, String> searchInExcelFile(List<Map<String, String>> data, String keyword) {
        for (Map<String, String> row : data) {
            if (row.containsValue(keyword)) {
                return row;
            }
        }
        return null;
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