package com.richinfo.annotation.po;

public class EventPerson {

    private String name;//姓名
    private String alias;//别名
    private String type;//角色(买方、卖房、中间商、吸毒者)

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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
