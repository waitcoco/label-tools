package com.richinfo.annotation.repository;

import com.richinfo.annotation.po.Segment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SegmentRepository extends MongoRepository<Segment, String> {
    Segment findByTaskno(String taskno);
    List<Segment> findByTasknoIn(List<String> tasknolist);

}
