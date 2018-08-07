package com.richinfo.annotation.repository;

import com.richinfo.annotation.po.Record;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecordRepository extends MongoRepository<Record, String> {

    Record findByFilenameAndCasename(String filename,String casename);

}
