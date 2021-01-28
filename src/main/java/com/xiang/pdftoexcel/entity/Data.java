package com.xiang.pdftoexcel.entity;

/**
 * 实体类：用于存放pdf中读取出来的数据
 */
public class Data {
    private String title; //文章名称
    private String result; //文章结论

    public Data() {
    }

    public Data(String title, String result) {
        this.title = title;
        this.result = result;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Data{" +
                "title='" + title + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
