package com.example.tku.accountingsd.model;



public class Categories {

    int id;
    String title;
    String fileName;
    Integer color;
    int type;
    int imageId;

    public Categories() {
    }

    public Categories(String title, int imageId) {
        this.title = title;
        this.imageId = imageId;
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

    public int getImageId(){
        return imageId;
    }

    public void setImageId(int imageId){
        this.imageId=imageId;
    }


}



