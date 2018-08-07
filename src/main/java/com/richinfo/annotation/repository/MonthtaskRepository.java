package com.richinfo.annotation.repository;

import com.richinfo.annotation.po.Monthtask;
import com.richinfo.annotation.po.Sysuser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MonthtaskRepository extends MongoRepository<Monthtask, String> {

     Monthtask findByMonthAndSysuser(String month, Sysuser sysuser);
     List<Monthtask> findByMonth(String month);
}
