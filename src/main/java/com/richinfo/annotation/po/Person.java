package com.richinfo.annotation.po;

import java.util.List;

public class Person {

    private String name;//姓名
    private String alias;//别名
    private String age;//年龄
    private String origin;//籍贯
    private String birthday;//出生日期
    private String nation;//民族
    private String sex;//性别（男，女）
    private String work;//工作
    private String education;//文化程度
    private String domicile;//户籍地
    private String place;//居住地
    private List<Family> familys;//家庭成员
    private List<Contact> contacts;//联系方式
    private Identify identify;//身份证件
    private Preconviction preconviction;//前科
    private String tag;//标签
    private List<Relation> relations;//关联人

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getDomicile() {
        return domicile;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public List<Family> getFamilys() {
        return familys;
    }

    public void setFamilys(List<Family> familys) {
        this.familys = familys;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public Identify getIdentify() {
        return identify;
    }

    public void setIdentify(Identify identify) {
        this.identify = identify;
    }

    public Preconviction getPreconviction() {
        return preconviction;
    }

    public void setPreconviction(Preconviction preconviction) {
        this.preconviction = preconviction;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<Relation> getRelations() {
        return relations;
    }

    public void setRelations(List<Relation> relations) {
        this.relations = relations;
    }
}
