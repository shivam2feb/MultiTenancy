package com.mfsi.appbuilder.master.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mfsi.appbuilder.master.document.User;

@Repository
public interface UserDao extends MongoRepository<User, Integer> {

    User findByUsername(String username);
}
