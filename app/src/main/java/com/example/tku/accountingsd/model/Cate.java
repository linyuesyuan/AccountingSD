package com.example.tku.accountingsd.model;

import java.util.WeakHashMap;

public class Cate {

    long id;
    String title;
    String imageId;

    public Cate() {
    }

    public Cate(String title, String imageId) {
        this.title = title;
        this.imageId = imageId;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageId() {
        return this.imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }


}
