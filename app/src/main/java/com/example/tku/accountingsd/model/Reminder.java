package com.example.tku.accountingsd.model;

public class Reminder {

    long id;
    String title;
    String startDate;
    String dateCycle;
    String money;

    public Reminder(String title, String startDate, String dateCycle, String money) {
        this.title = title;
        this.startDate = startDate;
        this.dateCycle = dateCycle;
        this.money = money;
    }

    public Reminder() {
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

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDateCycle() {
        return this.dateCycle;
    }

    public void setDateCycle(String dateCycle) {
        this.dateCycle = dateCycle;
    }

    public String getMoney() {
        return this.money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

}
