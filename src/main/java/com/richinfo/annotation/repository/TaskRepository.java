package com.richinfo.annotation.repository;

import com.richinfo.annotation.po.Sysuser;
import com.richinfo.annotation.po.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {

     List<Task> findBySysuser(Sysuser sysuser);
     List<Task> findBySysuserAndStatus(Sysuser sysuser,int status);

     Task findByTaskno(String taskno);
     List<Task> findByBatchnoAndStatus(String batchno,int status);
     List<Task> findByStatus(int status);
     List<Task> findByBatchnoAndSysuserAndStatus(String batchno,Sysuser sysuser,int status);
     List<Task> findByFirstcheckerAndStatusIn(Sysuser sysuser,List<Integer> status);
     Page<Task> findByFirstcheckerAndStatus(Sysuser sysuser, int status, Pageable pageable);

     List<Task> findBySecondcheckerAndStatus(Sysuser sysuser,int status);
     Page<Task> findBySecondcheckerAndStatus(Sysuser sysuser,int status, Pageable pageable);

     List<Task> findByBatchnoAndFirstresultAndSysuser(String batchno,int firstresult,Sysuser sysuser);
     List<Task> findByBatchnoAndFirstresultLessThanAndSysuser(String batchno,int firstresult,Sysuser sysuser);

     List<Task> findByBatchno(String taskbatchno);

}
