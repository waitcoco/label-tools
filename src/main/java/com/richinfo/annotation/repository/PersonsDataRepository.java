package com.richinfo.annotation.repository;

import com.richinfo.annotation.po.CarsData;
import com.richinfo.annotation.po.PersonsData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PersonsDataRepository extends MongoRepository<PersonsData, String> {

    PersonsData findByTaskno(String taskno);
    List<PersonsData> findByTasknoIn(List<String> tasknos);
    List<PersonsData> findByTaskbatchno(String batchno);
    PersonsData findByTaskbatchnoAndAndFilename(String batchno, String filename);
}
