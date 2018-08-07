package com.richinfo.annotation.repository;

import com.richinfo.annotation.po.Sysuser;
import com.richinfo.annotation.po.TaskbatchUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskbatchUserRepository extends MongoRepository<TaskbatchUser, String> {

  TaskbatchUser findByUserAndTaskbatchno(Sysuser sysuser,String taskbatchno);

  List<TaskbatchUser> findByUser(Sysuser user);

}
