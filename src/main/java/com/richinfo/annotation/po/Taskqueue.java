package com.richinfo.annotation.po;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Taskqueue {
    @Id
    private String id;
    private String batchno;
    private List<String[]> filelist;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    public List<String[]> getFilelist() {
        return filelist;
    }

    public void setFilelist(List<String[]> filelist) {
        this.filelist = filelist;
    }
}
