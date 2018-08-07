package com.richinfo.annotation.po;

import java.util.List;

public class Car {

    private String color;//颜色
    private String brand;//品牌
    private String type;//车型
    private String no;//车牌
    private List<CarPerson> persons;//相关人员

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public List<CarPerson> getPersons() {
        return persons;
    }

    public void setPersons(List<CarPerson> persons) {
        this.persons = persons;
    }
}
