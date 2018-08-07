package com.richinfo.annotation.po;

import org.springframework.data.annotation.Id;

public class TaskbatchUser {
    @Id
    private String id;
    private Sysuser user;//用户
    private String taskbatchno;//任务批次号
    private String createtime;//接收时间
    private Integer docount;//完成数量

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Sysuser getUser() {
        return user;
    }

    public void setUser(Sysuser user) {
        this.user = user;
    }

    public String getTaskbatchno() {
        return taskbatchno;
    }

    public void setTaskbatchno(String taskbatchno) {
        this.taskbatchno = taskbatchno;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Integer getDocount() {
        return docount;
    }

    public void setDocount(Integer docount) {
        this.docount = docount;
    }
}
