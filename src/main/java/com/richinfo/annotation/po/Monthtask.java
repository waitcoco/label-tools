package com.richinfo.annotation.po;

import org.springframework.data.annotation.Id;

public class Monthtask {
    @Id
    private String id;

    private String month;//月份（yyyy-MM）
    private Sysuser sysuser;//用户
    private Long tasknum;//收到任务数
    private Long donum;//完成数
    private Long successnum;//成功数
    private Long failnum;//失败数
    private Double failrate;//失败率

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Sysuser getSysuser() {
        return sysuser;
    }

    public void setSysuser(Sysuser sysuser) {
        this.sysuser = sysuser;
    }

    public Long getSuccessnum() {
        return successnum;
    }

    public void setSuccessnum(Long successnum) {
        this.successnum = successnum;
    }

    public Long getFailnum() {
        return failnum;
    }

    public void setFailnum(Long failnum) {
        this.failnum = failnum;
    }

    public Long getTasknum() {
        return tasknum;
    }

    public void setTasknum(Long tasknum) {
        this.tasknum = tasknum;
    }

    public Long getDonum() {
        return donum;
    }

    public void setDonum(Long donum) {
        this.donum = donum;
    }

    public Double getFailrate() {
        return failrate;
    }

    public void setFailrate(Double failrate) {
        this.failrate = failrate;
    }
}
