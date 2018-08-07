package com.richinfo.annotation.repository;

import com.richinfo.annotation.po.Carpic;
import com.richinfo.annotation.po.Sysuser;
import com.richinfo.annotation.po.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CarpicRepository extends MongoRepository<Carpic, String> {

     Carpic findByTaskno(String taskno);
     List<Carpic> findByTaskbatchno(String taskbatchno);
}
