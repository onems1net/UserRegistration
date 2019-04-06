package com.pccwglobal.repository;

import com.pccwglobal.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
    User findByEmail(String email);
}
