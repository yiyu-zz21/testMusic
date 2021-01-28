package com.xiang.pdftoexcel.service;

import com.xiang.pdftoexcel.entity.Data;
import com.xiang.pdftoexcel.util.ExcelUtils;
import com.xiang.pdftoexcel.util.OutputExcel;
import com.xiang.pdftoexcel.util.ReadPdfUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CreatePdfWindow {
    @Autowired
    private ReadPdfUtil readPdfUtil;
    private List<Data> dataList = new ArrayList<>();
    public void createPdf() {
        JFrame jf = new JFrame("PDF转换");
        jf.setSize(400, 250);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

        // 创建文本区域, 用于显示相关信息
        JTextArea msgTextArea = new JTextArea(10, 30);
        msgTextArea.setLineWrap(true);
        panel.add(msgTextArea);

        JButton openBtn = new JButton("打开");
        openBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFileOpenDialog(jf, msgTextArea);
            }
        });
        panel.add(openBtn);

        JButton saveBtn = new JButton("关闭");
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //showFileSaveDialog(jf, msgTextArea);
                System.exit(0);
            }
        });
        panel.add(saveBtn);

        jf.setContentPane(panel);
        jf.setVisible(true);
    }

    /*
     * 打开文件
     */
    private void showFileOpenDialog(Component parent, JTextArea msgTextArea) {
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();

        // 设置默认显示的文件夹为当前文件夹
        fileChooser.setCurrentDirectory(new File("."));

        // 设置文件选择的模式（只选文件、只选文件夹、文件和文件均可选）
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        // 设置是否允许多选
        fileChooser.setMultiSelectionEnabled(true);

        // 添加可用的文件过滤器（FileNameExtensionFilter 的第一个参数是描述, 后面是需要过滤的文件扩展名 可变参数）
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("zip(*.zip, *.rar)", "zip", "rar"));
        // 设置默认使用的文件过滤器
        fileChooser.setFileFilter(new FileNameExtensionFilter("pdf(*.pdf)", "pdf"));

        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showOpenDialog(parent);

        //excel对象
        OutputExcel outputExcel = new OutputExcel();

        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"确定", 则获取选择的文件路径
//                File file = fileChooser.getSelectedFile();

            // 如果允许选择多个文件, 则通过下面方法获取选择的所有文件
            File[] files = fileChooser.getSelectedFiles();
            for (File file1 : files) {
                Data data = readPdfUtil.getTextFromPDF(file1);
                dataList.add(data);
//                    System.out.println("-----------------------------------------------");
            }
            //将数据都写到LIST集合中
            if (dataList.size() != 0){
                //生成EXCEL,可以采用静态工具类，直接把list放进去
                for (int i = 0 ; i < dataList.size(); i++){
                    System.out.println(dataList.get(i).toString());
                }
            }
            //调用写入Excel类输出
//            outputExcel.readPdfToExcel(dataList);
//                msgTextArea.append("打开文件: " + file.getAbsolutePath() + "\n\n");
            HSSFWorkbook workbook = ExcelUtils.getHSSFWorkbook("sheet1", dataList, null);
            int flag = ExcelUtils.export(workbook);
            if (flag == 1){
                msgTextArea.append("Excel已生成到软件目录下，请查看");
            }else {
                msgTextArea.append("Excel生成失败，请重试或联系维护人员");
            }
        }
    }

    /*
     * 选择文件保存路径
     */
    private void showFileSaveDialog(Component parent, JTextArea msgTextArea) {
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();

        // 设置打开文件选择框后默认输入的文件名
        fileChooser.setSelectedFile(new File("测试文件.zip"));

        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showSaveDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"保存", 则获取选择的保存路径
            File file = fileChooser.getSelectedFile();
            msgTextArea.append("保存到文件: " + file.getAbsolutePath() + "\n\n");
        }
    }
}
