package com.xiang.pdftoexcel.util;//import org.pdfbox.pdfparser.PDFParser;
//import org.pdfbox.pdmodel.PDDocument;
//import org.pdfbox.util.PDFTextStripper;

import com.xiang.pdftoexcel.entity.Data;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

//解析PDF文件为文本
@Component
public class ReadPdfUtil {
    public Data getTextFromPDF(File file) {
        String result = null;
        FileInputStream is = null;
        PDDocument document = null;
        int flag = 0;
        Data data = new Data();
        //这个是为了解析题目有多少行
//        Pattern p = Pattern.compile("^[A-Z][^A-Z]*$");
        Pattern p = Pattern.compile("^[A-Z]");
        long start = System.currentTimeMillis();
        long end = 0;
        StringBuilder titleString = new StringBuilder();
        StringBuilder resultString = new StringBuilder();
        try {
            is = new FileInputStream(file);
//            PDFParser parser = new PDFParser(is);
//            parser.parse();
//            document = parser.getPDDocument();
            document = PDDocument.load(is);
            document.getClass();
            if (!document.isEncrypted()) {

                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);

                PDFTextStripper tStripper = new PDFTextStripper();

                result = tStripper.getText(document);

                String lines[] = result.split("\\r?\\n");
//                String lines[] = result.split("\\r");
                //这里已经把pdf转换为字符串了，然后写个逻辑取出题目和结果
                //题目这里有问题，可能会有多行题目，目前想到的办法是题目只有首字母大写，
                // 并且第一行肯定是题目，如果后面行中含有大写字母，说明题目结束，不继续取值
                for (String line : lines) {

//                    System.out.println(line);
                    //如果有REVIEW ARTICLE，则取下一行作为题目,标识为1
                    //如果有Abstract，标识为2
                    //如果标识为2，找Results:，如果有，标识为3，取后面的数据
                    if ("REVIEW ARTICLE".equals(line)){
                        flag = 1;
                        continue;
                    }else if ("Abstract".equals(line)){
                        flag = 2;
                        continue;
                    }
                    if (flag == 1){
                        //辩题是有首字母大写的，第二次碰到首字母大写，就说明标题结束了，不取值
                        if (data.getTitle() == null){
                            titleString.append(line);
                        }else {
                            if (p.matcher(line).find()) {
                                flag = 0;
                                continue;
                            }else {
                                titleString.append(" ");
                                titleString.append(line);
                            }
                        }
                        data.setTitle(titleString.toString());
                    }
                    //
                    if (flag == 2 && line.startsWith("Results:")){
                        flag = 3;
                    }
                    if (flag == 3){
                        if (line.startsWith("Conclusion:")){
                            flag = 0;
                            data.setResult(resultString.toString());
                            break;
                        }
                        if (resultString.length() > 0 ){
                            resultString.append("\\n");
                        }
                        resultString.append(line);
                    }
                }
                flag = 0;
                end = System.currentTimeMillis();
            }

//            document = PDDocument.load(is);
//            PDFTextStripper stripper = new PDFTextStripper();
//            result = stripper.getText(document);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("---------------读取时间为：" + ( end - start) );
        return data;
    }
}