package com.richinfo.annotation.po;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Taskbatch {
    @Id
    private String id;
    private String title;//任务标题
    private String batchno;//批次号（byyMMddHHmmssSSS+4位随机数）
    private String filepath;//目标文件路径
    private String type;//类型（1：笔录，2：车辆图片识别）
    private String descr;//任务描述
    private String createtime;//创建时间
    private Integer filecounts;//文件数量
    private Integer status;//状态（0：待发布，1：已发布，2：已完成,3:暂停，4：已经超时）
    private Integer usercounts;//用户数量
    private Long finishcounts;//完成数量
    private List<String[]> filenames;//[文件夹名,文件名]；（绝对路径）
    private Integer price;//单价（单位分）按件计费时使用
    private String starttime;//开始时间（定时任务启用）
    private String endtime;//最晚提交时间（限时任务启用）
    private String extend;//文件扩张名
    private String overtime;//队列取空时间（10分钟后结束任务）
    private String prebatchno;//继承任务批次号


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }



    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public List<String[]> getFilenames() {
        return filenames;
    }

    public void setFilenames(List<String[]> filenames) {
        this.filenames = filenames;
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

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }



    public Long getFinishcounts() {
        return finishcounts;
    }

    public void setFinishcounts(Long finishcounts) {
        this.finishcounts = finishcounts;
    }

    public Integer getFilecounts() {
        return filecounts;
    }

    public void setFilecounts(Integer filecounts) {
        this.filecounts = filecounts;
    }

    public Integer getUsercounts() {
        return usercounts;
    }

    public void setUsercounts(Integer usercounts) {
        this.usercounts = usercounts;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getOvertime() {
        return overtime;
    }

    public void setOvertime(String overtime) {
        this.overtime = overtime;
    }

    public String getPrebatchno() {
        return prebatchno;
    }

    public void setPrebatchno(String prebatchno) {
        this.prebatchno = prebatchno;
    }
}
