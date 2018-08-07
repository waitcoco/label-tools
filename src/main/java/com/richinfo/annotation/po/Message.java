package com.richinfo.annotation.po;

import org.springframework.data.annotation.Id;

public class Message {
    @Id
    private String id;
    private Sysuser sender;//发送者
    private Sysuser receiver;//接受者
    private String title;
    private String msg;//消息
    private String sendtime;//发送时间
    private String readtime;//查看时间
    private int status;//状态（0未读，1已读）

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Sysuser getSender() {
        return sender;
    }

    public void setSender(Sysuser sender) {
        this.sender = sender;
    }

    public Sysuser getReceiver() {
        return receiver;
    }

    public void setReceiver(Sysuser receiver) {
        this.receiver = receiver;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getReadtime() {
        return readtime;
    }

    public void setReadtime(String readtime) {
        this.readtime = readtime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
