package com.example.tku.accountingsd.model;


public class Categories {

    int id;
    String title;
    //String imageId;

    public Categories() {
    }

    public Categories(String title) {
        this.title = title;
        //this.imageId = imageId;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
/*
    public String getImageId() {
        return this.imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
*/
}



