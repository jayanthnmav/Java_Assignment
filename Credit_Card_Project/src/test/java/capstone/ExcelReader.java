package capstone;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelReader {
    public Map<Long, String> readExcel(String filePath) throws IOException {
        Map<Long, String> CCData = new HashMap<>();
        FileInputStream file = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // skip header
            long cc_num = (long) row.getCell(0).getNumericCellValue();
            String cc_type = row.getCell(1).getStringCellValue();
            CCData.put(cc_num, cc_type);
        }
        workbook.close();
        return CCData;
    }

}

