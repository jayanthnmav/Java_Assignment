package assignment;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Assignment03 {


    @BeforeClass
    public void setUp(){
    }

    @Test(priority = 0)
    public static void readingXlFileData() {
        try {

            String xlFile = "C:/Users/jayanthn/Downloads/Employee.xlsx";
            FileInputStream f = new FileInputStream(xlFile);
            Workbook workbook = new XSSFWorkbook(f);
            Sheet sheet = workbook.getSheet("Sheet1");
            int lastrow = sheet.getLastRowNum();

            for (int i = 0; i <= lastrow; i++) {
                Row row = sheet.getRow(i);
                int lastCell = row.getLastCellNum();

                for (int j = 0; j < lastCell; j++) {
                    System.out.print(row.getCell(j).getStringCellValue()+"   ||    ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 1)
    public static String validateDataAsPerExcel(String id,String colName){

        String data = null;
        try {
            String xlFile = "C:/Users/jayanthn/Downloads/Employee.xlsx";
            FileInputStream f = new FileInputStream(xlFile);
            Workbook workbook = new XSSFWorkbook(f);
            Sheet sheet = workbook.getSheet("Sheet1");
            int lastrow = sheet.getLastRowNum();
            System.out.println("*******Validate data as per excel*******");
            System.out.println("The last row number which has data is:"+lastrow);

            for(int i = 0; i<=lastrow; i++){
                Row row = sheet.getRow(i);
                int lastCell = row.getLastCellNum();
                Cell cell = row.getCell(0);
                String runTimeTestCaseName = cell.getStringCellValue();

                if(runTimeTestCaseName.equals(id)) {
                    Row rowNew = sheet.getRow(0);
                    for (int j = 1; j < lastCell; j++) {
                        Cell cell1 = rowNew.getCell(j);
                        String runTimeCellValue = cell1.getStringCellValue();
                        if (runTimeCellValue.equals(colName)) {
                            data = sheet.getRow(i).getCell(j).toString();
                            System.out.println("The Designation is :" + data);
                        }
                    }
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }

        return data;
    }

    @Test(priority = 2)
    public static void modifyDataToExcel() {

        try {
            String xlFile = "C:/Users/jayanthn/Downloads/Employee.xlsx";
            FileInputStream f = new FileInputStream(xlFile);
            Workbook workbook = new XSSFWorkbook(f);
            Sheet sheet = workbook.getSheet("Sheet1");

            Row row = sheet.getRow(20);
            Cell cell = row.getCell(2);
            System.out.println("***********after modifying data*********");
            cell.setCellValue("Technical lead");
            readingXlFileData();

            FileOutputStream fos = new FileOutputStream(xlFile);
            workbook.write(fos);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test(priority = 3)
    public static void validateDataInExcel() {
        try {
            String xlFile = "C:/Users/jayanthn/Downloads/Employee.xlsx";
            FileInputStream f = new FileInputStream(xlFile);
            Workbook workbook = new XSSFWorkbook(f);
            Sheet sheet = workbook.getSheet("Sheet1");

            Row row = sheet.getRow(20);
            Cell cell = row.getCell(2);
            String cellValue = cell.getStringCellValue();
            System.out.println("***** after modifying data validate ****");
            System.out.println("The designation is:"+cellValue);
            assert cellValue.equals("Technical lead");
            readingXlFileData();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Test(priority = 4)
    public static void deleteDataFromExcel(){
        try {
            String xlFile = "C:/Users/jayanthn/Downloads/Employee.xlsx";
            FileInputStream f = new FileInputStream(xlFile);
            Workbook workbook = new XSSFWorkbook(f);
            Sheet sheet = workbook.getSheet("Sheet1");

            Row row = sheet.getRow(12);
            Cell cell = row.getCell(0);
            Cell cell1 = row.getCell(1);
            Cell cell2 = row.getCell(2);
            cell.setCellValue("");
            cell1.setCellValue("");
            cell2.setCellValue("");

            FileOutputStream fos = new FileOutputStream(xlFile);
            workbook.write(fos);
            System.out.println("******the row is deleted*******");
            readingXlFileData();

        }catch (Exception e){
            System.out.println(e);
        }
    }
}
