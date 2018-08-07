package com.richinfo.annotation.repository;

import com.richinfo.annotation.po.CarsData;
import com.richinfo.annotation.po.Textdata;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TextdataRepository extends MongoRepository<Textdata, String> {

    Textdata findByTaskno(String taskno);
    List<Textdata> findByTasknoIn(List<String> tasknos);
}
