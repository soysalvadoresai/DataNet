package com.example.datanet_app.Models;

public class Name {
    private String name;
    private long date;

    public Name() {}

    public Name(String name, long date){
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
