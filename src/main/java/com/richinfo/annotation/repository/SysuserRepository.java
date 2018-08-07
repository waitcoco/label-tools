package com.richinfo.annotation.repository;

import com.richinfo.annotation.po.Sysuser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;

public interface SysuserRepository extends MongoRepository<Sysuser, String> {

 public Sysuser findByUsername(String username);

 public List<Sysuser> findByRoleInAndStatus(List<String> rolelist,Integer status);
 public List<Sysuser> findByRoleAndStatus(String role,Integer status);
 public List<Sysuser> findByRole(String role);


}
