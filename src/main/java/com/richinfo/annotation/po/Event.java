package com.richinfo.annotation.po;

import java.util.List;

public class Event {

    private String type;//事件类型(吸毒、贩毒、带货、吸毒贩毒)
    private List<EventPerson> persons;//参与人
    private String place;//地点
    private String time;//时间
    private List<Account> accounts;//交易账号
    private List<Goods> goods;//物品
    private List<Car> cars;//车
    private String tag;//标签

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<EventPerson> getPersons() {
        return persons;
    }

    public void setPersons(List<EventPerson> persons) {
        this.persons = persons;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
