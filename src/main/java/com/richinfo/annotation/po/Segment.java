package com.richinfo.annotation.po;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class Segment {
    @Id
    private String id;
    private String taskno;//任务编号
    private String filename;//文件名
    private String packagename;//路径
    private String taskbatchno;
    private String time;
    private List<Frame> framesInfo;
}
