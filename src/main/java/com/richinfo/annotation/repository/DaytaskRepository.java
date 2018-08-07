package com.richinfo.annotation.repository;

import com.richinfo.annotation.po.Daytask;
import com.richinfo.annotation.po.Sysuser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DaytaskRepository extends MongoRepository<Daytask, String> {

     Daytask findByDayAndSysuser(String day,Sysuser sysuser);
     List<Daytask> findByDay(String day);

}
