package com.prerak.demo.recyclerViewwithcheckbox.model;

/**
 * Created by emxcel on 1/9/17.
 */

public class Student {

    private String name;
    private String emailID;
    private boolean isSelected;
    private String img;

    public Student() {

    }

    public Student(String name, String emailID) {
        this.name = name;
        this.emailID = emailID;
    }

    public Student(String name, String emailID, boolean isSelected) {
        this.name = name;
        this.emailID = emailID;
        this.isSelected = isSelected;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
