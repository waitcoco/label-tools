package com.richinfo.annotation.po;

import lombok.Data;

@Data
public class Frame {
    private Boolean alert;
    private String objectLefttop;
    private String objectLeftbottom;
    private PersonBag object;
    private String objectRighttop;
    private String objectRightbottom;
}
