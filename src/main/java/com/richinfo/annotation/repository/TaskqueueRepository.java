package com.richinfo.annotation.repository;

import com.richinfo.annotation.po.Taskqueue;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskqueueRepository extends MongoRepository<Taskqueue, String> {

     Taskqueue findByBatchno(String batchno);

}
