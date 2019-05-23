package com.mfsi.appbuilder.repository;


import com.mfsi.appbuilder.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends MongoRepository<User, Integer> {

    User findByUsername(String username);
}
