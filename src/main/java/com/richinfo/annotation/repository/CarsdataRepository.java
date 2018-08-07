package com.richinfo.annotation.repository;

import com.richinfo.annotation.po.Carbrand;
import com.richinfo.annotation.po.CarsData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CarsdataRepository extends MongoRepository<CarsData, String> {

    CarsData findByTaskno(String taskno);
    List<CarsData> findByTasknoIn(List<String> tasknos);
    List<CarsData> findByTaskbatchno(String batchno);
    CarsData findByTaskbatchnoAndAndFilename(String batchno,String filename);
}
