package com.richinfo.annotation.po;

/**
 * 前科
 */
public class Preconviction {

    private String starttime;//时间
    private String place;//判刑地点
    private String term;//刑期
    private String reason;//原因

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
