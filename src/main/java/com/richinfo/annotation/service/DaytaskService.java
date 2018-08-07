package com.richinfo.annotation.service;

import com.richinfo.annotation.po.Daytask;
import com.richinfo.annotation.po.Sysuser;

import java.util.List;

public interface DaytaskService {
    void addDaytaskTasknum(Sysuser sysuser, Long adddonum);
    void addDaytaskDonum(Sysuser sysuser, Long adddonum);
    void addDaytaskSuccessnum(Sysuser sysuser, Long addsuccessnum);
    void addDaytaskFailnum(Sysuser sysuser, Long addfailnum);

    List<Daytask> findDaytaskList(String month);

    Daytask findByDayAndSysuser(String day,Sysuser sysuser);
}
