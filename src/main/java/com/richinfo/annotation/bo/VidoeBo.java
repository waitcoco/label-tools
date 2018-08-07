package com.richinfo.annotation.bo;

import com.richinfo.annotation.po.Segment;
import lombok.Data;

import java.util.List;

@Data
public class VidoeBo {
    private String vidoeId;
    private List<Segment> segments_info;
}
