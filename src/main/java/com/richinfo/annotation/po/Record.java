package com.richinfo.annotation.po;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Record extends BasePo{

    @Id
    private String id;
    private String casename;//案件名称
    private String filename;//笔录文件名
    private String recordname;//笔录名称
    private String starttime;//开始时间
    private String endtime;//结束时间
    private String place;//地点
    private String asker;//询问人
    private String recorder;//记录人
    private Person person;//嫌疑人

    private String type;//类型
    private String tag;//标签

    private List<Person> personlist;//关联人

    private List<Event> events;//关联事件

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getRecordname() {
        return recordname;
    }

    public void setRecordname(String recordname) {
        this.recordname = recordname;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getAsker() {
        return asker;
    }

    public void setAsker(String asker) {
        this.asker = asker;
    }

    public String getRecorder() {
        return recorder;
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }

    public List<Person> getPersonlist() {
        return personlist;
    }

    public void setPersonlist(List<Person> personlist) {
        this.personlist = personlist;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public String getCasename() {
        return casename;
    }

    public void setCasename(String casename) {
        this.casename = casename;
    }
}
