package com.xiang.pdftoexcel.util;

import com.xiang.pdftoexcel.entity.Data;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ExcelUtils {
    /**
     * 导出Excel
     * @param sheetName sheet名称
     * @param values 内容
     * @param wb HSSFWorkbook对象
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, List<Data> values, HSSFWorkbook wb){
        //标题是固定的，就不用传入的方式了
        String[] title = {"文章题目","摘要结果"};
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if(wb == null){
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);
        //设置默认列宽行高
        sheet.setDefaultColumnWidth(25);//列宽
        sheet.setDefaultRowHeightInPoints(40);//行高

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); //水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        style.setWrapText(true);//自动换行

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for(int i=0;i<values.size();i++){
            row = sheet.createRow(i + 1);
            //将内容按顺序赋给对应的列对象
            row.createCell(0).setCellValue(values.get(i).getTitle());
            row.getCell(0).setCellStyle(style);
            row.createCell(1).setCellValue(values.get(i).getResult());
            row.getCell(1).setCellStyle(style);
        }
        for(int i=0;i<title.length;i++){
            sheet.autoSizeColumn(i);
        }
        return wb;
    }
    public static int export(HSSFWorkbook wb){
        OutputStream outputStream = null;
        int flag = 0;
        //excel文件生成的位置,这边有点小问题
        String path2 = System.getProperty("user.dir") + "\\";
        //文件名
        String name = path2 + "结果输出表" + System.currentTimeMillis() + ".xls";
        try {
            outputStream = new FileOutputStream(new File(name).getAbsoluteFile());
            wb.write(outputStream);
            flag = 1;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(outputStream);
        }
        return flag;
    }
}
