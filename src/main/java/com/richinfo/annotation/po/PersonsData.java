package com.richinfo.annotation.po;

import org.springframework.data.annotation.Id;

import java.util.List;

public class PersonsData extends BasePo{
    @Id
    private String id;
    private String taskno;
    private String taskbatchno;
    private List<PersonInfo> persons;
    private String filename;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskno() {
        return taskno;
    }

    public void setTaskno(String taskno) {
        this.taskno = taskno;
    }

    public String getTaskbatchno() {
        return taskbatchno;
    }

    public void setTaskbatchno(String taskbatchno) {
        this.taskbatchno = taskbatchno;
    }

    public List<PersonInfo> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonInfo> persons) {
        this.persons = persons;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
