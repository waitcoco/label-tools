package com.richinfo.annotation.repository;

import com.richinfo.annotation.po.Taskbatch;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskbatchRepository extends MongoRepository<Taskbatch, String> {

  Taskbatch findByBatchno(String batchno);
  List<Taskbatch> findByStatus(Integer status);

}
