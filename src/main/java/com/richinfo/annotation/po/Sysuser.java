package com.richinfo.annotation.po;

import org.springframework.data.annotation.Id;

public class Sysuser {
    @Id
    private String id;

    private String username;
    private String password;
    private String name;
    private String phone;
    private String createtime;
    private String lastlogin;
    private String idnumber;
    private String role;//角色（1：管理员，2标注专员,3初检员，4复检员）
    private Integer tasknum;//所有任务数
    private Integer donum;//正在做的任务数
    private Integer finishnum;//完成任务数
    private Integer status;//状态（1正常，0暂停）；
    private Integer errornum;//错误数量
    private Integer checkerrornum;//初检错误数
    private Integer checknum;//检查数
    public Sysuser(){

    }

    public Sysuser(String id, String username, String password, String name) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(String lastlogin) {
        this.lastlogin = lastlogin;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getTasknum() {
        return tasknum;
    }

    public void setTasknum(Integer tasknum) {
        this.tasknum = tasknum;
    }

    public Integer getDonum() {
        return donum;
    }

    public void setDonum(Integer donum) {
        this.donum = donum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getFinishnum() {
        return finishnum;
    }

    public void setFinishnum(Integer finishnum) {
        this.finishnum = finishnum;
    }

    @Override
    public String toString() {
        return String.format(
                "Sysuser[id=%s, username='%s', name='%s', phone='%s']",
                id, username, name,phone);
    }

    public Integer getErrornum() {
        return errornum;
    }

    public void setErrornum(Integer errornum) {
        this.errornum = errornum;
    }

    public Integer getCheckerrornum() {
        return checkerrornum;
    }

    public void setCheckerrornum(Integer checkerrornum) {
        this.checkerrornum = checkerrornum;
    }

    public Integer getChecknum() {
        return checknum;
    }

    public void setChecknum(Integer checknum) {
        this.checknum = checknum;
    }
}
