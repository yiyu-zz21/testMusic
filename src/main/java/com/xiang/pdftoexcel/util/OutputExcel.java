package com.xiang.pdftoexcel.util;

import com.xiang.pdftoexcel.entity.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class OutputExcel {
    public Sheet readPdfToExcel( List<Data> dataList){
        //模板文件所在路径
        String myPath = "H:\\EOL1\\pdftoexcel\\src\\main\\resources\\moban.xlsx";
        ClassPathResource classPathResource = new ClassPathResource("/moban.xlsx");
        //工具类读取EXCEL模板文件
        ReadExcelUtil readExcelUtil = new ReadExcelUtil(myPath,"sheet");
        //表名
        String filename = "题目及结果";
        Data data = new Data();
        int row = 2;
        //表中数据转移到excel
        for (int i = 0 ;i < dataList.size() ; i++){
            data = dataList.get(i);
            int cell = 0;
            Sheet sheet = readExcelUtil.getSheet();
            Row row1 = sheet.createRow(row);
            Cell c = row1.createCell(cell);
            readExcelUtil.replaceCellValue(c,data.getTitle());
            cell++;
            c = row1.createCell(cell);
            readExcelUtil.replaceCellValue(c,data.getTitle());
            cell++;
            row++;
        }

        //excel文件生成的位置,这边有点小问题
        String path2 = System.getProperty("user.dir") + "\\";
        //让生成的文件不重复
        String name = path2 + filename + UUID.randomUUID().toString().replace("-", "") + ".xlsx";
        //生成的文件
        readExcelUtil.exportExcel(new File(name));
        //工具类获取Sheet对象,进行返回.
        Sheet sheet = readExcelUtil.getSheet();
//        System.out.println(sheet.getRow(0).getCell(0).toString());//测试Sheet对象有没有值
        return sheet;
    }
}
