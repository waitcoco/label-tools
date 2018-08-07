package com.richinfo.annotation.service.impl;

import com.richinfo.annotation.po.Daytask;
import com.richinfo.annotation.po.Monthtask;
import com.richinfo.annotation.po.Sysuser;
import com.richinfo.annotation.repository.DaytaskRepository;
import com.richinfo.annotation.repository.MonthtaskRepository;
import com.richinfo.annotation.service.DaytaskService;
import com.richinfo.annotation.service.MonthtaskService;
import com.richinfo.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DaytaskServiceImpl implements DaytaskService {
    @Autowired
    DaytaskRepository daytaskRepository;

    @Override
    public void addDaytaskTasknum(Sysuser sysuser, Long addnum) {
        Daytask daytask = daytaskRepository.findByDayAndSysuser(DateTimeUtils.getDate("yyyy-MM-dd"),sysuser);
        if(daytask!=null){
            if(daytask.getTasknum()==null){
                daytask.setTasknum(addnum);
            }else{
                long num = daytask.getTasknum();
                daytask.setTasknum(num+addnum);
            }
        }else{
            daytask = new Daytask();
            daytask.setDay(DateTimeUtils.getDate("yyyy-MM-dd"));
            daytask.setSysuser(sysuser);
            daytask.setTasknum(addnum);
        }
        daytaskRepository.save(daytask);
    }

    @Override
    public void addDaytaskDonum(Sysuser sysuser, Long adddonum) {
        Daytask daytask = daytaskRepository.findByDayAndSysuser(DateTimeUtils.getDate("yyyy-MM-dd"),sysuser);
        if(daytask!=null){
            if(daytask.getDonum()==null){
                daytask.setDonum(adddonum);
            }else{
                long donum = daytask.getDonum();
                daytask.setDonum(donum+adddonum);
            }
        }else{
            daytask = new Daytask();
            daytask.setDay(DateTimeUtils.getDate("yyyy-MM-dd"));
            daytask.setSysuser(sysuser);
            daytask.setDonum(adddonum);
        }
        daytaskRepository.save(daytask);
    }

    @Override
    public void addDaytaskSuccessnum(Sysuser sysuser, Long addsuccessnum) {
        Daytask daytask = daytaskRepository.findByDayAndSysuser(DateTimeUtils.getDate("yyyy-MM-dd"),sysuser);
        if(daytask!=null){
            if(daytask.getSuccessnum()==null){
                daytask.setSuccessnum(addsuccessnum);
            }else{
                long successnum = daytask.getSuccessnum();
                daytask.setSuccessnum(successnum+addsuccessnum);
            }
        }else{
            daytask = new Daytask();
            daytask.setDay(DateTimeUtils.getDate("yyyy-MM-dd"));
            daytask.setSysuser(sysuser);
            daytask.setSuccessnum(addsuccessnum);
        }
        daytaskRepository.save(daytask);
    }

    @Override
    public void addDaytaskFailnum(Sysuser sysuser, Long addfailnum) {
        Daytask daytask = daytaskRepository.findByDayAndSysuser(DateTimeUtils.getDate("yyyy-MM-dd"),sysuser);
        if(daytask!=null){
            if(daytask.getFailnum()==null){
                daytask.setFailnum(addfailnum);
            }else{
                long failsnum = daytask.getFailnum();
                daytask.setFailnum(failsnum+addfailnum);
            }
        }else{
            daytask = new Daytask();
            daytask.setDay(DateTimeUtils.getDate("yyyy-MM-dd"));
            daytask.setSysuser(sysuser);
            daytask.setFailnum(addfailnum);
        }
        daytaskRepository.save(daytask);
    }

    @Override
    public List<Daytask> findDaytaskList(String day) {

        return daytaskRepository.findByDay(day);
    }

    @Override
    public Daytask findByDayAndSysuser(String day,Sysuser sysuser) {
        return daytaskRepository.findByDayAndSysuser(day,sysuser);
    }
}
