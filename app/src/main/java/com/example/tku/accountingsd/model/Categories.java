package com.example.tku.accountingsd.model;



public class Categories {

    int id;
    String title;
    String fileName;
    Integer color;
    int type;

    public Categories() {
    }

    public Categories(String title, String fileName, Integer color, int type) {
        this.title = title;
        this.fileName = fileName;
        this.color = color;
        this.type = type;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color){
        this.color = color;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}



