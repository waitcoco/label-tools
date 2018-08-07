package com.richinfo.util;

public class Variable {
    public static String SESSION_SYSUSER="ssysuser";

    public static  String USER_ROLE_MANAGER ="1";//管理员
    public static  String USER_ROLE_NORMAL ="2";//标注员
    public static  String USER_ROLE_FIRSTCHECKER ="3";//初检员
    public static  String USER_ROLE_SECONDCHECKER ="4";//复检员

    public static int USER_STATUS_NORMAL=1;//正常
    public static int USER_STATUS_PAUSE=2;//暂停

    public static int TASKBATCH_STATUS_WAITPUB =0;//待发布
    public static int TASKBATCH_STATUS_PUB =1;//已发布
    public static int TASKBATCH_STATUS_FINISH =2;//已完成
    public static int TASKBATCH_STATUS_PAUSE =3;//暂停
    public static int TASKBATCH_STATUS_OVERTIME =4;//超时
    public static int TASKBATCH_STATUS_CHECKED =5;//审核完成

    public static int TASK_STATUS_DOING =0;//未完成
    public static int TASK_STATUS_SUBMIT =1;//已提交
    public static int TASK_STATUS_FIRSTCHECK =2;//已初检
    public static int TASK_STATUS_FINISH =3;//复检完成
    public static int TASK_STATUS_PAUSE =4;//暂停
    public static int TASK_STATUS_OVERTIME =5;//超时

    public static String TASK_TYPE_RECORD="1";//笔录
    public static String TASK_TYPE_CARINFO="2";//车辆信息


    public static int MSG_STATUS_NEW =0;//未读
    public static int MSG_STATUS_READ =1;//已读
}
