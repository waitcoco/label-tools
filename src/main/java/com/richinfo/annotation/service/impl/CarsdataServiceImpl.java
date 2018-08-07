package com.richinfo.annotation.service.impl;

import com.richinfo.annotation.service.CarsdataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class CarsdataServiceImpl implements CarsdataService {

    @Autowired
    private MongoTemplate mongoTemplate ;



}
