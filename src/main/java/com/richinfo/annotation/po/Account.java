package com.richinfo.annotation.po;

public class Account {

    private String name;//所属人（户名）
    private String type;//账号类型（xx银行卡，支付宝，微信）
    private String tradetype;//交易类型（付款方、收款方）
    private String no;//账号

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

    public String getTradetype() {
        return tradetype;
    }

    public void setTradetype(String tradetype) {
        this.tradetype = tradetype;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
