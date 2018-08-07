package com.richinfo.annotation.po;

import org.springframework.data.annotation.Id;

public class Task {
    @Id
    private String id;
    private String taskno;//任务编号(tyyMMddHHmmssSSS+4位随机数)
    private String batchno;//任务批次号
    private String title;//任务标题
    private String filename;//文件路径（绝对路径）
    private String folder;//文件夹名
    private Sysuser sysuser;//接收用户
    private String createtime;//接收时间
    private String submittime;//提交时间
    private String finishtime;//完成时间
    private String type;//类型
    private Integer status;//状态（0：待完成，1:已提交，2：初检完成，3：复检完成，4：已暂停，5：已超时）

    private Sysuser firstchecker;//初检员
    private Sysuser secondchecker;//复检员
    private String firstchecktime;//初审时间
    private Integer firstresult;//初检结果（0未通过，1通过）
    private Integer secondresult;//复检结果（0未通过，1通过）
    private BasePo data;//数据

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

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Sysuser getSysuser() {
        return sysuser;
    }

    public void setSysuser(Sysuser sysuser) {
        this.sysuser = sysuser;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getSubmittime() {
        return submittime;
    }

    public void setSubmittime(String submittime) {
        this.submittime = submittime;
    }

    public String getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(String finishtime) {
        this.finishtime = finishtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Sysuser getFirstchecker() {
        return firstchecker;
    }

    public void setFirstchecker(Sysuser firstchecker) {
        this.firstchecker = firstchecker;
    }

    public Sysuser getSecondchecker() {
        return secondchecker;
    }

    public void setSecondchecker(Sysuser secondchecker) {
        this.secondchecker = secondchecker;
    }

    public Integer getFirstresult() {
        return firstresult;
    }

    public void setFirstresult(Integer firstresult) {
        this.firstresult = firstresult;
    }

    public Integer getSecondresult() {
        return secondresult;
    }

    public void setSecondresult(Integer secondresult) {
        this.secondresult = secondresult;
    }

    public String getFirstchecktime() {
        return firstchecktime;
    }

    public void setFirstchecktime(String firstchecktime) {
        this.firstchecktime = firstchecktime;
    }

    public BasePo getData() {
        return data;
    }

    public void setData(BasePo data) {
        this.data = data;
    }
}
