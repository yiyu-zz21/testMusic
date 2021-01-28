package com.xiang.pdftoexcel;

import com.xiang.pdftoexcel.controller.CreateWindow;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class PdftoexcelApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = new SpringApplicationBuilder(PdftoexcelApplication.class).headless(false).run(args);
        CreateWindow createWindow = (CreateWindow)configurableApplicationContext.getBean("createWindowImpl");
        createWindow.create();
    }

}
