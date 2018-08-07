package com.richinfo.annotation.po;

import org.springframework.data.annotation.Id;

public class Carpic extends BasePo{
    @Id
    private String id;
    private String person;
    private String carno;
    private String filename;
    private String taskno;
    private String taskbatchno;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getCarno() {
        return carno;
    }

    public void setCarno(String carno) {
        this.carno = carno;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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
}
