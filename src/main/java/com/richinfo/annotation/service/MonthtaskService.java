package com.richinfo.annotation.service;

import com.richinfo.annotation.po.Monthtask;
import com.richinfo.annotation.po.Sysuser;

import java.util.List;

public interface MonthtaskService {
    void addMonthtaskTasknum(Sysuser sysuser,Long adddonum);
    void addMonthtaskDonum(Sysuser sysuser,Long adddonum);
    void addMonthtaskSuccessnum(Sysuser sysuser,Long addsuccessnum);
    void addMonthtaskFailnum(Sysuser sysuser,Long addfailnum);

    List<Monthtask> findMonthtaskList(String month);
    Monthtask findByMonthAndSysuser(String month,Sysuser sysuser);
}
