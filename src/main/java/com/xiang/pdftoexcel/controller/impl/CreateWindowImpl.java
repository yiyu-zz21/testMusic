package com.xiang.pdftoexcel.controller.impl;

import com.xiang.pdftoexcel.controller.CreateWindow;
import com.xiang.pdftoexcel.service.CreatePdfWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.swing.*;

@Controller
public class CreateWindowImpl implements CreateWindow {
    @Autowired
    private CreatePdfWindow pdfWindow;
    @Override
    public void create() {
        pdfWindow.createPdf();
    }
}
