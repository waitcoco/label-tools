package com.richinfo.annotation.service.impl;

import com.richinfo.annotation.po.Monthtask;
import com.richinfo.annotation.po.Sysuser;
import com.richinfo.annotation.repository.MonthtaskRepository;
import com.richinfo.annotation.service.DaytaskService;
import com.richinfo.annotation.service.MonthtaskService;
import com.richinfo.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonthtaskServiceImpl  implements MonthtaskService {
    @Autowired
    MonthtaskRepository monthtaskRepository;
    @Autowired
    DaytaskService daytaskService;

    @Override
    public Monthtask findByMonthAndSysuser(String month,Sysuser sysuser) {
        return monthtaskRepository.findByMonthAndSysuser(month,sysuser);
    }

    @Override
    public void addMonthtaskTasknum(Sysuser sysuser, Long addnum) {
        Monthtask monthtask = monthtaskRepository.findByMonthAndSysuser(DateTimeUtils.getDate("yyyy-MM"),sysuser);
        if(monthtask!=null){
            if(monthtask.getTasknum()==null){
                monthtask.setTasknum(addnum);
            }else{
                long num = monthtask.getTasknum();
                monthtask.setTasknum(num+addnum);
            }
        }else{
            monthtask = new Monthtask();
            monthtask.setMonth(DateTimeUtils.getDate("yyyy-MM"));
            monthtask.setSysuser(sysuser);
            monthtask.setTasknum(addnum);
        }
        monthtaskRepository.save(monthtask);
        daytaskService.addDaytaskTasknum(sysuser,addnum);
    }

    @Override
    public void addMonthtaskDonum(Sysuser sysuser, Long adddonum) {
        Monthtask monthtask = monthtaskRepository.findByMonthAndSysuser(DateTimeUtils.getDate("yyyy-MM"),sysuser);
        if(monthtask!=null){
            if(monthtask.getDonum()==null){
                monthtask.setDonum(adddonum);
            }else{
                long donum = monthtask.getDonum();
                monthtask.setDonum(donum+adddonum);
            }
        }else{
            monthtask = new Monthtask();
            monthtask.setMonth(DateTimeUtils.getDate("yyyy-MM"));
            monthtask.setSysuser(sysuser);
            monthtask.setDonum(adddonum);
        }
        monthtaskRepository.save(monthtask);
        daytaskService.addDaytaskDonum(sysuser,adddonum);
    }

    @Override
    public void addMonthtaskSuccessnum(Sysuser sysuser, Long addsuccessnum) {
        Monthtask monthtask = monthtaskRepository.findByMonthAndSysuser(DateTimeUtils.getDate("yyyy-MM"),sysuser);
        if(monthtask!=null){
            if(monthtask.getSuccessnum()==null){
                monthtask.setSuccessnum(addsuccessnum);
            }else{
                long successnum = monthtask.getSuccessnum();
                monthtask.setSuccessnum(successnum+addsuccessnum);
            }
        }else{
            monthtask = new Monthtask();
            monthtask.setMonth(DateTimeUtils.getDate("yyyy-MM"));
            monthtask.setSysuser(sysuser);
            monthtask.setSuccessnum(addsuccessnum);
        }
        monthtaskRepository.save(monthtask);
        daytaskService.addDaytaskSuccessnum(sysuser,addsuccessnum);
    }

    @Override
    public void addMonthtaskFailnum(Sysuser sysuser, Long addfailnum) {
        Monthtask monthtask = monthtaskRepository.findByMonthAndSysuser(DateTimeUtils.getDate("yyyy-MM"),sysuser);
        if(monthtask!=null){
            if(monthtask.getFailnum()==null){
                monthtask.setFailnum(addfailnum);
            }else{
                long failsnum = monthtask.getFailnum();
                monthtask.setFailnum(failsnum+addfailnum);
            }
        }else{
            monthtask = new Monthtask();
            monthtask.setMonth(DateTimeUtils.getDate("yyyy-MM"));
            monthtask.setSysuser(sysuser);
            monthtask.setFailnum(addfailnum);
        }
        monthtaskRepository.save(monthtask);
        daytaskService.addDaytaskFailnum(sysuser,addfailnum);
    }

    @Override
    public List<Monthtask> findMonthtaskList(String month) {
        return monthtaskRepository.findByMonth(month);
    }

}
