package com.itheima.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class POITest {
    @Test
    public void test1() throws Exception {
        //加载指定文件，创建一个excel文件
        XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(new File("d:\\dsy.xlsx")));
        XSSFSheet rows = excel.getSheetAt(0);
        //遍历sheff标签页，获取每一行数据
        for (Row row : rows) {
           // 遍历行，获取每一个单元格对象
            for (Cell cell : row) {
                System.out.println(cell.getStringCellValue());
            }
        }
    }
    @Test
    public void test2() throws Exception{
        //在内存中创建一个excel文件
        XSSFWorkbook excel = new XSSFWorkbook();
        XSSFSheet sheet = excel.createSheet("传智播客");
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("姓名");
        row.createCell(1).setCellValue("性别");
        row.createCell(2).setCellValue("年龄");

        XSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("张三");
        row1.createCell(1).setCellValue("男");
        row1.createCell(2).setCellValue(10);

        FileOutputStream fos = new FileOutputStream(new File("d:\\hello.xlsx"));
        excel.write(fos);
        excel.close();
    }
}
