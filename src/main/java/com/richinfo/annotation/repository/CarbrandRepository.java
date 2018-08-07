package com.richinfo.annotation.repository;

import com.richinfo.annotation.po.Carbrand;
import com.richinfo.annotation.po.Message;
import com.richinfo.annotation.po.Sysuser;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CarbrandRepository extends MongoRepository<Carbrand, String> {

    Carbrand findByBrand(String brand);
}
