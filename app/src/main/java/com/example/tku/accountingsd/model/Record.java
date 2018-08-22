package com.example.tku.accountingsd.model;

public class Record {
    long id;
    String title;
    String date;
    Double money;
    String type;

    public Record(String title, String date, Double money, String type){
        this.title=title;
        this.date=date;
        this.money=money;
        this.type=type;
    }

    public Record(){}

    public long getId(){
        return this.id;
    }

    public void setId(long id){
        this.id=id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public String getDate(){
        return this.date;
    }

    public void setDate(String date){
        this.date=date;
    }

    public Double getMoney(){
        return this.money;
    }

    public void setMoney(Double money){
        this.money=money;
    }

    public String getType(){
        return this.type;
    }

    public void setType(String type){
        this.type=type;
    }
}
