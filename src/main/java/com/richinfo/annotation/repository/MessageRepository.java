package com.richinfo.annotation.repository;

import com.richinfo.annotation.po.Message;
import com.richinfo.annotation.po.Sysuser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {

     List<Message> findBySender(Sysuser sender);
     List<Message> findByReceiver(Sysuser receiver);
     List<Message> findByReceiverAndStatus(Sysuser receiver,int status);
}
