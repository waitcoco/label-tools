package com.richinfo.annotation.po;

public class Contact {
    private String name;//姓名
    private String type;//类型(手机号，座机，微信)
    private String no;//号码


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
